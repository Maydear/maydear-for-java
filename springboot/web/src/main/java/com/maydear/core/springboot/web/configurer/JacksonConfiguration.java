package com.maydear.core.springboot.web.configurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maydear.core.framework.*;
import com.maydear.core.framework.jackson.JsonConverterImpl;
import com.maydear.core.framework.jackson.XmlConverterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 领域事件配置
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Slf4j
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonConfiguration {

    /**
     * json序列化对象
     *
     * @return 返回json序列化对象
     */
    @Bean
    @ConditionalOnMissingBean(JsonConverter.class)
    public JsonConverter jsonConverter() {
        return new JsonConverterImpl();
    }

    /**
     * xml序列化对象
     *
     * @return 返回xml序列化对象
     */
    @Bean
    @ConditionalOnMissingBean(XmlConverter.class)
    public XmlConverter xmlConverter() {
        return new XmlConverterImpl();
    }
}
