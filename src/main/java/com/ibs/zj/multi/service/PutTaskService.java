package com.ibs.zj.multi.service;

import java.util.concurrent.Semaphore;

public interface PutTaskService<E> {

	
	/**
	 * putTask
	 * 
	 * @param groupId
	 * @param doTaskService
	 * @param runParam
	 * @param sema
	 */
	public void putTask(int groupId, final DoTaskService<E> doTaskService, final E runParam, final Semaphore semaphore);
	
	
	/**
	 * 获取运行结果
	 * 
	 * @param groupId
	 * @return
	 */
	public boolean getRunResult(int groupId);
	
	
	
	/**
	 * 清除运行结果
	 * 
	 * @param groupId
	 */
	public void cleanGroupOperation(int groupId);
}
