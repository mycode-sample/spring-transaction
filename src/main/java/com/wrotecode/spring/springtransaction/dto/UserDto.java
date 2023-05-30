package com.wrotecode.spring.springtransaction.dto;

import com.wrotecode.spring.springtransaction.entity.Account;
import com.wrotecode.spring.springtransaction.entity.Associate;
import com.wrotecode.spring.springtransaction.entity.Contact;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Account account;
    private List<Associate> associateList;
    private List<Contact> contactList;
}
