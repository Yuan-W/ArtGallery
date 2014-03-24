package com.comp1008.group26.FlaxmanGallery;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity  implements OnTouchListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video_player);

		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		videoView.setMediaController(null);
		videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.testing ));
		videoView.setOnTouchListener(this);
		videoView.requestFocus();
		videoView.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.video_player, menu);
		return true;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		finish();
		return false;
	}

}
