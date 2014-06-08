package com.comp1008.group26.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.comp1008.group26.FlaxmanGallery.AudioActivity;
import com.comp1008.group26.FlaxmanGallery.PhotoActivity;
import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.FlaxmanGallery.VideoActivity;
import com.comp1008.group26.Model.MediaInfo;
import com.comp1008.group26.utility.UsageLog.Action;

import java.util.List;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk)
 */
public class ItemListAdapter extends BaseAdapter
{

    private static LayoutInflater inflater = null;
    private Activity activity;
    private List<MediaInfo> data;

    public ItemListAdapter(Activity a, List<MediaInfo> b)
    {
        activity = a;
        data = b;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static int getImageScale(String imagePath)
    {
        BitmapFactory.Options option = new BitmapFactory.Options();
        // set inJustDecodeBounds to true, allowing the caller to query the bitmap info without having to allocate the
        // memory for its pixels.
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, option);

        int scale = 1;
        int IMAGE_MAX_WIDTH = 384;
        int IMAGE_MAX_HEIGHT = 384;
        while (option.outWidth / scale >= IMAGE_MAX_WIDTH || option.outHeight / scale >= IMAGE_MAX_HEIGHT)
        {
            scale *= 2;
        }
        return scale;
    }

    public int getCount()
    {
        return data.size();
    }

    public Object getItem(int position)
    {
        return data.get(position);
    }

    public long getItemId(int position)
    {
        return data.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View vi = convertView;
        // Item data.get(position) = data.get(position);

        if (convertView == null)
        {
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

        final MediaInfo.FileType type = data.get(position).getFileType();
        final String title = data.get(position).getTitle();
        final String summary = data.get(position).getSummary();
        final String image_src = data.get(position).getThumbnailPath();
        final String body = data.get(position).getDescription();
        final String link = data.get(position).getFilePath();
        final String caption = data.get(position).getCaption();
//		final String website = data.get(position).getWebsite();
        final String relatedInfoList = data.get(position).getRelatedItems();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = getImageScale(image_src);

        Bitmap thumbnail = BitmapFactory.decodeFile(image_src, options);
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
        image_srcB.setVisibility(View.VISIBLE);
        image_srcB.setImageBitmap(thumbnail);
        titleV.setVisibility(View.VISIBLE);
        titleV.setText(Html.fromHtml((title)));
        summaryV.setVisibility(View.INVISIBLE);
        summaryV.setText(Html.fromHtml((summary)));
        if (type == MediaInfo.FileType.Audio)
        {
            volume.setVisibility(View.VISIBLE);
            play.setVisibility(View.GONE);
        } else if (type == MediaInfo.FileType.Video)
        {
            volume.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
            play.setImageResource(R.drawable.play);
        } else if (type == MediaInfo.FileType.Image)
        {
            volume.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
        }
//      else if (type == Item.WEB || type == Item.PARTNER) {
//			volume.setVisibility(View.GONE);
//			largeTitle.setVisibility(View.VISIBLE);
//			play.setVisibility(View.GONE);
//			image_srcB.setVisibility(View.GONE);
//			titleV.setVisibility(View.GONE);
//			summaryV.setVisibility(View.GONE);
//			largeTitle.setText(Html.fromHtml((title)));
//		}

        vi.setOnClickListener(new OnClickListener()
                              {
                                  public void onClick(View v)
                                  {
                                      Intent intent = new Intent(activity, VideoActivity.class)
                                              .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      /*if (type == Item.TEXT) {
                                            intent = new Intent(activity, TextActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      }*/
                                      if (type == MediaInfo.FileType.Audio)
                                      {
                                          intent = new Intent(activity, AudioActivity.class)
                                                  .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      } else if (type == MediaInfo.FileType.Video)
                                      {
                                          intent = new Intent(activity, VideoActivity.class)
                                                  .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      } else if (type == MediaInfo.FileType.Image)
                                      {
                                          intent = new Intent(activity, PhotoActivity.class)
                                                  .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      }
                                      //else if (type == Item.WEB)
                                      //{
                                      //	intent = new Intent(activity, WebActivity.class)
                                      //			.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      //}
                                      //else if (type == Item.PARTNER)
                                      //{
                                      //	intent = new Intent(activity, PartnerActivity.class)
                                      //			.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                      //}
                                      intent.putExtra("title", title);
                                      intent.putExtra("body", body);
                                      intent.putExtra("caption", caption);
                                      intent.putExtra("img", image_src);
                                      intent.putExtra("link", link);
                                      //intent.putExtra("website", website);
                                      intent.putExtra("relatedInfoList", relatedInfoList);
                                      UsageLog.getInstance().writeEvent(Action.ENTER, title);
                                      activity.startActivity(intent);
                                  }
                              }
        );
        return vi;
    }
}