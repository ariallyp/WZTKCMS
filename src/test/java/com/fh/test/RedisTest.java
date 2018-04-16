package com.fh.test;


import redis.clients.jedis.Jedis;

public class RedisTest {
	private static final String ip= "127.0.0.1";
    private static final int port=6379;
    protected static Jedis jedis = new Jedis( ip, port);;
	public static void main(String[] args) {
		jedis.set("open_close_chat_key", "open");
		jedis.set("open_close_skin_key", "open");
		
		System.out.println(jedis.get("open_close_chat_key"));
		
	}
	

}
