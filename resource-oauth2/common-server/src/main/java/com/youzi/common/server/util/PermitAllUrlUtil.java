package com.youzi.common.server.util;

import java.util.ArrayList;
import java.util.List;

public class PermitAllUrlUtil {

    private static final String[] ENDPOINTS = {"/health", "/env", "/metrics", "/trace", "/dump", "/jolokia", "/info", "/logfile", "/refresh", "/flyway", "/liquibase", "/heapdump", "/loggers",
            "/auditevents", "/v2/api-docs/**", "/actuator/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/druid/**" };

    public static String[] permitAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return ENDPOINTS;
        }

        final List<String> list = new ArrayList<String>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : urls) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }
}
