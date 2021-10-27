package com.sunchaser.mojian.uploader.autoconfigure;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.qiniu.storage.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@Getter
@EnableConfigurationProperties(UploaderProperties.class)
@ConfigurationProperties(prefix = "sunchaser.mojian.uploader")
public class UploaderProperties {
    private String artifactId;

    private String namespace;

    private final QiNiu qiniu = new QiNiu();

    private final Local local = new Local();

    @Data
    public static class Local {
        private String path;
    }

    @Data
    public static class QiNiu {
        private String domain;
        private String accessKey;
        private String secretKey;
        private String bucketName;
        private String region;

        @Getter
        @AllArgsConstructor
        enum QiNiuRegionEnum {
            HUA_DONG("huadong", Region::huadong),
            HUA_BEI("huabei", Region::huabei),
            HUA_NAN("huanan", Region::huanan),
            BEI_MEI("beimei", Region::beimei),
            XIN_JIA_PO("xinjiapo", Region::xinjiapo)
            ;
            private final String property;
            private final Supplier<Region> region;

            private static final Map<String, QiNiuRegionEnum> enumMap = Maps.newHashMap();

            static {
                for (QiNiuRegionEnum qiNiuRegionEnum : QiNiuRegionEnum.values()) {
                    enumMap.put(qiNiuRegionEnum.property, qiNiuRegionEnum);
                }
            }

            public static Region matchRegionByProperty(String property) {
                QiNiuRegionEnum qiNiuRegionEnum = enumMap.get(property);
                Preconditions.checkArgument(Objects.nonNull(qiNiuRegionEnum), "七牛云配置机房不存在");
                return qiNiuRegionEnum.region.get();
            }
        }
    }
}
