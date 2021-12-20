package com.sly.myplugin.preventrepeat.configuration;

import com.sly.myplugin.preventrepeat.interceptor.PreventRepeatInterceptor;
import com.sly.myplugin.preventrepeat.properties.PreventRepeatProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 重复提交配置类
 *
 * @author SLY
 * @date 2021/11/25
 */
@Import(PreventRepeatInterceptor.class)
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PreventRepeatProperties.class)
public class PreventRepeatConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreventRepeatConfig.class);

    @Autowired
    private PreventRepeatInterceptor preventRepeatInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.info("=====初始化反重复提交拦截器=====");
        // PreventRepeatInterceptor preventRepeatInterceptor = new PreventRepeatInterceptor();
        registry.addInterceptor(preventRepeatInterceptor).addPathPatterns("/**");
        LOGGER.info("=====反重复提交拦截器初始化完成=====");
    }
}
