package com.soddi.testmysensorapp;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onResume() {
    super.onResume();

    //getFragmentManager().beginTransaction().add(R.id.container, SensorListFragment.newInstance("", "")).commit();
    getFragmentManager().beginTransaction().add(R.id.container, ReadSensorFragment.newInstance("", "")).commit();
    //getFragmentManager().beginTransaction().add(R.id.container, GpsFragment.newInstance("", "")).commit();
  }

  private static final String TAG = "mainact";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SensorManager man = (SensorManager) getSystemService(SENSOR_SERVICE);

    if (man.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
      Log.d(TAG, "Device has temp sensor");
    }

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
