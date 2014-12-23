package com.soddi.mediasensorapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity implements SensorEventListener{

    MediaPlayer mPlayer;
    Button buttonPlay;
    Button buttonPause;
    Button buttonNext;
    Button buttonPrevious;
    Button buttonStop;

    Sensor sensor;
    SensorManager sensorManager;

    TextView displayReading;

    private final static double KNOCK_SENSITIVITY = 9.7;
    private final static int KNOCK_TIME_SECONDS = 1;

    private ArrayList<Float> arrayList;

    private Toast toast;

    private boolean knockActivated = false;

    int amountOfKnocks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*final MediaPlayer mediaPlayer = new MediaPlayer();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);
        mediaPlayer.create(this, R.raw.game_of_thrones);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                if(mp == mediaPlayer) {
                    mp.start();
                }
            }
        });
        //mediaPlayer.prepareAsync();
        //mediaPlayer.start(); //no need to call prepare, create does that for you :)
        */

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        displayReading = (TextView) findViewById(R.id.textView_SensorReading);

        buttonPlay = (Button) findViewById(R.id.button);
        buttonPlay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.game_of_thrones);
                mPlayer.start();//Start playing the music
            }
        });

        buttonPause = (Button) findViewById(R.id.button2);
        buttonPause.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mPlayer!=null && mPlayer.isPlaying()){//If music is playing already
                    mPlayer.pause();//Stop playing the music
                }
            }
        });

        buttonNext = (Button) findViewById(R.id.button3);
        buttonNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mPlayer!=null || mPlayer.isPlaying()){//If music is playing already
                    mPlayer.stop();//Stop playing the music
                    mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.the_big_bang_theory);
                    mPlayer.start();//Start playing the music
                }
            }
        });

        buttonPrevious = (Button) findViewById(R.id.button4);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mPlayer!=null || mPlayer.isPlaying()){//If music is playing already
                    mPlayer.stop();//Stop playing the music
                    mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.the_simpsons);
                    mPlayer.start();//Start playing the music
                }
            }
        });

        buttonStop = (Button) findViewById(R.id.button5);
        buttonStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mPlayer!=null && mPlayer.isPlaying()){//If music is playing already
                    mPlayer.stop();//Stop playing the music
                }
            }
        });

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
        float value = event.values[2];
        displayReading.setText("Z: " + value);
        int knocksCounter = 0;
        if(value> KNOCK_SENSITIVITY) {
            knockActivated = true;
            knocksCounter += 1;
            Time time = new Time();
            Time stopTime = new Time();
            stopTime.setToNow();
            stopTime.second += KNOCK_TIME_SECONDS;
            stopTime.format("%H:%M:%S");
            do {
                time.setToNow();
                if(event.values[2] > KNOCK_SENSITIVITY) {
                    amountOfKnocks++;
                }
            } while(time.before(stopTime));

        } else {
            if(toast != null) {
                toast.cancel();
            }
        }
        if(arrayList != null && knockActivated) {
            switch (knocksCounter) {
                case 1: toast.makeText(this, "Play music", Toast.LENGTH_SHORT).show();
                        knockActivated = false;
                        break;
                case 2: toast.makeText(this, "Pause music", Toast.LENGTH_SHORT).show();
                        knockActivated = false;
                        break;
                case 3: toast.makeText(this, "Next music", Toast.LENGTH_SHORT).show();
                        knockActivated = false;
                        break;
                case 4: toast.makeText(this, "Previous music", Toast.LENGTH_SHORT).show();
                        knockActivated = false;
                        break;
                case 5: toast.makeText(this, "Stop music", Toast.LENGTH_SHORT).show();
                        knockActivated = false;
                        break;
                default: toast.makeText(this, "" + knocksCounter, Toast.LENGTH_SHORT).show();
                        knockActivated = false;
                        break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
