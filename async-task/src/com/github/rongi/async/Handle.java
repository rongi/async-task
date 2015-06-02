package com.github.rongi.async;

import java.util.concurrent.Callable;

public class Handle {

	public Handle(Task task) {
		this.task = task;
	}

	public Callable getCallable() {
		return task.getCallable();
	}

	public void abandon() {
		task.abandon();
	}

	public final Task task;

}
