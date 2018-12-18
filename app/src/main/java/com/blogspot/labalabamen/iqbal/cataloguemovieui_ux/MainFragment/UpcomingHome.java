package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.BuildConfig;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter.AdapterFragment;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter.ItemsMovieFragment;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingHome extends Fragment {
    private RecyclerView rvCategory;
    private RecyclerView.Adapter adapter;
    private View view;
    private List<ItemsMovieFragment> ListMovie;

    private static final String API_URL = BuildConfig.MOVIE_URL+"/upcoming?api_key="+BuildConfig.API_KEY+"&language=en-US";


    public UpcomingHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming_home, container,false);

        rvCategory = (RecyclerView)view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListMovie = new ArrayList<>();

        loadDataUrl();

        return view;
    }

    private void loadDataUrl() { final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        ItemsMovieFragment movies = new ItemsMovieFragment();

                        JSONObject data = array.getJSONObject(i);
                        movies.setTitle_movie(data.getString("title"));
                        movies.setDescription_movie(data.getString("overview"));
                        movies.setRealese_movie(data.getString("release_date"));
                        movies.setImage_movie(data.getString("poster_path"));
                        movies.setRate_movie(data.getString("vote_average"));
                        ListMovie.add(movies);

                    }

                    adapter = new AdapterFragment(ListMovie, getActivity());
                    rvCategory.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadDataUrl();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
