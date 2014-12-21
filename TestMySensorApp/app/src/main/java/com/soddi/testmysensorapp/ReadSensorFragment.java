package com.soddi.testmysensorapp;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link com.soddi.testmysensorapp.ReadSensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadSensorFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private Sensor mSensor;

  private TextView mSensorVal, mSensorVal1, mSensorVal2, mSensorVal3;

  private SensorManager mManager;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ReadSensorFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ReadSensorFragment newInstance(String param1, String param2) {
    ReadSensorFragment fragment = new ReadSensorFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public ReadSensorFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    mManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

    if (mManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
      mSensor = mManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }
  }

  @Override
  public void onResume() {
    super.onResume();

    mManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  public void onPause() {
    super.onPause();

    mManager.unregisterListener(mSensorEventListener);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_read_sensor, container, false);

    mSensorVal = (TextView) root.findViewById(R.id.sensorVal);
    mSensorVal1 = (TextView) root.findViewById(R.id.sensorVal1);
    mSensorVal2 = (TextView) root.findViewById(R.id.sensorVal2);
    mSensorVal3 = (TextView) root.findViewById(R.id.sensorVal3);

    return root;
  }

  private SensorEventListener mSensorEventListener = new SensorEventListener() {
    @Override
    public void onSensorChanged(SensorEvent event) {
      mSensorVal.setText(Float.toString(event.values[0]));
//      mSensorVal1.setText(Float.toString(event.values[1]));
//      mSensorVal2.setText(Float.toString(event.values[2]));
//      mSensorVal3.setText(Float.toString(event.values[3]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };


}
