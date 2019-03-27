package cn.just.shinelon.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.curator.framework.CuratorFramework;

import cn.just.shinelon.service.impl.AtomicorderServiceImpl;

public class OrderServiceTask implements Runnable{
	OrderService orderService;
	CountDownLatch latch;
	CuratorFramework client;
	public OrderServiceTask(OrderService orderService,CountDownLatch latch) {
		this.orderService=orderService;
		this.latch=latch;
	}
	public OrderServiceTask(OrderService orderService,CountDownLatch latch,CuratorFramework client) {
		this.client=client;
		this.orderService=orderService;
		this.latch=latch;
	}

	public void run() {
		try {
//			try {
//				latch.await();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			String orderId=orderService.getOrderId();
			System.out.println(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
