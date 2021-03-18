package com.maydear.core.sample.web;

import com.maydear.core.framework.infrastructure.LocalFileOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
@Configuration
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "maydear.common.attachment")
public class LocalFileProperties extends LocalFileOptions {

}
