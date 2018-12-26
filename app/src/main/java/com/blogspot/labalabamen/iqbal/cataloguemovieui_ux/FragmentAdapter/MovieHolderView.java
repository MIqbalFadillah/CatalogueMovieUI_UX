package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieHolderView extends RecyclerView.ViewHolder {

    public TextView title, overview, date, rate;
    public ImageView imgMovie;
    public Button btnDetail, btnShare;
    public LinearLayout cvDetail;

    public MovieHolderView(View itemView) {
        super(itemView);

        rate = (TextView)itemView.findViewById(R.id.tv_item_rate);
        title = (TextView)itemView.findViewById(R.id.tv_item_name);
        imgMovie      = (ImageView) itemView.findViewById(R.id.img_item_photo);
        overview    = (TextView) itemView.findViewById(R.id.tv_item_overview);
        date        = (TextView) itemView.findViewById(R.id.tv_item_date);
        btnShare    = (Button) itemView.findViewById(R.id.btn_set_share);
        btnDetail = (Button) itemView.findViewById(R.id.btn_set_detail);
        cvDetail = (LinearLayout)itemView.findViewById(R.id.cr_movies);
    }

    public void bind(final ItemsListMovie movieFragment){
        title.setText(movieFragment.getTitle_movie());
        rate.setText(movieFragment.getRate_movie());
        overview.setText(movieFragment.getDescription_movie());

        String realese_movie = movieFragment.getRealese_movie();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateof = dateFormat.parse(realese_movie);

            SimpleDateFormat date_realese = new SimpleDateFormat("E, MM/dd/yyyy");
            String date_of_realese = date_realese.format(dateof);
            date.setText(date_of_realese);

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500/"+movieFragment.getImage_movie())
                    .into(imgMovie);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), "Favorite: "+movieFragment.getTitle_movie(), Toast.LENGTH_SHORT).show();
            }
        });


        cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(itemView.getContext(), DetailHome.class);
                Intent.putExtra(DetailHome.EXTRA_ID, movieFragment.getId_movie());
                Intent.putExtra(DetailHome.EXTRA_TITLE, movieFragment.getTitle_movie());
                Intent.putExtra(DetailHome.EXTRA_OVERVIEW, movieFragment.getDescription_movie());
                Intent.putExtra(DetailHome.EXTRA_POSTER_JPG, movieFragment.getImage_movie());
                Intent.putExtra(DetailHome.EXTRA_RELEASE_DATE, movieFragment.getRealese_movie());
                Intent.putExtra(DetailHome.EXTRA_RATE, movieFragment.getRate_movie());
               itemView.getContext().startActivity(Intent);

            }
        });
    }
}
