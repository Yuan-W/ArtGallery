package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import java.util.ArrayList;

import com.comp1008.group26.Model.DatabaseHandler;
import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.MediaInfo;
import com.comp1008.group26.utility.ItemListAdapterSmall;
import com.comp1008.group26.utility.TimeoutManager;
import com.comp1008.group26.utility.UsageLog;
import com.comp1008.group26.utility.UsageLog.Action;
import com.devsmart.android.ui.HorizontalListView;

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
import android.widget.ImageView;
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
	
	TimeoutManager latestTOM;

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

		((ImageView) findViewById(R.id.videomain))
		.setImageBitmap(BitmapFactory.decodeFile(img));
		((TextView) findViewById(R.id.title)).setText(Html.fromHtml(title));
		((TextView) findViewById(R.id.body)).setText(Html.fromHtml(body));
		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);
		((ImageView) findViewById(R.id.videomain)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.fontsize)).setOnClickListener(this);

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

		DatabaseHandler databaseHandler = new DatabaseHandler(this);
		String relatedItemRaw = getIntent().getExtras().getString("relatedInfoList");
		ArrayList<Item> items = new ArrayList<Item>();

		if(!relatedItemRaw.trim().equals(""))
		{
			String[] relatedList = relatedItemRaw.split(",");
			for(String relatedItem : relatedList)
			{
				MediaInfo info = databaseHandler.getMediaInfo(relatedItem);

				String msg = "\nRelatedID: " + info.getId() + ",\nTitle: "
						+ info.getTitle() + ",\nName: " + info.getFileName()
						+ ",\nSummary: " + info.getSummary() + ",\nDescription: "
						+ info.getDescription() + ",\nThumbnail: "
						+ info.getThumbnailName() + ",\nPath: "
						+ info.getFilePath() + ",\nRelated: "
						+ info.getRelatedItems() + ",\nIsOnHome: "
						+ info.getIsOnHomeGrid() + "\n\n";
				System.out.println(msg);
				MediaInfo.FileType fileType = info.getFileType();

				Item item = new Item();
				item.setTitle(info.getTitle());
				item.setSummary(info.getSummary());
				item.setBody(info.getDescription());
				item.setImage_src(info.getThumbnailPath());

				if (fileType == MediaInfo.FileType.Audio)
				{
					item.setType(Item.AUDIO);
					item.setLink(info.getFilePath());
				}
				else if (fileType == MediaInfo.FileType.Video)
				{
					item.setType(Item.VIDEO);
					item.setLink(info.getFilePath());
				}
				else if (fileType == MediaInfo.FileType.Image)
				{
					item.setCaption(info.getCaption());
					item.setType(Item.IMAGE);;
				}
				item.setRelatedInfoList(info.getRelatedItems());
				items.add(item);

			}
		}

		HorizontalListView listview = (HorizontalListView) findViewById(R.id.horizonListview);
		listview.setAdapter(new ItemListAdapterSmall(this, items));
		
		TimeoutManager tom = new TimeoutManager(this, title);
		decorView.postDelayed(tom, 300000);
		latestTOM = tom;
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
		latestTOM.setTimeout(false);
		TimeoutManager tom = new TimeoutManager(this, title);
		v.postDelayed(tom, 300000);
		latestTOM = tom;
		
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fontsize: {
			if(SettingActivity.fontSize == 1)
				SettingActivity.fontSize =2;
			else
				SettingActivity.fontSize =1;

			this.recreate();
			break;
		}
		case R.id.home: {
			UsageLog.getInstance().writeEvent(Action.EXIT, this.title);
			mplayer.stop();
			super.onBackPressed();
			break;
		}
		case R.id.videomain: {

			if (isPlay) {
				UsageLog.getInstance().writeEvent(Action.PLAY, this.title);
				imageButton.setBackgroundResource(R.drawable.pause);
				mplayer.start();
			} else {
				UsageLog.getInstance().writeEvent(Action.PAUSE, this.title);
				imageButton.setBackgroundResource(R.drawable.play2);
				mplayer.pause();
			}

			isPlay = !isPlay;
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
	protected void onResume() {
		// TODO Auto-generated method stub
		imageButton.setBackgroundResource(R.drawable.pause);
		mplayer.start();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		imageButton.setBackgroundResource(R.drawable.play2);
		mplayer.pause();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

}
