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
/*
 * Created by Arie Ridwansyah on 5/10/16 6:05 AM
 * Copyright (c) 2016. All rights reserved.
 * enjoy your coding and drink coffee ^_^
 * Last modified 5/8/16 10:52 PM
 */
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
        final View view = inflater.inflate(R.layout.item_review, parent, false);
        final Result result = reviewList.get(position);
        TextView author = (TextView) view.findViewById(R.id.tvAuthor);
        final TextView content = (TextView) view.findViewById(R.id.tvContent);
        final TextView more = (TextView)view.findViewById(R.id.tvMore);
        author.setText(result.getAuthor());
        content.setText(result.getContent());
        content.setMaxLines(3);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setMaxLines(Integer.MAX_VALUE);
                more.setVisibility(view.GONE);
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setMaxLines(3);
                more.setVisibility(view.VISIBLE);
            }
        });
        return view;
    }
}