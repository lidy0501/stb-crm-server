package cn.stb.stbcrmserver.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


@Configuration
//@EnableAutoConfiguration
public class RedisConfig {

//	@Bean
//	@ConfigurationProperties(prefix = "spring.redis.jedis.pool")
//	public JedisPoolConfig getRedisConfig() {
//		JedisPoolConfig config = new JedisPoolConfig();
//		return config;
//	}
//
//	@Bean
//	@ConfigurationProperties(prefix = "spring.redis")
//	public JedisConnectionFactory getConnectionFactory() {
//		JedisConnectionFactory factory = new JedisConnectionFactory();
//		factory.setUsePool(true);
//		JedisPoolConfig config = getRedisConfig();
//		factory.setPoolConfig(config);
//		return factory;
//	}
//
//	@Bean
//	public RedisTemplate<?, ?> getRedisTemplate() {
//		JedisConnectionFactory factory = getConnectionFactory();
//		RedisTemplate<?, ?> template = new StringRedisTemplate(factory);
//		return template;
//	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
}
