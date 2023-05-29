package com.wrotecode.spring.springtransaction.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "associate")
public class Associate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "first_account_id", nullable = false)
    private String firstAccountId;

    @Column(name = "second_account_id", nullable = false)
    private String secondAccountId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "delete_time")
    private Date deleteTime;

}
