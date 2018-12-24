package com.blogspot.labalabamen.iqbal.moviesfavorite.model;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.labalabamen.iqbal.moviesfavorite.Move_Movie_Detail;
import com.blogspot.labalabamen.iqbal.moviesfavorite.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.blogspot.labalabamen.iqbal.moviesfavorite.database.DatabaseContract.FavoriteField.*;
import static com.blogspot.labalabamen.iqbal.moviesfavorite.database.DatabaseContract.*;

public class AdapterMoviesProvider extends CursorAdapter {



    public AdapterMoviesProvider(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView title, overview, tvDate, rate;
        ImageView imgMovie;
        LinearLayout cvDetail;

        if (cursor != null) {


            title = (TextView) view.findViewById(R.id.tvTitle);
            imgMovie = (ImageView) view.findViewById(R.id.imageView);
            tvDate = (TextView) view.findViewById(R.id.tvRealese);
            rate = (TextView) view.findViewById(R.id.tvRate);
            overview = (TextView) view.findViewById(R.id.tvDeskripsi);
            cvDetail = (LinearLayout)view.findViewById(R.id.cr_movies);

            title.setText(getFieldString(cursor, FIELD_TITLE));
            overview.setText(getFieldString(cursor, FIELD_OVERVIEW));
            rate.setText(getFieldString(cursor, FIELD_RATE));

            String realese_movie = getFieldString(cursor, FIELD_RELEASE_DATE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date_ = dateFormat.parse(realese_movie);
                SimpleDateFormat date_realese = new SimpleDateFormat("E, MM/dd/yyyy");
                String date_of_realese = date_realese.format(date_);
                tvDate.setText(date_of_realese);

                Picasso.with(context)
                        .load("http://image.tmdb.org/t/p/w154/" + getFieldString(cursor, FIELD_POSTER))
                        .placeholder(context.getResources().getDrawable(R.drawable.baseline_switch_video_black_48dp))
                        .error(context.getResources().getDrawable(R.drawable.baseline_switch_video_black_48dp))
                        .into(imgMovie);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            cvDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Move_Movie_Detail.class);
                    intent.putExtra(Move_Movie_Detail.EXTRA_ID, getFieldString(cursor, FIELD_ID));
                    intent.putExtra(Move_Movie_Detail.EXTRA_TITLE, getFieldString(cursor, FIELD_TITLE));
                    intent.putExtra(Move_Movie_Detail.EXTRA_POSTER_JPG, getFieldString(cursor, FIELD_POSTER));
                    intent.putExtra(Move_Movie_Detail.EXTRA_OVERVIEW, getFieldString(cursor, FIELD_OVERVIEW));
                    intent.putExtra(Move_Movie_Detail.EXTRA_RATE, getFieldString(cursor, FIELD_RATE));
                    intent.putExtra(Move_Movie_Detail.EXTRA_RELEASE_DATE, getFieldString(cursor, FIELD_RELEASE_DATE));
                    context.startActivity(intent);

                }
            });


        }

    }
}
