package com.comp1008.group26.FlaxmanGallery;

import java.util.ArrayList;
import java.util.List;

import com.comp1008.group26.Model.DatabaseHandler;
import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.MediaInfo;
import com.comp1008.group26.utility.ItemListAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class StartActivity extends Activity {

	ListView listview;
	ItemListAdapter listAdapter;
	View decorView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (SettingActivity.fontSize == 2) {
			setTheme(R.style.Theme_Large);
		} else if (SettingActivity.fontSize == 1) {
			setTheme(R.style.Theme_Small);
		}

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_start);

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		DatabaseHandler databaseHandler = new DatabaseHandler(this);
		List<MediaInfo> infoList = databaseHandler.getAllMediaInfo();
		
		ArrayList<Item> items = new ArrayList<Item>();
		
		for (MediaInfo info : infoList)
        {

			String msg = "\nID: " + info.getId() + ",\nTitle: "
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
            //////////////////////////////////////////////////////////////
            // Sample code for get relatedItem from database by title
            /*String relatedItemRaw = info.getRelatedItems();
            if(!relatedItemRaw.trim().equals(""))
            {
                String[] relatedList = relatedItemRaw.split(",");
                for(String relatedItem : relatedList)
                {
                    MediaInfo relatedInfo = databaseHandler.getMediaInfo(relatedItem);
                }
            }*/
            //////////////////////////////////////////////////////////////
            item.setRelatedInfoList(info.getRelatedItems());
            items.add(item);
            
            
		}
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

		listAdapter = new ItemListAdapter(this, items);

		GridView gridView = (GridView) findViewById(R.id.grid_view);
		gridView.setAdapter(listAdapter);

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
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}
}
