package com.github.rongi.async;

import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Tasks {

	public static <T> Handle execute(Callable<T> callable, Callback callback) {
		final Task<T> task = new Task<T>(callable, callback);
		final Handle handle = new Handle(task);
		executor = getThreadPoolExecutor();
		executor.submit(task);
		return handle;
	}

	public static <T> Handle execute(Callable<T> callable, Callback callback, ThreadPoolExecutor threadPoolExecutor) {
		final Task<T> task = new Task<T>(callable, callback);
		final Handle handle = new Handle(task);
		threadPoolExecutor.submit(task);
		return handle;
	}

	private static ThreadPoolExecutor getThreadPoolExecutor() {
		synchronized(Tasks.class) {
			if(executor == null) {
				executor = new ThreadPoolExecutor(4, 128, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
			}
		}

		return executor;
	}

	private static ThreadPoolExecutor executor;

}
