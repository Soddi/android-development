package com.soddi.mediasensorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by soddi on 2014-12-24.
 */
public class CustomAdapter<Integer> extends ArrayAdapter<Integer> {

    private final Context context;
    private ArrayList<Integer> songs;

    public CustomAdapter(Context context, ArrayList<Integer> songs) {
        super(context, R.layout.listview_layout, songs);
        this.context = context;
        this.songs = songs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_layout, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.list_TextView);
        ImageView imageView = (ImageView) view.findViewById(R.id.list_ImageView);
        Object song = songs.get(position);
        String name = context.getResources().getResourceName(songs.get(position));
        textView.setText(song.toString());
        imageView.setImageResource(R.drawable.ic_action_headphones);
        return view;
    }
}
