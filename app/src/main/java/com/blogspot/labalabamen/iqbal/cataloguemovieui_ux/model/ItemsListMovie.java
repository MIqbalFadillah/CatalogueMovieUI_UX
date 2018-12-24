package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField.FIELD_OVERVIEW;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField.FIELD_POSTER;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField.FIELD_RATE;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField.FIELD_RELEASE_DATE;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.FavoriteField.FIELD_TITLE;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.getFieldInt;
import static com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.Database.DatabaseContract.getFieldString;

public class ItemsListMovie implements Parcelable {

    private int id_movie;
    private String title_movie;
    private String description_movie;
    private String image_movie;
    private String rate_movie;
    private String realese_movie;


    public ItemsListMovie(JSONObject object) {
        try {
            String title = object.getString("title");
            String description = object.getString("overview");
            String language = object.getString("original_language");
            String realase = object.getString("release_date");
            String rate = object.getString("vote_average");
            String image = object.getString("poster_path");


            this.title_movie = title;
            this.description_movie = description;
            this.rate_movie = rate;
            this.image_movie = image;
            this.realese_movie = realase;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ItemsListMovie() {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_movie);
        dest.writeString(title_movie);
        dest.writeString(description_movie);
        dest.writeString(image_movie);
        dest.writeString(rate_movie);
        dest.writeString(realese_movie);

    }

    public ItemsListMovie(Cursor cursor){ //untuk Content Provider
        this.id_movie             = getFieldInt(cursor, _ID);
        this.title_movie          = getFieldString(cursor, FIELD_TITLE);
        this.image_movie          = getFieldString(cursor, FIELD_POSTER);
        this.realese_movie        = getFieldString(cursor, FIELD_RELEASE_DATE);
        this.rate_movie           = getFieldString(cursor, FIELD_RATE);
        this.description_movie    = getFieldString(cursor, FIELD_OVERVIEW);
    }

    protected ItemsListMovie(Parcel in) {
        this.id_movie = in.readInt();
        this.title_movie = in.readString();
        this.description_movie = in.readString();
        this.image_movie = in.readString();
        this.rate_movie = in.readString();
        this.realese_movie = in.readString();
    }

    public static final Creator<ItemsListMovie> CREATOR = new Creator<ItemsListMovie>() {
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