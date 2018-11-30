package com.fh.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.wztk.SysConfig;

import oracle.net.aso.l;
import redis.clients.jedis.Jedis;

public class RedisTest {
	private static final String ip= "127.0.0.1";
    private static final int port=6379;
    protected static Jedis jedis = new Jedis( ip, port);;
	
	public static void main(String[] args) {
		SysConfig sysConfig = new SysConfig("wztk_1","111");
		SysConfig sysConfig1 = new SysConfig("wztk_2","222");
		SysConfig sysConfig2 = new SysConfig("wztk_3","open");
		SysConfig sysConfig3 = new SysConfig("wztk_4","false");
		
		
		
		List<SysConfig> list = new ArrayList<>();
		list.add(sysConfig);
		list.add(sysConfig1);
		list.add(sysConfig2);
		list.add(sysConfig3);
	
		
		String jString = JSON.toJSONString(list);
		
		jedis.set("wiztalk_sys_config", jString);
		System.out.println(jedis.get("jString"));
        
        String value = jedis.get("sys_test2");
     // json to list<map<>> 写成工具方法
        List<SysConfig> syslist =JSONObject.parseArray(value, SysConfig.class);
         sysConfig = new SysConfig("wztk_4","5000");
        list.add(sysConfig);
        System.err.println(list.toString());
        
        Map<String, String> map = new HashMap<String, String>();
                map.put("name", "xinxin");
                map.put("age", "22");
                 map.put("qq", "123456");
                 jedis.hmset("user",map);
                 //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List  
                 //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
                 List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
                 System.out.println(rsmap);  
                // jedis.del("wiztalk_sys_config");
		
	}

	
	public static Map<String, Object> jsonToMap(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		Map<String, Object> map = new HashMap<>();
		Set<String> keys = jsonObject.keySet();
		for (String key : keys) {
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	public static List<Map<String, Object>> jsonToList(String jsonList) {
		List<Map<String, Object>> list = new ArrayList<>();
		JSONArray parseArray = JSONArray.parseArray(jsonList);
		for (Object object : parseArray) {
			String jsonString = JSON.toJSONString(object);
			Map<String, Object> map = jsonToMap(jsonString);
			list.add(map);
		}
		return list;
	}
}
