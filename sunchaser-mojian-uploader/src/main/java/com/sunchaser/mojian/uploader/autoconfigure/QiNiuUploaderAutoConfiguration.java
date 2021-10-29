package com.sunchaser.mojian.uploader.autoconfigure;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.sunchaser.mojian.uploader.impl.kodo.QiNiuCloudUploader;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static com.sunchaser.mojian.uploader.autoconfigure.UploaderProperties.QiNiu.QiNiuRegionEnum.matchRegionByProperty;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/29
 */
@Configuration
@AutoConfigureAfter(UploaderAutoConfiguration.class)
@ConditionalOnProperty(prefix = "sunchaser.mojian.uploader", value = "uploader-class-name", havingValue = "com.sunchaser.mojian.uploader.impl.kodo.QiNiuCloudUploader")
public class QiNiuUploaderAutoConfiguration {
    private final UploaderProperties uploaderProperties;

    public QiNiuUploaderAutoConfiguration(UploaderProperties uploaderProperties) {
        this.uploaderProperties = uploaderProperties;
    }

    @Bean
    public com.qiniu.storage.Configuration qiNiuConfig() {
        return new com.qiniu.storage.Configuration(matchRegionByProperty(uploaderProperties.getQiniu().getRegion()));
    }

    @Bean
    public UploadManager uploadManager(com.qiniu.storage.Configuration configuration) {
        return new UploadManager(configuration);
    }

    @Bean
    public Auth auth() {
        return Auth.create(uploaderProperties.getQiniu().getAccessKey(), uploaderProperties.getQiniu().getSecretKey());
    }

    @Bean
    @DependsOn(value = {"qiNiuConfig", "uploadManager", "auth"})
    public QiNiuCloudUploader qiNiuCloudUploader(UploadManager uploadManager, Auth auth) {
        return new QiNiuCloudUploader(uploadManager, auth);
    }
}
