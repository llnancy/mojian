package io.github.llnancy.mojian.log.config.property;

import static io.github.llnancy.mojian.log.config.property.AccessLogProperties.PREFIX;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * access log properties
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
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
