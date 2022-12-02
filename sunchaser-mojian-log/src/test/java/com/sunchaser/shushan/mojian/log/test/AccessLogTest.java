package com.sunchaser.shushan.mojian.log.test;

import com.sunchaser.shushan.mojian.log.util.Ip2regionUtils;
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
        String logMethod = service.logMethod("logMethod");
        String ignore = service.ignore("ignore");
    }

    @Test
    public void testIp2region() {
        // 国内 IP
        String ip = "183.136.182.140";
        String region = Ip2regionUtils.searchRegion(ip);
        String friendlyRegion = Ip2regionUtils.searchFriendlyRegion(ip);
        System.out.println(region);
        System.out.println(friendlyRegion);

        // 国外 IP
        ip = "67.220.90.13";
        region = Ip2regionUtils.searchRegion(ip);
        friendlyRegion = Ip2regionUtils.searchFriendlyRegion(ip);
        System.out.println(region);
        System.out.println(friendlyRegion);
    }
}
