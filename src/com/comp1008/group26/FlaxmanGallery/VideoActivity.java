package com.comp1008.group26.FlaxmanGallery;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.*;
import com.comp1008.group26.Model.DatabaseHandler;
import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.MediaInfo;
import com.comp1008.group26.utility.ButtonEventHandler;
import com.comp1008.group26.utility.ItemListAdapterSmall;
import com.comp1008.group26.utility.TimeoutManager;
import com.comp1008.group26.utility.UsageLog;
import com.comp1008.group26.utility.UsageLog.Action;
import com.devsmart.android.ui.HorizontalListView;

import java.io.File;
import java.util.ArrayList;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class VideoActivity extends Activity implements OnClickListener,
		OnTouchListener {

	String title;
	String body;
	String caption;
	String img;
	String link;
	VideoView videoView;
	View decorView;
	TimeoutManager latestTOM;

	boolean isPlay = true, isFirst = true;
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
		setContentView(R.layout.activity_video);

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		title = getIntent().getExtras().getString("title");
		body = getIntent().getExtras().getString("body");
		caption = getIntent().getExtras().getString("caption");
		img = getIntent().getExtras().getString("img");
		link = getIntent().getExtras().getString("link");

		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);

		((ImageView) findViewById(R.id.videomain))
				.setImageBitmap(BitmapFactory.decodeFile(img));
		((ImageView) findViewById(R.id.videomain)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.videoplay)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.fontsize)).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText(Html.fromHtml(title));
		((TextView) findViewById(R.id.body)).setText(Html.fromHtml(body));

		videoView = (VideoView) findViewById(R.id.video);

		videoView.setMediaController(null);
		File f = new File(link);
		Uri uri = Uri.fromFile(f);
		videoView.setVideoURI(uri);
		videoView.setOnTouchListener(this);
		videoView.requestFocus();

		layoutParamsParent = ((RelativeLayout) findViewById(R.id.videoLayoutParent))
				.getLayoutParams();
		layoutParams = videoView.getLayoutParams();
		own_w = layoutParams.width;
		own_h = layoutParams.height;
		parent_w = layoutParamsParent.width;
		parent_h = layoutParamsParent.height;
		
		DatabaseHandler databaseHandler = new DatabaseHandler(this);
		String relatedItemRaw = getIntent().getExtras().getString("relatedInfoList");
        if(relatedItemRaw.trim().equals(""))
        {
            findViewById(R.id.relatedLabel).setVisibility(View.INVISIBLE);
            findViewById(R.id.horizontalDivisor).setVisibility(View.INVISIBLE);
            findViewById(R.id.horizonListview).setVisibility(View.INVISIBLE);
        }
        else
        {
            ArrayList<Item> items = new ArrayList<Item>();
            String[] relatedList = relatedItemRaw.split(",");
            for (String relatedItem : relatedList)
            {
                MediaInfo info = databaseHandler.getMediaInfo(relatedItem);
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
                } else if (fileType == MediaInfo.FileType.Video)
                {
                    item.setType(Item.VIDEO);
                    item.setLink(info.getFilePath());
                } else if (fileType == MediaInfo.FileType.Image)
                {
                    item.setType(Item.IMAGE);
                    item.setLink(info.getFilePath());
                    item.setCaption(info.getCaption());
                }
                item.setRelatedInfoList(info.getRelatedItems());
                items.add(item);
            }
            ((HorizontalListView) findViewById(R.id.horizonListview)).setAdapter(new ItemListAdapterSmall(this, items));
        }
		
		TimeoutManager tom = new TimeoutManager(this, title);
		videoView.postDelayed(tom, 300000);
		latestTOM = tom;
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

			case R.id.home: {
				UsageLog.getInstance().writeEvent(Action.EXIT, this.title);
				latestTOM.setTimeout(false);
                ButtonEventHandler.backToHome(this);
				break;
			}
			case R.id.videomain: {
				playMedia();
				break;
			}
			case R.id.videoplay: {
				playMedia();
				break;
			}
			case R.id.fontsize: {
				ButtonEventHandler.changeFontSize(this);
				break;
			}
			
		}
	}

	private void playMedia() {

		if (isFirst) {
			videoView.start();
			videoView.setVisibility(View.VISIBLE);
			((ImageView) findViewById(R.id.videomain))
					.setVisibility(View.GONE);
			((ImageButton) findViewById(R.id.videoplay))
					.setVisibility(View.GONE);
			isFirst = false;
		}

		layoutParams = videoView.getLayoutParams();
		if (isPlay) {

			UsageLog.getInstance().writeEvent(Action.PLAY, this.title);

			FrameLayout.LayoutParams llp2 = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp2.setMargins(0, 0, 0, 0); 
			llp2.width = parent_w;
			llp2.height = parent_h;
			((RelativeLayout) findViewById(R.id.videoLayoutParent))
					.setLayoutParams(llp2);
			((RelativeLayout) findViewById(R.id.videoLayoutParent))
					.setBackgroundColor(Color.BLACK);

			RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp.setMargins(0, 0, 0, 0); // llp.setMargins(left, top, right,
										// bottom);
			llp.width = parent_w;
			llp.height = parent_h;
			videoView.setLayoutParams(llp);

			findViewById(R.id.home).setVisibility(View.INVISIBLE);
            findViewById(R.id.fontsize).setVisibility(View.INVISIBLE);
			findViewById(R.id.horizonListview).setVisibility(View.INVISIBLE);
            String relatedItemRaw = getIntent().getExtras().getString("relatedInfoList");
            findViewById(R.id.relatedLabel).setVisibility(View.INVISIBLE);
            findViewById(R.id.horizontalDivisor).setVisibility(View.INVISIBLE);
            findViewById(R.id.horizonListview).setVisibility(View.INVISIBLE);

		} else {
			UsageLog.getInstance().writeEvent(Action.PAUSE, this.title);

			((RelativeLayout) findViewById(R.id.videoLayoutParent))
					.setBackgroundColor(Color.parseColor("#BBFFFFFF"));
			FrameLayout.LayoutParams llp2 = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp2.setMargins(30, 30, 30, 30); // llp.setMargins(left, top, right,
												// bottom);
			((RelativeLayout) findViewById(R.id.videoLayoutParent))
					.setLayoutParams(llp2);
			findViewById(R.id.home).setVisibility(View.VISIBLE);
            findViewById(R.id.fontsize).setVisibility(View.VISIBLE);
			findViewById(R.id.horizonListview).setVisibility(View.VISIBLE);
			layoutParams.width = own_w;
			layoutParams.height = own_h;
			videoView.setLayoutParams(layoutParams);
            findViewById(R.id.relatedLabel).setVisibility(View.VISIBLE);
            findViewById(R.id.horizontalDivisor).setVisibility(View.VISIBLE);
            findViewById(R.id.horizonListview).setVisibility(View.VISIBLE);
            String relatedItemRaw = getIntent().getExtras().getString("relatedInfoList");
            if(relatedItemRaw.trim().equals(""))
            {
                findViewById(R.id.relatedLabel).setVisibility(View.INVISIBLE);
                findViewById(R.id.horizontalDivisor).setVisibility(View.INVISIBLE);
                findViewById(R.id.horizonListview).setVisibility(View.INVISIBLE);
            }
		}

		isPlay = !isPlay;
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
