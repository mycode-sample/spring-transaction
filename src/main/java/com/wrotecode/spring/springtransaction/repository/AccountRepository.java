package com.wrotecode.spring.springtransaction.repository;

import com.wrotecode.spring.springtransaction.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {

}
