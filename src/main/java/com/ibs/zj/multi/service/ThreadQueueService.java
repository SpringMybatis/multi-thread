package com.ibs.zj.multi.service;

public interface ThreadQueueService<E> {
	
	/**
	 * 将数据放入到队列中
	 * @param item 选项信息
	 * @throws InterruptedException
	 */
	public void put(E item) throws InterruptedException;

	/**
	 * 从获取数据
	 * 
	 * @return 元素信息
	 * @throws InterruptedException
	 *             异常
	 */
	public E takeLast() throws InterruptedException;
	
}
