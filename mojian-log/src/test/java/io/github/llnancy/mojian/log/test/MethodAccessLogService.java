package io.github.llnancy.mojian.log.test;

import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.annotation.LogIgnore;
import io.github.llnancy.mojian.log.enums.AccessType;
import org.springframework.stereotype.Component;

/**
 * a service.
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
@Component
public class MethodAccessLogService {

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
}
