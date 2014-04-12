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
private static class MyCallable implements Callable<Bitmap> {
	public Bitmap call() throws Exception {
		// Do you networking here
	}
}
```

You will get results in your callback object (in the main thread):

```java
private final Callback<Bitmap> taskCallback = new Callback<Bitmap>() {
	public void onFinish(Bitmap bitmap, Callable callable, Throwable t) {
		if(t == null) {
			myImageView.setImageBitmap(bitmap);
		} else {
			t.printStackTrace();
		}
	}
};
```

Features
========

1. Good design. Task code and result handlers are separated. So you can reuse task code anywhere with any handler or just as part of another task.
2. Callabcks are weakly referenced so stalled task will not leak your activity. 
3. Callbacks can be forced to be strong referenced via options.
4. You can "abandon" task. Abandoned tasks do not call it's callback when done. Do it by storing `Handle` returned from `Tasks.execute(callable, taskCallback)` and then call `myHandle.abandon()`
5. It is possible to run tasks with you own `ThreadPoolExecutor` just specify one in `Options`
