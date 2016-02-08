async-task for Android
===========================

This library does the same thing AsyncTask does – runs code on background thread and gets result back to the main thread. But it is simpler, cleaner, easier to understand, straightforward to use, reqires no arcane knowledge to be used. And it requires less boilerplate code than RxJava.

Basic usage
===========

This is how task execution looks in the code:

```java
Task task = new Task();
Tasks.execute(task, callback);
```

Tasks are implemented as simple `Callable`s:

```java
private static class MyCallable implements Callable<String> {
	public String call() throws Exception {
		// Do you networking (i.e.) here
	}
}
```

You will get results in your callback object (in the main thread):

```java
private final Callback<String> callback = new Callback<String>() {
	public void onFinish(String result, Callable callable, Throwable e) {
		// Here you'll get either throwable or result
		if (e == null) {
			// do something with the result
		} else {
			// handle error
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

1. Task code and result handlers are separated. This means that it is possible to use same async-task implementation in diferent situations. This is not an option with vanila Android AsyncTask.
2. Callbacks are weakly referenced so stalled task will not leak your activity.
3. This also means that you don't have to unsubscribe from task in onStop().
4. Callbacks can be forced to be strong referenced via options.
5. You can "abandon" task. Abandoned tasks do not call it's callback when done. Do it by storing `Handle` object returned from `Tasks.execute(callable, taskCallback)` and then call `myHandle.abandon()`
6. It is possible to run tasks with you own `ThreadPoolExecutor` just specify one in `Options`
