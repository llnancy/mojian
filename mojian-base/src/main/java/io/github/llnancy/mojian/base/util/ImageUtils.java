package io.github.llnancy.mojian.base.util;

import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * image util
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
public class ImageUtils {

    private ImageUtils() {
    }

    public static ImmutablePair<Integer, Integer> getImageHeightAndWidth(InputStream is) throws Exception {
        BufferedImage bufferedImage = doGetBufferedImage(is);
        return ImmutablePair.of(bufferedImage.getHeight(), bufferedImage.getWidth());
    }

    public static Integer getImageHeight(InputStream is) throws Exception {
        return doGetBufferedImage(is).getHeight();
    }

    public static Integer getImageWidth(InputStream is) throws Exception {
        return doGetBufferedImage(is).getWidth();
    }

    private static BufferedImage doGetBufferedImage(InputStream is) throws Exception {
        if (is == null) {
            throw new NullPointerException("InputStream == null!");
        }
        BufferedImage bufferedImage = ImageIO.read(is);
        if (bufferedImage == null) {
            throw new IllegalArgumentException("文件非图片格式");
        }
        return bufferedImage;
    }
}
