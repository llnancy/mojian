package io.github.llnancy.mojian.log.test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AccessLogApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AccessLogApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
