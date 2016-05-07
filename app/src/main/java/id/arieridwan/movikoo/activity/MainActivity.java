package id.arieridwan.movikoo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.adapter.MovieAdapter;
import id.arieridwan.movikoo.api.MovieAPI;
import id.arieridwan.movikoo.model.MovieModel;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getData();


    }

    public void getData(){
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
    service.getLatestMovies(new Callback<MovieModel.MovieResult>() {
        @Override
        public void success(MovieModel.MovieResult movieResult, Response response) {
            mAdapter.setMovieList(movieResult.getResults());
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }
    });
}
}
