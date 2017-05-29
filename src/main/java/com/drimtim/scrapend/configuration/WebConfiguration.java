package com.drimtim.scrapend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Value(value = "${configuration.webConfiguration.allowedOrigins:*}")
    private String allowedOrigins;

    @Value(value = "#{'${configuration.webConfiguration.allowedMethods:GET,POST,PATCH}'.split(',')}")
    private String [] allowedMethods;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods(allowedMethods).allowedOrigins(allowedOrigins);

    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        //TODO: We add the interceptors here
//        registry.addInterceptor(securityInterceptor);
    }

}