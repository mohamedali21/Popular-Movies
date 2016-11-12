package com.example.mohamedali.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Ali on 8/19/2016.
 */
public class MovieModel implements Parcelable{
    public   String url;
    String name;
    double rate;
    String overview;
    String releaseDate;
    int id;
    String key;
    String trailer;
    String content;
    String urlContent;
    String author;

    public MovieModel(Parcel in) {
        url = in.readString();
        name = in.readString();
        rate = in.readDouble();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readInt();

    }

    public MovieModel(){}


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(overview);
        parcel.writeString(url);
        parcel.writeString(releaseDate);
        parcel.writeInt(id);
        parcel.writeDouble(rate);
    }
    public static final Parcelable.Creator<MovieModel> CREATOR =
            new Parcelable.Creator<MovieModel>() {
                public MovieModel createFromParcel(Parcel in) {
                    return new MovieModel(in);
                }
                public MovieModel[] newArray(int size) {
                    return new MovieModel[size];
                }
            };
}
