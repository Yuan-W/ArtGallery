package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import java.util.ArrayList;

import com.comp1008.group26.utility.ItemListAdapter;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends Activity  implements OnClickListener, OnTouchListener {

	String title;
	String body;
	String caption;
	int img;
	int link;
	VideoView videoView ;
	View decorView;
	
	boolean isPlay = true, isFirst=true;
	android.view.ViewGroup.LayoutParams layoutParamsParent;
	android.view.ViewGroup.LayoutParams layoutParams;
	int own_h, own_w, parent_h, parent_w;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (SettingActivity.fontSize==2)
        {
            setTheme(R.style.Theme_Large);
        }
        else if (SettingActivity.fontSize==1)
        {
            setTheme(R.style.Theme_Small);
        }
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		title = getIntent().getExtras().getString("title");
		body = getIntent().getExtras().getString("body");
		caption = getIntent().getExtras().getString("caption");
		img = getIntent().getExtras().getInt("img");
		link = getIntent().getExtras().getInt("link");
		
		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);
		
		((ImageButton) findViewById(R.id.videomain)).setImageResource(img);
		((ImageButton) findViewById(R.id.videomain)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.videoplay)).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText( title);
		((TextView)findViewById(R.id.body)).setText( body);
		
		videoView = (VideoView) findViewById(R.id.video);
		videoView.setMediaController(null);
		videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+link ));
		videoView.setOnTouchListener(this);
		videoView.requestFocus();
		
		layoutParamsParent = ((RelativeLayout)findViewById(R.id.videoLayoutParent)).getLayoutParams();
		layoutParams = videoView.getLayoutParams();
		own_w = layoutParams.width;
		own_h = layoutParams.height;
		parent_w = layoutParamsParent.width;
		parent_h = layoutParamsParent.height;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId())
		{

         case R.id.home:
         {
        	 super.onBackPressed();
        	 break;
         } 
         case R.id.videomain:
         {
     		playMedia();
     		break;
         } 
         case R.id.videoplay:
         {
     		playMedia();
     		break;
         }
   
        }
	}
	

	private void playMedia()
	{

       //	Intent intent=new Intent(this, VideoPlayerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       	//this.startActivity(intent);
		if(isFirst)
		{
			videoView.start();
			videoView.setVisibility(View.VISIBLE);
			((ImageButton) findViewById(R.id.videomain)).setVisibility(View.GONE);
			((ImageButton) findViewById(R.id.videoplay)).setVisibility(View.GONE);
			isFirst=false;
		}
		
		layoutParams = videoView.getLayoutParams();
		if(isPlay)
		{

			FrameLayout.LayoutParams llp2 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp2.setMargins(0, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
			llp2.width = parent_w;
			llp2.height = parent_h;
			((RelativeLayout)findViewById(R.id.videoLayoutParent)).setLayoutParams(llp2);
			((RelativeLayout)findViewById(R.id.videoLayoutParent)).setBackgroundColor(Color.BLACK);
			
			RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(0, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
			llp.width = parent_w;
			llp.height = parent_h;
			videoView.setLayoutParams(llp);
			
			((ImageButton) findViewById(R.id.home)).setVisibility(View.INVISIBLE);
			/*layoutParams.width = parent_w;
			layoutParams.height = parent_h;
	    	videoView.setLayoutParams(layoutParams);*/
			
			
		}else
		{
			((RelativeLayout)findViewById(R.id.videoLayoutParent)).setBackgroundColor(Color.WHITE);
			
			((ImageButton) findViewById(R.id.home)).setVisibility(View.VISIBLE);
			layoutParams.width = own_w;
			layoutParams.height = own_h;
	    	videoView.setLayoutParams(layoutParams);
		}
		
		isPlay=!isPlay;
       	/*
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		File file = new File(path);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);*/
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		playMedia();
		return false;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	    if (hasFocus) {

	        decorView.setSystemUiVisibility(
	        		  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
		            | View.SYSTEM_UI_FLAG_FULLSCREEN
		            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
		            
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}

}
