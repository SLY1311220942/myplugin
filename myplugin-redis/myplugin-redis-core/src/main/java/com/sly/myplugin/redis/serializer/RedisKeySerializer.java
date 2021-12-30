package com.sly.myplugin.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * @author SLY
 * @date 2021/12/30
 */
public class RedisKeySerializer implements RedisSerializer<Serializable> {

    private final static Charset CHARSET_UFT8 = Charset.forName("UTF8");

    @Override
    public byte[] serialize(Serializable serializable) throws SerializationException {
        if (serializable instanceof String) {
            String s = (String) serializable;
            return s.getBytes(CHARSET_UFT8);
        } else if (serializable instanceof Integer) {
            String s = String.valueOf((Integer) serializable);
            return (s == null ? null : s.getBytes(CHARSET_UFT8));
        } else if (serializable instanceof Long) {
            String s = String.valueOf((Long) serializable);
            return (s == null ? null : s.getBytes(CHARSET_UFT8));
        } else {
            String s = String.valueOf(serializable);
            return (s == null ? null : s.getBytes(CHARSET_UFT8));
        }
    }

    @Override
    public Serializable deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, CHARSET_UFT8));
    }
}
