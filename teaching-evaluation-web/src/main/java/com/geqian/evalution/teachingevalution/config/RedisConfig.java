//package com.geqian.evalution.teachingevalution.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @author geqian
// * @date 11:11 2023/1/12
// */
//@Configuration
//public class RedisConfig {
//    @Bean
//    @Primary
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        // key序列化
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        // value序列化
//        redisTemplate.setValueSerializer(jsonRedisSerializer);
//        // hash类型 key序列化
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        // hash类型 value序列化
//        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
//
//        // 注入连接工厂
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//}
