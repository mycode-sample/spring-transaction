package com.wrotecode.spring.springtransaction.controller;

import com.wrotecode.spring.springtransaction.dto.UserDto;
import com.wrotecode.spring.springtransaction.entity.Associate;
import com.wrotecode.spring.springtransaction.entity.Contact;
import com.wrotecode.spring.springtransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
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

    @PostMapping("/deleteUser")
    public UserDto deleteUser(@RequestParam(value = "accountId") String accountId) {
        return service.deleteAccount(accountId);
    }

    @PostMapping("/saveAssociate")
    public Associate saveAssociate(@RequestBody Associate associate) {
        return service.saveAssociate(associate);
    }

    @PostMapping("/deleteAssociate")
    public Associate deleteAssociate(@RequestParam(value = "associateId") String associateId) {
        return service.deleteAssociate(associateId);
    }

    @PostMapping("/saveContact")
    public Contact saveContact(@RequestBody Contact contact) {
        return service.saveContact(contact);
    }

    @PostMapping("/deleteContact")
    public Contact deleteContact(@RequestParam(value = "contactId") String contactId) {
        return service.deleteContact(contactId);
    }

    @GetMapping("/queryAll")
    public List queryAll(@RequestParam(required = false) String id) {
        return service.queryAll(id);
    }

}
