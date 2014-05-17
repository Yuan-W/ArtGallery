package com.comp1008.group26.utility;

import java.util.ArrayList;
import java.util.List;

import com.comp1008.group26.FlaxmanGallery.AudioActivity;
import com.comp1008.group26.FlaxmanGallery.PartnerActivity;
import com.comp1008.group26.FlaxmanGallery.PhotoActivity;
import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.FlaxmanGallery.TextActivity;
import com.comp1008.group26.FlaxmanGallery.VideoActivity;
import com.comp1008.group26.FlaxmanGallery.WebActivity;
import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.MediaInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class ItemListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Item> data;
	private static LayoutInflater inflater = null;

	public ItemListAdapter(Activity a, ArrayList<Item> b) {
		activity = a;
		data = b;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		View vi = convertView;
		// Item data.get(position) = data.get(position);

		if (convertView == null) {
			vi = inflater.inflate(R.layout.item, null);

			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT, 600);
			vi.setLayoutParams(param);

		}

		ImageView image_srcB = (ImageView) vi.findViewById(R.id.image_src);
		ImageView play = (ImageView) vi.findViewById(R.id.play);
		ImageView volume = (ImageView) vi.findViewById(R.id.volume);
		TextView titleV = (TextView) vi.findViewById(R.id.title);
		TextView summaryV = (TextView) vi.findViewById(R.id.summary);
		TextView largeTitle = (TextView) vi.findViewById(R.id.large_title);

		final int type = data.get(position).getType(); // 0 text 1 video 2 audio
														// 3 image
		final String title = data.get(position).getTitle();
		final String summary = data.get(position).getSummary();
		final String image_src = data.get(position).getImage_src();
		final String body = data.get(position).getBody();
		final String link = data.get(position).getLink();
		final String caption = data.get(position).getCaption();
		final String website = data.get(position).getWebsite();
		final String relatedInfoList = data.get(position).getRelatedInfoList();
		
		/*if (type == Item.TEXT) {
			image_srcB.setVisibility(View.GONE);
			volume.setVisibility(View.GONE);
			play.setVisibility(View.GONE);
			titleV.setVisibility(View.VISIBLE);
			summaryV.setVisibility(View.VISIBLE);
			titleV.setText(Html.fromHtml((title)));
			summaryV.setText(Html.fromHtml((summary)));
			largeTitle.setVisibility(View.GONE);
		} else */
		if (type == Item.AUDIO) {
			volume.setVisibility(View.VISIBLE);
			image_srcB.setVisibility(View.VISIBLE);
			titleV.setVisibility(View.VISIBLE);
			summaryV.setVisibility(View.VISIBLE);
			play.setVisibility(View.GONE);
			titleV.setText(Html.fromHtml((title)));
			summaryV.setText(Html.fromHtml((summary)));
			image_srcB.setImageBitmap(BitmapFactory.decodeFile(image_src));
			largeTitle.setVisibility(View.GONE);
		} else if (type == Item.VIDEO) {
			volume.setVisibility(View.GONE);
			image_srcB.setVisibility(View.VISIBLE);
			titleV.setVisibility(View.VISIBLE);
			summaryV.setVisibility(View.VISIBLE);
			play.setVisibility(View.VISIBLE);
			play.setImageResource(R.drawable.play);
			titleV.setText(Html.fromHtml((title)));
			summaryV.setText(Html.fromHtml((summary)));
			image_srcB.setImageBitmap(BitmapFactory.decodeFile(image_src));
			largeTitle.setVisibility(View.GONE);
		} else if (type == Item.IMAGE) {
			volume.setVisibility(View.GONE);
			play.setVisibility(View.GONE);
			titleV.setVisibility(View.VISIBLE);
			summaryV.setVisibility(View.VISIBLE);
			image_srcB.setVisibility(View.VISIBLE);
            titleV.setText(Html.fromHtml((title)));
            summaryV.setText(Html.fromHtml((summary)));
			image_srcB.setImageBitmap(BitmapFactory.decodeFile(image_src));
			largeTitle.setVisibility(View.GONE);
		} else if (type == Item.WEB || type == Item.PARTNER) {
			volume.setVisibility(View.GONE);
			largeTitle.setVisibility(View.VISIBLE);
			play.setVisibility(View.GONE);
			image_srcB.setVisibility(View.GONE);
			titleV.setVisibility(View.GONE);
			summaryV.setVisibility(View.GONE);
			largeTitle.setText(Html.fromHtml((title)));
		}

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
				activity.startActivity(intent);
			}
		});
		return vi;
	}
}