package com.github.rongi.async;

import java.util.concurrent.Callable;

/**
 * Handler that can be used to control running task
 */
public class Handle {

	public Handle(Task task) {
		this.task = task;
	}

	public Callable getCallable() {
		return task.getCallable();
	}

	/**
	 * Abandon task. Abandoned task do not call it's callback
	 */
	public void abandon() {
		task.abandon();
	}

	public final Task task;

}
