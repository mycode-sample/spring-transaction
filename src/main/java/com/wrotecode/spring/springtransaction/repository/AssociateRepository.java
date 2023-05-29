package com.wrotecode.spring.springtransaction.repository;

import com.wrotecode.spring.springtransaction.entity.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssociateRepository extends JpaRepository<Associate, String>, JpaSpecificationExecutor<Associate> {

}
