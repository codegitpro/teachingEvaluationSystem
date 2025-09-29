package com.geqian.evalution.teachingevalution.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author geqian
 * @date 18:38 2023/11/15
 */
@Configuration
public class CaffeineCacheConfig {

    @Bean
    public LoadingCache<String, Object> loadingCache() {
        return Caffeine.newBuilder()
                //设置缓存中保存数据的最大量
                .maximumSize(Integer.MAX_VALUE)
                //初始化缓存空间大小
                .initialCapacity(100)
                //构建Cache实例
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String s) throws Exception {
                        return null;
                    }
                });
    }

}
