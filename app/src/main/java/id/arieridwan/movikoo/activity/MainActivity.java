package id.arieridwan.movikoo.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.adapter.FavAdapter;
import id.arieridwan.movikoo.adapter.MovieAdapter;
import id.arieridwan.movikoo.api.MovieAPI;
import id.arieridwan.movikoo.model.Favorite;
import id.arieridwan.movikoo.model.MovieModel;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
/*
 * Created by Arie Ridwansyah on 5/10/16 6:04 AM
 * Copyright (c) 2016. All rights reserved.
 * enjoy your coding and drink coffee ^_^
 * Last modified 5/10/16 5:25 AM
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String POPULARITY_DESC = "popularity.desc";
    private static final String RATING_DESC = "vote_average.desc";
    private static final String FAVORITE = "favorite";
    private String mSortBy = POPULARITY_DESC;
    private String load = "pop";
    private FavAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieAdapter(this);
        favAdapter = new FavAdapter(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                dataLoad();
            }
        });
    }

    public void getDataPop(){
        mSwipeRefreshLayout.setRefreshing(true);
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://api.themoviedb.org/3")
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addEncodedQueryParam("api_key", "765e4945e78f4a4497cd97a5734894d9");
                }
            })
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();
        MovieAPI service = restAdapter.create(MovieAPI.class);
        service.getPop(new Callback<MovieModel.MovieResult>() {
            @Override
            public void success(MovieModel.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
                mRecyclerView.setAdapter(mAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public void getDataRate(){
        mSwipeRefreshLayout.setRefreshing(true);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "765e4945e78f4a4497cd97a5734894d9");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MovieAPI service = restAdapter.create(MovieAPI.class);
        service.getRate(new Callback<MovieModel.MovieResult>() {
            @Override
            public void success(MovieModel.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
                mRecyclerView.setAdapter(mAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onRefresh() {
        dataLoad();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem mItem = menu.findItem(R.id.action_sort);
        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);
        MenuItem action_sort_by_favorite = menu.findItem(R.id.action_sort_by_favorite);

        if (mSortBy.contentEquals(POPULARITY_DESC)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_popularity.setChecked(true);
            }
        } else if (mSortBy.contentEquals(RATING_DESC)) {
            if (!action_sort_by_rating.isChecked()) {
                action_sort_by_rating.setChecked(true);
            }
        } else if (mSortBy.contentEquals(FAVORITE)) {
            if (!action_sort_by_favorite.isChecked()) {
                action_sort_by_favorite.setChecked(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = POPULARITY_DESC;
                load = "pop";
                dataLoad();
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = RATING_DESC;
                load = "rate";
                dataLoad();
                return true;
            case R.id.action_sort_by_favorite:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = FAVORITE;
                load = "fav";
                dataLoad();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dataLoad(){
        switch (load){
            case "pop":
                getDataPop();
                break;
            case "rate":
                getDataRate();
                break;
            case "fav":
                getFavoriteList();
                break;
        }
    }

    private void getFavoriteList() {
        List<Favorite> fav = Favorite.listAll(Favorite.class);
        Log.d("test", String.valueOf(fav.size()));
        Log.d("test", String.valueOf(fav));
        favAdapter.setFavList(fav);
        mRecyclerView.setAdapter(favAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
