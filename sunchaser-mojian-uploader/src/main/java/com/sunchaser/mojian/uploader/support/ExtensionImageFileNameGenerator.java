package com.sunchaser.mojian.uploader.support;

import com.sunchaser.mojian.util.IdUtils;
import com.sunchaser.mojian.util.ImageUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.multipart.MultipartFile;

import static com.sunchaser.mojian.base.Constants.StringConstants.DASH;
import static com.sunchaser.mojian.base.Constants.StringConstants.UNDERSCORE;

/**
 *
 * FileName: height_width-UUID.fileSuffix
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ExtensionImageFileNameGenerator extends AbstractFileNameGenerator {

    @Override
    protected String doGenerateFileName(MultipartFile multipartFile, String fileSuffix) throws Exception {
        ImmutablePair<Integer, Integer> hw = ImageUtils.getImageHeightAndWidth(multipartFile);
        Integer height = hw.getLeft();
        Integer width = hw.getRight();
        return height + UNDERSCORE + width + DASH + IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
