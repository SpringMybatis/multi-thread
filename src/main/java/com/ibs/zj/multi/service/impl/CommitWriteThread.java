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
import com.ibs.zj.multi.service.WriteTaskService;

public class CommitWriteThread<T> implements PutTaskService<T>{

	
	private ThreadPoolContainer<FutureTask<RunRsp>> writePool;
	
	public CommitWriteThread(ThreadPoolContainer<FutureTask<RunRsp>> writePool) {
		this.writePool = writePool;
	}

	public void putTask(final WriteTaskService<T> doTaskService,final T runParams,final Semaphore semaphore,final CyclicBarrier cyclicBarrier,final CountDownLatch doneSignal,final ConcurrentHashMap<String,List<UserGoods>> dataMap){
		final RunRsp rsp = new RunRsp();
		rsp.setTableName(Thread.currentThread().getName());
		FutureTask<RunRsp> task = new FutureTask<RunRsp>(new Callable<RunRsp>() {
			public RunRsp call() throws Exception {
				boolean result = doTaskService.writeListToCsv(runParams, semaphore, cyclicBarrier,doneSignal,dataMap);
				// 设置操作结果信息
				rsp.setSuceess(result);
				return rsp;
			}
		});
		// 加入到容器
		try {
			writePool.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(runParams.toString()+"加入容器....");
		}
	}
	
	public void putTask(int groupId, DoTaskService<T> doTaskService,
			T runParam, Semaphore semaphore) {
	}

	public boolean getRunResult(int groupId) {
		return false;
	}

	public void cleanGroupOperation(int groupId) {
		
	}
}
