package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import com.comp1008.group26.FlaxmanGallery.R;

public class MainActivity extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		((Button) findViewById(R.id.button1)).setOnClickListener(this);
		((Button) findViewById(R.id.button2)).setOnClickListener(this);
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
	
			case R.id.button1:
			{
				Intent intent = new Intent(this, StartActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.button2:
			{
				Intent intent = new Intent(this, SettingActivity.class);
				startActivity(intent);
				break;
			}
			
		}
	}

}
