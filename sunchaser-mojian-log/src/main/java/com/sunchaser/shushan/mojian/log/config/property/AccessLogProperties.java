package com.sunchaser.shushan.mojian.log.config.property;

import static com.sunchaser.shushan.mojian.log.config.property.AccessLogProperties.PREFIX;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * access log properties
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
@Getter
@Setter
@ConfigurationProperties(prefix = PREFIX)
public class AccessLogProperties {

    public static final String PREFIX = "mj.log";

    private Boolean enable;

    private String appId;

    private String env;
}
