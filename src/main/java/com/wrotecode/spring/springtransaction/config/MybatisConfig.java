package com.wrotecode.spring.springtransaction.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class MybatisConfig {
    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        ClassPathResource cpr = new ClassPathResource("config/mybatis.xml");
        return builder.build(cpr.getInputStream());
    }
}
