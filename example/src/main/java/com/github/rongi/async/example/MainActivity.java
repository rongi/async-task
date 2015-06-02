package com.github.rongi.async.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.button).setOnClickListener(onButtonClickListener);
	}

	private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

}
