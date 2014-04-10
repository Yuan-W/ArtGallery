package com.comp1008.group26.FlaxmanGallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import com.comp1008.group26.utility.*;
import com.dropbox.sync.android.*;

import java.io.*;
import java.util.List;

import static com.comp1008.group26.utility.DbxSyncConfig.getAccountManager;

	
public class MainActivity extends Activity implements View.OnClickListener
{
    private DbxAccountManager mDbxAcctMgr;
    private static final String LOG_TAG = "ART_GALLERY";
    private DbxPath mSyncPath;
    private ProgressDialog syncProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbxAcctMgr = getAccountManager(this);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

        findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickUpdate();
            }
        });

        linkToDropbox();
    }

    private void onClickUpdate()
    {
        try
        {
            CharSequence[] charSequence = getArmatureList();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Folders").setItems(charSequence, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int armatureIndex)
                {
                    updateFile(dialog, armatureIndex);
                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        catch (DbxException e)
        {
            e.printStackTrace();
        }
    }

    private CharSequence[] getArmatureList() throws DbxException
    {
        List<DbxFileInfo> folderList = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount()).listFolder(DbxPath.ROOT);
        CharSequence[] charSequence = new CharSequence[folderList.size()];
        int i = 0;
        for (DbxFileInfo info : folderList)
        {
            charSequence[i++] = info.path.getName();
        }
        return charSequence;
    }

    private void updateFile(DialogInterface dialog, int armatureIndex)
    {
        String selectedArmature = ((AlertDialog) dialog).getListView().getItemAtPosition(armatureIndex).toString();
        Log.d(LOG_TAG, selectedArmature);
        mSyncPath = DbxPath.ROOT.getChild(selectedArmature);

        new FileSyncTask(this, mDbxAcctMgr, mSyncPath).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button1:
            {
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button2:
            {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void linkToDropbox()
    {
        if (!mDbxAcctMgr.hasLinkedAccount())
        {
            mDbxAcctMgr.startLink(this, DbxSyncConfig.REQUEST_LINK_TO_DBX);
        }
    }
}
