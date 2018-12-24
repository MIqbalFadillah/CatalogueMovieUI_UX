package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model.ItemsListMovie;
import com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterMovies extends BaseAdapter {
    private ArrayList<ItemsListMovie> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public AdapterMovies(Context context){
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setData(ArrayList<ItemsListMovie> items){
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final ItemsListMovie item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @Override
    public int getCount() {
        if(mData == null) return 0;
        return mData. size();

    }

    @Override
    public ItemsListMovie getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.items_list_movie, null);
            holder.imgMovie = (ImageView)convertView.findViewById(R.id.imageView);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            holder.tvDeskripsi = (TextView)convertView.findViewById(R.id.tvDeskripsi);
            holder.tvRealese = (TextView)convertView.findViewById(R.id.tvRealese);
            holder.tvRate = (TextView)convertView.findViewById(R.id.tvRate);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvTitle.setText(mData.get(position).getTitle_movie());
        holder.tvDeskripsi.setText(mData.get(position).getDescription_movie());
        holder.tvRate.setText(mData.get(position).getRate_movie()+" SCORE");
        String getDate = mData.get(position).getRealese_movie();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(getDate);
            SimpleDateFormat new_date = new SimpleDateFormat("EEEE, dd-MM-yyyy");
            String reales_movie = new_date.format(date);
            holder.tvRealese.setText(reales_movie);

            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w154/"+mData.get(position).getImage_movie())
                    .placeholder(context.getResources().getDrawable(R.drawable.baseline_switch_video_black_48dp))
                    .error(context.getResources().getDrawable(R.drawable.baseline_switch_video_black_48dp))
                    .into(holder.imgMovie);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;

    }

    private static class ViewHolder{
        ImageView imgMovie;
        TextView tvTitle,tvRealese,tvDeskripsi,tvRate;




    }
}
