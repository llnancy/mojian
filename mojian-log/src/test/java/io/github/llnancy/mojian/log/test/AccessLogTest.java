package io.github.llnancy.mojian.log.test;

import io.github.llnancy.mojian.log.util.Ip2regionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.llnancy.mojian.log.test.ClassAccessLogService.LogRequest;

/**
 * {@link io.github.llnancy.mojian.log.annotation.AccessLog} unit test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/29
 */
@SpringBootTest
public class AccessLogTest {

    @Autowired
    private ClassAccessLogService classAccessLogService;

    @Autowired
    private MethodAccessLogService methodAccessLogService;

    @Test
    public void testClass() {
        classAccessLogService.doLog("log");
        classAccessLogService.logMethod("logMethod");
        classAccessLogService.log();
        classAccessLogService.ignore("ignore");
        LogRequest request = new LogRequest();
        request.setUsername("llnancy");
        request.setPassword("123456");
        classAccessLogService.log(request);
    }

    @Test
    public void testMethod() {
        methodAccessLogService.doLog("log");
        methodAccessLogService.logMethod("logMethod");
        methodAccessLogService.log();
        methodAccessLogService.ignore("ignore");
    }

    @Test
    public void testIp2region() {
        // 本地内网 ip
        // String ip = "0:0:0:0:0:0:0:1";
        String ip = "127.0.0.1";
        String region = Ip2regionUtils.searchRegion(ip);
        String friendlyRegion = Ip2regionUtils.searchFriendlyRegion(ip);
        System.out.println(region);
        System.out.println(friendlyRegion);

        // 国内 IP
        ip = "183.136.182.140";
        region = Ip2regionUtils.searchRegion(ip);
        friendlyRegion = Ip2regionUtils.searchFriendlyRegion(ip);
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
