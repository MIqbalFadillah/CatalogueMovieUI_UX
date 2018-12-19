package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.MovieFavoriteHelper;

import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.AUTHORITY;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.CONTENT_URI;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.TABLE_NAME;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE       = 100;
    private static final int FAVORITE_ID    = 101;

    private MovieFavoriteHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {


        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE);


        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME+ "/#",
                FAVORITE_ID);
    }


    @Override
    public boolean onCreate() {
        favoriteHelper = new MovieFavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable  String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                cursor = favoriteHelper.queryProvider();
                break;

            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                added = favoriteHelper.insertProvider(values);
                break;

            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                updated = favoriteHelper.updateProvider(uri.getLastPathSegment(), values);
                break;

            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;

    }
}
