package com.maydear.core.springboot.web.configurer;

import com.maydear.core.framework.infrastructure.FileStorageInfrastructure;
import com.maydear.core.framework.infrastructure.LocalFileOptions;
import com.maydear.core.framework.infrastructure.LocalFileStorageInfrastructure;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认文件系统
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@ConditionalOnClass(LocalFileStorageInfrastructure.class)
@ConditionalOnMissingClass
public class DefaultFileStorageBeanConfigurer {

    @Bean
    @ConditionalOnMissingBean(FileStorageInfrastructure.class)
    @ConditionalOnBean(LocalFileOptions.class)
    public FileStorageInfrastructure fileStorageInfrastructure(LocalFileOptions localFileOptions) {
        return new LocalFileStorageInfrastructure(localFileOptions);
    }


}
