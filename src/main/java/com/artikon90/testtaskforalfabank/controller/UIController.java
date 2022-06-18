package com.artikon90.testtaskforalfabank.controller;

import com.artikon90.testtaskforalfabank.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UIController {
    final CourseService courseService;

    public UIController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
