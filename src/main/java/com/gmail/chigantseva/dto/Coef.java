package com.gmail.chigantseva.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record Coef(
        @JsonProperty("Date")
        Date date,
        @JsonProperty("Currency_1")
        String currency1,
        @JsonProperty("Currency_2")
        String currency2,
        @JsonProperty("Period_Number")
        int periodNumber,
        @JsonProperty("Period_Unit_Code")
        String periodUnitCode,
        @JsonProperty("Positive_Coefficient")
        double positiveCoefficient,
        @JsonProperty("Negative_Coefficient")
        double negativeCoefficient,
        @JsonProperty("External_ID")
        String id
) {
}
