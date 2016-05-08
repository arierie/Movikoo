package id.arieridwan.movikoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.adapter.ReviewAdapter;
import id.arieridwan.movikoo.adapter.TrailerAdapter;
import id.arieridwan.movikoo.api.MovieAPI;
import id.arieridwan.movikoo.model.MovieModel;
import id.arieridwan.movikoo.model.Result;
import id.arieridwan.movikoo.model.ReviewModel;
import id.arieridwan.movikoo.model.VideoModel;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private MovieModel mMovie;
    private List<VideoModel> mVideoList;
    private List<Result> mReviewList;
    private LinearListView mTrailersView;
    private LinearListView mReviewView;
    private ImageView poster,backdrop;
    private TextView title,overview,date,rate,pop;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
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
        Picasso.with(this)
                .load(mMovie.getPoster_path())
                .into(poster);
        Picasso.with(this).load(mMovie.getBackdrop_path())
                .into(backdrop);
        title.setText(mMovie.getTitle());
        overview.setText(mMovie.getOverview());
        date.setText(mMovie.getRelease_date());
        rate.setText(String.valueOf(mMovie.getVote_average()));
        pop.setText(String.valueOf(mMovie.getPopularity()));
        id = mMovie.getId();
        getTrailer();
        getReview();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        service.getVideos(id, new Callback<VideoModel.VideoResult>() {
            @Override
            public void success(VideoModel.VideoResult videoModel, Response response) {
                mVideoList  = new ArrayList<>();
                mVideoList.addAll(videoModel.getResults());
                TrailerAdapter adapt = new TrailerAdapter(getApplicationContext(),
                        R.layout.item_trailer,
                        (ArrayList<VideoModel>) mVideoList);
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
}
