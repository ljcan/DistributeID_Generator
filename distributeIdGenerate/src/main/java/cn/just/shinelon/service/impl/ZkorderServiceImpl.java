package cn.just.shinelon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.InternalACLProvider;

import cn.just.shinelon.service.OrderService;
//订单服务
public class ZkorderServiceImpl implements OrderService{
	CuratorFramework client;
	public ZkorderServiceImpl(CuratorFramework client){
		this.client=client;
	}
	
	int num;
	public String OrderId(){
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYYmmDDHHMMss");
		return dateFormat.format(new Date())+"_"+num++;
	}
	/**
	 * 使用ZK分布式锁
	 * @param client
	 * @return
	 * @throws Exception
	 */
	public String getOrderId() throws Exception {
		InterProcessMutex mutex=new InterProcessMutex(client, "/OrderId");
		mutex.acquire();
		String id=OrderId();
		mutex.release();
		return id;
	}
}
