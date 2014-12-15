package com.soddi.ChatApp;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.soddi.assignment1.R;


public class MainActivity extends TemplateMenuActivity {

    private static final int MENU_SETTINGS = Menu.FIRST + 1;
    private static final int MENU_NEW_GROUP = Menu.FIRST + 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String email = intent.getStringExtra(LoginActivity.EXTRA_EMAIL);

        if (!email.isEmpty()) {
            Toast.makeText(this, "Welcome " + email + "!", Toast.LENGTH_SHORT).show();
        }


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_chat_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }
            GroupFragment groupFragment = new GroupFragment();
            groupFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.fragment_chat_container, groupFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //menu.add("Settings");
        menu.add(0, MENU_SETTINGS, Menu.NONE, "Settings");
        menu.add(0, MENU_NEW_GROUP, Menu.NONE, "New Group");
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
            case MENU_SETTINGS:
                settings();
                return true;
            case MENU_NEW_GROUP:
                createNewGroup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewGroup() {
        showInputDialog();
    }

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promtView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promtView);

        final EditText editText = (EditText) promtView.findViewById(R.id.editGroupText);

        //Setup a dialog window
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                GroupFragment groupFragment = (GroupFragment) getFragmentManager().findFragmentById(R.id.fragment_chat_container);
                groupFragment.newGroup(name);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    private void settings() {

        if (findViewById(R.id.fragment_chat_container) != null) {
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_chat_container, settingsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void about() {

        if (findViewById(R.id.fragment_chat_container) != null) {
            AboutFragment aboutFragment = new AboutFragment();
            aboutFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_chat_container, aboutFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void sendMessage(View view) {
        ChatFragment chatFragment = (ChatFragment) getFragmentManager().findFragmentById(R.id.fragment_chat_container);
        chatFragment.sendMessage();
    }
}