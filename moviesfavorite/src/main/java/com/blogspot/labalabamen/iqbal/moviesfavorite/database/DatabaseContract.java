package com.blogspot.labalabamen.iqbal.moviesfavorite.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "T_favorite_movie";

    public static final String AUTHORITY = "com.blogspot.labalabamen.iqbal.cataloguemovieui_ux";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final class FavoriteField implements BaseColumns {


        public static String FIELD_TITLE          = "title";
        public static String FIELD_POSTER         = "poster";
        public static String FIELD_RELEASE_DATE   = "release_date";
        public static String FIELD_RATE           = "rate";
        public static String FIELD_OVERVIEW       = "overview";
    }

    public static String getFieldString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getFieldInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getFieldLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

    public static Double getFieldDouble(Cursor cursor, String columnName) {
        return cursor.getDouble( cursor.getColumnIndex(columnName) );
    }

}
