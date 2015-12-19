package com.everlesslycoding.hackindr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Login Activity";

    EditText emailInput;
    EditText passwordInput;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        loginButton = (Button) findViewById(R.id.logInButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Email: " + emailInput.getText().toString());
                Log.d(TAG, "Password: " + passwordInput.getText().toString());

                // TODO: 19/12/2015 Use the webserver to handle user auth

                // TODO: 19/12/2015 Move to new activity

                Toast.makeText(getApplicationContext(), emailInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
