package com.comp1008.group26.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.comp1008.group26.FlaxmanGallery.SettingActivity;
import com.comp1008.group26.FlaxmanGallery.StartActivity;

/**
 * @author Yuan Wei
 */
public final class ButtonEventHandler
{
    public static void backToHome(Activity activity)
    {
        Intent intent = new Intent(activity, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public  static void changeFontSize(Activity activity)
    {
        if(SettingActivity.fontSize == 1)
            SettingActivity.fontSize =2;
        else
            SettingActivity.fontSize =1;

        activity.recreate();
    }
}
