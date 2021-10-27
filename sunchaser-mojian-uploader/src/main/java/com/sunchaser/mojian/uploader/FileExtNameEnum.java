package com.sunchaser.mojian.uploader;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@Getter
@AllArgsConstructor
public enum FileExtNameEnum {
    JPEG("jpg", "JPEG (jpg)"),
    PNG("png", "PNG (png)"),
    GIF("gif", "GIF (gif)"),

    TIFF("tif", "TIFF (tif)"),
    BMP("bmp", "16色位图(bmp)/24色位图(bmp)/256色位图(bmp)"),
    DWG("dwg", "CAD (dwg)"),
    ;
    private final String extName;
    private final String desc;
}
