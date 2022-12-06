package com.sunchaser.shushan.mojian.log.aspect;

import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/6
 */
@Getter
@Setter
public class AccessLogAspectContext {

    private AccessLogBean accessLogBean = new AccessLogBean();

    /**
     * 方法上的注解，如果不存在，则为类上的注解。
     */
    private AccessLog accessLog;
}
