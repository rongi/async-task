package com.github.rongi.async;

import java.util.concurrent.Callable;

public interface Callback<T> {

	/**
	 * @param result Task's result or null if task threw exception
	 * @param callable Callable that was executed, you can use it to get it's params for example
	 * @param e Exception thrown by task or null if task successfully finished
	 */
	void onFinish(T result, Callable callable, Throwable e);

}
