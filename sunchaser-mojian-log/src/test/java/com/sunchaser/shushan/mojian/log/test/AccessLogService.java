package com.sunchaser.shushan.mojian.log.test;

import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import org.springframework.stereotype.Component;


/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/29
 */
@AccessLog
@Component
public class AccessLogService {

    public String doLog(String param) {
        return "Hello:" + param;
    }
}