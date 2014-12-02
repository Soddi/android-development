package com.soddi.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
    private String[] chatMsgs = {"chatmsg 1", "chatmsg 2", "chatmsg 3", "chatmsg 4", "chatmsg 5",
            "chatmsg 6", "chatmsg 7", "chatmsg 8", "chatmsg 9", "chatmsg 10",
            "chatmsg 11", "chatmsg 12", "chatmsg 13", "chatmsg 14", "chatmsg 15",};

    private ListView chatList;
    private ArrayAdapter<String> chatListAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("ChatList");
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < chatMsgs.length; ++i) {
            list.add(chatMsgs[i]);
        }
        chatListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        chatList = (ListView) view.findViewById(R.id.GroupListView);
        chatList.setAdapter(chatListAdapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Click ListItem number " + (position + 1), Toast.LENGTH_SHORT).show();
                Log.d("GroupFragment", "You have clicked a chat");
            }
        });
        return view;
    }
}
