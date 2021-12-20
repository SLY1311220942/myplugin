package com.sly.myplugin.validate.configuration;

import com.sly.myplugin.validate.handler.ValidateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 参数验证config
 *
 * @author SLY
 * @date 2021/11/25
 */
@Configuration
public class ValidateConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateConfig.class);

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        LOGGER.info("初始化参数验证");
        return new MethodValidationPostProcessor();
    }

    @Bean
    public ValidateExceptionHandler validateExceptionHandler() {
        LOGGER.info("初始化验证异常处理器");
        return new ValidateExceptionHandler();
    }
}
