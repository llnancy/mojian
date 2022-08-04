package com.sunchaser.shushan.mojian.uploader.support;

import com.sunchaser.shushan.mojian.uploader.autoconfigure.UploaderProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static cn.hutool.core.text.CharSequenceUtil.EMPTY;
import static cn.hutool.core.text.StrPool.SLASH;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class DefaultFileUriGenerator implements FileUriGenerator {

    private final UploaderProperties uploaderProperties;

    public DefaultFileUriGenerator(UploaderProperties uploaderProperties) {
        this.uploaderProperties = uploaderProperties;
    }

    @Override
    public String generateFileUri(MultipartFile multipartFile, FileNameGenerator fileNameGenerator) throws Exception {
        UploaderProperties properties = this.uploaderProperties;
        String artifactId = StringUtils.hasLength(properties.getArtifactId()) ? EMPTY : properties.getArtifactId() + SLASH;
        String namespace = StringUtils.hasLength(properties.getNamespace()) ? EMPTY : properties.getNamespace() + SLASH;
        LocalDate now = LocalDate.now();
        String year = now.getYear() + SLASH;
        String month = now.getMonth().getValue() + SLASH;
        String day = now.getDayOfMonth() + SLASH;
        return artifactId +
                namespace +
                year +
                month +
                day +
                fileNameGenerator.generateFileName(multipartFile);
    }

}
