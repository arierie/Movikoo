package id.arieridwan.movikoo.api;

import id.arieridwan.movikoo.model.MovieModel;
import id.arieridwan.movikoo.model.ReviewModel;
import id.arieridwan.movikoo.model.VideoModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by root on 05/05/16.
 */
public interface MovieAPI {
    @GET("/movie/popular")
    void getLatestMovies(Callback<MovieModel.MovieResult> cb);
    @GET("/movie/{id}/videos")
    public void getVideos(@Path("id") int id,Callback<VideoModel.VideoResult> response);
    @GET("/movie/{id}/reviews")
    public void getReview(@Path("id") int id,Callback<ReviewModel> response);
}
