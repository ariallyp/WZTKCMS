package com.fh.service.redis;

import org.springframework.beans.factory.annotation.Autowired;




import com.fh.util.Logger;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisDataSourceImpl implements RedisDataSource {   
    
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired    
    private ShardedJedisPool shardedJedisPool;    
        
    public ShardedJedisPool getShardedJedisPool() {    
        return shardedJedisPool;    
    }    
    
    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {    
        this.shardedJedisPool = shardedJedisPool;    
    }    
    
    /**  
     * 获取redis客户端  
     */    
    public ShardedJedis getRedisClient() {    
        try {    
            ShardedJedis shardedJedis=shardedJedisPool.getResource();    
            return shardedJedis;    
        } catch (Exception e) {    
            log.error("getRedisClient ,error",e);    
            e.printStackTrace();    
        }    
        return null;    
    }    
    
    /**  
     * 将资源返还给pool  
     */    
    @SuppressWarnings("deprecation")    
    public void returnResource(ShardedJedis shardedJedis) {    
        shardedJedisPool.returnResource(shardedJedis);    
            
    }    
    
    /**  
     * 出现异常后返回资源给pool  
     */    
    @SuppressWarnings("deprecation")    
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {    
        if(broken){    
            shardedJedisPool.returnBrokenResource(shardedJedis);    
        }else{    
            shardedJedisPool.returnResource(shardedJedis);    
        }    
            
    }    }
