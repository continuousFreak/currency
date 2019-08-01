package com.curerny.currency.services;

import datadog.trace.api.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {

    private static Logger LOG = LoggerFactory.getLogger(ScheduledService.class);


    @Scheduled(fixedDelay = 5000) //5s
    @Trace(operationName = "opration nazwa", resourceName = "WAZZUPPP")
    public void wazzupMethod() {
        LOG.info("WAZZUP");
    }

}
