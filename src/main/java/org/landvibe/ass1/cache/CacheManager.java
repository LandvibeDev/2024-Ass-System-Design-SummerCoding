package org.landvibe.ass1.cache;

public interface CacheManager {

    Object get(String key);

    void put(String key, Object value);

    void evict(String key);
}