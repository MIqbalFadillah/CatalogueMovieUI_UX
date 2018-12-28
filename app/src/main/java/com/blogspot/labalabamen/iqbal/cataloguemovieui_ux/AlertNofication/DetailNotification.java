package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.AlertNofication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.MovieFavoriteHelper;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemListMovieNotify;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.CONTENT_URI;

public class DetailNotification extends AppCompatActivity {

    public static String EXTRA_ID        = "extra_id";
    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER_JPG   = "extra_image";
    public static String EXTRA_RATE         = "extra_rate";

    @BindView(R.id.mTvDesc_recieve)
    TextView tvOverview;
    @BindView(R.id.mTvPoster_Movie)
    ImageView imgPoster;
    @BindView(R.id.img_item_favorite)
    ImageView imgFavorite;
    @BindView(R.id.mTvRealese_recieve)
    TextView tvReleaseDate;
    @BindView(R.id.mTvJudul_recieve)
    TextView tvJudul;
    @BindView(R.id.mTvRate_recieve)
    TextView tvRating;


    private Boolean isFavorite = false;
    private MovieFavoriteHelper favoriteHelper;
    private ItemsListMovie itemsMovie;


    String  title, image, description, realese, rating;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_home);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        itemsMovie = intent.getParcelableExtra("MOVE_MOVIE");
        NotifyData(itemsMovie);
        id = itemsMovie.getId_movie();
        title = itemsMovie.getTitle_movie();
        image = itemsMovie.getImage_movie();
        description = itemsMovie.getDescription_movie();




//       int id = itemsMovie.getId_movie();
//       String title = itemsMovie.getTitle_movie();
//        String realese = itemsMovie.getRealese_movie();
//        String  rating = itemsMovie.getRate_movie();
//        String image = itemsMovie.getImage_movie();
//        String description = itemsMovie.getDescription_movie();
//
//        itemsMovie = new ItemsListMovie();
//        itemsMovie.setId_movie(id);
//        itemsMovie.setTitle_movie(title);
//        itemsMovie.setDescription_movie(description);
//        itemsMovie.setImage_movie(image);
//        itemsMovie.setRealese_movie(description);
//        itemsMovie.setRate_movie(rating);


//        tvJudul.setText(title);
//        tvOverview.setText(description);
//        tvOverview = (TextView)findViewById(R.id.mTvDesc_recieve);
//        tvReleaseDate.setText(realese);
//        tvReleaseDate = (TextView)findViewById(R.id.mTvRealese_recieve);
//        tvRating.setText(rating);
//        tvRating = (TextView)findViewById(R.id.mTvRate_recieve);
//
//        imgPoster = (ImageView)findViewById(R.id.tvPoster_Movie);



        loadDataFavorite();

        imgFavorite.setOnClickListener(new View.OnClickListener() {
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

    public void NotifyData (ItemsListMovie movie){
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+image).into(imgPoster);
        tvJudul.setText(movie.getTitle_movie());
        tvOverview.setText(movie.getDescription_movie());
        tvReleaseDate.setText(movie.getRealese_movie());
        tvRating.setText(movie.getRate_movie());

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
            imgFavorite.setImageResource(R.drawable.ic_favorite_red);
        }else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_border_red);
        }
    }

    private void FavSave(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.FavoriteField.FIELD_ID,itemsMovie.getId_movie());
        contentValues.put(DatabaseContract.FavoriteField.FIELD_TITLE,itemsMovie.getTitle_movie());
        contentValues.put(DatabaseContract.FavoriteField.FIELD_POSTER,itemsMovie.getImage_movie());
        contentValues.put(DatabaseContract.FavoriteField.FIELD_RELEASE_DATE,itemsMovie.getRealese_movie());
        contentValues.put(DatabaseContract.FavoriteField.FIELD_RATE,itemsMovie.getRate_movie());
        contentValues.put(DatabaseContract.FavoriteField.FIELD_OVERVIEW,itemsMovie.getDescription_movie());
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
