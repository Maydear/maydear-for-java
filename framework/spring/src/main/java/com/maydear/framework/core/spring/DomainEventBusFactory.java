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
package com.maydear.framework.core.spring;

import com.maydear.core.framework.AbstractDomainEvent;
import com.maydear.core.framework.DomainEventBus;
import com.maydear.framework.core.spring.util.SpringContextUtils;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 领域事件工厂
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public class DomainEventBusFactory {

    private static DomainEventBus domainEventBus;

    /**
     * 防止静态类被实例化
     */
    private DomainEventBusFactory() {
        throw new IllegalStateException("Utility class");
    }

    static void setDomainEventBus(DomainEventBus domainEventBus) {
        DomainEventBusFactory.domainEventBus = domainEventBus;
    }

    /**
     * 发布事件
     *
     * @param event
     */
    public static void publish(AbstractDomainEvent event) {
        if(ObjectUtils.isEmpty(domainEventBus)){
            setDomainEventBus(SpringContextUtils.getBean(DomainEventBus.class));
        }
        domainEventBus.publish(event);
    }

}
