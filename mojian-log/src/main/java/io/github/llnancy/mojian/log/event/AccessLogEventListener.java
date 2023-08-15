package io.github.llnancy.mojian.log.event;

import io.github.llnancy.mojian.log.entity.AccessLogBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.function.Consumer;

/**
 * {@link AccessLogEvent} listener
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
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
