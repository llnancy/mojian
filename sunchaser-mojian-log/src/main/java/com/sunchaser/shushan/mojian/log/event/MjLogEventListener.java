package com.sunchaser.shushan.mojian.log.event;

import com.sunchaser.shushan.mojian.log.entity.MjLogBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.function.Consumer;

/**
 * mj log event listener
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/30
 */
@RequiredArgsConstructor
public class MjLogEventListener {

    private final Consumer<MjLogBean> consumer;

    @EventListener(MjLogEvent.class)
    public void consumeMjLogEvent(MjLogEvent event) {
        MjLogBean source = (MjLogBean) event.getSource();
        consumer.accept(source);
    }
}
