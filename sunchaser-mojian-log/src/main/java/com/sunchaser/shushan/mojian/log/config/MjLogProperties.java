package com.sunchaser.shushan.mojian.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.sunchaser.shushan.mojian.log.config.MjLogProperties.PREFIX;


/**
 * log properties
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
@Data
@ConfigurationProperties(prefix = PREFIX)
public class MjLogProperties {

    public static final String PREFIX = "mj.log";

    private Boolean enable;

    private String appId;

    private String env;
}
