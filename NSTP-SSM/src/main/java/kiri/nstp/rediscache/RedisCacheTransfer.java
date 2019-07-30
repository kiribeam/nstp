package kiri.nstp.rediscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheTransfer {
	@Autowired
	public void setJedisConnectionFactory(JedisConnectionFactory jcf) {
		MybatisRedisCache.setJedisConnectionFactory(jcf);
	}

}
