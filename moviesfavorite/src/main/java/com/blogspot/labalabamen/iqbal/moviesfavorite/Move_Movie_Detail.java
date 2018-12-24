package com.blogspot.labalabamen.iqbal.moviesfavorite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Move_Movie_Detail extends AppCompatActivity {

    public static String EXTRA_ID        = "extra_id";
    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER_JPG   = "extra_image";
    public static String EXTRA_RATE         = "extra_rate";

    private TextView tvJudul, tvOverview, tvReleaseDate, tvRating;
    private ImageView imgPoster, imgFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        tvJudul = (TextView)findViewById(R.id.mTvJudul_recieve);
        tvOverview = (TextView)findViewById(R.id.mTvDesc_recieve);
        tvReleaseDate = (TextView)findViewById(R.id.mTvRealese_recieve);
        tvRating = (TextView)findViewById(R.id.mTvRate_recieve);
        imgPoster = (ImageView)findViewById(R.id.tvPoster_Movie);

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String description = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String imageview = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String rating = getIntent().getStringExtra(EXTRA_RATE);


        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvReleaseDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvJudul.setText(title);
        tvOverview.setText(description);
        tvRating.setText(String.format(getString(R.string.score),rating));
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+imageview).into(imgPoster);
    }
}
