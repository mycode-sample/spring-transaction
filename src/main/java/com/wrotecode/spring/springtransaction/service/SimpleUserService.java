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

    public Account save(Account account, Integer level) {
        account.setId(snowFlake.nextIdString());
        update("user.insertAccount", account, level);
        return account;
    }

    public List<Account> queryAll(Account account, Integer level) {
        return selectList("user.selectAll", account, level);
    }


    private <E> List<E> selectList(String namespace, Object parameter, Integer level) {
        SqlSession sqlSession = null;
        List list = null;
        try {
            sqlSession = factory.openSession(getLevel(level));
            list = sqlSession.selectList(namespace, parameter);
            sqlSession.commit();
        } catch (Exception e) {
            log.error("发生错误", e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return list;
    }

    private int update(String namespace, Object parameter, Integer level) {
        SqlSession sqlSession = null;
        int result = 0;
        try {
            sqlSession = factory.openSession(getLevel(level));
            result = sqlSession.insert(namespace, parameter);
            sqlSession.commit();
        } catch (Exception e) {
            log.error("发生错误", e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        return result;
    }


    private TransactionIsolationLevel getLevel(Integer level) {
        TransactionIsolationLevel isolationLevel;
        level = level == null ? 0 : level;
        switch (level) {
            case 1: {
                isolationLevel = TransactionIsolationLevel.READ_COMMITTED;
                break;
            }
            case 2: {
                isolationLevel = TransactionIsolationLevel.READ_UNCOMMITTED;
                break;
            }
            case 3: {
                isolationLevel = TransactionIsolationLevel.REPEATABLE_READ;
                break;
            }
            case 4: {
                isolationLevel = TransactionIsolationLevel.SERIALIZABLE;
                break;
            }
            default: {
                isolationLevel = TransactionIsolationLevel.NONE;
                break;
            }
        }
        log.info("隔离级别:" + isolationLevel.name());
        return isolationLevel;
    }
}
