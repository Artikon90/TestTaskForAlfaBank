package com.artikon90.testtaskforalfabank.feign;

import com.artikon90.testtaskforalfabank.model.Gif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gif", url = "${integration.gif.url}")
public interface FeignClientGif {
    @GetMapping()
    Gif getGif(@RequestParam("api_key") String key,
               @RequestParam("tag") String status);
}
