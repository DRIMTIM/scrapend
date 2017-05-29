package com.drimtim.scrapend.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;

/**
 * Created by jonathan on 24/05/17.
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.drimtim.scrapend.repositories"
)
public class DBConfiguration extends AbstractMongoConfiguration {

    @Value("${configuration.DBConfiguration.databaseHost:localhost}")
    private String databaseHost;

    @Value("${configuration.DBConfiguration.databasePort:27017}")
    private Integer databasePort;

    @Value("${configuration.DBConfiguration.databaseName:scrapend}")
    private String databaseName;

    @Value("${configuration.DBConfiguration.username}")
    private String username;

    @Value("${configuration.DBConfiguration.password}")
    private char [] password;

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(String.valueOf(password))) {
            return new MongoClient(Collections.singletonList(new ServerAddress(databaseHost, databasePort)),
                    Collections.singletonList(MongoCredential.createScramSha1Credential(username, databaseName, password)));
        } else {
            return new MongoClient(Collections.singletonList(new ServerAddress(databaseHost, databasePort)));
        }
    }
}
