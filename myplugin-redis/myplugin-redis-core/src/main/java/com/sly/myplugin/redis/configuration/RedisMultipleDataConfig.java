package com.sly.myplugin.redis.configuration;

import com.sly.myplugin.redis.properties.RedisMultipleDataSourceProperties;
import com.sly.myplugin.redis.register.RedisRegister;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * redis 多数据源配置
 *
 * @author SLY
 * @date 2021/12/2
 */
@Import(RedisRegister.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisMultipleDataSourceProperties.class)
public class RedisMultipleDataConfig {

    
}
