package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import java.util.ArrayList;

import com.comp1008.group26.utility.ListAdapterVideo;
import com.comp1008.group26.utility.ListAdapterVideoSmall;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class VideoActivity extends Activity  implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		((ImageButton) findViewById(R.id.videomain)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.videoplay)).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText( getIntent().getExtras().getString("title"));
		
		ArrayList<String> videos = new ArrayList<String>();
		videos.add("Video 4");
		videos.add("Video 5");
		ListView listview = (ListView) findViewById(R.id.relatedVideo);
		listview.setAdapter(new ListAdapterVideoSmall(this, videos));
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
		switch (v.getId()) {
		
			case R.id.videomain:
			{
				playMedia(R.raw.testing, "video/*");
				break;
			}
			case R.id.videoplay:
			{
				playMedia(R.raw.testing, "video/*");
				break;
			}
		}
	}
	

	private void playMedia(int path, String type)
	{

       	Intent intent=new Intent(this, VideoPlayerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       	this.startActivity(intent);
       	
       	/*
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		File file = new File(path);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);*/
	}

}
