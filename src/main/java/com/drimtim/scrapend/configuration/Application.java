package com.drimtim.scrapend.configuration;

import com.drimtim.scrapend.exceptions.ExceptionControllerAdvice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

@SpringBootApplication(exclude = {
            MongoAutoConfiguration.class,
            MongoDataAutoConfiguration.class,
            XADataSourceAutoConfiguration.class,
            JndiDataSourceAutoConfiguration.class,
            DataSourceAutoConfiguration.class,
            JpaRepositoriesAutoConfiguration.class
        },
		scanBasePackages = {
            "com.drimtim.scrapend.controllers",
            "com.drimtim.scrapend.services",
            "com.drimtim.scrapend.managers",
            "com.drimtim.scrapend.aspects"
		},
		scanBasePackageClasses = {
            SwaggerConfig.class,
            WebConfiguration.class,
            ExceptionControllerAdvice.class,
            DBConfiguration.class
		})
@PropertySource(value = { "classpath:application.properties" })
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableAspectJAutoProxy
public class Application extends SpringBootServletInitializer {

    @Value("${configuration.application.corePoolSize:2}")
    private Integer corePoolSize;
    @Value("${configuration.application.maxPoolSize:2}")
    private Integer maxPoolSize;
    @Value("${configuration.application.queueCapacity:500}")
    private Integer queueCapacity;
    @Value("${configuration.application.threadNamePrefix:SCRAPEND-}")
    private String threadNamePrefix;
    @Value("${configuration.application.schedulerPoolSize:10}")
    private Integer schedulerPoolSize;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix + "EXECUTOR");
        executor.initialize();
        return executor;
    }

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix(threadNamePrefix + "SCHEDULER");
        scheduler.setPoolSize(schedulerPoolSize);
        return scheduler;
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }

}