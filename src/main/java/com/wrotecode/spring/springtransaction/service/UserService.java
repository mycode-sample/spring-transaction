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

    public UserDto saveUser(UserDto userDto) throws Exception {
        boolean rollback = false;
        userDto = createId(userDto);
        accountRepository.save(userDto.getAccount());
        if (!userDto.getAssociateList().isEmpty()) {
            associateRepository.saveAll(userDto.getAssociateList());
        }
        if (!userDto.getContactList().isEmpty()) {
            contactRepository.saveAll(userDto.getContactList());
        }
        rollback(rollback);
        return userDto;
    }

    private void rollback(boolean rollback) throws Exception {
        if (rollback) {
            throw new Exception("发生错误");
        }
    }

    public UserDto createId(UserDto userDto) {
        userDto.getAccount().setId(snowFlake.nextIdString());
        userDto.getAssociateList().forEach(e -> e.setId(snowFlake.nextIdString()));
        userDto.getContactList().forEach(e -> e.setId(snowFlake.nextIdString()));
        return userDto;
    }

    public UserDto deleteAccount(String accountId) throws Exception {
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
        rollback(false);
        return userDto;
    }

    public List queryAll() throws Exception {
        rollback(false);
        return jdbcTemplate.queryForList(sql);
    }

    public Associate deleteAssociate(String id) throws Exception {
        Optional<Associate> associate = associateRepository.findById(id);
        if (associate.isPresent()) {
            associateRepository.deleteById(id);
            return associate.orElse(null);
        }
        rollback(false);
        return null;
    }

    public Contact deleteContact(String id) throws Exception {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            contactRepository.deleteById(id);
            return contact.orElse(null);
        }
        rollback(false);
        return null;
    }

    public Contact saveContact(Contact contact) throws Exception {
        contact.setId(snowFlake.nextIdString());
        Contact saved = contactRepository.save(contact);
        rollback(false);
        return saved;
    }

    public Associate saveAssociate(Associate associate) throws Exception {
        associate.setId(snowFlake.nextIdString());
        Associate saved = associateRepository.save(associate);
        rollback(false);
        return saved;
    }
}
