package com.gmail.chigantseva.route;

import com.gmail.chigantseva.dto.RequestBody;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import org.springframework.stereotype.Component;

import static org.apache.camel.support.builder.PredicateBuilder.and;

@Component
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .handled(false)
                .log(LoggingLevel.ERROR, "Ошибка обработки - ${exchangeProperty." + Exchange.EXCEPTION_CAUGHT + "}")
                .log(LoggingLevel.ERROR, "Файл перемещен в директорию {{failDir}}")
                .end();


        from("file:{{FilesPath}}?delay={{TimeExpression}}&include={{fileFilters}}&noop=false&move={{targetDir}}&moveFailed={{failDir}}")
                .routeId("FileRoute")
                .log("Старт обработки файла: ${file:name}")
                .log("${body}")
                .unmarshal().json(JsonLibrary.Jackson, RequestBody.class)
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .log("Файл обработан. Запрос в сервис")

                .to("http://{{RestURL}}/data/testdata?bridgeEndpoint=true")
                .log("Получен код ответа от сервера - ${header.CamelHttpResponseCode}")

                .choice()
                    .when(and(header("CamelHttpResponseCode").isEqualTo(200), jsonpath("$[?(@.success == false)]")))
                        .log("Success is not true. Формируем html-отчет")
                        .process(exchange -> {
                            String title = "Report from service";
                            String body = exchange.getIn().getBody(String.class);

                            String htmlReport =
                                    "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                                    "<title>$title</title></head>" +
                                    "<body>$body</body></html>";

                            htmlReport = htmlReport.replace("$title", title);
                            htmlReport = htmlReport.replace("$body", body);

                            String fileName = "report_" + exchange.getIn().getHeader("CamelFileName", String.class).replace(".json", ".html");

                            exchange.getIn().setHeader("CamelFileName", fileName);
                            exchange.getIn().setBody(htmlReport);
                        })
                        .to("file:{{reportDir}}")
                        .log("html-отчет сформирован и находится в директории {{reportDir}}");
    }
}
