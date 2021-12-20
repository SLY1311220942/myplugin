package com.sly.myplugin.preventrepeat.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 防重复提交token
 *
 * @author SLY
 * @date 2021/12/6
 */
@ConfigurationProperties(prefix = "plugin.prevent-repeat")
public class PreventRepeatProperties {
    /**
     * token key
     */
    private String tokenKey;


    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }
}
