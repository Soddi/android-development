package com.soddi.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
    private ListView chatList;
    private ArrayAdapter<ChatMessage> chatListAdapter;
    private ArrayList<ChatMessage> chatMessages;

    private static final String TAG = "ChatFragment";

    private static String groupName;
    private static String groupID;
    public Firebase myFireBaseRef;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(Group group) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString("groupName", group.getName());
        args.putString("groupID", group.getId());
        fragment.setArguments(args);
        Log.d(TAG, "NewInstance created");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ChatList");
        if(getArguments() != null) {
            groupName = getArguments().getString("groupName");
            groupID = getArguments().getString("groupID");
        }

        Firebase.setAndroidContext(getActivity());
        myFireBaseRef = new Firebase( (String) getResources().getText(R.string.firebase_url)).child(groupID).child("messages");

        chatMessages = new ArrayList<ChatMessage>();
        chatListAdapter = new ArrayAdapter<ChatMessage>(getActivity(), android.R.layout.simple_list_item_1, chatMessages);

        myFireBaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {

                chatListAdapter.add(new ChatMessage((String) snapshot.child("id").getValue(), (String) snapshot.child("from").getValue()
                        , (String) snapshot.child("message").getValue(), (String) snapshot.child("timestamp").getValue()));
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot snapshot, String s) {
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        EditText editText = (EditText) view.findViewById(R.id.editChatText);

        chatList = (ListView) view.findViewById(R.id.ChatListView);
        chatList.setAdapter(chatListAdapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("GroupFragment", "You have clicked a chat");
            }
        });
        return view;
    }
}
