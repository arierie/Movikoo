package id.arieridwan.movikoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.adapter.VideoAdapter;
import id.arieridwan.movikoo.api.MovieAPI;
import id.arieridwan.movikoo.model.MovieModel;
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
    ListView lv;
    ImageView poster;
    TextView title;
    TextView overview;
    TextView date;
    TextView rate;
    TextView pop;
    int id;
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
        poster = (ImageView) findViewById(R.id.ivPoster);
        title = (TextView) findViewById(R.id.tvTitle);
        overview = (TextView) findViewById(R.id.tvOverview);
        date = (TextView) findViewById(R.id.tvDate);
        rate = (TextView) findViewById(R.id.tvRate);
        pop = (TextView)findViewById(R.id.tvPopular);
        lv=(ListView) findViewById(R.id.listView);
        Picasso.with(this)
                .load(mMovie.getPoster_path())
                .into(poster);
        title.setText(mMovie.getTitle());
        overview.setText(mMovie.getOverview());
        date.setText(mMovie.getRelease_date());
        rate.setText(String.valueOf(mMovie.getVote_average()));
        pop.setText(String.valueOf(mMovie.getPopularity()));
        id = mMovie.getId();
        getData();



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
        service.getVideos(id, new Callback<VideoModel.VideoResult>() {
            @Override
            public void success(VideoModel.VideoResult videoModel, Response response) {
                mVideoList  = new ArrayList<>();
                mVideoList.addAll(videoModel.getResults());
                VideoAdapter adapt = new VideoAdapter(getApplicationContext(),R.layout.list_video, (ArrayList<VideoModel>) mVideoList);
                lv.setAdapter(adapt);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }
}
