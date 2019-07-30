package kiri.nstp.rediscache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class MybatisRedisCache implements Cache{

	private static JedisConnectionFactory jcf;
	
	private final String id;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public MybatisRedisCache(String id) {
		if(id==null)
			throw new IllegalArgumentException("Cache instance Require an ID");
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		RedisConnection con = null;
		try {
			con = jcf.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			con.set(serializer.serialize(key), serializer.serialize(value));
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) con.close();
		}
		
	}

	@Override
	public Object getObject(Object key) {
		Object result = null;
		RedisConnection con = null;
		try {
			con = jcf.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = serializer.deserialize(con.get(serializer.serialize(key)));
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) con.close();
		}
		return result;
	}

	@Override
	public Object removeObject(Object key) {
		Object result = null;
		RedisConnection con = null;
		try {
			con = jcf.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = con.expire(serializer.serialize(key), 0);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) con.close();
		}
		return result;
	}

	@Override
	public void clear() {
		RedisConnection con = null;
		try {
			con = jcf.getConnection();
			con.flushDb();
			con.flushAll();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) con.close();
		}
	}

	@Override
	public int getSize() {
		int result = 0;
		RedisConnection con = null;
		try {
			con = jcf.getConnection();
			result = Integer.valueOf(con.dbSize().toString());
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) con.close();
		}
		return result;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
	
	public static void setJedisConnectionFactory(JedisConnectionFactory jcff) {
		jcf = jcff;
	}

}
