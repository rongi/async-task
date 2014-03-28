package com.github.rongi.async;

import android.os.Handler;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

class Task<T> implements Runnable {

	public Task(Callable<T> callable, Callback callback, boolean callbackStrongReferenced) {
		this.callable = callable;
		if(callbackStrongReferenced) {
			callbackStrongReference = callback;
		} else if(callback != null && !callbackStrongReferenced) {
			this.callbackWeakReference = new WeakReference<Callback>(callback);
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
	
	private Callback getCallback() {
		if(callbackStrongReference != null) {
			return callbackStrongReference;
		} else if(callbackWeakReference != null) {
			return callbackWeakReference.get();
		} else {
			return null;
		}
	}

	private final Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			final com.github.rongi.async.Callback callback = getCallback();
			if(callback != null && !abandoned) {
				callback.onFinish(result, callable, exception);
			}
		}
	};

	private final Callable<T> callable;
	
	private WeakReference<Callback> callbackWeakReference;
	private Callback callbackStrongReference;

	private boolean abandoned;

	private T result;
	private Throwable exception;

}
