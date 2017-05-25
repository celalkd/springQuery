/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 *
 * @author celalkd
 */
public class JedisConnectionConfig {
    
    final static int TR_DATABASE = 0;
    final static int ENG_DATABASE = 1;
    
    @Bean
    public JedisPoolConfig poolConfig() {
        final JedisPoolConfig jedisPoolConfig =  new JedisPoolConfig(); 
        
        jedisPoolConfig.setTestOnBorrow( true );
        jedisPoolConfig.setMaxTotal(10);
        
        return jedisPoolConfig;
    }
    
    @Bean
    public JedisConnectionFactory connectionFactory() {
        final JedisConnectionFactory connectionFactory = new JedisConnectionFactory( poolConfig() );    
        
        connectionFactory.setHostName( "localhost" );
        connectionFactory.setDatabase(ENG_DATABASE);
        connectionFactory.setPort( Protocol.DEFAULT_PORT );       
        
        return connectionFactory;
    }
    

}
