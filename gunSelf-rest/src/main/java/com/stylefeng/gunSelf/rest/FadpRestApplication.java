package com.stylefeng.gunSelf.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.gunSelf"})
public class FadpRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FadpRestApplication.class, args);
    }
}
