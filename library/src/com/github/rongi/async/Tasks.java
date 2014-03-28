package com.github.rongi.async;

import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Tasks {
	
	public static class Options {
		public boolean strongReferencedCallback;
		public ThreadPoolExecutor threadPoolExecutor;
	}

	public static <T> Handle execute(Callable<T> callable, Callback callback) {
		return execute(callable, callback, null);
	}

	public static <T> Handle execute(Callable<T> callable, Callback callback, Options options) {
		if(options == null) {
			if(defaultOptions == null) {
				defaultOptions = new Options();
			}
			options = defaultOptions;
		}
		
		final Task<T> task = new Task<T>(callable, callback, options.strongReferencedCallback);
		final Handle handle = new Handle(task);
		final ThreadPoolExecutor executor = options.threadPoolExecutor != null ? options.threadPoolExecutor : getThreadPoolExecutor();
		executor.submit(task);
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
	private static Options defaultOptions;

}
