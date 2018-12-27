package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ItemListMovieNotify implements Parcelable {
    public int id_movie;
    public String title_movie;
    public String description_movie;
    public String image_movie;
    public String rate_movie;
    public String realese_movie;


//    public ItemListMovieNotify() {
//        try {
//            int id = object.getInt("id");
//            String title = object.getString("title");
//            String description = object.getString("overview");
//            String realase = object.getString("release_date");
//            String rate = object.getString("vote_average");
//            String image = object.getString("poster_path");
//
//            this.id_movie = id;
//            this.title_movie = title;
//            this.description_movie = description;
//            this.rate_movie = rate;
//            this.image_movie = image;
//            this.realese_movie = realase;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public ItemListMovieNotify(){}

    public ItemListMovieNotify(int idMovie, String title, String description, String rate, String image, String realase) {
        id_movie = idMovie;
        title_movie = title;
        description_movie = description;
        rate_movie = rate;
        image_movie = image;
        realese_movie = realase;

    }


    public int getId_movie() {
        return id_movie;
    }

    public void setId_movie(int id_movie) {
        this.id_movie = id_movie;
    }

    public String getTitle_movie() {
        return title_movie;
    }

    public void setTitle_movie(String title_movie) {
        this.title_movie = title_movie;
    }

    public String getDescription_movie() {
        return description_movie;
    }

    public void setDescription_movie(String description_movie) {
        this.description_movie = description_movie;
    }

    public String getImage_movie() {
        return image_movie;
    }

    public void setImage_movie(String image_movie) {
        this.image_movie = image_movie;
    }

    public String getRate_movie() {
        return rate_movie;
    }

    public void setRate_movie(String rate_movie) {
        this.rate_movie = rate_movie;
    }

    public String getRealese_movie() {
        return realese_movie;
    }

    public void setRealese_movie(String realese_movie) {
        this.realese_movie = realese_movie;
    }



    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_movie);
        dest.writeString(this.title_movie);
        dest.writeString(this.description_movie);
        dest.writeString(this.image_movie);
        dest.writeString(this.rate_movie);
        dest.writeString(this.realese_movie);

    }
//
//    public ItemListMovieNotify() {
//        this.id_movie = in.readInt();
//        this.title_movie = in.readString();
//        this.description_movie = in.readString();
//        this.image_movie = in.readString();
//        this.rate_movie = in.readString();
//        this.realese_movie = in.readString();
//    }

    public static final Parcelable.Creator<ItemsListMovie> CREATOR = new Parcelable.Creator<ItemsListMovie>() {
        @Override
        public ItemsListMovie createFromParcel(Parcel source) {
            return new ItemsListMovie(source);
        }

        @Override
        public ItemsListMovie[] newArray(int size) {
            return new ItemsListMovie[size];
        }
    };
}
