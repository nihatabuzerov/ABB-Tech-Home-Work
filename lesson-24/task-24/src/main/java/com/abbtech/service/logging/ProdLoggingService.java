package com.abbtech.service.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class ProdLoggingService implements LoggingService {

    private static final Logger log = LoggerFactory.getLogger(
            ProdLoggingService.class
            );

    @Override
    public void log(String message, Object... args) {
        log.info(message, args);
    }
}
