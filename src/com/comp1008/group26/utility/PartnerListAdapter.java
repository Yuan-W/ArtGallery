package com.comp1008.group26.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.comp1008.group26.FlaxmanGallery.AudioActivity;
import com.comp1008.group26.FlaxmanGallery.PhotoActivity;
import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.FlaxmanGallery.TextActivity;
import com.comp1008.group26.FlaxmanGallery.VideoActivity;
import com.comp1008.group26.Model.Item;
import com.comp1008.group26.Model.Partner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PartnerListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Partner> data;
    private static LayoutInflater inflater=null;
    public PartnerListAdapter(Activity a, ArrayList<Partner> b) {
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
    


	public View getView( int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        Partner p = data.get(position) ;

        if(convertView==null)
        {
        	vi = inflater.inflate(R.layout.item_partner, null);
        	
        }

       ((ImageView)vi.findViewById(R.id.logo)).setBackgroundResource(p.getLogo());
       ( (TextView)vi.findViewById(R.id.name)).setText(p.getName());
       ( (TextView)vi.findViewById(R.id.details)).setText(p.getDetails());
       ( (TextView)vi.findViewById(R.id.link)).setText(p.getLink());
        
        vi.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
	           	
            }
        }); 
        return vi;
    }
}