package com.maydear.core.sample.web.event.observer;

import com.maydear.core.framework.AbstractDomainEvent;
import com.maydear.core.framework.DomainEventObserver;
import com.maydear.core.sample.web.event.entity.OperateLogDomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * 订阅记录操作日志事件
 *
 * @author joshua.jiang
 * @version 1.0.0
 */
@Slf4j
@Component
public class OperateLogDomainEventObserver implements DomainEventObserver {

    @Override
    public boolean support(Class<?> aClass) {
        return aClass.equals(OperateLogDomainEvent.class);
    }

    @Override
    public void notify(AbstractDomainEvent event) {
        if (ObjectUtils.isEmpty(event)) {
            return;
        }

        log.debug("run " + this.getClass().getName());
    }
}
