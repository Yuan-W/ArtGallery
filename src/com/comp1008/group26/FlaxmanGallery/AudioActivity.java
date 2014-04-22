package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import java.util.ArrayList;


import com.comp1008.group26.utility.ItemListAdapter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.ImageButton;

public class AudioActivity extends Activity implements OnClickListener{
	MediaPlayer mplayer;
	SeekBar seekBar;
    Handler seekHandler;
    ImageButton imageButton;
    boolean isPlay;

	String title;
	String body;
	String caption;
	int img;
	int link;
	
    final Runnable run = new Runnable() {
		 
       @Override
       public void run() {
       	seekBar.setProgress(mplayer.getCurrentPosition());
    	seekHandler.postDelayed(run, 1000);
    	System.out.println("testing");
       }
   };
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_audio);
		
		title = getIntent().getExtras().getString("title");
		body = getIntent().getExtras().getString("body");
		caption = getIntent().getExtras().getString("caption");
		img = getIntent().getExtras().getInt("img");
		link = getIntent().getExtras().getInt("link");


		((ImageButton) findViewById(R.id.videomain)).setImageResource(img);
		((TextView)findViewById(R.id.title)).setText( title);
		((TextView)findViewById(R.id.body)).setText( body);
		
		mplayer = MediaPlayer.create(this, link);

       // mplayer.start();
		/*try {
	        mplayer.prepare();
	        mplayer.start();
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
		*/
		seekBar =((SeekBar) findViewById(R.id.seekBar1));
		seekBar.setMax(mplayer.getDuration());

		imageButton=  ((ImageButton) findViewById(R.id.audioControl));
		imageButton.setOnClickListener(this);
	     seekHandler = new Handler();
	     isPlay = true;	
	     imageButton.setBackgroundResource(R.drawable.play2);
	     run.run();
	}

    public void updateAudio() {
    	seekBar.setProgress(mplayer.getCurrentPosition());
    	seekHandler.postDelayed(run, 1000);
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
		
		if(isPlay)
		{
			imageButton.setBackgroundResource(R.drawable.pause);
			mplayer.start();
		}
		else
		{
			imageButton.setBackgroundResource(R.drawable.play2);
			mplayer.pause();
		}
		
		isPlay = !isPlay;
	}
	

}
