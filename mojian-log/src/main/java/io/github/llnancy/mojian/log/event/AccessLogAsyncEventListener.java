package io.github.llnancy.mojian.log.event;

import io.github.llnancy.mojian.log.entity.AccessLogBean;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * {@link AccessLogEvent} async listener
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class AccessLogAsyncEventListener extends AccessLogEventListener {

    public AccessLogAsyncEventListener(Consumer<AccessLogBean> consumer) {
        super(consumer);
    }

    @Override
    @Async
    public void consumeAccessLogEvent(AccessLogEvent event) {
        super.consumeAccessLogEvent(event);
    }
}
