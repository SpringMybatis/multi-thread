package com.ibs.zj.multi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Service;

import com.csvreader.CsvWriter;
import com.ibs.zj.multi.model.UserGoods;
import com.ibs.zj.multi.service.WriteTaskService;

@Service
public class WriteTaskServiceImpl implements WriteTaskService<String[]> {

	private static final String PATH = "E:\\ThreadTest.csv";
	
	private static int index = 0;
	// 网文件里面些数据
	private	FileOutputStream out = null;
	
	private CsvWriter writer = null;
	
	private char separator =',';
	
	public Boolean writeListToCsv(String[] runParams, Semaphore semaphore,
			CyclicBarrier cyclicBarrier, CountDownLatch doneSignal,
			ConcurrentHashMap<String, List<UserGoods>> dataMap) {
		Boolean flag = false;
		try {
			semaphore.acquire();
			for(int i=0;i<runParams.length;i++){
				List<UserGoods> list = dataMap.get(runParams[i]);
				if(list!=null && !list.isEmpty()){
					this.wirteUserGoodsToCsv(list);
				}else{
					System.out.println(runParams[i]+" 没有商品!");
				}
			}
			semaphore.release();
			doneSignal.countDown();
			cyclicBarrier.await();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flag;
	}

	
	/**
	 * 写入数据到csv
	 * 
	 * @param list
	 * @throws Exception 
	 */
	public synchronized void wirteUserGoodsToCsv(List<UserGoods> list) throws Exception{
		// 首先确定文件是不是存在
		File file = new File(PATH);
		if(!file.exists()){
			file.createNewFile();
		}
		if(out==null){
			out = new FileOutputStream(file, true);
			writer = new CsvWriter(out, separator, Charset.forName("GBK"));
		}
		// 表头
		if(index==0){
			writer.writeRecord(new String[]{"商品ID","用户名","商品名","商品价格","商品信息"});
		}
		// 表体
		for(UserGoods userGoods:list){
			writer.writeRecord(new String[]{String.valueOf(userGoods.getId()),
					userGoods.getUserName(),userGoods.getGoodName(),
					String.valueOf(userGoods.getGoodPrice()),userGoods.getGoodInfo()});
		}
		index++;
		if(index%6==0){
			if (null != writer) {
				writer.close();
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					throw e;
				}
			}
			index = 0;
		}
	}
}
