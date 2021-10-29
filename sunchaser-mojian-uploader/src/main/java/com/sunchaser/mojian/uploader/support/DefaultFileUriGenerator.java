package com.sunchaser.mojian.uploader.support;

import com.sunchaser.mojian.uploader.autoconfigure.UploaderProperties;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static com.sunchaser.mojian.base.Constants.StringConstants.EMPTY;
import static com.sunchaser.mojian.base.Constants.StringConstants.FORWARD_SLASH;

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
        String artifactId = StringUtils.isEmpty(properties.getArtifactId()) ? EMPTY : properties.getArtifactId() + FORWARD_SLASH;
        String namespace = StringUtils.isEmpty(properties.getNamespace()) ? EMPTY : properties.getNamespace() + FORWARD_SLASH;
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonth().getValue());

        return artifactId +
                namespace +
                LocalDate.now().toString() + FORWARD_SLASH +
                fileNameGenerator.generateFileName(multipartFile);
    }
}
