package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.FragmentAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.CustomOnItemClickListener;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.MainFragment.DetailHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterFragment extends RecyclerView.Adapter<AdapterFragment.ViewHolder> {
    private List<ItemsMovieFragment> movieListFragment;
    private Context context;
    static final String EXTRA_MOVIE = "EXTRA_MOVIE";


    public AdapterFragment(List<ItemsMovieFragment> movieListFragment, Context context){
        this.movieListFragment = movieListFragment;
        this.context = context;
    }

    public void setData(ArrayList<ItemsMovieFragment> items){
        movieListFragment = items;
        notifyDataSetChanged();
    }

    public void addItem(final ItemsMovieFragment item){
        movieListFragment.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        movieListFragment.clear();
    }


    public int getCount() {
        if(movieListFragment == null) return 0;
        return movieListFragment. size();

    }


    public ItemsMovieFragment getItem(int position) {
        return movieListFragment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    public int getViewTypeCount() {
        return 1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_movie_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ItemsMovieFragment movieFragment = movieListFragment.get(position);
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
                ItemsMovieFragment movie_list = movieListFragment.get(position);

                ItemsMovieFragment movieList = movieListFragment.get(position);
                Intent Intent = new Intent(context, DetailHome.class);
                Intent.putExtra(DetailHome.EXTRA_TITLE, movieList.getTitle_movie());
                Intent.putExtra(DetailHome.EXTRA_OVERVIEW, movieList.getDescription_movie());
                Intent.putExtra(DetailHome.EXTRA_POSTER_JPG, movieList.getImage_movie());
                Intent.putExtra(DetailHome.EXTRA_RELEASE_DATE, movieList.getRealese_movie());
                Intent.putExtra(DetailHome.EXTRA_RATE, movieList.getRate_movie());
                context.startActivity(Intent);

            }
        });
    }


    @Override
        public int getItemCount () {
            return movieListFragment.size();
        }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, overview, date, rate;
        public ImageView imgMovie;
        public Button btnDetail, btnShare;

        public ViewHolder(View itemView) {
            super(itemView);

            rate = (TextView)itemView.findViewById(R.id.tv_item_rate);
            title = (TextView)itemView.findViewById(R.id.tv_item_name);
            imgMovie      = (ImageView) itemView.findViewById(R.id.img_item_photo);
            overview    = (TextView) itemView.findViewById(R.id.tv_item_overview);
            date        = (TextView) itemView.findViewById(R.id.tv_item_date);
            btnShare    = (Button) itemView.findViewById(R.id.btn_set_share);
            btnDetail = (Button) itemView.findViewById(R.id.btn_set_detail);
        }
    }
}
