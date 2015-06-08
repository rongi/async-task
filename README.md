async-task for Android
===========================

It is a lightweight and easy to use replacement for standard Android async tasks.

Basic usage
===========

This is how task execution looks in your code:

```java
MyCallable callable = new MyCallable();
Tasks.execute(callable, taskCallback);
```

Implement you background task as a `Callable`:

```java
private static class MyCallable implements Callable<String> {
	public String call() throws Exception {
		// Do you networking (i.e.) here
	}
}
```

You will get results in your callback object (in the main thread):

```java
private final Callback<String> taskCallback = new Callback<String>() {
	public void onFinish(String result, Callable callable, Throwable e) {
		if (e == null) {
			// do something with the String
		} else {
			t.printStackTrace();
		}
	}
};
```

Features
========

1. Good design. Task code and result handlers are separated. So you can reuse task code anywhere with any handler or just as part of another task.
2. Callbacks are weakly referenced so stalled task will not leak your activity. 
3. Callbacks can be forced to be strong referenced via options.
4. You can "abandon" task. Abandoned tasks do not call it's callback when done. Do it by storing `Handle` returned from `Tasks.execute(callable, taskCallback)` and then call `myHandle.abandon()`
5. It is possible to run tasks with you own `ThreadPoolExecutor` just specify one in `Options`
