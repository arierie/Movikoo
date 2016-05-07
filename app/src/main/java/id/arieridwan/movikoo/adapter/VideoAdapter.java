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

import java.util.ArrayList;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.model.VideoModel;

public class VideoAdapter extends ArrayAdapter<VideoModel> {

    private Context context;
    private ArrayList<VideoModel> flowerList;
    public VideoAdapter(Context context, int resource, ArrayList<VideoModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.flowerList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_video,parent,false);
        final VideoModel flower = flowerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.tvVideo);
        tv.setText(flower.getName());
        ImageView img = (ImageView) view.findViewById(R.id.ivVideo);
        img.setImageResource(R.drawable.ic_play_circle_fill_24dp);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + flower.getKey()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);

            }
        });
        return view;
    }
}
