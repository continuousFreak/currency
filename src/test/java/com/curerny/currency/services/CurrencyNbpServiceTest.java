package com.curerny.currency.services;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class CurrencyNbpServiceTest {


    @Test
    public void should_connect_with_nbp_api() throws IOException {
        String nbpApiLink = "http://api.nbp.pl/api/exchangerates/rates/A/EUR/2019-07-08?format=json";
        URL url = new URL(nbpApiLink);
        url.openConnection().getInputStream();
    }

    /**
     * http://api.nbp.pl/api/exchangerates/rates/A/EUR/2019-07-10?format=json
     * {
     * "table": "A",
     * "currency": "euro",
     * "code": "EUR",
     * "rates": [
     * {
     * "no": "132/A/NBP/2019",
     * "effectiveDate": "2019-07-10",
     * "mid": 4.2729
     * }
     * ]
     * }
     */
    @Test
    public void should_get_correct_response_from_nbp() {
        //given
        CurrencyService service = new CurrencyNbpService();
        LocalDate julyTenth = LocalDate.of(2019, 7, 10);
        Currency eurCurrency = Currency.EUR;

        //when
        CurrencyResponse response = service.getResponse(eurCurrency, julyTenth);

        //then
        CurrencyResponse expectedResponse = new CurrencyResponse(
                Currency.EUR,
                BigDecimal.valueOf(4.2729),
                LocalDate.of(2019, 7, 10),
                "132/A/NBP/2019"
        );

        assertEquals(expectedResponse, response);
    }


    /**
     * http://api.nbp.pl/api/exchangerates/rates/A/GBP/2019-07-05?format=json
     * {
     * "table": "A",
     * "currency": "funt szterling",
     * "code": "GBP",
     * "rates": [
     * {
     * "no": "129/A/NBP/2019",
     * "effectiveDate": "2019-07-05",
     * "mid": 4.7319
     * }
     * ]
     * }
     */
    @Test
    public void should_get_effective_rate_for_sunday() {
        //given
        CurrencyService service = new CurrencyNbpService();
        LocalDate sunday = LocalDate.of(2019, 7, 7);  // Sunday
        Currency pound = Currency.GBP;

        //when
        CurrencyResponse response = service.getResponse(pound, sunday);


        //then
        CurrencyResponse expectedResponse = new CurrencyResponse(
                Currency.GBP,
                BigDecimal.valueOf(4.7319),
                LocalDate.of(2019, 7, 5),  // Friday 05 -first effective day before Sunday 07
                "129/A/NBP/2019"
        );

        assertEquals(expectedResponse, response);
    }


    /**
     * http://api.nbp.pl/api/exchangerates/rates/A/EUR/2019-07-07?format=json
     * 404 NotFound - Not Found - Brak danych
     * It is because NBP doesn't publish on Sat, Sun & Polish bank holidays
     */
    @Test(expected = FileNotFoundException.class)
    public void should_throw_file_not_found_exception() throws IOException {
        String nbpApiLink = "http://api.nbp.pl/api/exchangerates/rates/A/EUR/2019-07-07?format=json";  // Sunday!

        URL url = new URL(nbpApiLink);
        url.openConnection().getInputStream();
    }

}