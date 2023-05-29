package com.wrotecode.spring.springtransaction.repository;

import com.wrotecode.spring.springtransaction.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactRepository extends JpaRepository<Contact, String>, JpaSpecificationExecutor<Contact> {

}
