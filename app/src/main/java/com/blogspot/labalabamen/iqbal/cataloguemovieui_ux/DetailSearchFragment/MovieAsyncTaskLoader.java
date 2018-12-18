package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.BuildConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovieAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<ItemsMovie>> {
    private ArrayList<ItemsMovie> mData;
    private boolean mHasResult = false;
    private String mTitleMovie;



    public MovieAsyncTaskLoader(final Context context, String TitleMovie) {
        super(context);

        onContentChanged();
        this.mTitleMovie = TitleMovie;
    }
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<ItemsMovie> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }


    @Override
    public ArrayList<ItemsMovie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<ItemsMovie> ItemsesMovie = new ArrayList<>();
        String url = BuildConfig.MOVIE_SEARCH+"?api_key="+BuildConfig.API_KEY+"&language=en-US&query="+mTitleMovie;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responObject = new JSONObject(result);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){

                        JSONObject movie = list.getJSONObject(i);
                        ItemsMovie movie_item = new ItemsMovie(movie);
                        ItemsesMovie.add(movie_item);

                    }
                }catch (Exception e){
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                
            }
            
        });


        return ItemsesMovie;
    }

    private void onReleaseResources(ArrayList<ItemsMovie> mData) {
    }
}
