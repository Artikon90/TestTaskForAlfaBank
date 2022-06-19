package com.artikon90.testtaskforalfabank.controller;

import com.artikon90.testtaskforalfabank.exception.CurrencyNotFoundException;
import com.artikon90.testtaskforalfabank.exception.CurrencyRateException;
import com.artikon90.testtaskforalfabank.service.CourseService;
import com.artikon90.testtaskforalfabank.service.GifService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RateController.class)
public class RateControllerTest {
    public static final String URI_RICH = "https://richGif.com";
    public static final String URI_POOR = "https://brokeGif.com";
    public static final String URI_ERROR_CURR_NOT_FOUND = "http://localhost:8080/api/errorNotFound";
    public static final String URI_ERROR_SOME_ERR = "http://localhost:8080/api/someError";
    public static final String CURRENCY_FOR_TEST = "EUR";
    public static final String BAD_CURRENCY_FOR_TEST = "WWW";
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

//    @Before
//    public void init() {
//        mockMvc = MockMvcBuilder.webAppContextSetup().build();
//    }

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @MockBean
    private CourseService courseService;

    @MockBean
    private GifService gifService;

    @Test
    public void shouldBeRich() throws Exception {
        when(courseService.getDifferenceRate(CURRENCY_FOR_TEST)).thenReturn(0.2);
        when(gifService.getGifSource(0.2)).thenReturn(URI_RICH);
        mockMvc.perform(get("/api/gif/EUR"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(URI_RICH));
    }
    @Test
    public void shouldBeBroke() throws Exception {
        when(courseService.getDifferenceRate(CURRENCY_FOR_TEST)).thenReturn(-0.2);
        when(gifService.getGifSource(-0.2)).thenReturn(URI_POOR);
        mockMvc.perform(get("/api/gif/EUR"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(URI_POOR));
    }
    @Test
    public void shouldBeErrorNotFoundCurrency() throws Exception {
        when(courseService.getDifferenceRate(BAD_CURRENCY_FOR_TEST))
                .thenThrow(new CurrencyNotFoundException());
        mockMvc.perform(get("/api/gif/WWW"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(URI_ERROR_CURR_NOT_FOUND));
    }
    @Test
    public void shouldBeSomeError() throws Exception {
        double someErrorMock = 0.0;
        when(courseService.getDifferenceRate(CURRENCY_FOR_TEST))
                .thenReturn(someErrorMock);
        when(gifService.getGifSource(someErrorMock)).thenThrow(new CurrencyRateException());
        mockMvc.perform(get("/api/gif/EUR"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(URI_ERROR_SOME_ERR));
    }

}
