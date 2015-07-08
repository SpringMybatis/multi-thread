package com.ibs.zj.multi.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibs.zj.multi.model.RunRsp;
import com.ibs.zj.multi.model.UserGoods;
import com.ibs.zj.multi.service.DoTaskService;
import com.ibs.zj.multi.service.impl.CommitReadThread;
import com.ibs.zj.multi.service.impl.ThreadPoolContainer;
import com.ibs.zj.multi.service.impl.ThreadRunOperation;

@Controller
@RequestMapping(value = "/thread")
@Scope("prototype")
public class ThreadMainController {

	private int maxThreadReadPoolSize = 10;

	private int maxThreadWritePoolSize = 5;

	private ExecutorService executors = Executors.newFixedThreadPool(maxThreadReadPoolSize + maxThreadWritePoolSize);

	// 读的队列（线程任务）
	ThreadPoolContainer<FutureTask<RunRsp>> readPool = new ThreadPoolContainer<FutureTask<RunRsp>>();

	// 写的队列（线程任务）
	ThreadPoolContainer<FutureTask<RunRsp>> writePool = new ThreadPoolContainer<FutureTask<RunRsp>>();

	// put写线程
	CommitReadThread<String> readPut = new CommitReadThread<String>(readPool);

	// 线程实现操作
	@Autowired
	DoTaskService<String> doTaskService;

	public void threadInit() {
		// 读的队列
		for (int i = 0; i < maxThreadReadPoolSize; i++) {
			executors.submit(new ThreadRunOperation(readPool));
		}
		// 写的队列
		for (int i = 0; i < maxThreadWritePoolSize; i++) {
			executors.submit(new ThreadRunOperation(writePool));
		}
	}

	@RequestMapping(value = "/queryUserGoods.html")
	public void queryUserGoods(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}

	public void testThread() throws Exception {
		// 初始化线程容器
		this.threadInit();
		// 定义读取的信号量--假设4个
		Semaphore semaphore = new Semaphore(4);
		// 设置一个栅栏
		CyclicBarrier cyclicBarrier = new CyclicBarrier(6, new Runnable() {
			public void run() {
				System.out.println("读取操作完毕,开始写入....");
			}
		});
		// 线程执行完毕信号
		CountDownLatch doneSignal = new CountDownLatch(6);  
		// 数据存储的Map
		ConcurrentHashMap<String,List<UserGoods>> dataMap = new ConcurrentHashMap<String, List<UserGoods>>(); 
		// 假设有六个人的货物
		String[] users = new String[] { "Lili", "Amy", "John", "Lee", "James","Blike" };
		for (int i = 0; i < users.length; i++) {
			readPut.putTask(doTaskService, users[i], semaphore, cyclicBarrier,doneSignal,dataMap);
		}
		System.out.println("success");
	}

}
