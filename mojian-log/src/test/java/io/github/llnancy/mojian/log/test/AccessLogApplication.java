package io.github.llnancy.mojian.log.test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/30
 */
@SpringBootApplication
public class AccessLogApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AccessLogApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
