package com.epam.resourceservice.config;

import com.epam.common.handler.GlobalExceptionHandler;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ValidatorFactory getValidatorFactory(){
        return Validation.byProvider( HibernateValidator.class )
                .configure()
                .constraintExpressionLanguageFeatureLevel( ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .customViolationExpressionLanguageFeatureLevel( ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .failFast(false)
                .buildValidatorFactory();
    }

    @Bean
    public Validator validator() {
        return getValidatorFactory().getValidator();
    }

    @Bean
    public GlobalExceptionHandler getGlobalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
