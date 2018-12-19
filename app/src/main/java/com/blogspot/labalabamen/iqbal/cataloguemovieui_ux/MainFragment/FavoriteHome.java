package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter.AdapterFavorite;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;

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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
