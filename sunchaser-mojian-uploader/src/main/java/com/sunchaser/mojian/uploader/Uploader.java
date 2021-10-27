package com.sunchaser.mojian.uploader;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public interface Uploader {
    String upload(MultipartFile multipartFile) throws Exception;

    default FileTypeEnum getFileTypeEnum() {
        return FileTypeEnum.ALL;
    }
}
