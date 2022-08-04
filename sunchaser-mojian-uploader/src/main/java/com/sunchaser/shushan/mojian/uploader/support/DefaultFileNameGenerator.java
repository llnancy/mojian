package com.sunchaser.shushan.mojian.uploader.support;

import com.sunchaser.shushan.mojian.base.util.IdUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class DefaultFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) {
        return IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
