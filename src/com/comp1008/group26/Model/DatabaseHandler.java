package com.comp1008.group26.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "ArtGallery.db";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Schema.MediaInfoEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(Schema.MediaInfoEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addMediaInfo(MediaInfo info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;

        ContentValues values = new ContentValues();
        values.put(Schema.MediaInfoEntry.COLUMN_TITLE, info.getTitle());
        values.put(Schema.MediaInfoEntry.COLUMN_FILE_NAME, info.getFileName());
        values.put(Schema.MediaInfoEntry.COLUMN_DESCRIPTION, info.getDescription());
        values.put(Schema.MediaInfoEntry.COLUMN_THUMBNAIL, info.getThumbnail());

        db.insert(Schema.MediaInfoEntry.TABLE_NAME, null, values);
        db.close();
    }


    public MediaInfo getMediaInfo(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        assert db != null;

        Cursor cursor = db.query(Schema.MediaInfoEntry.TABLE_NAME, new String[] { Schema.MediaInfoEntry._ID,
                Schema.MediaInfoEntry.COLUMN_TITLE, Schema.MediaInfoEntry.COLUMN_FILE_NAME,
                Schema.MediaInfoEntry.COLUMN_DESCRIPTION, Schema.MediaInfoEntry.COLUMN_THUMBNAIL },
                Schema.MediaInfoEntry._ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        return new MediaInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
    }

    public List<MediaInfo> getAllMediaInfo()
    {
        List<MediaInfo> mediaList = new ArrayList<MediaInfo>();

        String selectQuery = "SELECT  * FROM " + Schema.MediaInfoEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                MediaInfo info = new MediaInfo(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

                mediaList.add(info);
            } while (cursor.moveToNext());
        }

        return mediaList;
    }

    public int updateMediaInfo(MediaInfo info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;

        ContentValues values = new ContentValues();
        values.put(Schema.MediaInfoEntry.COLUMN_TITLE, info.getTitle());
        values.put(Schema.MediaInfoEntry.COLUMN_FILE_NAME, info.getFileName());
        values.put(Schema.MediaInfoEntry.COLUMN_DESCRIPTION, info.getDescription());
        values.put(Schema.MediaInfoEntry.COLUMN_THUMBNAIL, info.getThumbnail());

        return db.update(Schema.MediaInfoEntry.TABLE_NAME, values, Schema.MediaInfoEntry._ID + " = ?",
                new String[] { String.valueOf(info.getId()) });
    }

    public void deleteMediaInfo(MediaInfo info)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        assert db != null;
        db.delete(Schema.MediaInfoEntry.TABLE_NAME, Schema.MediaInfoEntry._ID + " = ?",
                new String[] { String.valueOf(info.getId()) });
        db.close();
    }

    public int getMediaInfoCount()
    {
        String countQuery = "SELECT  * FROM " + Schema.MediaInfoEntry.TABLE_NAME;
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
        db.execSQL("DELETE FROM " + Schema.MediaInfoEntry.TABLE_NAME);
    }
}
