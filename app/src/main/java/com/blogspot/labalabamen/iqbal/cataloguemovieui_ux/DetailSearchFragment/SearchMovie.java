package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovie extends Fragment implements LoaderManager.LoaderCallbacks
        <ArrayList<ItemsListMovie>>{
    ListView listView;
    AdapterMovies adapter;
    Button btnCari;
    EditText editJudul;

    private View view;

    static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    public SearchMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_movie, container, false);

        adapter = new AdapterMovies(getActivity());
        adapter.notifyDataSetChanged();

        listView = (ListView) view.findViewById(R.id.lsMovie);
        listView.setAdapter((ListAdapter) adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemsListMovie movieFragment = (ItemsListMovie) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), DetailHome.class);

                intent.putExtra(DetailHome.EXTRA_ID, movieFragment.getId_movie());
                intent.putExtra(DetailHome.EXTRA_TITLE, movieFragment.getTitle_movie());
                intent.putExtra(DetailHome.EXTRA_OVERVIEW, movieFragment.getDescription_movie());
                intent.putExtra(DetailHome.EXTRA_POSTER_JPG, movieFragment.getImage_movie());
                intent.putExtra(DetailHome.EXTRA_RELEASE_DATE, movieFragment.getRealese_movie());
                intent.putExtra(DetailHome.EXTRA_RATE, movieFragment.getRate_movie());

                startActivity(intent);
            }
        });

        editJudul = (EditText) view.findViewById(R.id.edt_cari);
        btnCari = (Button) view.findViewById(R.id.btn_cari);

        btnCari.setOnClickListener(mvListener);

        String movie = editJudul.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, movie);

        getLoaderManager().initLoader(0, bundle, SearchMovie.this);
        return view;

    }


    @NonNull
    @Override
    public android.support.v4.content.Loader<ArrayList<ItemsListMovie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String kumpulanMovie = "";
        if (bundle != null) {
            kumpulanMovie = bundle.getString(EXTRA_MOVIE);
        }
        return new MovieAsyncTaskLoader(getActivity(), kumpulanMovie);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<ItemsListMovie>> loader, ArrayList<ItemsListMovie> itemsMovieFragments) {
        adapter.setData(itemsMovieFragments);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<ItemsListMovie>> loader) {
        adapter.setData(null);

    }


    View.OnClickListener mvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movie = editJudul.getText().toString();

            if (TextUtils.isEmpty(movie)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_MOVIE, movie);
            getLoaderManager().restartLoader(0, bundle, SearchMovie.this);
        }
    };

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ItemsListMovie move_item = (ItemsListMovie) parent.getItemAtPosition(position);
//        Intent intent = new Intent(getActivity(), DetailHome.class);
//        intent.putExtra("MOVE_MOVIE", move_item);
//
//        startActivity(intent);
//    }
}