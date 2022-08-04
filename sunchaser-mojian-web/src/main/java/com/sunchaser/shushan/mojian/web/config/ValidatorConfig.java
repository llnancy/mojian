package com.sunchaser.shushan.mojian.web.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * validator config fail-fast
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/4/5
 */
@Configuration
@ConditionalOnClass(Validator.class)
public class ValidatorConfig {

    @Bean
    public Validator validator(AutowireCapableBeanFactory beanFactory) {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(beanFactory))
                .buildValidatorFactory()
                .getValidator();
    }
}
