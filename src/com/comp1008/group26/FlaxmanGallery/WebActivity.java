package com.comp1008.group26.FlaxmanGallery;

import java.io.File;
import java.util.ArrayList;

import com.comp1008.group26.utility.ItemListAdapter;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class WebActivity extends Activity implements OnClickListener{

	String website;
	View decorView;
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
		setContentView(R.layout.activity_website);
		
		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		website = getIntent().getExtras().getString("website");
		((WebView)findViewById(R.id.webView)).loadUrl(website);
		((ImageButton) findViewById(R.id.home)).setOnClickListener(this);
		
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
   
        }
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
