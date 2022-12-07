package io.github.llnancy.mojian.log.util;

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

    /**
     * ip2region.xdb 文件位置
     */
    private static final String IP2REGION_LOCATION = "ip2region/ip2region.xdb";

    /**
     * 内网 IP
     */
    private static final String LOCALHOST_REGION = "内网IP";

    /**
     * 未知 IP
     */
    private static final String UNKNOWN_REGION = "未知";

    /**
     * 查询器
     */
    private static Searcher searcher;

    static {
        loadIp2regionXdb();
        initSearcher();
    }

    /**
     * 加载 ip2region.xdb 文件
     */
    private static void loadIp2regionXdb() {
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
            // 注意，使用者需要在 .gitignore 中忽略此处写入到项目下的 ip2region 文件夹
            FileUtils.copyInputStreamToFile(is, dest);
        } catch (IOException e) {
            log.warn("init ip2region.xdb error.", e);
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
     * 初始化 {@link Searcher} 查询器
     */
    private static void initSearcher() {
        try {
            byte[] buf = Searcher.loadContentFromFile(IP2REGION_LOCATION);
            searcher = Searcher.newWithBuffer(buf);
        } catch (IOException e) {
            log.warn("init ip2region's searcher error.", e);
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

    /**
     * 查询友好的地理位置
     *
     * @param ip ip address
     * @return friendly region
     */
    public static String searchFriendlyRegion(String ip) {
        return friendlyRegion(searchRegion(ip));
    }

    /**
     * 返回友好的地理位置
     *
     * @param region 源地理位置
     * @return 友好的地理位置
     */
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
        // 国内仅展示省份/地区
        if (Objects.equals("中国", address[0])) {
            if (address.length > 1) {
                return address[1];
            }
        } else {
            // 国外展示国家-省份/地区
            if (address.length > 1) {
                return buildAddress1(address).toString();
            }
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
}
