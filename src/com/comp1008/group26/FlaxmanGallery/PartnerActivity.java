package com.comp1008.group26.FlaxmanGallery;

import java.util.ArrayList;

import com.comp1008.group26.Model.Partner;
import com.comp1008.group26.utility.ButtonEventHandler;
import com.comp1008.group26.utility.PartnerListAdapter;
import com.comp1008.group26.utility.TimeoutManager;
import com.comp1008.group26.utility.UsageLog;
import com.comp1008.group26.utility.UsageLog.Action;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class PartnerActivity extends Activity implements OnClickListener {

	String title;
	String body;

	View decorView;
	TimeoutManager latestTOM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (SettingActivity.fontSize == 2) {
			setTheme(R.style.Theme_Large);
		} else if (SettingActivity.fontSize == 1) {
			setTheme(R.style.Theme_Small);
		}

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_partner);

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);

		ArrayList<Partner> partners = new ArrayList<Partner>();
		Partner p1 = new Partner(R.drawable.uclbanner, "Partner1", "Details",
				"http://www.ucl.ac.uk/");
		Partner p2 = new Partner(R.drawable.uclbanner, "Partner2", "Details",
				"http://www.ucl.ac.uk/");
		partners.add(p1);
		partners.add(p2);

		ListView list = (ListView) findViewById(R.id.partnerList);
		PartnerListAdapter las = new PartnerListAdapter(this, partners);
		list.setAdapter(las);
		
		TimeoutManager tom = new TimeoutManager(this, title);
		decorView.postDelayed(tom, 300000);
		latestTOM = tom;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.text, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		latestTOM.setTimeout(false);
		TimeoutManager tom = new TimeoutManager(this, title);
		v.postDelayed(tom, 300000);
		latestTOM = tom;
		
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.home: {
			UsageLog.getInstance().writeEvent(Action.EXIT, this.title);
			latestTOM.setTimeout(false);
            ButtonEventHandler.backToHome(this);
			break;
		}

		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {

			decorView
					.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
																	// bar
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}
}
