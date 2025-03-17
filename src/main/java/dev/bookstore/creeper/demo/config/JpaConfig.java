package dev.bookstore.creeper.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;


@Configuration
public class JpaConfig {

    @Bean
    @Primary 
    // 必须将这个TransactionManager标记为Primary，
    // 否则会报错存在两个TransactionManager，或者不执行数据库写操作
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}