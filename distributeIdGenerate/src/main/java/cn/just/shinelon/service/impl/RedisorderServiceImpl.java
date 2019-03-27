package cn.just.shinelon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.just.shinelon.service.OrderService;
import redis.clients.jedis.JedisPool;
//¶©µ¥·þÎñ
public class RedisorderServiceImpl implements OrderService{
	JedisPool pool;
	public RedisorderServiceImpl(JedisPool pool) {
		this.pool=pool;
	}
	
	public String getOrderId() throws Exception {
		return OrderId();
	}

	public String OrderId() throws Exception {
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYYmmDDHHMMss");
		return dateFormat.format(new Date())+"_"+pool.getResource().incr("orderId2");
	}
}
