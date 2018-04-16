package com.fh.service.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 
 * 
 * 类名称：RedisCache 类描述：使用第三方缓存服务器redis，处理二级缓存 修改备注：
 * 
 * @version V1.0
 * 
 */
public class RedisCache implements Cache {

	private static Log log = LogFactory.getLog(RedisCache.class);
	/** The ReadWriteLock. */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	
	private String id;  
	  
    public RedisCache(final String id) {  
        if (id == null) {  
            throw new IllegalArgumentException("必须传入ID");  
        }  
        log.debug("MybatisRedisCache:id=" + id);  
        this.id = id;  
    }  
	
	public void clear() {
		Jedis jedis = null;  
        JedisPool jedisPool = null;  
        boolean borrowOrOprSuccess = true;  
        try {  
            jedis = CachePool.getInstance().getJedis();  
            jedisPool = CachePool.getInstance().getJedisPool();  
            jedis.flushDB();  
            jedis.flushAll();  
        } catch (JedisConnectionException e) {  
            borrowOrOprSuccess = false;  
            if (jedis != null)  
                jedisPool.returnBrokenResource(jedis);  
        }  
        finally {  
            if (borrowOrOprSuccess)  
                jedisPool.returnResource(jedis);  
        }  

	}

	public String getId() {
		 return this.id; 
	}

	public Object getObject(Object key) {
		Jedis jedis = null;  
        JedisPool jedisPool = null;  
        Object value = null;  
        boolean borrowOrOprSuccess = true;  
        try {  
            jedis = CachePool.getInstance().getJedis();  
            jedisPool = CachePool.getInstance().getJedisPool();  
            value = SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(key.hashCode())));  
        } catch (JedisConnectionException e) {  
            borrowOrOprSuccess = false;  
            if (jedis != null)  
                jedisPool.returnBrokenResource(jedis);  
        }  
        finally {  
            if (borrowOrOprSuccess)  
                jedisPool.returnResource(jedis);  
        }  
        if (log.isDebugEnabled())  
            log.debug("getObject:" + key.hashCode() + "=" + value);  
        return value;  
	}

	public ReadWriteLock getReadWriteLock() {
		   return readWriteLock;  
	}

	public int getSize() {
		Jedis jedis = null;  
        JedisPool jedisPool = null;  
        int result = 0;  
        boolean borrowOrOprSuccess = true;  
        try {  
            jedis = CachePool.getInstance().getJedis();  
            jedisPool = CachePool.getInstance().getJedisPool();  
            result = Integer.valueOf(jedis.dbSize().toString());  
        } catch (JedisConnectionException e) {  
            borrowOrOprSuccess = false;  
            if (jedis != null)  
                jedisPool.returnBrokenResource(jedis);  
        }  
        finally {  
            if (borrowOrOprSuccess)  
                jedisPool.returnResource(jedis);  
        }  
        return result;  
	}

	public void putObject(Object key, Object value) {
		if (log.isDebugEnabled())  
            log.debug("putObject:" + key.hashCode() + "=" + value);  
        if (log.isInfoEnabled())  
            log.info("put to redis sql :" + key.toString());  
        Jedis jedis = null;  
        JedisPool jedisPool = null;  
        boolean borrowOrOprSuccess = true;  
        try {  
            jedis = CachePool.getInstance().getJedis();  
            jedisPool = CachePool.getInstance().getJedisPool();  
            jedis.set(SerializeUtil.serialize(key.hashCode()), SerializeUtil.serialize(value));  
        } catch (JedisConnectionException e) {  
            borrowOrOprSuccess = false;  
            if (jedis != null)  
                jedisPool.returnBrokenResource(jedis);  
        }  
        finally {  
            if (borrowOrOprSuccess)  
                jedisPool.returnResource(jedis);  
        }  

	}

	public Object removeObject(Object key) {
		Jedis jedis = null;  
        JedisPool jedisPool = null;  
        Object value = null;  
        boolean borrowOrOprSuccess = true;  
        try {  
            jedis = CachePool.getInstance().getJedis();  
            jedisPool = CachePool.getInstance().getJedisPool();  
            value = jedis.expire(SerializeUtil.serialize(key.hashCode()), 0);  
        } catch (JedisConnectionException e) {  
            borrowOrOprSuccess = false;  
            if (jedis != null)  
                jedisPool.returnBrokenResource(jedis);  
        }  
        finally {  
            if (borrowOrOprSuccess)  
                jedisPool.returnResource(jedis);  
        }  
        if (log.isDebugEnabled())  
            log.debug("getObject:" + key.hashCode() + "=" + value);  
        return value;  
	}
	 /** 
     * 
    * 类名称：CachePool 
    * 类描述：(单例Cache池) 
    * 修改备注： 
    * @version V1.0 
    * 
     */  
    public static class CachePool {  
        JedisPool pool;  
        private static final CachePool cachePool = new CachePool();  
  
  
        public static CachePool getInstance() {  
            return cachePool;  
        }  
  
  
        private CachePool() {  
            JedisPoolConfig config = new JedisPoolConfig();  
            PropertiesLoader pl = new PropertiesLoader("classpath:redis.properties");  
            // 最大空闲连接数, 默认8个  
            config.setMaxIdle(Integer.parseInt(pl.getProperty("maxIdle")));  
            // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1  
            config.setMaxWaitMillis(Integer.parseInt(pl.getProperty("maxWaitMillis")));  
            pool = new JedisPool(config, pl.getProperty("redisvip"));  
        }  
  
  
        public Jedis getJedis() {  
            Jedis jedis = null;  
            boolean borrowOrOprSuccess = true;  
            try {  
                jedis = pool.getResource();  
            } catch (JedisConnectionException e) {  
                borrowOrOprSuccess = false;  
                if (jedis != null)  
                    pool.returnBrokenResource(jedis);  
            }  
            finally {  
                if (borrowOrOprSuccess)  
                    pool.returnResource(jedis);  
            }  
            jedis = pool.getResource();  
            return jedis;  
        }  
  
  
        public JedisPool getJedisPool() {  
            return this.pool;  
        }  
  
  
    }  
  
  
    public static class SerializeUtil {  
        public static byte[] serialize(Object object) {  
            ObjectOutputStream oos = null;  
            ByteArrayOutputStream baos = null;  
            try {  
                // 序列化  
                baos = new ByteArrayOutputStream();  
                oos = new ObjectOutputStream(baos);  
                oos.writeObject(object);  
                byte[] bytes = baos.toByteArray();  
                return bytes;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
  
        public static Object unserialize(byte[] bytes) {  
            if (bytes == null)  
                return null;  
            ByteArrayInputStream bais = null;  
            try {  
                // 反序列化  
                bais = new ByteArrayInputStream(bytes);  
                ObjectInputStream ois = new ObjectInputStream(bais);  
                return ois.readObject();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
    }  
}
