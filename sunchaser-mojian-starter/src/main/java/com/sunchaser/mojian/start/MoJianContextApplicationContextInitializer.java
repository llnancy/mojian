package com.sunchaser.mojian.start;

import com.sunchaser.mojian.uploader.autoconfigure.QiNiuUploaderAutoConfiguration;
import com.sunchaser.mojian.uploader.autoconfigure.UploaderAutoConfiguration;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.Ordered;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 扫描mojian组件
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/25
 */
public class MoJianContextApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
    private final String[] packages;

    public MoJianContextApplicationContextInitializer() {
        this.packages = new String[]{"com.sunchaser.mojian"};
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        if (applicationContext instanceof BeanDefinitionRegistry) {
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) applicationContext);
            scanner.addExcludeFilter(new MoJianClassExcludeFilter(UploaderAutoConfiguration.class, QiNiuUploaderAutoConfiguration.class));
            scanner.scan(packages);
        }
    }

    private static class MoJianClassExcludeFilter implements TypeFilter {
        private final Set<String> classNames = new HashSet<>();

        MoJianClassExcludeFilter(Object... sources) {
            for (Object source : sources) {
                if (source instanceof Class<?>) {
                    this.classNames.add(((Class<?>) source).getName());
                }
            }
        }

        @Override
        public boolean match(MetadataReader metadataReader, @NonNull MetadataReaderFactory metadataReaderFactory) throws IOException {
            ClassMetadata metadata = metadataReader.getClassMetadata();
            return this.classNames.contains(metadata.getClassName());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
