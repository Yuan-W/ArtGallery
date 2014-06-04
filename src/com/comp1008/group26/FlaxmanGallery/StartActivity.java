package com.comp1008.group26.FlaxmanGallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import com.comp1008.group26.Model.DatabaseHandler;
import com.comp1008.group26.Model.MediaInfo;
import com.comp1008.group26.utility.ItemListAdapter;

import java.util.List;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class StartActivity extends Activity implements OnClickListener
{
    ItemListAdapter listAdapter;
    View decorView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        if (SettingActivity.fontSize == 2)
        {
            setTheme(R.style.Theme_Large);
        } else if (SettingActivity.fontSize == 1)
        {
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

//		Item i8 = new Item();
//		i8.setType(Item.PARTNER);
//		i8.setTitle("Partner");
//		items.add(i8);

        listAdapter = new ItemListAdapter(this, infoList);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(listAdapter);

        ((ImageButton) findViewById(R.id.home)).setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                    // bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.home:
            {
                gridView.smoothScrollToPositionFromTop(0, 0, 1000);
                break;
            }
        }
    }
}
