package com.sunchaser.mojian.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ImageUtils {
    private ImageUtils() {
    }

    public static ImmutablePair<Integer, Integer> getImageHeightAndWidth(MultipartFile file) throws Exception {
        BufferedImage bufferedImage = doGetBufferedImage(file);
        return ImmutablePair.of(bufferedImage.getHeight(), bufferedImage.getWidth());
    }

    public static Integer getImageHeight(MultipartFile file) throws Exception {
        return doGetBufferedImage(file).getHeight();
    }

    public static Integer getImageWidth(MultipartFile file) throws Exception {
        return doGetBufferedImage(file).getWidth();
    }

    private static BufferedImage doGetBufferedImage(MultipartFile file) throws Exception {
        if (file == null) {
            throw new NullPointerException("file == null!");
        }
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null) {
            String contentType = file.getContentType();
            if (contentType == null) {
                throw new NullPointerException("contentType == null!");
            }
            throw new InvalidMimeTypeException(contentType, "文件非图片格式");
        }
        return bufferedImage;
    }
}
