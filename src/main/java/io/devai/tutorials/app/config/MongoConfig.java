package io.devai.tutorials.app.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${db.name}")
    String dbName;

    @Value("${db.url}")
    String dbUrl;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(dbUrl);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, dbName);
    }
}
