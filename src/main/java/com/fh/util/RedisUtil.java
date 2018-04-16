package com.fh.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	private static final String ip= "192.168.1.22";
    private static final int port=6379;
    private static Jedis jedis = new Jedis( ip, port);
	public static Jedis getJedis() {
		return jedis;
	}
	public static void setJedis(Jedis jedis) {
		RedisUtil.jedis = jedis;
	}
	

}
