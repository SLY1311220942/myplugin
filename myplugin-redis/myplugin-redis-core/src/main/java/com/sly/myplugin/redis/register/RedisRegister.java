package com.sly.myplugin.redis.register;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * redis 注册类
 *
 * @author SLY
 * @date 2021/12/3
 */
@SuppressWarnings("unchecked")
public class RedisRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRegister.class);

    private static final String CONFIG_PREFIX = "plugin.redis.data-sources";

    private static final String REDIS_CLUSTER = "cluster";
    private static final String REDIS_CLUSTER_NODES = "nodes";
    private static final String REDIS_CLUSTER_MAX_REDIRECTS = "max-redirects";
    private static final String REDIS_PASSWORD = "password";
    private static final String REDIS_DATABASE = "database";
    private static final String REDIS_HOST = "host";
    private static final String REDIS_PORT = "port";

    private static final Map<String, Object> REGISTER_BEAN = new ConcurrentHashMap<>();

    private Binder binder;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, RedisProperties> dataSources;
        try {
            dataSources = binder.bind(CONFIG_PREFIX, Map.class).get();
        } catch (NoSuchElementException e) {
            LOGGER.error("配置redis失败:'plugin.redis'属性未在配置文件中配置");
            return;
        }
        // 默认第一个为Primary
        boolean onPrimary = true;
        for (String key : dataSources.keySet()) {
            Map<?, ?> map = binder.bind(CONFIG_PREFIX + "." + key, Map.class).get();
            // 获取redis config
            RedisConfiguration redisConfiguration = getRedisConfiguration(map);
            // 连接池配置
            GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
            try {
                RedisProperties.Pool pool = binder.bind(CONFIG_PREFIX + "." + key + ".lettuce.pool", RedisProperties.Pool.class).get();
                genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
                genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
                genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
                if (pool.getMaxWait() != null) {
                    genericObjectPoolConfig.setMaxWait(pool.getMaxWait());
                }
            } catch (NoSuchElementException e) {
                LOGGER.warn("获取lettuce.pool配置失败");
            }
            // LettuceConnectionFactory
            Supplier<LettuceConnectionFactory> lettuceConnectionFactorySupplier = () -> {
                LettuceConnectionFactory factory = (LettuceConnectionFactory) REGISTER_BEAN.get(key + "LettuceConnectionFactory");
                if (factory != null) {
                    return factory;
                }
                LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
                try {
                    Duration shutdownTimeout = binder.bind(CONFIG_PREFIX + "." + key + ".shutdown-timeout", Duration.class).get();
                    if (shutdownTimeout != null) {
                        builder.shutdownTimeout(shutdownTimeout);
                    }
                } catch (NoSuchElementException ignore) {
                }
                LettuceClientConfiguration clientConfiguration = builder.poolConfig(genericObjectPoolConfig).build();
                factory = new LettuceConnectionFactory(redisConfiguration, clientConfiguration);
                REGISTER_BEAN.put(key + "LettuceConnectionFactory", factory);
                return factory;
            };

            LettuceConnectionFactory lettuceConnectionFactory = lettuceConnectionFactorySupplier.get();
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LettuceConnectionFactory.class, lettuceConnectionFactorySupplier);
            AbstractBeanDefinition factoryBean = builder.getRawBeanDefinition();
            factoryBean.setPrimary(onPrimary);
            beanDefinitionRegistry.registerBeanDefinition(key + "LettuceConnectionFactory", factoryBean);
            // StringRedisTemplate
            GenericBeanDefinition stringRedisTemplate = new GenericBeanDefinition();
            stringRedisTemplate.setBeanClass(StringRedisTemplate.class);
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, lettuceConnectionFactory);
            stringRedisTemplate.setConstructorArgumentValues(constructorArgumentValues);
            stringRedisTemplate.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
            stringRedisTemplate.setPrimary(onPrimary);
            beanDefinitionRegistry.registerBeanDefinition(key + "StringRedisTemplate", stringRedisTemplate);
            // RedisTemplate
            GenericBeanDefinition redisTemplate = new GenericBeanDefinition();
            redisTemplate.setBeanClass(RedisTemplate.class);
            redisTemplate.getPropertyValues().add("connectionFactory", lettuceConnectionFactory);
            try {
                String keySerializer = "com.sly.myplugin.redis.serializer.RedisKeySerializer";
                if (StringUtils.hasText((String) map.get("keySerializer"))) {
                    keySerializer = (String) map.get("keySerializer");
                }
                String valueSerializer = "org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer";
                if (StringUtils.hasText((String) map.get("valueSerializer"))) {
                    valueSerializer = (String) map.get("valueSerializer");
                }
                String hashKeySerializer = "com.sly.myplugin.redis.serializer.RedisKeySerializer";
                if (StringUtils.hasText((String) map.get("hashKeySerializer"))) {
                    hashKeySerializer = (String) map.get("hashKeySerializer");
                }
                String hashValueSerializer = "org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer";
                if (StringUtils.hasText((String) map.get("hashValueSerializer"))) {
                    hashValueSerializer = (String) map.get("hashValueSerializer");
                }
                redisTemplate.getPropertyValues().add("keySerializer", Class.forName(keySerializer).newInstance());
                redisTemplate.getPropertyValues().add("valueSerializer", Class.forName(valueSerializer).newInstance());
                redisTemplate.getPropertyValues().add("hashKeySerializer", Class.forName(hashKeySerializer).newInstance());
                redisTemplate.getPropertyValues().add("hashValueSerializer", Class.forName(hashValueSerializer).newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            redisTemplate.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
            redisTemplate.setPrimary(onPrimary);
            beanDefinitionRegistry.registerBeanDefinition(key + "RedisTemplate", redisTemplate);
            LOGGER.info("Registration redis ({}) !", key);
            if (onPrimary) {
                onPrimary = false;
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        binder = Binder.get(environment);
    }

    /**
     * 获取redis配置
     *
     * @param map redis配置
     * @return {@link RedisConfiguration}
     * @author SLY
     * @date 2021/12/6
     */
    private RedisConfiguration getRedisConfiguration(Map<?, ?> map) {
        if (map.get(REDIS_CLUSTER) != null) {
            Map<?, ?> clusterMap = (Map<?, ?>) map.get(REDIS_CLUSTER);
            // 集群
            RedisClusterConfiguration configuration = new RedisClusterConfiguration();
            String[] nodes = String.valueOf(clusterMap.get(REDIS_CLUSTER_NODES)).split(",");
            List<RedisNode> redisNodeList = new ArrayList<>();
            for (String node : nodes) {
                String[] split = node.split(":");
                RedisNode redisNode = new RedisNode(split[0], Integer.parseInt(split[1]));
                redisNodeList.add(redisNode);
            }
            if (map.get(REDIS_PASSWORD) != null) {
                String password = String.valueOf(map.get(REDIS_PASSWORD));
                if (StringUtils.hasText(password)) {
                    RedisPassword redisPassword = RedisPassword.of(password);
                    configuration.setPassword(redisPassword);
                }
            }
            configuration.setClusterNodes(redisNodeList);
            if (clusterMap.get(REDIS_CLUSTER_MAX_REDIRECTS) != null) {
                configuration.setMaxRedirects(Integer.parseInt(String.valueOf(clusterMap.get(REDIS_CLUSTER_MAX_REDIRECTS))));
            }
            return configuration;
        } else {
            // 单机
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName(String.valueOf(map.get(REDIS_HOST)));
            configuration.setPort(Integer.parseInt(String.valueOf(map.get(REDIS_PORT))));
            configuration.setDatabase(Integer.parseInt(String.valueOf(map.get(REDIS_DATABASE))));
            if (map.get(REDIS_PASSWORD) != null) {
                String password = String.valueOf(map.get(REDIS_PASSWORD));
                if (StringUtils.hasText(password)) {
                    RedisPassword redisPassword = RedisPassword.of(password);
                    configuration.setPassword(redisPassword);
                }
            }

            return configuration;
        }
    }


}
