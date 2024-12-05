package com.colak.springtutorial.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSource2Config {

    @Bean
    @ConfigurationProperties("spring.datasource.tenant2")
    public DataSourceProperties dataSource2Properties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource2() {
        return dataSource2Properties()
                .initializeDataSourceBuilder()
                .build();
    }
}
