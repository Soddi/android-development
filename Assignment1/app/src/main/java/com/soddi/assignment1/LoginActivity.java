package com.soddi.assignment1;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


/**
 * This App should be complete for Assignment1
 */

public class LoginActivity extends TemplateMenuActivity {
    public final static String EXTRA_EMAIL = "com.soddi.EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_login_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction().add(R.id.fragment_login_container, loginFragment).commit();
        }
    }

    /**
     * Called when the user clicks the login button
     */
    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.emailField);
        String email = editText.getText().toString();
        intent.putExtra(EXTRA_EMAIL, email);
        startActivity(intent);
    }

    public void register(View view) {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_login_container, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void revivePassword(View view) {
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_login_container, forgotPasswordFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void about() {
        AboutFragment aboutFragment = new AboutFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_login_container, aboutFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
