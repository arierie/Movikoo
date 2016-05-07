package id.arieridwan.movikoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.activity.DetailActivity;
import id.arieridwan.movikoo.model.MovieModel;

public  class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieModel> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MovieAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, mMovieList.get(position));
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movie = mMovieList.get(position);
        Picasso.with(mContext)
                .load(movie.getPoster_path())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void setMovieList(List<MovieModel> movieList) {
        this.mMovieList = new ArrayList<>();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
