package com.sotream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MarketDataApp {

    private static final Logger logger = LogManager.getLogger("Log4j2Application");

    public static void main(String[] args) {
        SpringApplication.run(MarketDataApp.class, args);
    }
}
