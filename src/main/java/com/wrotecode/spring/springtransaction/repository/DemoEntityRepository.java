package com.wrotecode.spring.springtransaction.repository;

import com.wrotecode.spring.springtransaction.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DemoEntityRepository extends JpaRepository<DemoEntity, Integer>, JpaSpecificationExecutor<DemoEntity> {

}
