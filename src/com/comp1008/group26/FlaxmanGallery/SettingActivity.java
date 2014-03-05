package com.comp1008.group26.FlaxmanGallery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.comp1008.group26.FlaxmanGallery.R;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
