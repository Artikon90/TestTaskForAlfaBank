package com.artikon90.testtaskforalfabank.service;

import com.artikon90.testtaskforalfabank.feign.FeignClientExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

@Service
public class CourseService {

    @Value("${integration.exchange.key}")
    private String apiKey;
    private final FeignClientExchange feignClientExchange;
    private final Calendar cal;

    @Autowired
    public CourseService(FeignClientExchange exchange) {
        this.cal = Calendar.getInstance();
        this.feignClientExchange = exchange;
    }

    public Double getDifferenceRate(String currency) {
        cal.add(Calendar.DATE, -1);
        DateFormat yesterday = new SimpleDateFormat("yyyy-MM-dd");
        yesterday.format(cal.getTime());
        Map<String, Double> yesterdayCourse = feignClientExchange.getYesterday(yesterday.toString(), apiKey)
                .getRates();
        Map<String, Double> todayCourse = feignClientExchange.getToday(apiKey).getRates();
        Double yesterdayRate = yesterdayCourse.get(currency);
        Double todayRate = todayCourse.get(currency);
        return todayRate - yesterdayRate;
    }


}
