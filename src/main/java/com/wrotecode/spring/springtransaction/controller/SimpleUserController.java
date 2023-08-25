package com.wrotecode.spring.springtransaction.controller;

import com.wrotecode.spring.springtransaction.entity.Account;
import com.wrotecode.spring.springtransaction.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/simpleUser")
public class SimpleUserController {
    @Autowired
    private SimpleUserService service;

    @PostMapping("/save")
    public Account save(@RequestBody Account account) {
        return service.save(account);
    }

    @PostMapping("/queryAll")
    public List<Account> queryAll(@RequestBody Account account) {
        return service.queryAll(account);
    }
}
