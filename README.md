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

Implement you background task in a Callable:

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
		if(exception == null) {
			myImageView.setImageBitmap(bitmap);
		} else {
			t.printStackTrace();
		}
	}
};
```
