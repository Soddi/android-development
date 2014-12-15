package com.soddi.ChatApp;

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
import com.soddi.assignment1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupFragment extends Fragment {
    private final static String TAG = "GroupFragment";
    private ListView groupList;
    private ArrayAdapter<Group> groupListAdapter;
    private ArrayList<Group> groups;

    private View view;

    public Firebase myFireBaseRef;
    public String groupID;

    private ChatFragment chatFragment;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        myFireBaseRef = new Firebase((String)(getResources().getText(R.string.firebase_url)));
        getActivity().setTitle("Group List");
        groups = new ArrayList<Group>();
        groupListAdapter = new ArrayAdapter<Group>(getActivity(), android.R.layout.simple_list_item_1, groups);

        myFireBaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                groupListAdapter.add(new Group((String) snapshot.child("id").getValue(), (String) snapshot.child("name").getValue()));
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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_group, container, false);
        groupList = (ListView) view.findViewById(R.id.GroupListView);
        groupList.setAdapter(groupListAdapter);

        groupList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Group group = groupListAdapter.getItem(position);
                chatFragment = ChatFragment.newInstance(group);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_chat_container, chatFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d("GroupFragment", "clicked a group");
            }
        });
        return view;
    }

    public void newGroup(String name) {
        if(name.isEmpty()) {
            Toast.makeText(getActivity(), "group name must contain characters", Toast.LENGTH_SHORT);
        } else {
            String id = myFireBaseRef.push().getKey();

            Map<String, Object> groupObjects = new HashMap<String, Object>();
            Map<String, Object> group = new HashMap<String, Object>();

            group.put("name", name);
            group.put("id", id);
            groupObjects.put(id, group);

            myFireBaseRef.updateChildren(groupObjects);
        }
    }
}
