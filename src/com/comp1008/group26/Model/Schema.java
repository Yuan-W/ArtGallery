package com.comp1008.group26.Model;

import android.provider.BaseColumns;

public final class Schema
{
    private static final String TEXT_TYPE = " TEXT";
    private static final String PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE ";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";

    public static abstract class MediaInfoEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "media";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_THUMBNAIL = "thumbnail";

        public static final String SQL_CREATE_ENTRIES = SQL_CREATE_TABLE + TABLE_NAME + " (" +
                _ID + PRIMARY_KEY_TYPE + COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                COLUMN_THUMBNAIL + TEXT_TYPE +
                " )";

        public static final String SQL_DELETE_ENTRIES = SQL_DROP_TABLE + TABLE_NAME;
    }

}
