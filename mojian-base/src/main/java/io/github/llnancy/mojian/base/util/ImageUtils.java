package io.github.llnancy.mojian.base.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * image util
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
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
            throw new IllegalStateException("文件非图片格式");
        }
        return bufferedImage;
    }
}
