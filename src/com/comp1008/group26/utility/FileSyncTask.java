package com.comp1008.group26.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.comp1008.group26.Model.DatabaseHandler;
import com.comp1008.group26.Model.MediaInfo;
import com.dropbox.sync.android.*;

import java.io.*;
import java.util.*;

/**
 * AsyncTask for dropbox file and database synchronization.
 *
 * @author  Yuan Wei
 */

public class FileSyncTask extends AsyncTask<Void, String, List<MediaInfo>>
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
    protected List<MediaInfo> doInBackground(Void... params)
    {
        DbxFileSystem fs = getDbxFileSystem();
        List<MediaInfo> mediaInfoList = new ArrayList<MediaInfo>();
        if (fs != null)
        {
            try
            {
                DbxDatastore store = DbxDatastore.openDefault(mAccountManager.getLinkedAccount());
                publishProgress("Syncing Database...");
                store.sync();
                while (store.getSyncStatus().hasIncoming || store.getSyncStatus().isDownloading)
                {
                }
                DbxTable tasksTbl = store.getTable("media");
                DbxTable.QueryResult results = tasksTbl.query();
                Iterator<DbxRecord> iterator = results.iterator();
                String armatureDownload = mPath.getName();
                while(iterator.hasNext())
                {
                    DbxRecord record = iterator.next();
                    String armature = record.getString("armature");

                    if(armature.equals(armatureDownload))
                    {
                        String title = record.getString("title");
                        String fileName = record.getString("file_name");
                        String summary = record.getString("summary");
                        String description = record.getString("description");
                        String caption = record.getString("caption");
                        String thumbnail = record.getString("thumbnail_name");
                        String relatedItems = record.getString("related_items");
                        Boolean isOnHomeGrid = record.getBoolean("is_on_home_grid");
                        MediaInfo.FileType fileType = MediaInfo.FileType.from((int) record.getLong("file_type"));
                        int order = (int) record.getLong("order");
                        Log.d(LOG_TAG, String.valueOf(order));
                        mediaInfoList.add(new MediaInfo(title, fileName, summary, description, caption, thumbnail, relatedItems, isOnHomeGrid, fileType, order));
                    }
                }
                store.close();

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

                return mediaInfoList;
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
    }

    @Override
    protected void onPostExecute(List<MediaInfo> result)
    {
        super.onPostExecute(result);

        Iterator<MediaInfo> iterator = result.iterator();
        while(iterator.hasNext())
        {
            MediaInfo info = iterator.next();
            mDatabase.addMediaInfo(info);
        }

        List<MediaInfo> infoList = mDatabase.getAllMediaInfo();

        for(MediaInfo info : infoList)
        {
            String msg = "ID: " + info.getId() + ", Title: " + info.getTitle() + ", Name: " + info.getFileName() +
                    ", Summary" + info.getSummary() + ", Description: " + info.getDescription() +
                    ", Caption: " + info.getCaption() + ", Thumbnail: " + info.getThumbnailName() +
                    ", Path: " + info.getFilePath() + ", Related: " + info.getRelatedItems() +
                    ", IsOnHome: " + info.getIsOnHomeGrid() + ", FileType: " + info.getFileType() + ", Order: " + info.getOrder();
            Log.d("MediaInfo: ", msg);
        }
        mProgressDialog.dismiss();
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