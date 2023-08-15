package io.github.llnancy.mojian.log.event;

import io.github.llnancy.mojian.log.entity.AccessLogBean;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * access log event
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/07/10
 */
public class AccessLogEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 8514554078048892107L;

    public AccessLogEvent(AccessLogBean source) {
        super(source);
    }
}
