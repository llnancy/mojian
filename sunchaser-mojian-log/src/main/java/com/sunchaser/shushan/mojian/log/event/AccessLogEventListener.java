package com.sunchaser.shushan.mojian.log.event;

import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.function.Consumer;

/**
 * {@link AccessLogEvent} listener
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
@RequiredArgsConstructor
public class AccessLogEventListener {

    private final Consumer<AccessLogBean> consumer;

    @EventListener(AccessLogEvent.class)
    public void consumeAccessLogEvent(AccessLogEvent event) {
        AccessLogBean source = (AccessLogBean) event.getSource();
        consumer.accept(source);
    }
}
