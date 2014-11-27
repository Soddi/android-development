package com.soddi.assignment1;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ChatActivity extends TemplateMenuActivity {
    private boolean fragmentExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String email = intent.getStringExtra(LoginActivity.EXTRA_EMAIL);

        if (!email.isEmpty()) {
            Toast.makeText(this, "Welcome " + email + "!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //menu.add("Settings");
        menu.add(1, 1, 1, "Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_about:
                about();
                return true;
            case 1:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void settings() {

        if (findViewById(R.id.fragment_chat_container) != null) {
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if (fragmentExists) {
                fragmentTransaction.replace(R.id.fragment_chat_container, settingsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.add(R.id.fragment_chat_container, settingsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fragmentExists = true;
            }
        }
        //Toast.makeText(this, "Settings fragment created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void about() {
        //Toast.makeText(this, "YES, this works!", Toast.LENGTH_SHORT).show();

        if (findViewById(R.id.fragment_chat_container) != null) {
            AboutFragment aboutFragment = new AboutFragment();
            aboutFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if (fragmentExists) {
                fragmentTransaction.replace(R.id.fragment_chat_container, aboutFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.add(R.id.fragment_chat_container, aboutFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fragmentExists = true;
            }
        }
        //Toast.makeText(this, "About fragment created", Toast.LENGTH_SHORT).show();
    }
}
