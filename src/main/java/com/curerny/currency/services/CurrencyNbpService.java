package com.curerny.currency.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

@Component
public class CurrencyNbpService implements CurrencyService {


    @Override
    public CurrencyResponse getResponse(Currency currency, LocalDate date) {
        String nbpApilink = "http://api.nbp.pl/api/exchangerates/rates/A/" + currency +  "/" + date + "?format=json";

        try {
            URL url = new URL(nbpApilink);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String jsonString = bufferedReader.readLine();

            bufferedReader.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonString);

            String rateString = node
                    .get("rates")
                    .get(0)
                    .get("mid")
                    .asText();

            LocalDate effectiveDate = LocalDate.parse(node
                    .get("rates")
                    .get(0)
                    .get("effectiveDate")
                    .asText());

            String tableNo = node
                    .get("rates")
                    .get(0)
                    .get("no")
                    .asText();

            BigDecimal rate = new BigDecimal(rateString);

            return new CurrencyResponse(rate, effectiveDate, tableNo);
        }

        catch (FileNotFoundException ex) {
            return getResponse(currency, date.minusDays(1));
        }

        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
