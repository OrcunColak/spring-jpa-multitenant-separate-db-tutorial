package com.colak.springtutorial.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSource1Config {

    @Bean
    @ConfigurationProperties("spring.datasource.tenant1")
    public DataSourceProperties dataSource1Properties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource1() {
        return dataSource1Properties()
                .initializeDataSourceBuilder()
                .build();
    }
}
