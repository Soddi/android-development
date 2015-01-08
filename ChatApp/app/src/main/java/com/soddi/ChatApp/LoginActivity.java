package com.soddi.ChatApp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.soddi.assignment1.R;

/**
 * Assignment 1 finished, this is the base of Assignment 2 now.
 */
public class LoginActivity extends TemplateMenuActivity {
    public final static String EXTRA_EMAIL = "com.soddi.EMAIL";
    public Firebase myFireBaseRef;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        myFireBaseRef = new Firebase((String)(getResources().getText(R.string.firebase_url)));

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

            //getFragmentManager().beginTransaction().add(R.id.fragment_login_container, loginFragment).commit();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_login_container, loginFragment).commit();
            customAnimate(fragmentTransaction);
        }
    }

    /**
     * Called when the user clicks the login button
     */
    public void login(View view) {
        EditText editText = (EditText) findViewById(R.id.emailField);
        String email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.passwordField);
        String password = editText.getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter both email and password", Toast.LENGTH_SHORT).show();
        } else {
            authUser(email, password);
        }
    }

    public void register(View view) {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_login_container, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        customAnimate(fragmentTransaction);
    }

    public void revivePassword(View view) {
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_login_container, forgotPasswordFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        customAnimate(fragmentTransaction);

    }

    @Override
    public void about() {
        AboutFragment aboutFragment = new AboutFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_login_container, aboutFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        customAnimate(fragmentTransaction);
    }

    private void customAnimate(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,
                android.R.animator.fade_in, android.R.animator.fade_out);
    }

    public void createAccount(View view) {
        EditText editText = (EditText) findViewById(R.id.emailRegisterField);
        final String email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.passwordRegisterField);
        final String password = editText.getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter both email and password", Toast.LENGTH_SHORT).show();
        }
        else {
           myFireBaseRef.createUser(email, password, new Firebase.ResultHandler() {
              @Override
           public void onSuccess() {
                  Log.d(TAG, "Registration Successful");
                  authUser(email, password);
              }
               @Override
           public void onError(FirebaseError firebaseError) {
                   Log.d(TAG, "Registration error");
                   if(firebaseError.getCode() == firebaseError.EMAIL_TAKEN) {
                       Toast.makeText(getApplicationContext(), "Email is taken", Toast.LENGTH_SHORT).show();
                   }
               }
           });
        }
    }

    public void authUser(final String email, final String password) {
        myFireBaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d(TAG, "Authentication Successful");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_EMAIL, email);
                startActivity(intent);
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.d(TAG, "Authentication error");
                switch (firebaseError.getCode()) {
                    case FirebaseError.INVALID_EMAIL:
                        Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                    case FirebaseError.INVALID_PASSWORD:
                        Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                    default:
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
