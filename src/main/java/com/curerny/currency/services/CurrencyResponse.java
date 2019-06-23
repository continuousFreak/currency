package com.curerny.currency.services;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;


@Value
public class CurrencyResponse {

    private final Currency currency;
    private final BigDecimal rate;
    private final LocalDate effectiveDate;
    private final String tableNo;
}
