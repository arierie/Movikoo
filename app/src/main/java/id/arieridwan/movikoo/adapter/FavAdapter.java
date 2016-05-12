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
import id.arieridwan.movikoo.model.Favorite;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>  {

    private List<Favorite> favList;
    private LayoutInflater layoutInflater;
    private Context mContext;


    public FavAdapter(Context context) {
        this.mContext = context;
        this.layoutInflater = layoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FAV, favList.get(position));

                mContext.startActivity(intent);
            }
            });
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Favorite favorite = favList.get(position);
        Picasso.with(mContext).load(favorite.getPoster_path()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return (favList== null) ? 0 : favList.size();
    }

    public void setFavList(List<Favorite> favList){
        this.favList = new ArrayList<>();
        this.favList.addAll(favList);
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
