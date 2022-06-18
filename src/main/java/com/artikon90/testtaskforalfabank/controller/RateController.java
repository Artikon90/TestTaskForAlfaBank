package com.artikon90.testtaskforalfabank.controller;

import com.artikon90.testtaskforalfabank.service.CourseService;
import com.artikon90.testtaskforalfabank.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class RateController {
    final CourseService courseService;
    final GifService gifService;
    @Autowired
    public RateController(CourseService courseService, GifService gifService) {
        this.courseService = courseService;
        this.gifService = gifService;
    }

    @GetMapping("/gif/{rate}")
    public ResponseEntity<?> getGifURL(@PathVariable("rate") String rate) {
        double diff = courseService.getDifferenceRate(rate);
        if (diff < 99999d)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        String gifUrl = gifService.getGifSource(diff);
        if (gifUrl.equals("error"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(gifUrl)).build();
    }

}
