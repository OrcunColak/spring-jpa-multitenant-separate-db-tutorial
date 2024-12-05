package com.colak.springtutorial.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DataSource dataSource1;
    private final DataSource dataSource2;

    @Bean
    public DataSource dataSource() {
        MultitenantDataSource routingDataSource = new MultitenantDataSource();

        Map<Object, Object> dataSourcesMap = new HashMap<>();
        dataSourcesMap.put("tenant1", dataSource1);
        dataSourcesMap.put("tenant2", dataSource2);

        routingDataSource.setTargetDataSources(dataSourcesMap);
        routingDataSource.setDefaultTargetDataSource(dataSourceDefault());

        // Initialize all data sources
        initializeDataSource(dataSourceDefault());
        initializeDataSource(dataSource1);
        initializeDataSource(dataSource2);

        return routingDataSource;
    }

    private void initializeDataSource(DataSource dataSource) {
        DatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql")
        );
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

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


    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaProperties jpaProperties) {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                jpaProperties.getProperties(),
                null
        );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.colak.springtutorial.jpa") // Adjust to your entity package
                .persistenceUnit("default")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
