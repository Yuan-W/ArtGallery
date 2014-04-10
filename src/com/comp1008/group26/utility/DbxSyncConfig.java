package com.comp1008.group26.utility;

import android.content.Context;
import android.os.Environment;
import com.dropbox.sync.android.DbxAccountManager;

/**
 * Created by Yuan on 16/03/14.
 */

public final class DbxSyncConfig
{
    private DbxSyncConfig() {}

    public static final int REQUEST_LINK_TO_DBX = 0;

    public static final String appKey = "viaohzfio4qj4dq";
    public static final String appSecret = "1m4a46y34gezltu";

    public static final String storeDir = Environment.getExternalStorageDirectory() + "/ArtGallery/";

    public static DbxAccountManager getAccountManager(Context context)
    {
        return DbxAccountManager.getInstance(context.getApplicationContext(), appKey, appSecret);
    }
}
