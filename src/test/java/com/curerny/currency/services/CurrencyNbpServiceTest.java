package com.curerny.currency.services;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CurrencyNbpServiceTest {


    @Test
    public void should_bla() {
        //given
        CurrencyService service = new CurrencyNbpService();


        //when
        CurrencyResponse response = service.getResponse(Currency.EUR, LocalDate.now());


        //then
        System.out.println(response.getRate().doubleValue());
        assertTrue(response.getRate().doubleValue() > 2);



    }

}