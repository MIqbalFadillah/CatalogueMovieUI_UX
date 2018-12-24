package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter.AdapterFavorite;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;

import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteHome extends Fragment {
    private Context context;
    private Cursor list;
    private AdapterFavorite adapter;

    RecyclerView rv_favorite;
    ProgressBar progressBar;


    public FavoriteHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_fav);

        adapter = new AdapterFavorite(context);
        adapter.setListNotes(list);
        rv_favorite = (RecyclerView) view.findViewById(R.id.rv_fav_category);
        rv_favorite.setLayoutManager(new LinearLayoutManager(context));
        rv_favorite.setAdapter(adapter);

        new loadFavAsync().execute();
        ToastMessageShow("Favorite on Create");

        return view;

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadFavAsync().execute();
    }

    private class loadFavAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            ToastMessageShow("Favorite on onPreExe");
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        progressBar.setVisibility(View.GONE);
        ToastMessageShow("Favorite on onPostExe");

        list = cursor;

        adapter.setListNotes(list);
        adapter.notifyDataSetChanged();

        if (list.getCount() == 0) {
            ToastMessageShow("Tidak ada data saat ini");
        } else {
            ToastMessageShow("Total list saat ini: "+list.getCount());
        }
    }

}


    private void ToastMessageShow(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
