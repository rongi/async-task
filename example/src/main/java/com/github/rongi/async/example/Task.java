package com.github.rongi.async.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class Task implements Callable<Bitmap> {

	private final String imageUrl;

	public Task(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Bitmap call() throws Exception {
		// This may throw IOException, you can handle it in callback if you want
		URL url = new URL(imageUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		return BitmapFactory.decodeStream(input);
	}

}
