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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.CustomOnItemClickListener;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailHome;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterFragment extends RecyclerView.Adapter<AdapterFragment.ViewHolder> {
    private List<ItemsListMovie> movieListFragment;
    private Context context;


    public AdapterFragment(List<ItemsListMovie> movieListFragment, Context context){
        this.movieListFragment = movieListFragment;
        this.context = context;
    }

    public void setData(ArrayList<ItemsListMovie> items){
        movieListFragment = items;
        notifyDataSetChanged();
    }

//    public void addItem(final ItemsListMovie item){
//        movieListFragment.add(item);
//        notifyDataSetChanged();
//    }
//
//    public void clearData(){
//        movieListFragment.clear();
//    }
//
//
//    public int getCount() {
//        if(movieListFragment == null) return 0;
//        return movieListFragment. size();
//
//    }


//    public ItemsListMovie getItem(int position) {
//        return movieListFragment.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return 0;
//    }
//
//
//    public int getViewTypeCount() {
//        return 1;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_movie_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ItemsListMovie movieFragment = movieListFragment.get(position);
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
                ItemsListMovie movie_list = movieListFragment.get(position);
                Intent Intent = new Intent(context, DetailHome.class);
                Intent.putExtra(DetailHome.EXTRA_ID, movie_list.getId_movie());
                Intent.putExtra(DetailHome.EXTRA_TITLE, movie_list.getTitle_movie());
                Intent.putExtra(DetailHome.EXTRA_OVERVIEW, movie_list.getDescription_movie());
                Intent.putExtra(DetailHome.EXTRA_POSTER_JPG, movie_list.getImage_movie());
                Intent.putExtra(DetailHome.EXTRA_RELEASE_DATE, movie_list.getRealese_movie());
                Intent.putExtra(DetailHome.EXTRA_RATE, movie_list.getRate_movie());
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

        }
    }
}
