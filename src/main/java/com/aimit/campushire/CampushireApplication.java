package com.aimit.campushire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Configuration
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class CampushireApplication {
    private static final Logger logger = LogManager.getLogger(CampushireApplication.class);

    @Value("${app.api}")
    private String api;

    public static void main(String[] args) {
        logger.info("Running spring boot campus hire");
        SpringApplication.run(CampushireApplication.class, args);
    }

    @Scheduled(fixedRate = 60000)
    public void scheduleFixedRateTask() {
        double minute = 60000 / 60000.0;
        logger.info("CRON RUNNING every " + minute + " minute..");

        try {

            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                logger.info("HTTP ping success: "
                        + conn.getResponseCode());
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.error("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (IOException e) {
            logger.error("Exception: " + e.getLocalizedMessage());
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        final String host = "${app.host:default}";
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(host);
            }
        };
    }
}