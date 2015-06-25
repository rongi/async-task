async-task for Android
===========================

This library does the same thing AsyncTask does – runs code on background thread and get result back to the main thread. But it is simpler, cleaner, easier to understand, strait-forward to use, hides no arcane knowledge (like how exactly cancel() works in AsyncTask). And it is more simple and straitforward than RxJava.

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
			// do something with the result
		} else {
			e.printStackTrace();
		}
	}
};
```

Download
========

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'rongi.async-task:async-task:1.0.1'
}
```

Features
========

1. Task code and result handlers are separated. In AsyncTask they are not, and this is madmans design, right?
2. Callbacks are weakly referenced so stalled task will not leak your activity.
3. This also means that you don't need to unsubscribe from task in onStop().
4. Callbacks can be forced to be strong referenced via options.
5. You can "abandon" task. Abandoned tasks do not call it's callback when done. Do it by storing `Handle` returned from `Tasks.execute(callable, taskCallback)` and then call `myHandle.abandon()`
6. It is possible to run tasks with you own `ThreadPoolExecutor` just specify one in `Options`
