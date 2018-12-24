package com.blogspot.labalabamen.iqbal.moviesfavorite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blogspot.labalabamen.iqbal.moviesfavorite.model.AdapterMoviesProvider;

import static com.blogspot.labalabamen.iqbal.moviesfavorite.database.DatabaseContract.CONTENT_URI;

import static com.blogspot.labalabamen.iqbal.moviesfavorite.database.DatabaseContract.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private AdapterMoviesProvider adapter;
    ListView listView;

    private final int DATA_FAV_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.rv_category);
        adapter = new AdapterMoviesProvider(this,null,true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(DATA_FAV_ID,null,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(DATA_FAV_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(DATA_FAV_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Cursor cursor = (Cursor) adapter.getItem(i);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavoriteField._ID));
        Intent intent = new Intent(MainActivity.this, Move_Movie_Detail.class);
        intent.setData(Uri.parse(CONTENT_URI+"/"+id));
        startActivity(intent);


    }
}
