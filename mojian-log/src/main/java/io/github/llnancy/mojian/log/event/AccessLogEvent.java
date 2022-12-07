package io.github.llnancy.mojian.log.event;

import io.github.llnancy.mojian.log.entity.AccessLogBean;
import org.springframework.context.ApplicationEvent;

/**
 * access log event
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
public class AccessLogEvent extends ApplicationEvent {

    private static final long serialVersionUID = 8514554078048892107L;

    public AccessLogEvent(AccessLogBean source) {
        super(source);
    }
}
