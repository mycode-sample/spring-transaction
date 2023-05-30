package com.wrotecode.spring.springtransaction.service;

import com.wrotecode.spring.springtransaction.dto.UserDto;
import com.wrotecode.spring.springtransaction.entity.Account;
import com.wrotecode.spring.springtransaction.repository.AccountRepository;
import com.wrotecode.spring.springtransaction.repository.AssociateRepository;
import com.wrotecode.spring.springtransaction.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private ContactRepository contactRepository;

    public void addUser(UserDto userDto) {
        contactRepository.saveAll(userDto.getContactList());
    }

    @Transactional
    public UserDto queryUserByUserName(String username) {
        UserDto userDto = new UserDto();
        Optional<Account> account = accountRepository.findOne(
                (root, query, builder) -> builder.equal(root.get("username"), username));
        if (account.isEmpty()) {
            return null;
        }
        userDto.setAccount(account.orElse(null));
        return userDto;
    }
}
