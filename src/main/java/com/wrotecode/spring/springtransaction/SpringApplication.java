package com.wrotecode.spring.springtransaction;

import com.wrotecode.spring.springtransaction.repository.DemoEntityRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/spring.xml");
        DemoEntityRepository repository = context.getBean("demoEntityRepository", DemoEntityRepository.class);
        long count = repository.count();
        System.out.println(count);
    }
}
