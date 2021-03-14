/*
 * Copyright 2008-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maydear.core.framework;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 领域事件管道
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
public final class DomainEventBus {

    /**
     * 订阅得观察者
     */
    private Map<Class, DomainEventObserver> observersMaps = Maps.newConcurrentMap();

    /**
     * 事件朔源服务
     */
    private DomainEventStoreService domainEventStoreService;

    /**
     * 构造事件管道
     *
     * @param domainEventStoreService 事件朔源服务
     */
    public DomainEventBus(DomainEventStoreService domainEventStoreService) {
        this.domainEventStoreService = domainEventStoreService;
    }

    /**
     * 构造事件管道
     *
     * @param domainEventStoreService 事件朔源服务
     */
    public DomainEventBus(DomainEventStoreService domainEventStoreService, List<DomainEventObserver> domainEventObserverList) {
        this.domainEventStoreService = domainEventStoreService;
        for (DomainEventObserver observer: domainEventObserverList) {
            register(observer);
        }
    }

    /**
     * 注册事件观察者
     *
     * @param observer 待注册的事件观察者
     */
    public void register(DomainEventObserver observer) {
        //避免重复注册
        if (!observersMaps.containsKey(observer.getClass())) {
            observersMaps.put(observer.getClass(), observer);
        }
    }

    /**
     * 获取已经注册的事件观察者
     *
     * @return 返回已注册的事件观察者
     */
    public Collection<DomainEventObserver> getEventObservers() {
        return observersMaps.values();
    }

    /**
     * 发布通知
     *
     * @param event 事件
     */
    public void publish(AbstractDomainEvent event) {
        for (DomainEventObserver observer : getEventObservers()) {
            try {
                if (observer.support(event.getClass())) {
                    observer.notify(event);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //吃掉所有的异常。避免打断广播事件
                domainEventStoreService.saveSource(event, e);
            }
        }
    }

}
