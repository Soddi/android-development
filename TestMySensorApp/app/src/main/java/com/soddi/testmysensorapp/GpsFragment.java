package com.soddi.testmysensorapp;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Use the {@link com.soddi.testmysensorapp.GpsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GpsFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private LocationManager mLocationManager;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment GpsFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static GpsFragment newInstance(String param1, String param2) {
    GpsFragment fragment = new GpsFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public GpsFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
  }

  @Override
  public void onResume() {
    super.onResume();

    if( mLocationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null ){
      mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
    }

  }

  @Override
  public void onPause() {
    super.onPause();

    mLocationManager.removeUpdates(mLocationListener);
  }

  TextView mLocation;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_gps, container, false);

    mLocation = (TextView) root.findViewById(R.id.location);

    return root;
  }


  LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {

      mLocation.setText(location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };

}
