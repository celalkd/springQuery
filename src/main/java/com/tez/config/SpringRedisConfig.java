/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.config;

import com.tez.config.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author celalkd
 */
@Configuration
@Import( value = JedisConfig.class )
public class SpringRedisConfig {
    @Bean @Autowired
    public RedisTemplate< String, String > redisTemplate(
        final JedisConnectionFactory connectionFactory ) {
        final RedisTemplate< String, String > template = new RedisTemplate< String, String >();       
        
        template.setConnectionFactory( connectionFactory );
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new StringRedisSerializer() );
        template.setHashKeySerializer( new StringRedisSerializer() );
        template.setValueSerializer( new StringRedisSerializer() );
        template.setStringSerializer( new StringRedisSerializer() );
        return template;
    }

}

