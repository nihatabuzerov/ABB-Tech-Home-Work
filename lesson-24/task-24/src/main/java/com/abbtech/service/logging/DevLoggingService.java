package com.abbtech.service.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevLoggingService implements LoggingService {

    private static final Logger log = LoggerFactory.getLogger(
            DevLoggingService.class
    );

    @Override
    public void log(String message, Object... args) {
        log.debug(message, args);
    }
}
