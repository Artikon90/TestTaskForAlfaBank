package com.artikon90.testtaskforalfabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestTaskForAlfaBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskForAlfaBankApplication.class, args);
    }

}
