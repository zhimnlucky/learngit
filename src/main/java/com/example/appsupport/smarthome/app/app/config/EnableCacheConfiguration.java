package com.example.appsupport.smarthome.app.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Qiuhua Lai
 * @email qhlai@gizwits
 */
@Configuration
@EnableCaching
public class EnableCacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public CacheManager redisCacheManager(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = new RedisCacheManager(objectRedisTemplate(redisConnectionFactory));
//        cacheManager.setDefaultExpiration(300);
        // 启动时加载远程缓存
        cacheManager.setLoadRemoteCachesOnStartup(true);
        //是否使用前缀生成器
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }

    @Bean("objectRedisTemplate")
    @Primary
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer gserializer=new GenericJackson2JsonRedisSerializer();
//        Jackson2JsonRedisSerializer<Object> serializer = jackson2JsonRedisSerializer();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(gserializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(gserializer);
        return redisTemplate;
    }


//    @Bean
//    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
//        final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
//                .json().build();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        return jackson2JsonRedisSerializer;
//    }

    /**
     * Cacheable key生成器
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuffer sb = new StringBuffer();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for(Object obj:params){
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}
