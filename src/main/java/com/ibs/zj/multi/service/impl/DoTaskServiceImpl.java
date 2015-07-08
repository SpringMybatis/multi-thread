package com.ibs.zj.multi.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibs.zj.multi.dao.DoTaskDAO;
import com.ibs.zj.multi.model.UserGoods;
import com.ibs.zj.multi.service.DoTaskService;
@Service
public class DoTaskServiceImpl implements DoTaskService<String> {

	@Autowired
	private DoTaskDAO doTaskDAO;
	
	public Boolean queryGoodsByName(String runParams,Semaphore semaphore,CyclicBarrier cyclicBarrier,CountDownLatch doneSignal,ConcurrentHashMap<String,List<UserGoods>> dataMap) {
		Boolean flag = false;
		List<UserGoods> list = null;
		try {
			semaphore.acquire();
			list = doTaskDAO.queryUserGoodsByName(runParams);
			if(list!=null && !list.isEmpty()){
				flag = true;
				for(UserGoods good:list){
					System.out.println(good.toString());
				}
				System.out.println(runParams+"执行完成...等待其他小伙伴!");
				dataMap.put(runParams, list);
			}else{
				System.out.println(runParams+" 没有商品!");
			}
			semaphore.release();
			doneSignal.countDown();
			cyclicBarrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flag;
	}

}
