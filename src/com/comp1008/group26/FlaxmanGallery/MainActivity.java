package com.comp1008.group26.FlaxmanGallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.dropbox.sync.android.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.comp1008.group26.FlaxmanGallery.DbxSyncConfig.getAccountManager;

	
public class MainActivity extends Activity implements View.OnClickListener
{
    private DbxAccountManager mDbxAcctMgr;
    private static final String mTag = "ART_GALLERY";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbxAcctMgr = getAccountManager(this);

        ((Button) findViewById(R.id.button1)).setOnClickListener(this);
        ((Button) findViewById(R.id.button2)).setOnClickListener(this);

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
                public void onClick(DialogInterface dialog, int which)
                {
                    String selectedArmature = ((AlertDialog) dialog).getListView().getItemAtPosition(which).toString();
                    DbxPath syncPath = DbxPath.ROOT.getChild(selectedArmature);
                    try
                    {
                        DbxFileSystem fileSystem = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
                        List<DbxFileInfo> fileList = fileSystem.listFolder(syncPath);
                        Log.d(mTag, "Sync Path: " + syncPath.toString());
                        File dbxStoreDir = new File(DbxSyncConfig.storeDir);
                        if(!dbxStoreDir.exists())
                        {
                            Log.d(mTag, "Download Path: " + dbxStoreDir.toString());
                            dbxStoreDir.mkdirs();
                        }

                        for (DbxFileInfo info : fileList)
                        {
                            Log.d(mTag, "Downloading File: " + info.path.getName());
                            DbxFile inputFile = fileSystem.open(info.path);
                            InputStream inputStream = inputFile.getReadStream();
                            FileOutputStream outputStream = new FileOutputStream(dbxStoreDir + "/" + info.path.getName());
                            byte data[] = new byte[1024];
                            int count;
                            while ((count = inputStream.read(data)) != -1)
                            {
                                outputStream.write(data, 0, count);
                            }
                            outputStream.flush();
                            outputStream.close();
                            inputStream.close();
                            inputFile.close();
                        }
                    }
                    catch (DbxException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    Log.d(mTag, selectedArmature);
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
