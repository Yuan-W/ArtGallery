package com.comp1008.group26.FlaxmanGallery;

import java.io.IOException;
import java.util.ArrayList;

import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.Partner;
import com.comp1008.group26.utility.ItemListAdapter;
import com.comp1008.group26.utility.PartnerListAdapter;

import android.os.Bundle;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


class MyTabsListener implements ActionBar.TabListener {
	  public Fragment fragment;

	  public MyTabsListener(Fragment fragment) {
	   this.fragment = fragment;
	  }

	  @Override
	  public void onTabReselected(Tab tab, FragmentTransaction ft) {
	   /*Toast.makeText(MainActivity.appContext, "Reselected!",
	     Toast.LENGTH_LONG).show();*/
	  }

	  @Override
	  public void onTabSelected(Tab tab, FragmentTransaction ft) {
	   ft.replace(R.id.fragment_container, fragment);
	  }

	  @Override
	  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	   ft.remove(fragment);
	  }

}
	  
class Fragment1 extends Fragment {
	ListView listview ;
	ItemListAdapter listAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_item_grid, container, false);
    	super.onCreate(savedInstanceState);
    	
		ArrayList<Item> items = new ArrayList<Item>();
		Item i1 = new Item();
		i1.setType(Item.TEXT);
		i1.setTitle("text title");
		i1.setSummary("text summary");
		i1.setBody("text body");
		items.add(i1);
		
		Item i2 = new Item();
		i2.setType(Item.AUDIO);
		i2.setTitle("audio title");
		i2.setSummary("audio summary");
		i2.setBody("audio body");
		i2.setImage_src(R.drawable.video);
		i2.setLink(R.raw.phyllida_surprise);
		items.add(i2);	

		Item i3 = new Item();
		i3.setType(Item.VIDEO);
		i3.setTitle("video title");
		i3.setSummary("video summary");
		i3.setBody("video body");
		i3.setImage_src(R.drawable.video);
		i3.setLink(R.raw.testing);
		items.add(i3);	
		
		Item i4 = new Item();
		i4.setType(Item.IMAGE);
		i4.setTitle("image title");
		i4.setCaption("image caption");
		i3.setImage_src(R.drawable.video);
		i4.setBody("image body");
		items.add(i4);
		
		//listview = (ListView) findViewById(R.id.listViewVideo);
		listAdapter  = new ItemListAdapter(this.getActivity(), items);
		//listview.setAdapter(listAdapter);
		

        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
 
        // Instance of ImageAdapter Class
        gridView.setAdapter(listAdapter);
		
		        
		return view ;
	}

}
	 
class Fragment2 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_about, container, false);
    	
		WebView webView = (WebView) view.findViewById(R.id.webView); 
		webView.loadData("load on the webView " , "text/html" , "utf-8");
		webView.loadUrl("http://www.ucl.ac.uk/museums/uclart");
		return view;
	}
  
}

class Fragment3 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View view = inflater.inflate(R.layout.activity_contact, container, false);
    	
		WebView webView = (WebView) view.findViewById(R.id.webView); 
		webView.loadData("load on the webView " , "text/html" , "utf-8");
		webView.loadUrl("http://www.ucl.ac.uk/museums/uclart/visit/find-us");
		return view;
	}
  
}
class Fragment4 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.activity_special, container, false);
    	
		WebView webView = (WebView) view.findViewById(R.id.webView); 
		webView.loadData("load on the webView " , "text/html" , "utf-8");
		webView.loadUrl("http://www.ucl.ac.uk/museums/uclart/whats-on");
		return view;
	}
  
}

class Fragment5 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
			

		View view = inflater.inflate(R.layout.activity_partner, container, false);
		ArrayList<Partner> partners = new ArrayList<Partner>();
		Partner p1 = new Partner( R.drawable.video, "Partner1", "Details", "Link");
		Partner p2 = new Partner( R.drawable.video, "Partner2", "Details", "Link");
		partners.add(p1);
		partners.add(p2);
		
		ListView list = (ListView) view.findViewById(R.id.partnerList);
		PartnerListAdapter las = new PartnerListAdapter(this.getActivity(), partners);
		list.setAdapter(las);
		return view;
	}
  
}
public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);

	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    setContentView(R.layout.activity_start);
		
		
		ActionBar actionbar = getActionBar();
		//actionbar.hide();
	    actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM);
	    
		ActionBar.Tab tab1 = actionbar.newTab().setText("Home");
		ActionBar.Tab tab2 = actionbar.newTab().setText("About");
		ActionBar.Tab tab3 = actionbar.newTab().setText("Contact");
		ActionBar.Tab tab4 = actionbar.newTab().setText("Special");
		ActionBar.Tab tab5 = actionbar.newTab().setText("Partners");
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setDisplayShowTitleEnabled(false);
		//actionbar.setIcon(R.drawable.actionbar_tab_indicator);
		
		Fragment fragment1 = new Fragment1();
		Fragment fragment2 = new Fragment2();
		Fragment fragment3 = new Fragment3();
		Fragment fragment4 = new Fragment4();
		Fragment fragment5 = new Fragment5();

		tab1.setTabListener(new MyTabsListener(fragment1));
		tab2.setTabListener(new MyTabsListener(fragment2));
		tab3.setTabListener(new MyTabsListener(fragment3));
		tab4.setTabListener(new MyTabsListener(fragment4));
		tab5.setTabListener(new MyTabsListener(fragment5));
		//tab1.setIcon(R.drawable.play);
		//tab2.setIcon(R.drawable.play2);
		actionbar.addTab(tab1);
		actionbar.addTab(tab2);
		actionbar.addTab(tab3);
		actionbar.addTab(tab4);
		actionbar.addTab(tab5);
		
		/* First, get the Display from the WindowManager */
		//Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		
		
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
		super.onBackPressed();
	}
	
}
