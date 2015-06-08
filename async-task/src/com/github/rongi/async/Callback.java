package com.github.rongi.async;

import java.util.concurrent.Callable;

public interface Callback<T> {

	public void onFinish(T result, Callable callable, Throwable e);

}
