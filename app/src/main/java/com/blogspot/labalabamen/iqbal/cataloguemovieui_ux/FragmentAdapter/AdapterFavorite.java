package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.CustomOnItemClickListener;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {

    private Cursor list;
    private Context context;

    public AdapterFavorite(Context context){
        this.context = context;
    }

    public void setListNotes(Cursor list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movie_fragment, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position) {
        final ItemsListMovie movieFragment = getItem(position);

        holder.title.setText(movieFragment.getTitle_movie());
        holder.rate.setText(movieFragment.getRate_movie());
        holder.overview.setText(movieFragment.getDescription_movie());

        String realese_movie = movieFragment.getRealese_movie();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(realese_movie);

            SimpleDateFormat date_realese = new SimpleDateFormat("E, MM/dd/yyyy");
            String date_of_realese = date_realese.format(date);
            holder.date.setText(date_of_realese);

            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w154/" + movieFragment.getImage_movie())
                    .placeholder(context.getResources().getDrawable(R.drawable.baseline_switch_video_black_48dp))
                    .error(context.getResources().getDrawable(R.drawable.baseline_switch_video_black_48dp))
                    .into(holder.imgMovie);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share: " + movieFragment.getTitle_movie(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ItemsListMovie movie_list = movieListFragment.get(position);
//                ItemsListMovie movieList = movieListFragment.get(position);

                Intent Intent = new Intent(holder.itemView.getContext(), DetailHome.class);
                Intent.putExtra(DetailHome.EXTRA_ID, movieFragment.getId_movie());
                Intent.putExtra(DetailHome.EXTRA_TITLE, movieFragment.getTitle_movie());
                Intent.putExtra(DetailHome.EXTRA_OVERVIEW, movieFragment.getDescription_movie());
                Intent.putExtra(DetailHome.EXTRA_POSTER_JPG, movieFragment.getImage_movie());
                Intent.putExtra(DetailHome.EXTRA_RELEASE_DATE, movieFragment.getRealese_movie());
                Intent.putExtra(DetailHome.EXTRA_RATE, movieFragment.getRate_movie());
                holder.itemView.getContext().startActivity(Intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    private ItemsListMovie getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new ItemsListMovie(list);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, overview, date, rate;
        public ImageView imgMovie;
        public Button btnDetail, btnShare;
        public LinearLayout cvDetail;

        public ViewHolder(View itemView) {
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
    }


}
