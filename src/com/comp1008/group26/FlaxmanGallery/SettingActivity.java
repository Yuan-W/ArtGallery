package com.comp1008.group26.FlaxmanGallery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import com.comp1008.group26.FlaxmanGallery.R;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class SettingActivity extends Activity implements OnClickListener {
	public static int fontSize = 1;

	public void onCreate(Bundle savedInstanceState) {

		if (SettingActivity.fontSize == 2) {
			setTheme(R.style.Theme_Large);
		} else if (SettingActivity.fontSize == 1) {
			setTheme(R.style.Theme_Small);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		if (SettingActivity.fontSize == 2) {
			((RadioButton) findViewById(R.id.textSizeLarge)).setChecked(true);
			((RadioButton) findViewById(R.id.textSizeSmall)).setChecked(false);

		} else if (SettingActivity.fontSize == 1) {
			((RadioButton) findViewById(R.id.textSizeSmall)).setChecked(true);
			((RadioButton) findViewById(R.id.textSizeLarge)).setChecked(false);
		}

		((RadioButton) findViewById(R.id.textSizeLarge))
				.setOnClickListener(this);
		((RadioButton) findViewById(R.id.textSizeSmall))
				.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.textSizeLarge: {
			fontSize = 2;
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.textSizeSmall: {
			fontSize = 1;
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		}
		}
	}

}
