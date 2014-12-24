package com.soddi.mediasensorapp;

import android.app.Activity;
import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ListActivity implements SensorEventListener {

    Sensor sensor;
    SensorManager sensorManager;
    TextView displayReading;
    private Toast toast;
    private boolean knockActivated = false;

    private final static double KNOCK_THRESHOLD = 0.2;
    private long knockingTime = 1250;
    private final static int SAMPLING_TIME = 20;
    private float last_z = 0;
    private long lastUpdate = 0;
    private long timeFirstKnock = 0;
    int amountOfKnocks = 0;
    float avgValue = 0;
    private ArrayList<Float> arraylistValues = new ArrayList<Float>();
    private ArrayList<Integer> songList = new ArrayList<Integer>();
    int songCounter = 0;

    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        displayReading = (TextView) findViewById(R.id.textView_SensorReading);
        songList.add(R.raw.game_of_thrones);
        songList.add(R.raw.the_big_bang_theory);
        songList.add(R.raw.the_simpsons);

        CustomAdapterSong adapterSong = new CustomAdapterSong(this, songList);
        setListAdapter(adapterSong);
    }

    private int chooseSong() {
        if (songCounter == 3) {
            songCounter = 0;
        }
        songCounter++;
        int song = songList.get(songCounter);
        return song;
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        float zValue = event.values[2];
        arraylistValues.add(zValue);

        long currentTime = System.currentTimeMillis();

        if ((currentTime - lastUpdate) > SAMPLING_TIME) {
            lastUpdate = currentTime;
            for (Float value : arraylistValues) {
                avgValue += value;
            }
            if (amountOfKnocks > 4) {
                knockActivated = false;
                amountOfKnocks = 0;
                timeFirstKnock = 0;
            }
            avgValue /= arraylistValues.size();
            avgValue = Math.abs(avgValue - last_z);
            displayReading.setText("Z: " + avgValue + "\nNumberOfKnocks: " + amountOfKnocks);
            if (avgValue > KNOCK_THRESHOLD) {
                timeFirstKnock = System.currentTimeMillis();
                knockActivated = true;
                amountOfKnocks++;
            }
            avgValue = 0;
            last_z = zValue;

        }
        if (currentTime - timeFirstKnock > knockingTime && knockActivated) {
            switch (amountOfKnocks) {
                case 1:
                    if (mPlayer == null) {
                        Toast.makeText(this, "Starting music (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.game_of_thrones);
                        mPlayer.start();//Start playing the music
                    } else {
                        Toast.makeText(this, "Pausing music (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                        mPlayer.pause();
                    }
                    break;
                case 2:
                    Toast.makeText(this, "Next song (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if (mPlayer != null) {//If music is playing already
                        mPlayer.stop();//Stop playing the music
                        mPlayer = MediaPlayer.create(getApplicationContext(), chooseSong());
                        mPlayer.start();//Start playing the music
                    }
                    break;
                case 3:
                    Toast.makeText(this, "previous song (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if (mPlayer != null) {//If music is playing already
                        mPlayer.stop();//Stop playing the music
                        mPlayer = MediaPlayer.create(getApplicationContext(), chooseSong());
                        mPlayer.start();//Start playing the music
                    }
                    break;
                case 4:
                    Toast.makeText(this, "Stopping music (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if (mPlayer != null && mPlayer.isPlaying()) {//If music is playing already
                        mPlayer.stop();//Stop playing the music
                    }
                    break;
                default:
                    toast.makeText(this, "Too many knocks: " + amountOfKnocks, Toast.LENGTH_SHORT).show();
                    break;
            }
            avgValue = 0;
            knockActivated = false;
            amountOfKnocks = 0;
            timeFirstKnock = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
