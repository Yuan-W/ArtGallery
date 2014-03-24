package com.comp1008.group26.FlaxmanGallery;

import java.util.ArrayList;

import com.comp1008.group26.utility.ListAdapterAudio;
import com.comp1008.group26.utility.ListAdapterPhoto;
import com.comp1008.group26.utility.ListAdapterVideo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		//Video
		ArrayList<String> videos = new ArrayList<String>();
		videos.add("Video 1");
		videos.add("Video 2");
		videos.add("Video 3");
		ListView listview = (ListView) findViewById(R.id.listViewVideo);
		listview.setAdapter(new ListAdapterVideo(this, videos));
		
		//Audio
		ArrayList<String> audios = new ArrayList<String>();
		audios.add("Audio 1");
		audios.add("Audio 2");
		audios.add("Audio 3");
		ListView listview2 = (ListView) findViewById(R.id.listViewAudio);
		listview2.setAdapter(new ListAdapterAudio(this, audios));
		

		//Photo
		ArrayList<String> photos = new ArrayList<String>();
		photos.add("Photo 1");
		photos.add("Photo 2");
		photos.add("Photo 3");
		ListView listview3 = (ListView) findViewById(R.id.listViewPhoto);
		listview3.setAdapter(new ListAdapterPhoto(this, photos));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}
	
}
