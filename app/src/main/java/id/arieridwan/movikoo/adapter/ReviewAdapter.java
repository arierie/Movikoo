package id.arieridwan.movikoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.arieridwan.movikoo.R;
import id.arieridwan.movikoo.model.Result;

public class ReviewAdapter extends ArrayAdapter<Result> {

    private Context context;
    private ArrayList<Result> reviewList;
    public ReviewAdapter(Context context, int resource, ArrayList<Result> objects) {
        super(context, resource, objects);
        this.context = context;
        this.reviewList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_review, parent, false);
        final Result result = reviewList.get(position);
        TextView author = (TextView) view.findViewById(R.id.tvAuthor);
        TextView content = (TextView) view.findViewById(R.id.tvContent);
        author.setText(result.getAuthor());
        content.setText(result.getContent());
        return view;
    }
}