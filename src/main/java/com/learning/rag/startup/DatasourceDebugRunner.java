package com.learning.rag.startup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatasourceDebugRunner implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Override
    public void run(String... args) {
        System.out.println("Datasource URL : " + url);
        System.out.println("Datasource User: " + username);
    }
}