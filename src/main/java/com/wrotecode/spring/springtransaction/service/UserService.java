package com.wrotecode.spring.springtransaction.service;

import com.wrotecode.spring.springtransaction.config.SnowFlake;
import com.wrotecode.spring.springtransaction.dto.UserDto;
import com.wrotecode.spring.springtransaction.entity.Account;
import com.wrotecode.spring.springtransaction.entity.Associate;
import com.wrotecode.spring.springtransaction.entity.Associate_;
import com.wrotecode.spring.springtransaction.entity.Contact;
import com.wrotecode.spring.springtransaction.entity.Contact_;
import com.wrotecode.spring.springtransaction.repository.AccountRepository;
import com.wrotecode.spring.springtransaction.repository.AssociateRepository;
import com.wrotecode.spring.springtransaction.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${queryAll}")
    private String sql;

    public UserDto saveUser(UserDto userDto) {
        userDto = createId(userDto);
        accountRepository.save(userDto.getAccount());
        associateRepository.saveAll(userDto.getAssociateList());
        contactRepository.saveAll(userDto.getContactList());
        return userDto;
    }

    public UserDto createId(UserDto userDto) {
        userDto.getAccount().setId(snowFlake.nextIdString());
        userDto.getAssociateList().forEach(e -> e.setId(snowFlake.nextIdString()));
        userDto.getContactList().forEach(e -> e.setId(snowFlake.nextIdString()));
        return userDto;
    }

    public UserDto deleteAccount(String accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            log.info("用户不存在");
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setAccount(account.orElse(null));
        log.info("删除联系方式");
        List<Contact> contactList = contactRepository.findAll(
                (root, query, builder) -> builder.equal(root.get(Contact_.accountId), accountId));
        userDto.setContactList(contactList);
        if (contactList.isEmpty()) {
            log.warn("没有联系方式");
        } else {
            contactRepository.deleteAll(contactList);
        }
        log.info("删除关联账号");
        List<Associate> associateList = associateRepository.findAll(
                (root, query, builder) -> builder.equal(root.get(Associate_.FIRST_ACCOUNT_ID), accountId));
        userDto.setAssociateList(associateList);
        if (associateList.isEmpty()) {
            log.info("没有关联账号");
        } else {
            associateRepository.deleteAll(associateList);
        }
        return userDto;
    }


    public List queryAll() {
        return jdbcTemplate.queryForList(sql);
    }
}
