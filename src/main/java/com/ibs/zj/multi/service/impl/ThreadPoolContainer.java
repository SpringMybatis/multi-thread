package com.ibs.zj.multi.service.impl;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ibs.zj.multi.service.ThreadQueueService;

public class ThreadPoolContainer<E> implements ThreadQueueService<E> {

	/**
	 * 默认的线程池的大小
	 */
	private int defaultPoolSize = 256;

	/**
	 * 线程池的大小
	 */
	private int poolSize = defaultPoolSize;

	public ThreadPoolContainer() {

	}

	public ThreadPoolContainer(int size) {
		poolSize = size;
	}

	/**
	 * 加锁信息
	 */
	private Lock lock = new ReentrantLock();

	/**
	 * 当队列满了，就等待
	 */
	private Condition fulCon = lock.newCondition();

	/**
	 * 队列为空也让其等待
	 */
	private Condition emptyCon = lock.newCondition();

	/**
	 * 用来存储消息列队操作
	 */
	private LinkedList<E> queue = new LinkedList<E>();

	/**
	 * 放入文件对象
	 * 
	 * @param item
	 *            对象信息
	 * @param size
	 *            队列的大小
	 * @throws InterruptedException
	 *             异常对象
	 */
	public void put(E item) throws InterruptedException {
		lock.lock();
		try {
			// 如果当前队列已满，则等待
			while (queue.size() >= poolSize) {
				fulCon.await();
			}

			queue.add(item);
			// 唤醒在为空等待的队列,可以进行运作
			emptyCon.signal();
		} finally {
			lock.unlock();
		}

	}

	/**
	 * 取得队列的末尾信息
	 * 
	 * @return 元素信息
	 * @throws InterruptedException
	 *             异常
	 */
	public E takeLast() throws InterruptedException {

		lock.lock();
		E result = null;

		try {
			// 如果当前队列为空，则等待
			while (queue.size() == 0) {
				emptyCon.await();
			}
			result = queue.removeLast();
			// 唤醒已经满的线程，进行继续放入操作
			fulCon.signal();
		} finally {
			lock.unlock();
		}

		return result;
	}
	
}
