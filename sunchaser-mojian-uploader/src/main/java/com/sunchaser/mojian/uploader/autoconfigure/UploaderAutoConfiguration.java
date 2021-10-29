package com.sunchaser.mojian.uploader.autoconfigure;

import com.sunchaser.mojian.uploader.support.DefaultFileNameGenerator;
import com.sunchaser.mojian.uploader.support.DefaultFileUriGenerator;
import com.sunchaser.mojian.uploader.support.FileNameGenerator;
import com.sunchaser.mojian.uploader.support.FileUriGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/28
 */
@Configuration
@EnableConfigurationProperties(UploaderProperties.class)
@AutoConfigureBefore(QiNiuUploaderAutoConfiguration.class)
public class UploaderAutoConfiguration {

    @Bean
    @ConditionalOnClass(UploaderProperties.class)
    public FileUriGenerator fileUriGenerator(UploaderProperties uploaderProperties) {
        return new DefaultFileUriGenerator(uploaderProperties);
    }

    @Bean
    public FileNameGenerator fileNameGenerator() {
        return new DefaultFileNameGenerator();
    }
}
