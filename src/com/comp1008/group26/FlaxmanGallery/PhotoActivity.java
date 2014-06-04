package com.comp1008.group26.FlaxmanGallery;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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

import java.util.ArrayList;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class PhotoActivity extends Activity implements OnClickListener
{
    String title;
    String body;
    String caption;
    String img;
    TimeoutManager latestTOM;

    View decorView;

    ImageView imageView;
    boolean isPlay = true;
    android.view.ViewGroup.LayoutParams layoutParamsParent;
    android.view.ViewGroup.LayoutParams layoutParams;

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
        setContentView(R.layout.activity_photo);

        decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        title = getIntent().getExtras().getString("title");
        body = getIntent().getExtras().getString("body");
        caption = getIntent().getExtras().getString("caption");
        img = getIntent().getExtras().getString("link");

        imageView = ((ImageView) findViewById(R.id.videomain));
        imageView.setImageBitmap(BitmapFactory.decodeFile(img));
        imageView.setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText(Html.fromHtml(title));
        ((TextView) findViewById(R.id.body)).setText(Html.fromHtml(body));
        ((TextView) findViewById(R.id.caption)).setText(Html.fromHtml(caption));
        ((ImageButton) findViewById(R.id.home)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.fontsize)).setOnClickListener(this);

        layoutParamsParent = findViewById(R.id.photoLayoutParent).getLayoutParams();
        layoutParams = imageView.getLayoutParams();

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        String relatedItemRaw = getIntent().getExtras().getString("relatedInfoList");
        ArrayList<Item> items = new ArrayList<Item>();

        if (!relatedItemRaw.trim().equals(""))
        {
            String[] relatedList = relatedItemRaw.split(",");
            for (String relatedItem : relatedList)
            {
                MediaInfo info = databaseHandler.getMediaInfo(relatedItem);

//                String msg = "\nRelatedID: " + info.getId() + ",\nTitle: "
//                        + info.getTitle() + ",\nName: " + info.getFileName()
//                        + ",\nSummary: " + info.getSummary() + ",\nDescription: "
//                        + info.getDescription() + ",\nThumbnail: "
//                        + info.getThumbnailName() + ",\nPath: "
//                        + info.getFilePath() + ",\nRelated: "
//                        + info.getRelatedItems() + ",\nIsOnHome: "
//                        + info.getIsOnHomeGrid() + "\n\n";
//                System.out.println(msg);
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
        }

        ((HorizontalListView) findViewById(R.id.horizonListview)).setAdapter(new ItemListAdapterSmall(this, items));

        TimeoutManager tom = new TimeoutManager(this, this.title);
        imageView.postDelayed(tom, 300000);
        latestTOM = tom;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.video, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        latestTOM.setTimeout(false);
        TimeoutManager tom = new TimeoutManager(this, title);
        v.postDelayed(tom, 300000);
        latestTOM = tom;

        switch (v.getId())
        {

            case R.id.fontsize:
            {
                ButtonEventHandler.changeFontSize(this);
                break;
            }
            case R.id.home:
            {
                UsageLog.getInstance().writeEvent(Action.EXIT, this.title);
                latestTOM.setTimeout(false);
                ButtonEventHandler.backToHome(this);
                break;
            }
            case R.id.videomain:
            {
                if (isPlay)
                {

                    UsageLog.getInstance().writeEvent(Action.PLAY, this.title);

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 0, 0);
                    params.width = layoutParamsParent.width;
                    params.height = layoutParamsParent.height;
                    findViewById(R.id.photoLayoutParent).setLayoutParams(params);
                    findViewById(R.id.photoLayoutParent).setBackgroundColor(Color.BLACK);

                    RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    llp.setMargins(0, 0, 0, 0);

                    llp.width = layoutParamsParent.width;;
                    llp.height = layoutParamsParent.height;;
                    imageView.setLayoutParams(llp);

                    findViewById(R.id.home).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fontsize).setVisibility(View.INVISIBLE);
                    findViewById(R.id.remind).setVisibility(View.INVISIBLE);
                    findViewById(R.id.title).setVisibility(View.INVISIBLE);
                    findViewById(R.id.body).setVisibility(View.INVISIBLE);
                    findViewById(R.id.caption).setVisibility(View.INVISIBLE);

                /*
				 * layoutParams.width = parent_w; layoutParams.height =
				 * parent_h; videoView.setLayoutParams(layoutParams);
				 */

                } else
                {
                    UsageLog.getInstance().writeEvent(Action.PAUSE, this.title);

                    findViewById(R.id.photoLayoutParent).setBackgroundColor(Color.parseColor("#BBFFFFFF"));
                    findViewById(R.id.photoLayoutParent).setLayoutParams(layoutParamsParent);
                    imageView.setLayoutParams(layoutParams);
                    findViewById(R.id.home).setVisibility(View.VISIBLE);
                    findViewById(R.id.title).setVisibility(View.VISIBLE);
                    findViewById(R.id.body).setVisibility(View.VISIBLE);
                    findViewById(R.id.caption).setVisibility(View.VISIBLE);
                    findViewById(R.id.fontsize).setVisibility(View.VISIBLE);
                    findViewById(R.id.remind).setVisibility(View.VISIBLE);
                }

                isPlay = !isPlay;
                break;
            }

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {

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
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        // super.onBackPressed();
    }

}
