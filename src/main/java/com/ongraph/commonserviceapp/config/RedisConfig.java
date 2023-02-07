package com.ongraph.commonserviceapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.ongraph.commonserviceapp.constants.Constant;
import com.ongraph.commonserviceapp.model.UserDetails;

@Configuration
public class RedisConfig {
	@Value("${spring.data.redis.host}")
	private String hostName;

	@Value("${spring.data.redis.port}")
	private int port;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration(this.hostName,this.port);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	
	@Bean(name =Constant.USER_DETAILS_REDIS_BEAN)
	public RedisTemplate<String, UserDetails> redisTemplate(){
		RedisTemplate<String, UserDetails> template=new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}
}
