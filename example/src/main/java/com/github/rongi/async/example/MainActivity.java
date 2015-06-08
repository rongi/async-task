package com.github.rongi.async.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.rongi.async.Callback;
import com.github.rongi.async.Tasks;

import java.util.concurrent.Callable;

public class MainActivity extends Activity {

	private View progress;
	private View button;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = findViewById(R.id.button);
		button.setOnClickListener(onButtonClickListener);

		progress = findViewById(R.id.progress);
		imageView = (ImageView) findViewById(R.id.result);
	}

	private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			Task task = new Task("http://www.online-image-editor.com//styles/2014/images/example_image.png");
			Tasks.execute(task, callback);

			progress.setVisibility(View.VISIBLE);
			button.setVisibility(View.INVISIBLE);
		}
	};

	private Callback<Bitmap> callback = new Callback<Bitmap>() {
		public void onFinish(Bitmap result, Callable callable, Throwable e) {
			if (e == null) {
				// On success
				imageView.setImageBitmap(result);
			} else {
				// On error
				Log.e(MainActivity.class.getSimpleName(), "Load error", e);
			}

			// Always
			progress.setVisibility(View.GONE);
			button.setVisibility(View.VISIBLE);
		}
	};

}
