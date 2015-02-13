package com.example.prog_gps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Top extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// res/layout/screen1.xml を初期画面に
		setContentView(R.layout.screen1);
		setTitle("画面1");

		Button btn2 = (Button) findViewById(R.id.btn2);
		Button btn3 = (Button) findViewById(R.id.btn3);

		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Top.this,GPSActivity.class );
				startActivity(intent);
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://j12024.sangi01.net/cakephp/locations");
				Intent i = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(i);
			}
		});
	}
}