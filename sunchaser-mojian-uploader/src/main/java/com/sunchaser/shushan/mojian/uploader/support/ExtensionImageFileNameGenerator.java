package com.sunchaser.shushan.mojian.uploader.support;

import cn.hutool.core.text.StrPool;
import com.sunchaser.shushan.mojian.base.util.IdUtils;
import com.sunchaser.shushan.mojian.base.util.ImageUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.multipart.MultipartFile;

/**
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
        return height + StrPool.UNDERLINE + width + StrPool.DASHED + IdUtils.simpleUUIDWithSuffix(fileSuffix);
    }
}
