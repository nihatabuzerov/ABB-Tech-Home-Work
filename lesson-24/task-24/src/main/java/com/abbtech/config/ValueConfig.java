package com.abbtech.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ValueConfig {

    @Value("${app.env:unknown}")
    private String env;

    @Value("${server.port:8080}")
    private int port;
}
