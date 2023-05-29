package com.wrotecode.spring.springtransaction;

import com.wrotecode.spring.springtransaction.config.SnowFlake;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
    public static void main(String[] args) {
        log.info("hello,world.");
        SnowFlake snowFlake = new SnowFlake(0, 0);
        log.info(snowFlake.nextIdString());
    }
}
