package com.soddi.ChatApp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.soddi.assignment1.R;

import java.util.ArrayList;

/**
 * Created by soddi on 2014-12-14.
 */
public class CustomAdapterChat<Object> extends ArrayAdapter<Object> {

    private View view;
    private final Context context;
    private final ArrayList<Object> values;

    public CustomAdapterChat(Context context, ArrayList<Object> values) {
        super(context, R.layout.chatstyle_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.chatstyle_layout, parent, false);

        TextView messageView = (TextView) view.findViewById(R.id.textview_chatStyleMessage);
        TextView timeView = (TextView) view.findViewById(R.id.textView_chatStyleTime);

        ChatMessage chatMessage = (ChatMessage) values.get(position);
        messageView.setText(chatMessage.getMessage());
        timeView.setText(chatMessage.getTimestamp());

        Firebase firebase = new Firebase((String) context.getResources().getText(R.string.firebase_url));
        String user = firebase.getAuth().getProviderData().get("email").toString();

        if( chatMessage.getFrom().equals(user) ) {
            messageView.setGravity(Gravity.RIGHT);
            timeView.setGravity(Gravity.RIGHT);
        }

        return view;
    }
}
