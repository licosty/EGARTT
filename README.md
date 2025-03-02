# Тестовое задание

1. Реализовать интеграцию с параметрами:

- TimeExpression – Таймер обработки данных;

- FilesPath – Директория с файлами json;

2. По таймеру TimeExpression интеграция должна отслеживать появление json-файлов в директории FilesPath.
После начала обработки файл перемещается в папку «processed».  
Далее интеграция должна преобразовывать данные из файла в формат JSON и отправлять на адрес REST-сервиса LM http://RestURL/api/v1 в метод PUT на URL **/data/testdata**  
Если Rest сервис присылает в ответ ошибку (code <> 200), то файл необходимо перенести в папку «failed».  
Если json-сообщение обработано успешно (code = 200), но в ответе от Rest сервиса есть ошибки ("success": false), то нужно подготовить html файл с ответом от Rest сервиса.


3. Маппинг для отправки в Rest сервис:
```json
[
  {
    "id": "string",
    "date": "2024-11-14",
    "currency1": "string",
    "currency2": "string",
    "periodNumber": 1,
    "periodUnit": "string",
    "positiveCoefficient": 1.02,
    "negativeCoefficient": 0.98
  }
]
```
***
Использованные технологии:
- [Java 17]
- [Maven]
- [Spring Boot]
- [Apache Camel]

---
![[java 17]](https://img.shields.io/static/v1?label=Java&message=17&color=007396&style=for-the-badge&logo=java)
![[maven]](https://img.shields.io/static/v1?label=Maven&message=3.6&color=C71A36&style=for-the-badge&logo=apachemaven)
![[spring boot]](https://img.shields.io/static/v1?label=Spring+Boot&message=3.4.2&color=6DB33F&style=for-the-badge&logo=springboot)
![[apache camel]](https://img.shields.io/static/v1?label=Apache+Camel&message=4.3.0&color=e97826&style=for-the-badge&logo=apachecamel)

[java 17]:<https://www.oracle.com/ru/java/technologies/javase-downloads.html>
[maven]:<https://maven.apache.org/download.cgi>
[spring boot]:<https://www.baeldung.com/spring-with-maven>
[apache camel]:<https://camel.apache.org>