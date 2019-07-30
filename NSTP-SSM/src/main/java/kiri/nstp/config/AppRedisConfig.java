package kiri.nstp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@PropertySource("classpath:property/redis.properties")
public class AppRedisConfig {

	public AppRedisConfig() {
		System.out.println("redis init");
	}
	
	@Bean("jedisPoolConfig")
	public JedisPoolConfig jpc(@Value("${redis.maxIdle}") int maxIdle,
			@Value("${redis.maxWait}") long maxWait,
			@Value("${redis.testOnBorrow}") boolean testOnBorrow,
			@Value("${redis.maxTotal}") int maxTotal,
			@Value("${redis.minIdle}") int minIdle) {
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxIdle(maxIdle);
		jpc.setMaxTotal(maxTotal);
		jpc.setMaxWaitMillis(maxWait);
		jpc.setMinIdle(minIdle);
		jpc.setTestOnBorrow(testOnBorrow);
		return jpc;
	}
	//Also set Redis Security
	@SuppressWarnings("deprecation")
	@Bean("jedisConnectionFactory")
	public JedisConnectionFactory jcf(
			@Value("${redis.host}") String hostName,
			@Value("${redis.port}") int port,
			@Value("${redis.password}") String password,
			@Value("${redis.timeout}") int timeout,
			@Value("${redis.database}") int index,
			JedisPoolConfig jpc) {
		JedisConnectionFactory jcf = new JedisConnectionFactory();
		jcf.setTimeout(timeout);
		jcf.setPoolConfig(jpc);
		jcf.setDatabase(index);
		jcf.setPassword(password);
		jcf.setPort(port);
		jcf.setHostName(hostName);
		return jcf;
	}
	@Bean(name="redisTemplate")
	public RedisTemplate<?, ?> getRedisTemplate(JedisConnectionFactory jcf){
		RedisTemplate<?, ?> template = new StringRedisTemplate(jcf);
		return template;
	}
}
