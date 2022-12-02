package com.sunchaser.shushan.mojian.log.test;

import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.annotation.LogIgnore;
import com.sunchaser.shushan.mojian.log.enums.AccessType;
import org.springframework.stereotype.Component;


/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/29
 */
@AccessLog(value = "AccessLogService-class", type = AccessType.STATUS)
@Component
public class AccessLogService {

    public String doLog(String param) {
        return "Hello:" + param;
    }

    @AccessLog(value = "logMethod-value", type = AccessType.SELECT)
    public String logMethod(String param) {
        return "Hello:" + param;
    }

    @LogIgnore
    public String ignore(String param) {
        return "Hello:" + param;
    }
}
