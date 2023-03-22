/**
 * Copyright (C), 2015-2023, XXX有限公司
 * FileName: RedisConfigCacheConfiguration
 * Author:   Derek Xu
 * Date:     2023/3/22 16:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Derek Xu         修改时间           版本号              描述
 */
package cn.com.xuct.group.purchase.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Derek Xu
 * @create 2023/3/22
 * @since 1.0.0
 */

@Slf4j
public class RedisConfigCacheManager extends RedisCacheManager {


    public RedisConfigCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    private static final RedisSerializationContext.SerializationPair<Object> DEFAULT_PAIR = RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer());

    private static final CacheKeyPrefix DEFAULT_CACHE_KEY_PREFIX = cacheName -> cacheName + ":";

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        final int lastIndexOf = StringUtils.lastIndexOf(name, '#');
        if (lastIndexOf > -1) {
            final String ttl = StringUtils.substring(name, lastIndexOf + 1);
            final Duration duration = Duration.ofSeconds(Long.parseLong(ttl));
            cacheConfig = cacheConfig.entryTtl(duration);
            //修改缓存key和value值的序列化方式
            cacheConfig = cacheConfig.computePrefixWith(DEFAULT_CACHE_KEY_PREFIX).serializeValuesWith(DEFAULT_PAIR);
            final String cacheName = StringUtils.substring(name, 0, lastIndexOf);
            return super.createRedisCache(cacheName, cacheConfig);
        } else {
            //修改缓存key和value值的序列化方式
            cacheConfig = cacheConfig.computePrefixWith(DEFAULT_CACHE_KEY_PREFIX).serializeValuesWith(DEFAULT_PAIR);
            return super.createRedisCache(name, cacheConfig);
        }
    }
}