package com.sly.myplugin.redis.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * redis多数据源属性类
 *
 * @author SLY
 * @date 2021/11/30
 */
@ConfigurationProperties(prefix = "plugin.redis")
@ConditionalOnProperty("plugin.redis.data-sources")
public class RedisMultipleDataSourceProperties {

    /** redis配置 */
    private Map<String, RedisProperties> dataSources = new LinkedHashMap<>(16);

    public Map<String, RedisProperties> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, RedisProperties> dataSources) {
        this.dataSources = dataSources;
    }

}
