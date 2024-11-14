package com.colak.springtutorial.filter;

import org.slf4j.MDC;

import java.util.Objects;

// Responsible for holding the current tenant identifier.
// We use a ThreadLocal variable to store the tenant identifier for each request.
public class TenantContext {

    private static final String DEFAULT_TENANT = "public";
    private static final String LOGGER_TENANT_ID = "tenant_id";
    private static final ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setCurrentTenant(String tenantId) {
        MDC.put(LOGGER_TENANT_ID, tenantId);
        currentTenant.set(tenantId);
    }

    public static String getCurrentTenant() {
        String tenant = currentTenant.get();
        return Objects.requireNonNullElse(tenant, DEFAULT_TENANT);
    }

    public static void clear() {
        MDC.clear();
        currentTenant.remove();
    }
}
