package id.arieridwan.movikoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.adapter.ReviewAdapter;
import id.arieridwan.movikoo.adapter.TrailerAdapter;
import id.arieridwan.movikoo.api.MovieAPI;
import id.arieridwan.movikoo.model.Favorite;
import id.arieridwan.movikoo.model.MovieModel;
import id.arieridwan.movikoo.model.Result;
import id.arieridwan.movikoo.model.ReviewModel;
import id.arieridwan.movikoo.model.TrailerModel;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
/*
 * Created by Arie Ridwansyah on 5/10/16 6:04 AM
 * Copyright (c) 2016. All rights reserved.
 * enjoy your coding and drink coffee ^_^
 * Last modified 5/10/16 5:23 AM
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_FAV = "favorite";
    private MovieModel mMovie;
    private Favorite mFavorite;
    private List<TrailerModel> mTrailerList;
    private List<Result> mReviewList;
    private LinearListView mTrailersView;
    private LinearListView mReviewView;
    private ImageView poster,backdrop;
    private TextView title,overview,date,rate,pop;
    private int id;
    private String mMoviePoster,mMovieTitle,mMovieReleaseDate,mMovieOverview,mMovieBackdrop;
    private double mMovieVoteAverage,mMoviePopularity;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else if (getIntent().hasExtra(EXTRA_FAV)){
            mFavorite = getIntent().getParcelableExtra(EXTRA_FAV);
        }
        else {
            throw new IllegalArgumentException("Detail not found");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        poster = (ImageView) findViewById(R.id.ivPoster);
        backdrop = (ImageView) findViewById(R.id.ivBackdrop);
        title = (TextView) findViewById(R.id.tvTitle);
        overview = (TextView) findViewById(R.id.tvOverview);
        date = (TextView) findViewById(R.id.tvDate);
        rate = (TextView) findViewById(R.id.tvRate);
        pop = (TextView)findViewById(R.id.tvPopular);
        mTrailersView = (LinearListView) findViewById(R.id.detail_trailers);
        mReviewView = (LinearListView) findViewById(R.id.detail_reviews);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coor);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        setData();
        getTrailer();
        getReview();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(id,mMovieTitle,mMoviePoster,mMovieOverview,mMovieBackdrop,mMovieReleaseDate,mMovieVoteAverage,mMoviePopularity);
                try {
                    favorite.save();
                    fab.setImageResource(R.drawable.ic_favorite_24dp_white);
                    fab.setImageResource(R.drawable.ic_favorite_outline_24dp);
                    Toast.makeText(DetailActivity.this, " Fav, save !", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    public void setData(){

        if (mMovie != null) {
            id = mMovie.getId();
            mMoviePoster = mMovie.getPoster_path();
            mMovieBackdrop = mMovie.getBackdrop_path();
            mMovieTitle = mMovie.getTitle();
            mMovieOverview = mMovie.getOverview();
            mMovieReleaseDate = mMovie.getRelease_date();
            mMovieVoteAverage = mMovie.getVote_average();
            mMoviePopularity = mMovie.getPopularity();

            Picasso.with(this)
                    .load(mMoviePoster)
                    .into(poster);
            Picasso.with(this).load(mMovieBackdrop)
                    .into(backdrop);
            title.setText(mMovieTitle);
            overview.setText(mMovieOverview);
            date.setText(mMovieReleaseDate);
            rate.setText(String.valueOf(mMovieVoteAverage));
            pop.setText(String.valueOf(mMoviePopularity));
        }
        else {
            id = mFavorite.getIds();
            mMovieTitle = mFavorite.getTitle();
            mMoviePoster = mFavorite.getPoster_path();
            mMovieOverview = mFavorite.getOverview();
            mMovieBackdrop = mFavorite.getBackdrop_path();
            mMovieReleaseDate = mFavorite.getRelease_date();
            mMovieVoteAverage = mFavorite.getVote_average();
            mMoviePopularity = mFavorite.getPopularity();

            Picasso.with(this)
                    .load(mMoviePoster)
                    .into(poster);
            Picasso.with(this).load(mMovieBackdrop)
                    .into(backdrop);
            title.setText(mMovieTitle);
            overview.setText(mMovieOverview);
            date.setText(mMovieReleaseDate);
            rate.setText(String.valueOf(mMovieVoteAverage));
            pop.setText(String.valueOf(mMoviePopularity));
        }
    }

    public void getTrailer(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",
                                "765e4945e78f4a4497cd97a5734894d9");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieAPI service = restAdapter.create(MovieAPI.class);
        service.getTrailer(id, new Callback<TrailerModel.TrailerResult>() {
            @Override
            public void success(TrailerModel.TrailerResult videoModel, Response response) {
                mTrailerList = new ArrayList<>();
                mTrailerList.addAll(videoModel.getResults());
                TrailerAdapter adapt = new TrailerAdapter(getApplicationContext(),
                        R.layout.item_trailer,
                        (ArrayList<TrailerModel>) mTrailerList);
                mTrailersView.setAdapter(adapt);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    public void getReview(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key",
                                "765e4945e78f4a4497cd97a5734894d9");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieAPI service = restAdapter.create(MovieAPI.class);
        service.getReview(id, new Callback<ReviewModel>() {
            @Override
            public void success(ReviewModel reviewModel, Response response) {
                mReviewList = new ArrayList<>();
                mReviewList.addAll(reviewModel.getResults());
                ReviewAdapter adapt = new ReviewAdapter(getApplicationContext(), R.layout.item_review, (ArrayList<Result>) mReviewList);
                mReviewView.setAdapter(adapt);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            break;
        }
        return true;

    }




}
