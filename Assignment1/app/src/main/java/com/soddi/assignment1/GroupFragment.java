package com.soddi.assignment1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class GroupFragment extends Fragment {
    private final static String TAG = "GroupFragment";
    private String[] groupNames = {"Group 1", "Group 2", "Group 3 ", "Group 4", "Group 5",
            "Group 6", "Group 7", "Group 8 ", "Group 9", "Group 10",
            "Group 11", "Group 12", "Group 13 ", "Group 14", "Group 15"};
    private ListView groupList;
    private ArrayAdapter<Group> groupListAdapter;
    private ArrayList<Group> groups;

    public Firebase myFireBaseRef;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        myFireBaseRef = new Firebase((String)(getResources().getText(R.string.firebase_url)));
        getActivity().setTitle("Group List");
        /*final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < groupNames.length; ++i) {
            list.add(groupNames[i]);
        }*/
        groups = new ArrayList<Group>();
        //groupListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        groupListAdapter = new ArrayAdapter<Group>(getActivity(), android.R.layout.simple_list_item_1, groups);

        myFireBaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                groupListAdapter.add(new Group(snapshot.getKey(), (String) snapshot.child("name").getValue()));
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
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        groupList = (ListView) view.findViewById(R.id.GroupListView);
        groupList.setAdapter(groupListAdapter);

        groupList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Click ListItem number " + (position + 1), Toast.LENGTH_SHORT).show();
                ChatFragment chatFragment = new ChatFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_chat_container, chatFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d("GroupFragment", "You have clicked a group");
            }
        });
        return view;
    }
}
