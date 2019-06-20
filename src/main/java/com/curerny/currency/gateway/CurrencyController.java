package com.curerny.currency.gateway;

import com.curerny.currency.services.Currency;
import com.curerny.currency.services.CurrencyResponse;
import com.curerny.currency.services.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/currency/{currency}/{date}")
    public CurrencyResponse getResponse(@PathVariable Currency currency, @PathVariable String date) {

        CurrencyResponse response = currencyService.getResponse(currency, LocalDate.parse(date));
        return response;
    }


    @GetMapping("/")
    public CurrencyResponse homePage() {

        Currency currency = Currency.EUR;
        LocalDate localDate = LocalDate.now();

        CurrencyResponse response = currencyService.getResponse(currency, localDate);
        return response;
    }



}
