package com.comp1008.group26.Model;

import android.provider.BaseColumns;

/**
 * Database Schema, stores constants needed for database operation.
 *
 * @author  Yuan Wei
 */

public final class Schema
{
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE ";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";

    public static abstract class MediaInfoEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "media";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_FILE_NAME = "file_name";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_THUMBNAIL_NAME = "thumbnail_name";
        public static final String COLUMN_RELATED_ITEMS= "related_items";
        public static final String COLUMN_IS_ON_HOME_GRID = "is_on_home_grid";

        public static final String SQL_CREATE_ENTRIES = SQL_CREATE_TABLE + TABLE_NAME + " (" +
                _ID + PRIMARY_KEY_TYPE + COMMA_SEP +
                COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_FILE_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_SUMMARY + TEXT_TYPE + COMMA_SEP +
                COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                COLUMN_THUMBNAIL_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_RELATED_ITEMS + TEXT_TYPE + COMMA_SEP +
                COLUMN_IS_ON_HOME_GRID + INTEGER_TYPE +
                " )";

        public static final String SQL_DELETE_ENTRIES = SQL_DROP_TABLE + TABLE_NAME;
    }

}
