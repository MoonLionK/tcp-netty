package chc.dts.api.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

/**
 * @author fyh
 * @date 2024/06/14
 */
@Configuration(proxyBeanMethods = false)
public class RedisAutoConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       @Value("${spring.redis.transaction:false}") boolean isTransaction) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        template.setDefaultSerializer(fastJsonRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置支持multi操作
        template.setEnableTransactionSupport(isTransaction);

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "resourceRedisTemplate")
    public RedisTemplate<String, Map<String, String>> resourceRedisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                                            @Value("${spring.redis.transaction:false}") boolean isTransaction) {
        RedisTemplate<String, Map<String, String>> template = new RedisTemplate<>();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        template.setDefaultSerializer(fastJsonRedisSerializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置支持multi操作
        template.setEnableTransactionSupport(isTransaction);

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public ValueOperations<String, Object> vOps(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> lOps(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public HashOperations<String, String, Object> hOps(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public SetOperations<String, Object> sOps(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

}
