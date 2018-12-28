package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.StackWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.BuildConfig;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField.*;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.bumptech.glide.Glide;


import java.lang.annotation.Target;
import java.util.concurrent.ExecutionException;

public class FavoriteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;

    int mAppWidgetId;

    private Cursor cursor;

    public FavoriteRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private ItemsListMovie getFav(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new  ItemsListMovie(cursor);

    }


    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                DatabaseContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                DatabaseContract.CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        ItemsListMovie listMovie = getFav(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w500/"+ listMovie.getImage_movie())
                    .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
