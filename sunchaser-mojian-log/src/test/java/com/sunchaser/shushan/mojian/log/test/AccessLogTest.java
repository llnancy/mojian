package com.sunchaser.shushan.mojian.log.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/29
 */
@SpringBootTest
public class AccessLogTest {

    @Autowired
    private AccessLogService service;

    @Test
    public void test() {
        String res = service.doLog("log");
        String ignore = service.ignore("ignore");
    }
}
