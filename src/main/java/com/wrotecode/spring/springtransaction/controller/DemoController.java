package com.wrotecode.spring.springtransaction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/time")
    public String time(@Param("id") String id) {
        Date date = new Date();
        log.info(date.toString());
        return date.toString();
    }
}
