package com.everlesslycoding.hackindr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPassword2EditText;
    private Button mSignUpButton;
    private String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mPassword2EditText = (EditText) findViewById(R.id.passwordEditText2);
        mSignUpButton = (Button) findViewById(R.id.signUpButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEditText.getText().toString();
                String password1 = mPasswordEditText.getText().toString();
                String password2 = mPassword2EditText.getText().toString();

                if (!password1.equals(password2)) {
                    Toast.makeText(SignUpActivity.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestBody createBody = new FormEncodingBuilder()
                        .add("email", mEmailEditText.getText().toString())
                        .add("password", mPasswordEditText.getText().toString())
                        .build();

                Request request = new Request.Builder().url(MainActivity.BASE_URL + "/signup").post(createBody).build();
                OkHttpClient client= new OkHttpClient();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        if (response.isSuccessful()) {
                            String res = response.body().string();
                            try {
                                final JSONObject data = new JSONObject(res);
                                boolean success = data.getBoolean("success");
                                Log.d(TAG, res);

                                if (success) {
                                    SignUpActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this)
                                                    .setTitle("Success")
                                                    .setMessage("Yoh have successfully signed up!").create();
                                            alertDialog.show();
                                        }
                                    });
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                Log.d(TAG, "Error parsing JSON");
                                e.printStackTrace();
                            }
                            Log.d(TAG, res);
                        } else {
                            Log.d(TAG, "The request was unsuccesful: " + response.body().string());
                        }
                    }
                });
            }
        });
    }

}
