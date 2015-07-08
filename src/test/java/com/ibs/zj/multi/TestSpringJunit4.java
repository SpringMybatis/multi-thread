package com.ibs.zj.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibs.zj.multi.model.RunRsp;
import com.ibs.zj.multi.model.UserGoods;
import com.ibs.zj.multi.service.DoTaskService;
import com.ibs.zj.multi.service.WriteTaskService;
import com.ibs.zj.multi.service.impl.CommitReadThread;
import com.ibs.zj.multi.service.impl.CommitWriteThread;
import com.ibs.zj.multi.service.impl.ThreadPoolContainer;
import com.ibs.zj.multi.service.impl.ThreadRunOperation;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/spring/applicationContext*.xml"})
public class TestSpringJunit4 {

	private int maxThreadReadPoolSize = 10;

	private int maxThreadWritePoolSize = 5;

	private ExecutorService executors = Executors.newFixedThreadPool(maxThreadReadPoolSize + maxThreadWritePoolSize);

	// 读的队列（线程任务）
	ThreadPoolContainer<FutureTask<RunRsp>> readPool = new ThreadPoolContainer<FutureTask<RunRsp>>();

	// 写的队列（线程任务）
	ThreadPoolContainer<FutureTask<RunRsp>> writePool = new ThreadPoolContainer<FutureTask<RunRsp>>();

	// put读线程
	CommitReadThread<String> readPut = new CommitReadThread<String>(readPool);
	
	// write写线程
	CommitWriteThread<String[]> writePut = new CommitWriteThread<String[]>(writePool);
	
	// 线程实现操作
	@Autowired
	DoTaskService<String> doTaskService;
	
	@Autowired
	WriteTaskService<String[]> writeTaskService;

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
	
	@Test
	public void testThread() throws Exception {
		// 初始化线程容器
		this.threadInit();
		// 数据存储的Map
		ConcurrentHashMap<String,List<UserGoods>> dataMap = new ConcurrentHashMap<String, List<UserGoods>>();
		// 定义读取的信号量--假设4个
		Semaphore semaphore = new Semaphore(4);
		// 线程执行完毕信号
		CountDownLatch doneSignal = new CountDownLatch(6); 
		// 控制栅栏在主线程之前结束
		final CountDownLatch start = new CountDownLatch(1); 
		// 设置一个栅栏
		CyclicBarrier cyclicBarrier = new CyclicBarrier(6, new Runnable() {
			public void run(){
				System.out.println("读取操作完毕,开始写入....");
				start.countDown();
			}
		});
		// 假设有六个人的货物
		String[] users = new String[] { "Lili", "Amy", "John", "Lee", "James","Blike" };
		for (int i = 0; i < users.length; i++) {
			readPut.putTask(doTaskService, users[i], semaphore, cyclicBarrier,doneSignal,dataMap);
		}
		doneSignal.await();
		// cyclicBarrier.await();
		start.await();
		System.out.println("read success.....");
		
		
		
		
		
		// 定义写的信号量--假设2个
		Semaphore writeSemaphore = new Semaphore(2);
		// 线程执行完毕信号
		CountDownLatch writeSignal = new CountDownLatch(3); 
		// 控制栅栏在主线程之前结束
		final CountDownLatch witeStart = new CountDownLatch(1); 
		// 设置一个栅栏
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
			public void run(){
				System.out.println("写入完毕,csv生成完成....");
				witeStart.countDown();
			}
		});
		// 写线程put到容器
		List<String[]> writeList = new ArrayList<String[]>();
		writeList.add(new String[]{"Lili"});
		writeList.add(new String[]{"Amy","John","Lee"});
		writeList.add(new String[]{"James","Blike"});
		for(String[] arr:writeList){
			writePut.putTask(writeTaskService, arr, writeSemaphore, barrier, writeSignal, dataMap);
		}
		// 保证主线程在后面执行
		writeSignal.await();
		witeStart.await();
		System.out.println("write success.....");
	}
	
}
