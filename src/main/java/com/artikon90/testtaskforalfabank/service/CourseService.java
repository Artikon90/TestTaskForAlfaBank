package com.artikon90.testtaskforalfabank.service;

import com.artikon90.testtaskforalfabank.feign.FeignClientExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CourseService {

    @Value("${integration.exchange.key}")
    private String apiKey;
    private final FeignClientExchange feignClientExchange;

    @Autowired
    public CourseService(FeignClientExchange exchange) {
        this.feignClientExchange = exchange;
    }

    public Double getDifferenceRate(String currency) {
        var curr = feignClientExchange.getAllCurrency();
        if (!curr.containsKey(currency))
            return -999999d;
        StringBuilder yesterday = new StringBuilder(LocalDate.now().minusDays(1).toString());
        Map<String, Double> yesterdayCourse = feignClientExchange.getYesterday(yesterday.toString(), apiKey)
                .getRates();
        Map<String, Double> todayCourse = feignClientExchange.getToday(apiKey).getRates();
        Double yesterdayRate = yesterdayCourse.get(currency);
        Double todayRate = todayCourse.get(currency);
        return todayRate - yesterdayRate;
    }
}
