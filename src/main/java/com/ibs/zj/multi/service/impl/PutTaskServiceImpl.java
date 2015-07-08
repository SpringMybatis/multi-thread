package com.ibs.zj.multi.service.impl;

import java.util.concurrent.Semaphore;

import com.ibs.zj.multi.service.DoTaskService;
import com.ibs.zj.multi.service.PutTaskService;

public class PutTaskServiceImpl implements PutTaskService<String> {

	public void putTask(int groupId, DoTaskService<String> doTaskService,
			String runParam, Semaphore semaphore) {
		
	}

	public boolean getRunResult(int groupId) {
		return false;
	}

	public void cleanGroupOperation(int groupId) {
	}
	
}
