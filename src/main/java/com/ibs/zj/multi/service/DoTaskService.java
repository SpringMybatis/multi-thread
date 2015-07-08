package com.ibs.zj.multi.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import com.ibs.zj.multi.model.UserGoods;

public interface DoTaskService<E> {
	
	public Boolean queryGoodsByName(E runParams,Semaphore semaphore,CyclicBarrier cyclicBarrier,CountDownLatch doneSignal, ConcurrentHashMap<String, List<UserGoods>> dataMap);
	
}
