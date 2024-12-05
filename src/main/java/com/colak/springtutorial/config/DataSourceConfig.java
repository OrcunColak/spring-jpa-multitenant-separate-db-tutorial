package com.colak.springtutorial.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
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
    private final DataSource dataSourceDefault;

    @Bean
    public DataSource dataSource() {
        MultitenantDataSource routingDataSource = new MultitenantDataSource();

        Map<Object, Object> dataSourcesMap = new HashMap<>();
        dataSourcesMap.put("tenant1", dataSource1);
        dataSourcesMap.put("tenant2", dataSource2);

        routingDataSource.setTargetDataSources(dataSourcesMap);
        routingDataSource.setDefaultTargetDataSource(dataSourceDefault);

        // Initialize all data sources
        initializeDataSource(dataSourceDefault);
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
                // this will use AbstractRoutingDataSource
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
