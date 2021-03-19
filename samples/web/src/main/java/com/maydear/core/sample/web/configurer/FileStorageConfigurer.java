package com.maydear.core.sample.web.configurer;

import com.maydear.core.framework.infrastructure.LocalFileOptions;
import com.maydear.core.sample.web.LocalFileProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@Slf4j
public class FileStorageConfigurer {

    @Bean
    public LocalFileOptions localFileOptions(LocalFileProperties properties){
        log.debug("111");
        return properties;
    }
}
