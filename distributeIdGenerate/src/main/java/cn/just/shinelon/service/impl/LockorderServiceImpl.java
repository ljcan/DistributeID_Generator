package cn.just.shinelon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import cn.just.shinelon.service.OrderService;
//订单服务
public class LockorderServiceImpl implements OrderService{
	Lock lock;
	public LockorderServiceImpl(Lock lock) {
		this.lock=lock;
	}
	
	int num;
	public String OrderId(){
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYYmmDDHHMMss");
		return dateFormat.format(new Date())+"_"+num++;
	}
	//使用显示锁
	public String getOrderId() {
		lock.lock();
		String id=OrderId();
		lock.unlock();
		return id;
	}

}
