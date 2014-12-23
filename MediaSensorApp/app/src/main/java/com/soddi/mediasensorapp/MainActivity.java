package com.soddi.mediasensorapp;

import android.app.Activity;
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


public class MainActivity extends Activity implements SensorEventListener {

    Sensor sensor;
    SensorManager sensorManager;
    TextView displayReading;
    private Toast toast;
    private boolean knockActivated = false;

    private final static double KNOCK_THRESHOLD = 1.5;
    private long knockGestureTimeInSeconds = 1500;
    private float last_z = 0;
    private long timeFirstKnock = 0;
    int amountOfKnocks = 0;

    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        displayReading = (TextView) findViewById(R.id.textView_SensorReading);

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
        float knockValue = Math.abs(zValue - last_z);
        long currentTime = System.currentTimeMillis();
        displayReading.setText("Z: " + knockValue);

        if (knockValue > KNOCK_THRESHOLD) {
            timeFirstKnock = System.currentTimeMillis();
            knockActivated = true;
            amountOfKnocks++;
        }
        last_z = zValue;
        if (currentTime - timeFirstKnock > knockGestureTimeInSeconds && knockActivated) {
            switch (amountOfKnocks) {
                case 1:
                    Toast.makeText(this, "Stopping music (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if(mPlayer!=null && mPlayer.isPlaying()){//If music is playing already
                        mPlayer.stop();//Stop playing the music
                    }
                    break;
                case 2:
                    Toast.makeText(this, "Starting music (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.game_of_thrones);
                    mPlayer.start();//Start playing the music
                    break;
                case 3:
                    Toast.makeText(this, "Pausing music (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if(mPlayer!=null && mPlayer.isPlaying()){//If music is playing already
                        mPlayer.pause();//Stop playing the music
                    }
                    break;
                case 4:
                    Toast.makeText(this, "Next song (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if(mPlayer!=null){//If music is playing already
                        mPlayer.stop();//Stop playing the music
                        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.the_simpsons);
                        mPlayer.start();//Start playing the music
                    }
                    break;
                case 5:
                    Toast.makeText(this, "previous song (" + amountOfKnocks + ")", Toast.LENGTH_SHORT).show();
                    if(mPlayer!=null){//If music is playing already
                        mPlayer.stop();//Stop playing the music
                        mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.the_big_bang_theory);
                        mPlayer.start();//Start playing the music
                    }
                    break;
                default:
                    toast.makeText(this, "Amount of knocks: " + amountOfKnocks, Toast.LENGTH_SHORT).show();
                    break;
            }
            knockActivated = false;
            amountOfKnocks = 0;
            timeFirstKnock = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
