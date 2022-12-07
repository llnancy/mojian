package io.github.llnancy.mojian.log.event;

import io.github.llnancy.mojian.log.entity.AccessLogBean;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * {@link AccessLogEvent} async listener
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/2
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
