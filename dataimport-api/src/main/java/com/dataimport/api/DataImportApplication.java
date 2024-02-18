package com.dataimport.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DataImportApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataImportApplication.class, args);
    }

}
