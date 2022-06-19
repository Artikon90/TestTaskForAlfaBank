package com.artikon90.testtaskforalfabank.controller;

import com.artikon90.testtaskforalfabank.service.CourseService;
import com.artikon90.testtaskforalfabank.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public ResponseEntity<?> getGifURL(@PathVariable("rate") String rate, HttpServletResponse response) throws IOException {
        double diff;
        String gifUrl;
        try {
            diff = courseService.getDifferenceRate(rate);
        } catch (RuntimeException e) {
            response.sendRedirect("http://localhost:8080/api/errorNotFound");
            return null;
        }
        try {
            gifUrl = gifService.getGifSource(diff);
        } catch (RuntimeException e) {
            response.sendRedirect("http://localhost:8080/api/someError");
            return null;
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(gifUrl)).build();
    }
    @GetMapping("/errorNotFound")
    @ResponseBody
    public String errNotFound() {
        return "Ошибка ввода валюты!";
    }
    @GetMapping("/someError")
    @ResponseBody
    public String someError() {
        return "Что-то пошло не так!";
    }
}
