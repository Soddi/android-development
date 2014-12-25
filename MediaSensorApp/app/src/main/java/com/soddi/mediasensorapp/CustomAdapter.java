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
public class CustomAdapter<String> extends ArrayAdapter<String> {

    private final Context context;
    private ArrayList<String> songs;

    public CustomAdapter(Context context, ArrayList<String> songs) {
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
        int songID = Integer.parseInt("" + songs.get(position));
        String songName = (String) context.getResources().getResourceName(songID);
        textView.setText("Song: " + songName);
        imageView.setImageResource(R.drawable.ic_action_headphones);
        return view;
    }
}
