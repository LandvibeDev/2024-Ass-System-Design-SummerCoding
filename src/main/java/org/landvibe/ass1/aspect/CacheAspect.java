package org.landvibe.ass1.aspect;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.landvibe.ass1.annotation.CacheoutLandvibe;
import org.landvibe.ass1.annotation.CachingLandvibe;
import org.landvibe.ass1.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {

    private final RedisCacheManager redisCacheManager;

    @Around("@annotation(org.landvibe.ass1.annotation.CachingLandvibe)")
    public Object cacheAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CachingLandvibe cachingLandvibe = method.getAnnotation(CachingLandvibe.class);

        String key = generateKey(cachingLandvibe.key(), joinPoint.getArgs());
        Object cachedValue = redisCacheManager.get(key);

        if (cachedValue != null) {
            System.out.println("Cache hit for key: " + key);
            return cachedValue;
        }

        Object result = joinPoint.proceed();
        redisCacheManager.put(key, result);
        System.out.println("Cache miss for key: " + key + ". Fetching from DB...");

        return result;
    }

    @Around("@annotation(org.landvibe.ass1.annotation.CacheoutLandvibe)")
    public Object cacheOutAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CacheoutLandvibe cacheoutLandvibe = method.getAnnotation(CacheoutLandvibe.class);

        String key = generateKey(cacheoutLandvibe.key(), joinPoint.getArgs());
        redisCacheManager.evict(key);
        System.out.println("Cache Evict key: " + cacheoutLandvibe.key());
        return joinPoint.proceed();
    }

    private String generateKey(String keyPattern, Object[] args) {
        String key = keyPattern;
        for (int i = 0; i < args.length; i++) {
            key = key.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return key;
    }
}