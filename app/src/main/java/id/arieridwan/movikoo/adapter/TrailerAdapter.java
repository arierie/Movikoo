package id.arieridwan.movikoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.model.VideoModel;

public class TrailerAdapter extends ArrayAdapter<VideoModel> {

    private Context context;
    private ArrayList<VideoModel> trailerList;
    public TrailerAdapter(Context context, int resource, ArrayList<VideoModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.trailerList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_trailer, parent, false);
        final VideoModel trailer = trailerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.tvTrailer);
        tv.setText(trailer.getName());
        ImageView img = (ImageView) view.findViewById(R.id.ivTrailer);
        String thumbnails = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        Picasso.with(getContext()).load(thumbnails).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);

            }
        });
        return view;
    }
}