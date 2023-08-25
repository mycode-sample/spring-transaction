package com.wrotecode.spring.springtransaction.service;

import com.wrotecode.spring.springtransaction.config.SnowFlake;
import com.wrotecode.spring.springtransaction.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SimpleUserService {
    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory factory;
    @Autowired
    private SnowFlake snowFlake;

    public Account save(Account account) {
        SqlSession sqlSession = null;
        try {
            account.setId(snowFlake.nextIdString());
            sqlSession = factory.openSession(TransactionIsolationLevel.READ_COMMITTED);
            sqlSession.insert("user.insertAccount", account);
            sqlSession.commit();
        } catch (Exception e) {
            log.error("发生错误", e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return account;
    }

    public List<Account> queryAll(Account account) {
        SqlSession sqlSession = null;
        List<Account> list = null;
        try {
            sqlSession = factory.openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
            list = sqlSession.selectList("user.selectAll", account);
            sqlSession.commit();
        } catch (Exception e) {
            log.error("发生错误", e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return list;
    }
}
