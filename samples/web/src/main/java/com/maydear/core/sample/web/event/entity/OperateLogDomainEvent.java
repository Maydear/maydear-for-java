package com.maydear.core.sample.web.event.entity;

import com.maydear.core.framework.AbstractDomainEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 类描述
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public class OperateLogDomainEvent extends AbstractDomainEvent {
    public OperateLogDomainEvent(String args) {
        super(args,OperateLogDomainEvent.class.getName() , UUID.randomUUID(),LocalDateTime.now());
    }
}
