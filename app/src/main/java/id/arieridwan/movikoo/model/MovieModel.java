package id.arieridwan.movikoo.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/*
 * Created by Arie Ridwansyah on 5/10/16 6:07 AM
 * Copyright (c) 2016. All rights reserved.
 * enjoy your coding and drink coffee ^_^
 * Last modified 5/8/16 9:10 PM
 */
public class MovieModel implements Parcelable{
    private String title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private int id;
    private boolean video;
    private double vote_average;
    private double popularity;

    public MovieModel(Cursor cursor) {

    }

    public MovieModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        popularity = in.readDouble();
        backdrop_path = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }

    public void setPoster_path(String poster) {
        this.poster_path = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String description) {
        this.overview = description;
    }

    public String getBackdrop_path() {
        return "http://image.tmdb.org/t/p/w500"  + backdrop_path;
    }

    public void setBackdrop_path(String backdrop) {
        this.backdrop_path = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeDouble(vote_average);
        parcel.writeString(overview);
        parcel.writeDouble(popularity);
        parcel.writeString(backdrop_path);
    }

    public static class MovieResult {
        private List<MovieModel> results;

        public List<MovieModel> getResults() {
            return results;
        }
    }
}
