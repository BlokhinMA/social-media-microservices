package ru.sstu.logging.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoggingService {

    public void get(String string) {
        log.info(string);
    }

}
