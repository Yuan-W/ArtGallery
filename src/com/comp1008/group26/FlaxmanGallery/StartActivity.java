package com.comp1008.group26.FlaxmanGallery;

import java.io.IOException;
import java.util.ArrayList;

import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.Partner;
import com.comp1008.group26.utility.ItemListAdapter;
import com.comp1008.group26.utility.PartnerListAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


public class StartActivity extends Activity {

	ListView listview ;
	ItemListAdapter listAdapter;
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
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	   // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    setContentView(R.layout.activity_start);

	    //getWindow().addFlags(  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		ArrayList<Item> items = new ArrayList<Item>();
		Item i1 = new Item();
		i1.setType(Item.TEXT);
		i1.setTitle("Text title");
		i1.setSummary("Text summary");
		i1.setBody("Text body");
		items.add(i1);
		
		Item i2 = new Item();
		i2.setType(Item.AUDIO);
		i2.setTitle("Audio title");
		i2.setSummary("Audio summary");
		i2.setBody("Audio body");
		i2.setImage_src(R.drawable.video);
		i2.setLink(R.raw.phyllida_surprise);
		items.add(i2);	

		Item i3 = new Item();
		i3.setType(Item.VIDEO);
		i3.setTitle("Video title");
		i3.setSummary("Video summary");
		i3.setBody("Video body");
		i3.setImage_src(R.drawable.video);
		i3.setLink(R.raw.testing);
		items.add(i3);	
		
		Item i4 = new Item();
		i4.setType(Item.IMAGE);
		i4.setTitle("Image title");
		i4.setCaption("Image caption");
		i3.setImage_src(R.drawable.video);
		i4.setBody("Image body");
		items.add(i4);
		
		Item i5 = new Item();
		i5.setType(Item.WEB);
		i5.setTitle("About");
		i5.setWebsite("http://www.ucl.ac.uk/museums/uclart");
		items.add(i5);

		Item i6 = new Item();
		i6.setType(Item.WEB);
		i6.setTitle("Contact");
		i6.setWebsite("http://www.ucl.ac.uk/museums/uclart/visit/find-us");
		items.add(i6);

		Item i7 = new Item();
		i7.setType(Item.WEB);
		i7.setTitle("Special");
		i7.setWebsite("http://www.ucl.ac.uk/museums/uclart/whats-on");
		items.add(i7);
		
		Item i8 = new Item();
		i8.setType(Item.PARTNER);
		i8.setTitle("Partner");
		items.add(i8);
		
		
		
		
		
		//listview = (ListView) findViewById(R.id.listViewVideo);
		listAdapter  = new ItemListAdapter(this, items);
		//listview.setAdapter(listAdapter);
		

        GridView gridView = (GridView) findViewById(R.id.grid_view);
 
        // Instance of ImageAdapter Class
        gridView.setAdapter(listAdapter);
		
		
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
		super.onBackPressed();
	}
}
