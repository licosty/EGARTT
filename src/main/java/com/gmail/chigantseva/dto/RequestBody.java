package com.gmail.chigantseva.dto;

import java.util.ArrayList;

public record RequestBody (
        ArrayList<Coef> FXRateCorrectionCoef
) {}
