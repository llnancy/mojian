package io.github.llnancy.mojian.log.aspect;

import io.github.llnancy.mojian.base.util.Optionals;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.entity.AccessLogBean;
import io.github.llnancy.mojian.log.enums.AccessType;
import lombok.Getter;
import lombok.Setter;

/**
 * AccessLogAspectContext
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/6
 */
@Getter
@Setter
public class AccessLogAspectContext {

    private AccessLogBean accessLogBean = new AccessLogBean();

    /**
     * 方法上的注解
     */
    private AccessLog methodAccessLog;

    /**
     * 类上的注解
     */
    private AccessLog classAccessLog;

    private AccessLog optionalAccessLog() {
        return Optionals.of(methodAccessLog, classAccessLog);
    }

    public boolean enableUserAgent() {
        return optionalAccessLog().enableUserAgent();
    }

    public boolean enableRegion() {
        return optionalAccessLog().enableRegion();
    }

    public boolean enableRequest() {
        return optionalAccessLog().enableRequest();
    }

    public boolean enableResponse() {
        return optionalAccessLog().enableResponse();
    }

    public boolean enableRt() {
        return optionalAccessLog().enableRt();
    }

    public AccessType accessType() {
        return optionalAccessLog().type();
    }
}
