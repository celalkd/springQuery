/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.config;

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
@Import( value = JedisConnectionConfig.class )
public class SpringRedisConfig {
    
    @Bean
    public RedisTemplate< String, String > redisTemplate(
        
            final JedisConnectionFactory connectionFactory ) {
        final RedisTemplate< String, String > redisTemplate = new RedisTemplate< String, String >();       
        
        redisTemplate.setConnectionFactory( connectionFactory );
        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setHashValueSerializer( new StringRedisSerializer() );
        redisTemplate.setHashKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer( new StringRedisSerializer() );
        redisTemplate.setStringSerializer( new StringRedisSerializer() );
        return redisTemplate;
    }
    //wire(bağ)'ın otomatik olarak kurulması için config içerisinde
    //inject edilecek(başka bir classın içinde objesi çağırılacak) classı döndüren method
    //inject etme işini yapan classın inject edilen classın objesiyle aynı isimde olması gerekir
    //yani configde xxx ismiyle yazılmış methodun gönderdiği x classı objesi, c classı objesini inject
    //etmiş olan y classı içinde xxx ismiyle tanımlanmış olmalıdır.
    //Üzerinde @Autowired bulunan obje bağlantı kurmak için, Config classındaki Bean'lerden
    //o obje ile aynı isimli methodu arar. Methodun döndürdüğü obje ile wire(bağ) kurulmuş olur.
 

}

