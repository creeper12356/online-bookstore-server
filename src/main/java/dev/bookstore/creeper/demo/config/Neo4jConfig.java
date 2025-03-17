package dev.bookstore.creeper.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Neo4jConfig {

    @Bean
    public Neo4jTransactionManager transactionManager(org.neo4j.driver.Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
}