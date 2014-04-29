package com.comp1008.group26.FlaxmanGallery;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class PhotoActivity extends Activity implements OnClickListener {

	String title;
	String body;
	String caption;
	String img;

	boolean isfill = false;
	View decorView;

	ImageView imageView;
	boolean isPlay = true;
	android.view.ViewGroup.LayoutParams layoutParamsParent;
	android.view.ViewGroup.LayoutParams layoutParams;
	int own_h, own_w, parent_h, parent_w;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (SettingActivity.fontSize == 2) {
			setTheme(R.style.Theme_Large);
		} else if (SettingActivity.fontSize == 1) {
			setTheme(R.style.Theme_Small);
		}

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_photo);

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		title = getIntent().getExtras().getString("title");
		body = getIntent().getExtras().getString("body");
		caption = getIntent().getExtras().getString("caption");
		img = getIntent().getExtras().getString("img");

		imageView = ((ImageView) findViewById(R.id.videomain));
		imageView.setImageBitmap(BitmapFactory.decodeFile(img));
		imageView.setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText(Html.fromHtml(title));
		((TextView) findViewById(R.id.body)).setText(Html.fromHtml(body));
		((TextView) findViewById(R.id.caption)).setText(Html.fromHtml(caption));
		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);

		layoutParamsParent = ((RelativeLayout) findViewById(R.id.photoLayoutParent))
				.getLayoutParams();
		layoutParams = imageView.getLayoutParams();
		own_w = layoutParams.width;
		own_h = layoutParams.height;
		parent_w = layoutParamsParent.width;
		parent_h = layoutParamsParent.height;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.home: {
			super.onBackPressed();
			break;
		}
		case R.id.videomain: {

			layoutParams = imageView.getLayoutParams();
			if (isPlay) {

				FrameLayout.LayoutParams llp2 = new FrameLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				llp2.setMargins(0, 0, 0, 0); // llp.setMargins(left, top, right,
												// bottom);
				llp2.width = parent_w;
				llp2.height = parent_h;
				((RelativeLayout) findViewById(R.id.photoLayoutParent))
						.setLayoutParams(llp2);
				((RelativeLayout) findViewById(R.id.photoLayoutParent))
						.setBackgroundColor(Color.BLACK);

				RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				llp.setMargins(0, 0, 0, 0); // llp.setMargins(left, top, right,
											// bottom);
				llp.width = parent_w;
				llp.height = parent_h;
				imageView.setLayoutParams(llp);

				((ImageButton) findViewById(R.id.home))
						.setVisibility(View.INVISIBLE);

				((TextView) findViewById(R.id.title))
						.setVisibility(View.INVISIBLE);
				((TextView) findViewById(R.id.body))
						.setVisibility(View.INVISIBLE);
				((TextView) findViewById(R.id.caption))
						.setVisibility(View.INVISIBLE);
				/*
				 * layoutParams.width = parent_w; layoutParams.height =
				 * parent_h; videoView.setLayoutParams(layoutParams);
				 */

			} else {
				((RelativeLayout) findViewById(R.id.photoLayoutParent))
						.setBackgroundColor(Color.parseColor("#BBFFFFFF"));
				FrameLayout.LayoutParams llp2 = new FrameLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				llp2.setMargins(30, 30, 30, 30); // llp.setMargins(left, top,
													// right, bottom);
				((RelativeLayout) findViewById(R.id.photoLayoutParent))
						.setLayoutParams(llp2);
				((ImageButton) findViewById(R.id.home))
						.setVisibility(View.VISIBLE);
				layoutParams.width = own_w;
				layoutParams.height = own_h;
				imageView.setLayoutParams(layoutParams);

				((TextView) findViewById(R.id.title))
						.setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.body))
						.setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.caption))
						.setVisibility(View.VISIBLE);
			}

			isPlay = !isPlay;
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
