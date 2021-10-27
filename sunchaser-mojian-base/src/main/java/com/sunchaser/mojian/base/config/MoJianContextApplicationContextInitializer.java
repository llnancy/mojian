package com.sunchaser.mojian.base.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

/**
 * 扫描mojian
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/25
 */
public class MoJianContextApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
    private final String[] packages;

    public MoJianContextApplicationContextInitializer() {
        this.packages = new String[] {"com.sunchaser.mojian"};
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        if (applicationContext instanceof BeanDefinitionRegistry) {
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) applicationContext);
            scanner.scan(packages);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
