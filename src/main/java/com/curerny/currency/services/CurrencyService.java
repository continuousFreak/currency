package com.curerny.currency.services;

import java.time.LocalDate;

public interface CurrencyService {

    CurrencyResponse getResponse(Currency currency, LocalDate date);

}
