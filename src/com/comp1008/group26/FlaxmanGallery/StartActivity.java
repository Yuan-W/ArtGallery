package com.comp1008.group26.FlaxmanGallery;

import java.io.File;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.MediaController;
import android.widget.VideoView;
import com.comp1008.group26.FlaxmanGallery.R;

public class StartActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		

		((ImageButton) findViewById(R.id.imageButton1)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.imageButton2)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView4)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView5)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView6)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView7)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView8)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView9)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView10)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView11)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView12)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView13)).setOnClickListener(this);
		((TextView) findViewById(R.id.textView14)).setOnClickListener(this);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	private void playMedia(String path, String type)
	{
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		File file = new File(path);
		intent.setDataAndType(Uri.fromFile(file), type);
		startActivity(intent);
	}
	public void onClick(View v) {
		switch (v.getId()) {
	
			case R.id.imageButton1:
			{
				playMedia(DbxSyncConfig.storeDir + "/testing.mp4", "video/*");
				break;
			}
			case R.id.imageButton2:
			{
				playMedia(DbxSyncConfig.storeDir +  "/testing.mp4", "video/*");
				break;
			}
			case R.id.textView4:
			{
				playMedia("/sdcard/ArtGallery/Audio/1  Phyllida - Casting and the carriated.wav", "audio/*");
				break;
			}
			case R.id.textView5:
			{
				playMedia("/sdcard/ArtGallery/Audio/1 Tom - Women in education.wav", "audio/*");
				break;
			}
			case R.id.textView6:
			{
				playMedia("/sdcard/ArtGallery/Audio/2 Phyllida - Weight of death.wav", "audio/*");
				break;
			}
			case R.id.textView7:
			{
				playMedia("/sdcard/ArtGallery/Audio/2 Tom - Argos Catalogue.wav", "audio/*");
				break;
			}
			case R.id.textView8:
			{
				playMedia("/sdcard/ArtGallery/Audio/3 Phyllida - Class, taste and aspiration", "audio/*");
				break;
			}
			case R.id.textView9:
			{
				playMedia("/sdcard/ArtGallery/Audio/3 Tom - Fixing an idea.wav", "audio/*");
				break;
			}
			case R.id.textView10:
			{
				playMedia("/sdcard/ArtGallery/Audio/4 Phyllida - Surprise.wav", "audio/*");
				break;
			}
			case R.id.textView11:
			{
				playMedia("/sdcard/ArtGallery/Audio/4 Tom - Flaxman Exchange", "audio/*");
				break;
			}
			case R.id.textView12:
			{
				playMedia("/sdcard/ArtGallery/Audio/5 Phyllida - kitsh.wav", "audio/*");
				break;
			}
			case R.id.textView13:
			{
				playMedia("/sdcard/ArtGallery/Audio/5 Tom - Gilbert and George", "audio/*");
				break;
			}
			case R.id.textView14:
			{
				playMedia("/sdcard/ArtGallery/Audio/6 Phyllida - The nude.wav", "audio/*");
				break;
			}
		}
	}

}
