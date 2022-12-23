package io.github.llnancy.mojian.desensitize.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import io.github.llnancy.mojian.desensitize.adapter.jackson.DesensitizeAnnotationIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/23
 */
@Slf4j
public class JacksonTest {

    private static TestBean testBean;

    @BeforeAll
    public static void init() {
        testBean = new TestBean();
        testBean.setName("李逍遥");
        testBean.setEmail("admin@lilu.org.cn");
        testBean.setMobile("15208900746");
        testBean.setAddress("浙江省杭州市滨江区网商路666号");
        testBean.setBankCard("6212364646483232455");
        testBean.setFixedPhone("076512344321");
        testBean.setIdCard("421083199905126789");
        testBean.setPassword("LoveNancy");
    }

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AnnotationIntrospector ai = mapper.getSerializationConfig().getAnnotationIntrospector();
        mapper.setAnnotationIntrospector(AnnotationIntrospectorPair.pair(ai, new DesensitizeAnnotationIntrospector()));
        String value = mapper.writeValueAsString(testBean);
        log.info("value: {}", value);
    }
}
