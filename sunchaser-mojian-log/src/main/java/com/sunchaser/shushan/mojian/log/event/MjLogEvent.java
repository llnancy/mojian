package com.sunchaser.shushan.mojian.log.event;

import com.sunchaser.shushan.mojian.log.entity.MjLogBean;
import org.springframework.context.ApplicationEvent;

/**
 * mj log event
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
public class MjLogEvent extends ApplicationEvent {

    private static final long serialVersionUID = 8514554078048892107L;

    public MjLogEvent(MjLogBean source) {
        super(source);
    }
}
