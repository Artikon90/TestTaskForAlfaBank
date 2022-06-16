package com.artikon90.testtaskforalfabank.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.artikon90.testtaskforalfabank.model.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "getExchange", url = "${integration.exchange.url}")
public interface FeignClientExchange {
    @GetMapping("/historical/{date}.json?app_id={key}")
    Course getYesterday(@PathVariable("date") String date,
                        @PathVariable("key") String key);

    @GetMapping("/latest.json?app_id={key}")
    Course getToday(@PathVariable("key") String key);
}
