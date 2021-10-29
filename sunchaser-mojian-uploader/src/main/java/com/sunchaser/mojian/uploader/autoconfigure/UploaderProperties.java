package com.sunchaser.mojian.uploader.autoconfigure;

import com.google.common.collect.Maps;
import com.qiniu.storage.Region;
import com.sunchaser.mojian.util.Optionals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/22
 */
@Data
@ConfigurationProperties(prefix = "sunchaser.mojian.uploader")
public class UploaderProperties {
    private String artifactId;

    private String namespace;

    private String uploaderClassName;

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
            XIN_JIA_PO("xinjiapo", Region::xinjiapo),
            AUTO("auto", Region::autoRegion)
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
                return Optionals.ofObject(qiNiuRegionEnum, AUTO).region.get();
            }
        }
    }
}
