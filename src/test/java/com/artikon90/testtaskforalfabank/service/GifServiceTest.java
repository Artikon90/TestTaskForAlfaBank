package com.artikon90.testtaskforalfabank.service;

import com.artikon90.testtaskforalfabank.exception.CurrencyNotFoundException;
import com.artikon90.testtaskforalfabank.exception.CurrencyRateException;
import com.artikon90.testtaskforalfabank.feign.FeignClientGif;
import com.artikon90.testtaskforalfabank.model.Gif;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.HashMap;
import java.util.Map;
import static com.artikon90.testtaskforalfabank.controller.RateControllerTest.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GifServiceTest {

    @MockBean
    private FeignClientGif client;

    @Autowired
    private GifService gifService;

    public static Gif getMockGif(String status) {
        Map<String, String > meta = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("embed_url", status);
        return Gif.builder()
                .meta(meta)
                .data(data)
                .build();
    }

    @Test
    public void shouldBeRich() {
        when(client.getGif(anyString(), anyString())).thenReturn(getMockGif(URI_RICH));
        Assertions.assertEquals(URI_RICH, gifService.getGifSource(0.2));
    }
    @Test
    public void shouldBeBroke() {
        when(client.getGif(anyString(), anyString())).thenReturn(getMockGif(URI_POOR));
        Assertions.assertEquals(URI_POOR, gifService.getGifSource(-0.2));
    }
    @Test
    public void shouldBeThrows() {
        when(client.getGif(anyString(), anyString())).thenReturn(getMockGif(URI_POOR));
        CurrencyRateException thrown = assertThrows(
                CurrencyRateException.class,
                () -> gifService.getGifSource(0.0));
        assertTrue(thrown.getMessage().contains("Что-то пошло не так!"));
    }
    @Test
    public void shouldBeThrows2() {
        when(client.getGif(anyString(), anyString())).thenReturn(getMockGif(URI_POOR));
        CurrencyRateException thrown = assertThrows(
                CurrencyRateException.class,
                () -> gifService.getGifSource(null));
        assertTrue(thrown.getMessage().contains("Что-то пошло не так!"));
    }
}
