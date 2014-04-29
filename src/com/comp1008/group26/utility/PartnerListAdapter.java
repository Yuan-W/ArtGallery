package com.comp1008.group26.utility;

import java.util.ArrayList;
import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.Model.Partner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class PartnerListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Partner> data;
	private static LayoutInflater inflater = null;

	public PartnerListAdapter(Activity a, ArrayList<Partner> b) {
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
		Partner p = data.get(position);

		if (convertView == null) {
			vi = inflater.inflate(R.layout.item_partner, null);

		}
		final String link = p.getLink();
		((ImageView) vi.findViewById(R.id.logo)).setBackgroundResource(p
				.getLogo());
		((TextView) vi.findViewById(R.id.name)).setText(Html.fromHtml((p
				.getName())));
		((TextView) vi.findViewById(R.id.details)).setText(Html.fromHtml((p
				.getDetails())));

		// (TextView)vi.findViewById(R.id.link)).setText(Html.fromHtml((p.getLink());
		((Button) vi.findViewById(R.id.button1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(link));
						activity.startActivity(myIntent);
					}
				});
	
		return vi;
	}
}