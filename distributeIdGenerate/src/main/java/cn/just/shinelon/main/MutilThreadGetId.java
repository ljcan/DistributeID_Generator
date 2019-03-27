package cn.just.shinelon.main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import cn.just.shinelon.service.OrderServiceTask;
import cn.just.shinelon.service.OrderService;
import cn.just.shinelon.service.impl.AtomicorderServiceImpl;
import cn.just.shinelon.service.impl.LockorderServiceImpl;
import cn.just.shinelon.service.impl.RedisorderServiceImpl;
import cn.just.shinelon.service.impl.ZkorderServiceImpl;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MutilThreadGetId {
	
	static CuratorFramework client;
	static JedisPool pool;
	static {
			client=CuratorFrameworkFactory
			.builder()
			.connectString("192.168.217.111:2181")
			.sessionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(5000, 10000))
			.build();
			client.start();
			
			JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxTotal(10);
			pool=new JedisPool(config,"127.0.0.1",6379,5000);
			
	}
	static OrderService orderService;
	public static void main(String[] args) {
		ExecutorService service=Executors.newCachedThreadPool();
		final CountDownLatch latch=new CountDownLatch(1);
		orderService=new AtomicorderServiceImpl();
		orderService=new LockorderServiceImpl(new ReentrantLock());
		orderService=new ZkorderServiceImpl(client);
		orderService=new RedisorderServiceImpl(pool);
		for(int i=0;i<10;i++) {
			service.submit(new OrderServiceTask(orderService, latch));
		}
		latch.countDown();
		service.shutdown();
	}
}
