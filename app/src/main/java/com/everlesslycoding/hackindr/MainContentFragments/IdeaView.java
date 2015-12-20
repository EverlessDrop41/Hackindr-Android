package com.everlesslycoding.hackindr.MainContentFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.everlesslycoding.hackindr.Home;
import com.everlesslycoding.hackindr.MainActivity;
import com.everlesslycoding.hackindr.Models.Idea;
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
import java.util.Deque;
import java.util.Objects;

public class IdeaView extends Fragment {

    public static final String TAG = "Idea View";

    Idea currentIdea;

    TextView IdeaTitle;
    TextView IdeaContent;
    Button LikeButton;
    Button DislikeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_idea_view, container, false);

        IdeaTitle = (TextView) rootView.findViewById(R.id.ideaTitle);
        IdeaContent = (TextView) rootView.findViewById(R.id.ideaDescription);
        LikeButton = (Button) rootView.findViewById(R.id.likeButton);
        DislikeButton = (Button) rootView.findViewById(R.id.dislikeButton);

        IdeaTitle.setText("Loading Title...");
        IdeaContent.setText("Loading Content...");
        hideButtons();

        LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You like this idea!", Toast.LENGTH_SHORT).show();
                VoteOnPost(true);
            }
        });

        DislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You dislike this idea!", Toast.LENGTH_SHORT).show();
                VoteOnPost(false);
            }
        });

        getNextIdea();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (IdeaTitle.getText() == "" || IdeaTitle.getText() == "No idea") {
            hideButtons();
            getNextIdea();
        }
    }


    public void VoteOnPost(boolean didLike) {
        try {
            RequestBody voteBody = new FormEncodingBuilder()
                    .add("vote", String.valueOf(didLike))
                    .add("idea", String.valueOf(currentIdea.getId()))
                    .build();

            Request request = new Request.Builder().url(MainActivity.BASE_URL + "/ideas/vote").post(voteBody).build();

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
                                getNextIdea();
                            }
                            else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String message = data.getString("message");
                                            if (message != null && message.equals("You have already voted")) {
                                                OutOfIdeas();
                                            } else {
                                                Toast.makeText(getContext(), "Error creating idea!", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getContext(), "Error creating idea!", Toast.LENGTH_LONG).show();
                                        }
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentIdea(Idea currentIdea) {
        this.currentIdea = currentIdea;
        updateIdeaView();
    }

    public void updateIdeaView() {
        if(getActivity() == null)
            return;
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IdeaTitle.setText(currentIdea.getTitle());
                    IdeaContent.setText(currentIdea.getContent());
                    showButtons();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNextIdea() {
        Request request = new Request.Builder().url(MainActivity.BASE_URL + "/ideas/next").build();
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
                        JSONObject data = new JSONObject(res);
                        boolean success = data.getBoolean("success");
                        if (success) {
                            JSONObject idea = data.getJSONObject("idea");
                            String title = idea.getString("title");
                            String content = idea.getString("content");
                            int id = idea.getInt("id");

                            setCurrentIdea(new Idea(title, content, id));
                            return;
                        } else {
                            OutOfIdeas();
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

    void showButtons(){
        LikeButton.setVisibility(View.VISIBLE);
        DislikeButton.setVisibility(View.VISIBLE);
    }

    void hideButtons() {
        Log.d(TAG, "Hide Buttons");
        LikeButton.setVisibility(View.GONE);
        DislikeButton.setVisibility(View.GONE);
    }

    private void OutOfIdeas() {
//        Toast.makeText(getContext(), "Sorry, we're out of ideas :(", Toast.LENGTH_LONG).show();

        setCurrentIdea(new Idea("No idea", "Sorry, like you, we have no ideas", -1));
        hideButtons();
    }
}