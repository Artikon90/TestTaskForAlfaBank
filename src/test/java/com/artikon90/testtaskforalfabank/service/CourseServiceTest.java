package com.artikon90.testtaskforalfabank.service;

import com.artikon90.testtaskforalfabank.exception.CurrencyNotFoundException;
import com.artikon90.testtaskforalfabank.feign.FeignClientExchange;
import com.artikon90.testtaskforalfabank.model.Course;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.artikon90.testtaskforalfabank.controller.RateControllerTest.BAD_CURRENCY_FOR_TEST;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static com.artikon90.testtaskforalfabank.controller.RateControllerTest.CURRENCY_FOR_TEST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @MockBean
    private FeignClientExchange feignClientExchange;

    private final String abcd = "some_text_for_text";
    private int timestamp_today = 1655724110;
    private int timestamp_yesterday = 1655637710;

    Course getCourseLowerCost() {
        Map<String, Double> rate = new HashMap<>();
        rate.put(CURRENCY_FOR_TEST, 10.0);
        rate.put("RUB", 100.0);
        return Course.builder()
                .base("USD")
                .license(abcd)
                .disclaimer(abcd)
                .timestamp(timestamp_today)
                .rates(rate)
                .build();
    }
    Course getCourseHighCost() {
        Map<String, Double> rate = new HashMap<>();
        rate.put(CURRENCY_FOR_TEST, 15.0);
        rate.put("RUB", 220.0);
        return Course.builder()
                .base("USD")
                .license(abcd)
                .disclaimer(abcd)
                .timestamp(timestamp_yesterday)
                .rates(rate)
                .build();
    }
    HashMap<String, String> getMockCurrency() {
        HashMap<String, String> res = new HashMap<>();
        res.put(CURRENCY_FOR_TEST, "Euro");
        res.put("RUB", "Rubles");
        return res;
    }

    @Test
    public void shouldBePlusReturn() {
        when(feignClientExchange.getAllCurrency()).thenReturn(getMockCurrency());
        when(feignClientExchange.getYesterday(anyString(), anyString())).thenReturn(getCourseLowerCost());
        when(feignClientExchange.getToday(anyString())).thenReturn(getCourseHighCost());
        Double res = courseService.getDifferenceRate(CURRENCY_FOR_TEST);
        assertTrue(res > 0);
    }

    @Test
    public void shouldBeMinusReturn() {
        when(feignClientExchange.getAllCurrency()).thenReturn(getMockCurrency());
        when(feignClientExchange.getYesterday(anyString(), anyString())).thenReturn(getCourseHighCost());
        when(feignClientExchange.getToday(anyString())).thenReturn(getCourseLowerCost());
        Double res = courseService.getDifferenceRate(CURRENCY_FOR_TEST);
        assertTrue(res < 0);
    }

    @Test
    public void shouldBeThrowException() {
        when(feignClientExchange.getAllCurrency()).thenReturn(getMockCurrency());
        when(feignClientExchange.getYesterday(anyString(), anyString())).thenReturn(getCourseHighCost());
        when(feignClientExchange.getToday(anyString())).thenReturn(getCourseLowerCost());
        CurrencyNotFoundException thrown = assertThrows(
                CurrencyNotFoundException.class,
                () -> courseService.getDifferenceRate(BAD_CURRENCY_FOR_TEST));
        assertTrue(thrown.getMessage().contains("Currency not found. Retry your input"));
    }
    @Test
    public void shouldBeThrowException2() {
        when(feignClientExchange.getAllCurrency()).thenReturn(getMockCurrency());
        when(feignClientExchange.getYesterday(anyString(), anyString())).thenReturn(getCourseHighCost());
        when(feignClientExchange.getToday(anyString())).thenReturn(getCourseLowerCost());
        CurrencyNotFoundException thrown = assertThrows(
                CurrencyNotFoundException.class,
                () -> courseService.getDifferenceRate(null));
        assertTrue(thrown.getMessage().contains("Currency not found. Retry your input"));
    }

}
