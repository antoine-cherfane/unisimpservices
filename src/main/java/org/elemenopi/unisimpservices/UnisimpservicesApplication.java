package org.elemenopi.unisimpservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UnisimpservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(UnisimpservicesApplication.class, args);
    }
}
