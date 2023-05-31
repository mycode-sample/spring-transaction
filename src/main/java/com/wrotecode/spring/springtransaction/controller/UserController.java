package com.wrotecode.spring.springtransaction.controller;

import com.wrotecode.spring.springtransaction.dto.UserDto;
import com.wrotecode.spring.springtransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/save")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        Date date = new Date();
        userDto.getAccount().setCreateTime(date);
        userDto.getAccount().setStatus("1");
        userDto.getAssociateList().forEach(ele -> {
            ele.setCreateTime(date);
            ele.setStatus("1");
        });
        userDto.getContactList().forEach(ele -> {
            ele.setCreateTime(date);
            ele.setStatus("1");
        });
        return service.saveUser(userDto);
    }

    public UserDto deleteUser(@RequestParam(value = "accountId") String accountId) {
        return service.deleteAccount(accountId);
    }

    @PostMapping("/queryAll")
    public List queryAll() {
        return service.queryAll();
    }

}
