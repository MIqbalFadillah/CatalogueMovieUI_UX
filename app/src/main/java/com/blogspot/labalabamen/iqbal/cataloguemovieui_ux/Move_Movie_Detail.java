package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment.ItemsMovie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move_Movie_Detail extends AppCompatActivity {
    public static String EXTRA_MOVE = "extra_move";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move__movie__detail);


        Intent intent = getIntent();
        ItemsMovie itemsMovie = intent.getParcelableExtra("MOVE_MOVIE");

        String imgPoster = itemsMovie.getImage_movie();
        String Judul = itemsMovie.getTitle_movie();
        String Rate = itemsMovie.getRate_movie();
        String Realese = itemsMovie.getRealese_movie();
        String Desc = itemsMovie.getDescription_movie();


        ImageView imageView = findViewById(R.id.tvPoster_Movie);
        TextView mTvJudul = findViewById(R.id.mTvJudul_recieve);
        mTvJudul.setText(Judul);
        TextView mTvRate = findViewById(R.id.mTvRate_recieve);
        mTvRate.setText(String.format(getString(R.string.score),Rate));
        TextView mTvRealese = findViewById(R.id.mTvRealese_recieve);

        TextView mDesc = findViewById(R.id.mTvDesc_recieve);
        mDesc.setText(Desc);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(Realese);
            SimpleDateFormat new_date = new SimpleDateFormat("EEEE, dd-MM-yyyy");
            String reales_movie = new_date.format(date);
            mTvRealese.setText(reales_movie);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+imgPoster).into(imageView);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
