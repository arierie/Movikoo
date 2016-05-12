package id.arieridwan.movikoo.api;

import id.arieridwan.movikoo.model.MovieModel;
import id.arieridwan.movikoo.model.ReviewModel;
import id.arieridwan.movikoo.model.TrailerModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/*
 * Created by Arie Ridwansyah on 5/10/16 6:05 AM
 * Copyright (c) 2016. All rights reserved.
 * enjoy your coding and drink coffee ^_^
 * Last modified 5/10/16 5:20 AM
 */
public interface MovieAPI {
    @GET("/movie/popular")
    void getPop(Callback<MovieModel.MovieResult> cb);
    @GET("/movie/top_rated")
    void getRate(Callback<MovieModel.MovieResult> cb);

    @GET("/movie/{id}/videos")
    public void getTrailer(@Path("id") int id,Callback<TrailerModel.TrailerResult> response);
    @GET("/movie/{id}/reviews")
    public void getReview(@Path("id") int id,Callback<ReviewModel> response);
}
