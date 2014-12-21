package com.soddi.testmysensorapp;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link com.soddi.testmysensorapp.SensorListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorListFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private ListView list;

  private ArrayAdapter<String> adapter;
  private ArrayList<String> mSensors = new ArrayList<String>();

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment SensorListFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SensorListFragment newInstance(String param1, String param2) {
    SensorListFragment fragment = new SensorListFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public SensorListFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    SensorManager manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);

    for (Sensor s : sensors) {
      String manufacturer = s.getVendor();
      String name = s.getName();
      float power = s.getPower();

      mSensors.add(name + "\n" + manufacturer + "\n" + power);
    }

    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mSensors);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_sensor_list, container, false);

    list = (ListView) root.findViewById(R.id.list);
    list.setAdapter(adapter);

    return root;
  }


}
