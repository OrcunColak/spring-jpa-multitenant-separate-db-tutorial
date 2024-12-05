package com.colak.springtutorial.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceDefaultConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.default")
    public DataSourceProperties dataSourceDefaultProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSourceDefault() {
        // Another way is
        // DataSourceBuilder.create()
        //         .url(dbUrl)
        //         .username(dbUsername)
        //         .password(dbPassword)
        //         .driverClassName(POSTGRES_JDBC_DRIVER)
        //         .build();

        return dataSourceDefaultProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}
