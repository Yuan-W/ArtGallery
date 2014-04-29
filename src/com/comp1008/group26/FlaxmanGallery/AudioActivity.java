package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.SeekBar;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class AudioActivity extends Activity implements OnClickListener {
	MediaPlayer mplayer;
	SeekBar seekBar;
	Handler seekHandler;
	ImageButton imageButton;
	boolean isPlay;

	String title;
	String body;
	String caption;
	String img;
	String link;

	View decorView;
	final Runnable run = new Runnable() {

		@Override
		public void run() {
			seekBar.setProgress(mplayer.getCurrentPosition());
			seekHandler.postDelayed(run, 1000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (SettingActivity.fontSize == 2) {
			setTheme(R.style.Theme_Large);
		} else if (SettingActivity.fontSize == 1) {
			setTheme(R.style.Theme_Small);
		}

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_audio);

		title = getIntent().getExtras().getString("title");
		body = getIntent().getExtras().getString("body");
		caption = getIntent().getExtras().getString("caption");
		img = getIntent().getExtras().getString("img");
		link = getIntent().getExtras().getString("link");

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		((ImageButton) findViewById(R.id.videomain))
				.setImageBitmap(BitmapFactory.decodeFile(img));
		((TextView) findViewById(R.id.title)).setText(Html.fromHtml(title));
		((TextView) findViewById(R.id.body)).setText(Html.fromHtml(body));
		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);

		File f = new File(link);
		Uri uri = Uri.fromFile(f);
		mplayer = MediaPlayer.create(this, uri);

		seekBar = ((SeekBar) findViewById(R.id.seekBar1));
		seekBar.setMax(mplayer.getDuration());

		imageButton = ((ImageButton) findViewById(R.id.audioControl));
		imageButton.setOnClickListener(this);
		seekHandler = new Handler();
		isPlay = true;
		imageButton.setBackgroundResource(R.drawable.play2);
		run.run();

		imageButton.setBackgroundResource(R.drawable.pause);
		mplayer.start();
		isPlay = !isPlay;
	}

	public void updateAudio() {
		seekBar.setProgress(mplayer.getCurrentPosition());
		seekHandler.postDelayed(run, 1000);
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
			mplayer.stop();
			super.onBackPressed();
			break;
		}
		case R.id.audioControl: {

			if (isPlay) {
				imageButton.setBackgroundResource(R.drawable.pause);
				mplayer.start();
			} else {
				imageButton.setBackgroundResource(R.drawable.play2);
				mplayer.pause();
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
