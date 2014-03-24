package com.comp1008.group26.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.comp1008.group26.FlaxmanGallery.AudioActivity;
import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.FlaxmanGallery.VideoActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class ListAdapterAudio extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<String> data;
    private static LayoutInflater inflater=null;
    public ListAdapterAudio(Activity a, ArrayList<String> b) {
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
        final String title = data.get(position);

        if(convertView==null)
            vi = inflater.inflate(R.layout.item_audio, null);

        TextView text=(TextView)vi.findViewById(R.id.title);
        text.setText(title);
        

        vi.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
	           	Intent intent=new Intent(activity, AudioActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	           	intent.putExtra("title", title);
	           	activity.startActivity(intent);
            }
        }); 
        return vi;
    }
}