package com.cisco.studnetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StudnetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudnetServiceApplication.class, args);
    }

}
