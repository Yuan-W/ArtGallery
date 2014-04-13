package com.comp1008.group26.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.comp1008.group26.Model.DatabaseHandler;
import com.comp1008.group26.Model.MediaInfo;
import com.dropbox.sync.android.*;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileSyncTask extends AsyncTask<Void, String, Void>
{
    private final DbxPath mPath;
    private final DbxAccountManager mAccountManager;
    private final Comparator<DbxFileInfo> mSortComparator;

    private ProgressDialog mProgressDialog;
    private DatabaseHandler mDatabase;

    private static final String LOG_TAG = "FILE_LOADER";

    public FileSyncTask(Context context,DbxAccountManager accountManager, DbxPath path)
    {
        this(context, accountManager, path, FolderListComparator.getNameFirst(true));
    }

    /**
     * Creates a FileSyncTask for the given path.
     *
     * @param path
     *            Path of folder to load
     * @param sortComparator
     *            A comparator for sorting the folder contents before they're
     *            delivered. May be null for no sort.
     */
    public FileSyncTask(Context context, DbxAccountManager accountManager, DbxPath path, Comparator<DbxFileInfo> sortComparator)
    {
        super();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Updating Files");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        mAccountManager = accountManager;
        mPath = path;
        mSortComparator = sortComparator;

        mDatabase = new DatabaseHandler(context);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mProgressDialog.setMessage("Downloading File...");
        mProgressDialog.show();
        mDatabase.clearMediaInfoTable();
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        DbxFileSystem fs = getDbxFileSystem();
        if (fs != null)
        {
            try
            {
                List<DbxFileInfo> entries = fs.listFolder(mPath);

                if (mSortComparator != null)
                {
                    Collections.sort(entries, mSortComparator);
                    File dbxStoreDir = new File(DbxSyncConfig.storeDir);
                    if(!dbxStoreDir.exists())
                    {
                        Log.d(LOG_TAG, "Download Path: " + dbxStoreDir.toString());
                        dbxStoreDir.mkdirs();
                    }
                    for (DbxFileInfo info : entries)
                    {
                        String fileName = info.path.getName();
                        Log.d(LOG_TAG, "Downloading File: " + fileName);
                        publishProgress(info.path.getName());
                        DbxFile inputFile = fs.open(info.path);

                        File outputFile = new File(dbxStoreDir + "/" + fileName);
                        if(!outputFile.exists())
                        {
                            InputStream inputStream = inputFile.getReadStream();
                            FileOutputStream outputStream = new FileOutputStream(dbxStoreDir + "/" + fileName);
                            byte data[] = new byte[1024];
                            int count;
                            while ((count = inputStream.read(data)) != -1)
                            {
                                outputStream.write(data, 0, count);
                            }
                            outputStream.flush();
                            outputStream.close();
                            inputStream.close();
                        }
                        inputFile.close();
                    }
                }

                return null;
            } catch (DbxException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... message)
    {
        super.onProgressUpdate(message);
        String fileName = message[0];
        mProgressDialog.setMessage(fileName);
        mDatabase.addMediaInfo(new MediaInfo(fileName, "1234567"));
        Log.d("INSERT: ", fileName);
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);
        mProgressDialog.dismiss();

        List<MediaInfo> infoList = mDatabase.getAllMediaInfo();

        for(MediaInfo info : infoList)
        {
            String msg = "ID: " + info.getId() + ", Name: " + info.getFileName() +
                    ", Description: " + info.getDescription() + ", Thumbnail:" + info.getThumbnail() +
                    ", Path: " + info.getPath();
            Log.d("MediaInfo: ", msg);
        }

    }

    private DbxFileSystem getDbxFileSystem()
    {
        DbxAccount account = mAccountManager.getLinkedAccount();
        if (account != null)
        {
            try
            {
                return DbxFileSystem.forAccount(account);
            }
            catch (DbxException.Unauthorized e)
            {
                // Account was unlinked asynchronously from server.
                return null;
            }
        }
        return null;
    }
}