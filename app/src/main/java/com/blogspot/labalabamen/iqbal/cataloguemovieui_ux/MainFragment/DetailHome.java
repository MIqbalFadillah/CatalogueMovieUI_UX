package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.MovieFavoriteHelper;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment.ItemsMovie;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter.AdapterFavorite;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter.ItemsMovieFragment;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.squareup.picasso.Picasso;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.CONTENT_URI;

public class DetailHome extends AppCompatActivity {

    public static String EXTRA_ID        = "extra_id";
    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER_JPG   = "extra_image";
    public static String EXTRA_RATE         = "extra_rate";

    private TextView tvJudul, tvOverview, tvReleaseDate, tvRating;
    private ImageView imgPoster;


    private Boolean isFavorite = false;
    private MovieFavoriteHelper favoriteHelper;
    private ItemsMovieFragment itemsMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home);

        tvJudul = (TextView)findViewById(R.id.home_TvJudul_recieve);
        tvOverview = (TextView)findViewById(R.id.home_TvDesc_recieve);
        tvReleaseDate = (TextView)findViewById(R.id.home_TvRealese_recieve);
        tvRating = (TextView)findViewById(R.id.home_TvRate_recieve);
        imgPoster = (ImageView)findViewById(R.id.home_Poster_Movie);

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String description = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String imageview = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String rating = getIntent().getStringExtra(EXTRA_RATE);

        itemsMovie = new ItemsMovieFragment();
        itemsMovie.setId_movie(id);
        itemsMovie.setTitle_movie(title);
        itemsMovie.setDescription_movie(description);
        itemsMovie.setImage_movie(imageview);
        itemsMovie.setRealese_movie(release_date);
        itemsMovie.setRate_movie(rating);


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


        loadDataFavorite();

        imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite){

                    FavDelete();
                }else
                {
                    FavSave();
                }

                isFavorite = !isFavorite;
                FavChangeColor();
            }
        });

    }

    private void loadDataFavorite(){
        favoriteHelper = new MovieFavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + itemsMovie.getId_movie()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        FavChangeColor();
    }

    private void FavChangeColor(){
        if (isFavorite){
            imgPoster.setImageResource(R.drawable.baseline_favorite_black_18dp);
        }else {
            imgPoster.setImageResource(R.drawable.baseline_favorite_border_black_18dp);
        }
    }

    private void FavSave(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteField.FIELD_ID,itemsMovie.getId_movie());
        contentValues.put(FavoriteField.FIELD_TITLE,itemsMovie.getTitle_movie());
        contentValues.put(FavoriteField.FIELD_POSTER,itemsMovie.getImage_movie());
        contentValues.put(FavoriteField.FIELD_RELEASE_DATE,itemsMovie.getRealese_movie());
        contentValues.put(FavoriteField.FIELD_RATE,itemsMovie.getRate_movie());
        contentValues.put(FavoriteField.FIELD_OVERVIEW,itemsMovie.getDescription_movie());
        getContentResolver().insert(CONTENT_URI, contentValues);
        Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
    }

    private void FavDelete(){
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + itemsMovie.getId_movie()),
                null,
                null);
        Toast.makeText(this, R.string.delete, Toast.LENGTH_SHORT).show();
    }

}
