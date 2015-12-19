package com.everlesslycoding.hackindr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Proxy;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Login Activity";
    public static final String BASE_URL = "http://f907f631.ngrok.io";

    EditText emailInput;
    EditText passwordInput;
    Button loginButton;

    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();

        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        loginButton = (Button) findViewById(R.id.logInButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Email: " + emailInput.getText().toString());
                Log.d(TAG, "Password: " + passwordInput.getText().toString());

                // TODO: 19/12/2015 Use the webserver to handle user auth
                client.setAuthenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Proxy proxy, Response response) throws IOException {
                        String credential = Credentials.basic(emailInput.getText().toString(), passwordInput.getText().toString());
                        if (credential.equals(response.request().header("Authorization"))) {
                            return null;
                        }
                        return response.request().newBuilder().header("Authorization", credential).build();
                    }

                    @Override
                    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                        return null;
                    }
                });

                Request request = new Request.Builder().url(BASE_URL + "/token").build();
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Toast.makeText(getApplicationContext(), "Error performing http requests", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String data = response.body().string();
                            Log.d(TAG, data);
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            try {
                                JSONObject JSONData = new JSONObject(data);
                                i.putExtra("Duration", JSONData.getString("duration"));
                                i.putExtra("Token", JSONData.getString("token"));
                                startActivity(i);
                            } catch (Exception e) {
                                Log.e(TAG, "Bad things have happened");
                            }
                        }
                        Log.d(TAG, "Error logging in");
                    }
                });
            }
        });
    }
}
