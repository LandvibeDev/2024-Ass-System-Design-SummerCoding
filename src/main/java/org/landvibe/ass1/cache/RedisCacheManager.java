package org.landvibe.ass1.cache;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCacheManager implements CacheManager {

    private final RedisTemplate<String, Object> redisTemplate;


    private long ttl = 60 * 10;

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }

    @Override
    public void evict(String key) {
        redisTemplate.delete(key);
    }
}