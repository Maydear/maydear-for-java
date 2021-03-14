package com.maydear.framework.core.spring;

import com.maydear.core.framework.DomainEventBus;
import com.maydear.core.framework.DomainEventObserver;
import com.maydear.core.framework.DomainEventStoreService;
import com.maydear.framework.core.spring.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * 领域事件配置
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@Configuration
public class DomainEventConfiguration {

    /**
     * 创建文件
     *
     * @return 返回领域事件管道
     */
    @Bean
    @Lazy
    public List<DomainEventObserver> observers() {
        log.debug("执行DomainEventConfiguration构造 List<DomainEventObserver>");
        return (List<DomainEventObserver>) SpringContextUtils.getBeans(DomainEventObserver.class);
    }

    /**
     * 创建文件
     *
     * @param observers               领域事件观察者
     * @param domainEventStoreService 领域事件服务
     * @return 返回领域事件管道
     */
    @Bean
    @Lazy
    public DomainEventBus domainEventBus(DomainEventStoreService domainEventStoreService, List<DomainEventObserver> observers) {
        log.debug("执行DomainEventConfiguration构造DomainEventBus");
        DomainEventBus domainEventBus = new DomainEventBus(domainEventStoreService, observers);
        DomainEventBusFactory.setDomainEventBus(domainEventBus);
        return domainEventBus;
    }
}
