package com.sunchaser.mojian.uploader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@AllArgsConstructor
@Getter
public enum FileTypeEnum {
    IMAGE(Lists.newCopyOnWriteArrayList(
            Lists.newArrayList(
                    FileExtNameEnum.JPEG.getExtName(),
                    FileExtNameEnum.PNG.getExtName(),
                    FileExtNameEnum.GIF.getExtName()
            )
    ), "图片大类") {
        @Override
        public void addFileExtName(String fileExtName) {
            doAddFileExtName(this, fileExtName);
        }
    },
    ALL(Lists.newCopyOnWriteArrayList(
            Lists.newArrayList(
                    Arrays.stream(FileExtNameEnum.values())
                            .map(FileExtNameEnum::getExtName)
                            .collect(Collectors.toList())
            )
    ), "全部") {
        @Override
        public void addFileExtName(String fileExtName) {
            doAddFileExtName(this, fileExtName);
        }
    }
    ;

    private static void doAddFileExtName(FileTypeEnum fileTypeEnum, String fileExtName) {
        fileTypeEnum.fileExtNameList.add(fileExtName);
    }

    private final List<String> fileExtNameList;
    private final String desc;
    private static final Map<String, FileTypeEnum> enumMap = Maps.newHashMap();

    static {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) enumMap.put(fileTypeEnum.name(), fileTypeEnum);
    }

    public static FileTypeEnum matchFileTypeEnum(String fileTypeEnumName) {
        return enumMap.get(fileTypeEnumName);
    }

    public abstract void addFileExtName(String fileExtName);
}
