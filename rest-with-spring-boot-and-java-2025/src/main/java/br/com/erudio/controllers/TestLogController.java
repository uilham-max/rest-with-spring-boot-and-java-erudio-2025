package br.com.erudio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLogController {

    private final Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping("/api/test/v1")
    public String testLog(){
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        return "test log";
    }

}
