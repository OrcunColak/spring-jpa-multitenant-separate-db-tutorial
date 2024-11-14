package com.colak.springtutorial.config;

import com.colak.springtutorial.filter.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

// Custom RoutingDataSource that decides which DataSource to use based on the tenant identifier.
public class MultitenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }
}

