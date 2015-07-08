package com.ibs.zj.multi.service.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import com.ibs.zj.multi.model.RunRsp;
import com.ibs.zj.multi.model.UserGoods;
import com.ibs.zj.multi.service.DoTaskService;
import com.ibs.zj.multi.service.PutTaskService;

public class CommitReadThread<E> implements PutTaskService<E>{

	/**
	 * 线程队列信息
	 */
	private ThreadPoolContainer<FutureTask<RunRsp>> threadPool;

	public CommitReadThread(ThreadPoolContainer<FutureTask<RunRsp>> threadPool) {
		this.threadPool = threadPool;
	}
	
	/**
	 * 添加任务
	 * 
	 * @param doTaskService 数据库操作接口 
	 * @param runParams     运行参数
	 * @param semaphore     信号量
	 * @param cyclicBarrier 栅栏
	 */
	public void putTask(final DoTaskService<E> doTaskService,final E runParams,final Semaphore semaphore,final CyclicBarrier cyclicBarrier,final CountDownLatch doneSignal,final ConcurrentHashMap<String,List<UserGoods>> dataMap){
		final RunRsp rsp = new RunRsp();
		rsp.setTableName(Thread.currentThread().getName());
		FutureTask<RunRsp> task = new FutureTask<RunRsp>(new Callable<RunRsp>() {
			public RunRsp call() throws Exception {
				boolean result = doTaskService.queryGoodsByName(runParams, semaphore, cyclicBarrier,doneSignal,dataMap);
				// 设置操作结果信息
				rsp.setSuceess(result);
				return rsp;
			}
		});
		// 加入到容器
		try {
			threadPool.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(runParams+"加入容器....");
		}
	}

	public void putTask(int groupId, DoTaskService<E> doTaskService,
			E runParam, Semaphore semaphore) {
		// TODO Auto-generated method stub
	}

	public boolean getRunResult(int groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void cleanGroupOperation(int groupId) {
		// TODO Auto-generated method stub
		
	}
	
}
