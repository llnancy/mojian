package io.github.llnancy.mojian.log.test;

import io.github.llnancy.mojian.desensitize.annotation.Desensitize;
import io.github.llnancy.mojian.desensitize.strategy.impl.PasswordDesensitizeStrategy;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.annotation.LogIgnore;
import io.github.llnancy.mojian.log.enums.AccessType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/29
 */
@AccessLog(value = "ClassAccessLogService-class", type = AccessType.STATUS)
@Component
public class ClassAccessLogService {

    public String doLog(String param) {
        return "Hello:" + param;
    }

    @AccessLog(value = "ClassAccessLogService-logMethod-value", type = AccessType.SELECT)
    public String logMethod(String param) {
        return "Hello:" + param;
    }

    @AccessLog(type = AccessType.UPDATE)
    public void log() {
    }

    @LogIgnore
    public String ignore(String param) {
        return "Hello:" + param;
    }

    @AccessLog(type = AccessType.LOGIN)
    public void log(LogRequest request) {
    }

    @Getter
    @Setter
    static class LogRequest {

        private String username;

        @Desensitize(strategy = PasswordDesensitizeStrategy.class)
        private String password;
    }
}
