package com.github.rongi.async;

import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

class Task<T> implements Runnable {

	public Task(Callable<T> callable, Callback callback) {
		this.callable = callable;
		if(callback != null) {
			this.callbackReference = new WeakReference<Callback>(callback);
		} else {
			this.callbackReference = null;
		}
	}

	public void abandon() {
		this.abandoned = true;
	}

	@Override
	public void run() {
		try{
			result = callable.call();
		} catch(Throwable e) {
			exception = e;
		}

		if(!abandoned) {
			final boolean success = handler.sendEmptyMessage(0);
			if(!success) {
				throw new IllegalStateException("Handler can't send message, probably sending not from GUI thread");
			}
		}
	}

	public Callable<T> getCallable() {
		return callable;
	}

	private final Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			if(callbackReference != null && !abandoned) {
				final com.github.rongi.async.Callback callback = callbackReference.get();
				if(callback != null) {
					callback.onFinish(result, callable, exception);
				}
			}
		}
	};

	private final Callable<T> callable;
	private final WeakReference<Callback> callbackReference;

	private boolean abandoned;

	private T result;
	private Throwable exception;

}
