package io.github.llnancy.mojian.base.util;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * image util
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class ImageUtils {

    private ImageUtils() {
    }

    public static ImmutablePair<Integer, Integer> getImageHeightAndWidth(File file) throws Exception {
        BufferedImage bufferedImage = doGetBufferedImage(file);
        return ImmutablePair.of(bufferedImage.getHeight(), bufferedImage.getWidth());
    }

    public static Integer getImageHeight(File file) throws Exception {
        return doGetBufferedImage(file).getHeight();
    }

    public static Integer getImageWidth(File file) throws Exception {
        return doGetBufferedImage(file).getWidth();
    }

    private static BufferedImage doGetBufferedImage(File file) throws Exception {
        if (file == null) {
            throw new NullPointerException("file == null!");
        }
        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage == null) {
            throw new IllegalStateException("文件非图片格式");
        }
        return bufferedImage;
    }
}
