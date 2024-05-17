package ru.sstu.logging.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sstu.logging.services.LoggingService;

@RestController
@RequestMapping("/logging")
@AllArgsConstructor
public class LoggingController {

    private final LoggingService loggingService;

    @PostMapping()
    public void get(@RequestBody String string, @RequestParam("for_audit") boolean forAudit) {
        loggingService.get(string, forAudit);
    }

}
