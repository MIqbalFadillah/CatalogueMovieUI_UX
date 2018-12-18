package com.blogspot.labalabamen.iqbal.cataloguemovieui_ux.DetailSearchFragment;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ItemsMovie implements Parcelable {

    private String title_movie;
    private String description_movie;
    private String image_movie;
    private String rate_movie;
    private String realese_movie;
    private String language_movie;

    public ItemsMovie (JSONObject object){
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
            this.language_movie = language;

        }catch (Exception e){
            e.printStackTrace();
        }

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

    public String getLanguage_movie() {
        return language_movie;
    }

    public void setLanguage_movie(String language_movie) {
        this.language_movie = language_movie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title_movie);
        dest.writeString(this.description_movie);
        dest.writeString(this.image_movie);
        dest.writeString(this.rate_movie);
        dest.writeString(this.realese_movie);
        dest.writeString(this.language_movie);
    }

    protected ItemsMovie(Parcel in) {
        this.title_movie = in.readString();
        this.description_movie = in.readString();
        this.image_movie = in.readString();
        this.rate_movie = in.readString();
        this.realese_movie = in.readString();
        this.language_movie = in.readString();
    }

    public static final Parcelable.Creator<ItemsMovie> CREATOR = new Parcelable.Creator<ItemsMovie>() {
        @Override
        public ItemsMovie createFromParcel(Parcel source) {
            return new ItemsMovie(source);
        }

        @Override
        public ItemsMovie[] newArray(int size) {
            return new ItemsMovie[size];
        }
    };
}
