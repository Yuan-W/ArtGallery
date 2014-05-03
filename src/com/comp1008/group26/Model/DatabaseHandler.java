package com.comp1008.group26.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.comp1008.group26.Model.Schema.*;

/**
 * DatabaseHandler, provides REST API of MediaInfo Model stored in the database..
 *
 * @author  Yuan Wei
 */

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "ArtGallery.db";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(MediaInfoEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(MediaInfoEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addMediaInfo(MediaInfo info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;

        ContentValues values = new ContentValues();
        values.put(MediaInfoEntry.COLUMN_TITLE, info.getTitle());
        values.put(MediaInfoEntry.COLUMN_FILE_NAME, info.getFileName());
        values.put(MediaInfoEntry.COLUMN_SUMMARY, info.getSummary());
        values.put(MediaInfoEntry.COLUMN_DESCRIPTION, info.getDescription());
        values.put(MediaInfoEntry.COLUMN_CAPTION, info.getCaption());
        values.put(MediaInfoEntry.COLUMN_THUMBNAIL_NAME, info.getThumbnailName());
        values.put(MediaInfoEntry.COLUMN_RELATED_ITEMS, info.getRelatedItems());
        values.put(MediaInfoEntry.COLUMN_IS_ON_HOME_GRID, booleanToInt(info.getIsOnHomeGrid()));
        values.put(MediaInfoEntry.COLUMN_FILE_TYPE, info.getFileType().getValue());
        values.put(MediaInfoEntry.COLUMN_ORDER, info.getOrder());
        db.insert(MediaInfoEntry.TABLE_NAME, null, values);
        db.close();
    }


    public MediaInfo getMediaInfo(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        assert db != null;

        Cursor cursor = db.query(MediaInfoEntry.TABLE_NAME, new String[] { MediaInfoEntry._ID,
                MediaInfoEntry.COLUMN_TITLE, MediaInfoEntry.COLUMN_FILE_NAME, MediaInfoEntry.COLUMN_SUMMARY, MediaInfoEntry.COLUMN_DESCRIPTION,
                MediaInfoEntry.COLUMN_CAPTION, MediaInfoEntry.COLUMN_THUMBNAIL_NAME, MediaInfoEntry.COLUMN_RELATED_ITEMS, MediaInfoEntry.COLUMN_IS_ON_HOME_GRID,
                MediaInfoEntry.COLUMN_FILE_TYPE, MediaInfoEntry.COLUMN_ORDER},
                MediaInfoEntry._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return new MediaInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                intToBoolean(cursor.getInt(8)), MediaInfo.FileType.values()[cursor.getInt(9)], cursor.getInt(10));
    }

    public List<MediaInfo> getAllMediaInfo()
    {
        List<MediaInfo> mediaList = new ArrayList<MediaInfo>();

        String selectQuery = "SELECT  * FROM " + MediaInfoEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                MediaInfo info = new MediaInfo(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        intToBoolean(cursor.getInt(8)), MediaInfo.FileType.from(cursor.getInt(9)), cursor.getInt(10));

                mediaList.add(info);
            } while (cursor.moveToNext());
        }

        Collections.sort(mediaList, new Comparator<MediaInfo>()
        {
            @Override
            public int compare(MediaInfo info1, MediaInfo info2)
            {
                return info1.getOrder() - info2.getOrder();
            }
        });

        return mediaList;
    }

    public int updateMediaInfo(MediaInfo info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;

        ContentValues values = new ContentValues();
        values.put(MediaInfoEntry.COLUMN_TITLE, info.getTitle());
        values.put(MediaInfoEntry.COLUMN_FILE_NAME, info.getFileName());
        values.put(MediaInfoEntry.COLUMN_SUMMARY, info.getSummary());
        values.put(MediaInfoEntry.COLUMN_DESCRIPTION, info.getDescription());
        values.put(MediaInfoEntry.COLUMN_CAPTION, info.getCaption());
        values.put(MediaInfoEntry.COLUMN_THUMBNAIL_NAME, info.getThumbnailName());
        values.put(MediaInfoEntry.COLUMN_RELATED_ITEMS, info.getRelatedItems());
        values.put(MediaInfoEntry.COLUMN_IS_ON_HOME_GRID, booleanToInt(info.getIsOnHomeGrid()));
        values.put(MediaInfoEntry.COLUMN_FILE_TYPE, info.getFileType().getValue());
        values.put(MediaInfoEntry.COLUMN_ORDER, info.getOrder());

        return db.update(MediaInfoEntry.TABLE_NAME, values, MediaInfoEntry._ID + " = ?",
                new String[] { String.valueOf(info.getId()) });
    }

    public void deleteMediaInfo(MediaInfo info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;
        db.delete(MediaInfoEntry.TABLE_NAME, MediaInfoEntry._ID + " = ?",
                new String[] { String.valueOf(info.getId()) });
        db.close();
    }

    public int getMediaInfoCount()
    {
        String countQuery = "SELECT  * FROM " + MediaInfoEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        assert db != null;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void clearMediaInfoTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;
        db.execSQL("DELETE FROM " + MediaInfoEntry.TABLE_NAME);
    }

    private Boolean intToBoolean(Integer value)
    {
        return value>0;
    }

    private Integer booleanToInt(Boolean value)
    {
        return value ? 1 : 0;
    }
}
