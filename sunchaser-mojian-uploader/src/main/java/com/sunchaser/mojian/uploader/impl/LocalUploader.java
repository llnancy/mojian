package com.sunchaser.mojian.uploader.impl;

import com.sunchaser.mojian.uploader.AbstractUploader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/23
 */
public class LocalUploader extends AbstractUploader {

    @Override
    protected String doUpload(MultipartFile multipartFile, String fileUri) throws Exception {
        String pathFile = uploaderProperties.getLocal().getPath() + fileUri;
        File file = new File(pathFile);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        multipartFile.transferTo(file);
        return pathFile;
    }
}
