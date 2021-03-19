package com.maydear.core.springboot.web.configurer;

import com.maydear.core.framework.*;
import com.maydear.core.framework.infrastructure.FileStorageInfrastructure;
import com.maydear.core.framework.infrastructure.LocalFileStorageInfrastructure;
import com.maydear.core.framework.jackson.JsonConverterImpl;
import com.maydear.core.framework.jackson.XmlConverterImpl;
import com.maydear.framework.core.spring.DomainEventBusFactory;
import com.maydear.framework.core.spring.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
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
@ConditionalOnClass(DomainEventObserver.class)
@ConditionalOnMissingClass
public class DomainEventStoreServiceConfiguration {

    /**
     * 领域事件存储
     *
     * @return 返回json序列化对象
     */
    @Bean
    @ConditionalOnMissingBean(DomainEventStoreService.class)
    public DomainEventStoreService domainEventStoreService(JsonConverter jsonConverter, FileStorageInfrastructure fileStorageInfrastructure) {
        return new FileDomainEventStoreService(jsonConverter, fileStorageInfrastructure);
    }
}
