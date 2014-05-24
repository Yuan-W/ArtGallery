package com.comp1008.group26.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.comp1008.group26.FlaxmanGallery.AudioActivity;
import com.comp1008.group26.FlaxmanGallery.PartnerActivity;
import com.comp1008.group26.FlaxmanGallery.PhotoActivity;
import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.FlaxmanGallery.VideoActivity;
import com.comp1008.group26.FlaxmanGallery.WebActivity;
import com.comp1008.group26.Model.Item;
import com.comp1008.group26.utility.UsageLog.Action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemListAdapterSmall extends BaseAdapter {

	private Activity activity;
	private ArrayList<Item> data;
	private String activityName;
	private static LayoutInflater inflater=null;

	public ItemListAdapterSmall(Activity a, ArrayList<Item> b) {
		activity = a;
		data = b;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return data.get(position).hashCode();
	}



	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;

		if(convertView==null)
			vi = inflater.inflate(R.layout.related_item, null);

		final int type = data.get(position).getType(); // 0 text 1 video 2 audio 3 image
		final String title = data.get(position).getTitle();
		final String summary = data.get(position).getSummary();
		final String image_src = data.get(position).getImage_src();
		final String body = data.get(position).getBody();
		final String link = data.get(position).getLink();
		final String caption = data.get(position).getCaption();
		final String website = data.get(position).getWebsite();
		final String relatedInfoList = data.get(position).getRelatedInfoList();

		ImageView image=(ImageView)vi.findViewById(R.id.image_src);
		image.setImageBitmap(BitmapFactory.decodeFile(image_src));
		//text.setText(title);


		vi.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(activity, VideoActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				/*if (type == Item.TEXT) {
 					intent = new Intent(activity, TextActivity.class)
 							.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 				} else*/ 
				if (type == Item.AUDIO) {
					intent = new Intent(activity, AudioActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				} else if (type == Item.VIDEO) {
					intent = new Intent(activity, VideoActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				} else if (type == Item.IMAGE) {
					intent = new Intent(activity, PhotoActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				} else if (type == Item.WEB) {
					intent = new Intent(activity, WebActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				} else if (type == Item.PARTNER) {
					intent = new Intent(activity, PartnerActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				}
				intent.putExtra("title", title);
				intent.putExtra("body", body);
				intent.putExtra("caption", caption);
				intent.putExtra("img", image_src);
				intent.putExtra("link", link);
				intent.putExtra("website", website);
				intent.putExtra("relatedInfoList", relatedInfoList);
				TimeoutManager.cancelAll();
				UsageLog.getInstance().writeEvent(Action.ENTER, title);
				activity.startActivity(intent);
			}
		});
		return vi;
	}
}