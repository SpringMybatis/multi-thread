package com.ibs.zj.multi.service.impl;

import java.util.concurrent.FutureTask;

import com.ibs.zj.multi.model.RunRsp;

public class ThreadRunOperation implements Runnable {

	private ThreadPoolContainer<FutureTask<RunRsp>> thredPool;
	
	public ThreadRunOperation(ThreadPoolContainer<FutureTask<RunRsp>> thredPool){
		this.thredPool = thredPool;
	}
	
	public void run() {
		while(true){
			FutureTask<RunRsp> futureTask = null;
			try {
				futureTask = thredPool.takeLast();
				futureTask.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
