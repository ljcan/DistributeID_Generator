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
//��������
public class AtomicorderServiceImpl implements OrderService{
	//ʹ��CAS��ʽ
    AtomicInteger num=new AtomicInteger(0);
	public String OrderId() throws Exception {
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYYmmDDHHMMss");
		return dateFormat.format(new Date())+"_"+num.addAndGet(1);
	}
	
	public String getOrderId() throws Exception {
		return OrderId();
	}

}
