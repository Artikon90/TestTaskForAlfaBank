package com.artikon90.testtaskforalfabank.service;

import com.artikon90.testtaskforalfabank.exception.CurrencyRateException;
import com.artikon90.testtaskforalfabank.feign.FeignClientGif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifService {
    @Value("${integration.gif.key}")
    private String apiKey;
    private final String STATUS_GOOD = "rich";
    private final String STATUS_BAD = "broke";
    private final FeignClientGif feignClientGif;

    @Autowired
    public GifService(FeignClientGif feignClientGif) {
        this.feignClientGif = feignClientGif;
    }

    public String getGifSource(Double difference) {
        String res;
        if (difference > 0)
            res = STATUS_GOOD;
        else if (difference < 0)
            res = STATUS_BAD;
        else {
            throw new CurrencyRateException();
        }
        return (String) feignClientGif.getGif(apiKey, res).getData().get("embed_url");
    }
}
