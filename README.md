async-task for Android
===========================

It is a lightweight and simple replacement for standard Android async tasks.

Basic usage
===========

Create a subclass of `Callable`:

```java
private static class LoadPictureCallable implements Callable<Bitmap> {
	public Bitmap call() throws Exception {
		// Do you networking here
	}
}
```

Then you want to execute task use this

```java
LoadPictureCallable loadPictureCallable = new LoadPictureCallable();
Tasks.execute(loadPictureCallable, loadPictureCallback);
```

You will get results in your callback object (in the main thread):

```java
private final Callback<Bitmap> loadPictureCallback = new Callback<Bitmap>() {
	public void onFinish(Bitmap bitmap, Callable callable, Throwable t) {
		if(exception == null) {
			myImageView.setImageBitmap(bitmap);
		} else {
			t.printStackTrace();
		}
	}
};
```
