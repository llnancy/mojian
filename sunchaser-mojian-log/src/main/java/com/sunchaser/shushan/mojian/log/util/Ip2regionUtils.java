package com.sunchaser.shushan.mojian.log.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * ip2region utils
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/1
 */
@Slf4j
public class Ip2regionUtils {

    private static final String IP2REGION_LOCATION = "ip2region/ip2region.xdb";

    private static final String LOCALHOST_REGION = "内网IP";

    private static final String UNKNOWN_REGION = "未知";

    private static Searcher searcher;

    static {
        initIp2region();
    }

    private static void initIp2region() {
        if (Objects.nonNull(searcher)) {
            return;
        }
        InputStream is = null;
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(IP2REGION_LOCATION);
            if (ArrayUtils.isEmpty(resources)) {
                log.warn("ip2region.xdb resource file does not exist.");
                return;
            }
            Resource resource = resources[0];
            is = resource.getInputStream();
            File dest = new File(IP2REGION_LOCATION);
            FileUtils.copyInputStreamToFile(is, dest);
            byte[] buf = Searcher.loadContentFromFile(IP2REGION_LOCATION);
            searcher = Searcher.newWithBuffer(buf);
        } catch (IOException e) {
            log.warn("init ip2region error", e);
        } finally {
            try {
                if (Objects.nonNull(is)) {
                    is.close();
                }
            } catch (IOException ignore) {
                // ignore
            }
        }
    }

    /**
     * eg. 中国|0|浙江省|杭州市|电信
     *
     * @param ip ip address
     * @return region
     */
    @SneakyThrows
    public static String searchRegion(String ip) {
        return searcher.search(ip);
    }

    public static String searchFriendlyRegion(String ip) {
        String region = searchRegion(ip);
        return friendlyRegion(region);
    }

    public static String friendlyRegion(String region) {
        if (region.contains(LOCALHOST_REGION)) {
            return LOCALHOST_REGION;
        }
        region = region.replace("|0", StringUtils.EMPTY)
                .replace("0|", StringUtils.EMPTY)
                .replace("省", StringUtils.EMPTY);
        String[] address = region.split("\\|");
        if (ArrayUtils.isEmpty(address)) {
            return UNKNOWN_REGION;
        }
        if (address.length > 1) {
            return buildAddress1(address).toString();
        }
        return buildAddress0(address).toString();
    }

    private static StringBuilder buildAddress1(String[] address) {
        return buildAddress0(address).append(address[1]);
    }

    private static StringBuilder buildAddress0(String[] address) {
        return new StringBuilder().append(address[0]);
    }

    @SneakyThrows
    public static void closeSearcher() {
        searcher.close();
    }

    public static void main(String[] args) {
        System.out.println(searchFriendlyRegion("127.0.0.1"));
    }
}
