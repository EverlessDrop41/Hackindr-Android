package com.everlesslycoding.hackindr.MainContentFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everlesslycoding.hackindr.MainActivity;
import com.everlesslycoding.hackindr.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class CreateIdea extends Fragment {

    public static final String TAG = "CreateIdea";

    EditText titleInput;
    EditText contentInput;
    Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_idea, container, false);

        titleInput = (EditText) rootView.findViewById(R.id.titleInput);
        contentInput = (EditText) rootView.findViewById(R.id.descriptionInput);
        submitButton = (Button) rootView.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitIdea();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    void submitIdea() {
        RequestBody createBody = new FormEncodingBuilder()
                .add("title", titleInput.getText().toString())
                .add("content", contentInput.getText().toString())
                .build();

        Request request = new Request.Builder().url(MainActivity.BASE_URL + "/ideas/add").post(createBody).build();

        Call call = MainActivity.client.newCall(request);

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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                            .setTitle("Success")
                                            .setMessage("Your idea has successfully been made").create();
                                    alertDialog.show();
                                }
                            });
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

    void clearForm () {
        titleInput.setText("");
        contentInput.setText("");
    }
    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}

