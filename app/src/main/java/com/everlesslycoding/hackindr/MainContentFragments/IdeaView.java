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
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Deque;

public class IdeaView extends Fragment {

    public static final String TAG = "Idea View";

    Idea currentIdea;

    TextView IdeaTitle;
    TextView IdeaContent;
    Button LikeButton;
    Button DislikeButton;
    Button SkipButton;

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
        SkipButton = (Button) rootView.findViewById(R.id.skipButton);

        getNextIdea();

        return rootView;
    }

    public void setCurrentIdea(Idea currentIdea) {
        this.currentIdea = currentIdea;
        updateIdeaView();
    }

    public void updateIdeaView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IdeaTitle.setText(currentIdea.getTitle());
                IdeaContent.setText(currentIdea.getContent());
            }
        });
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
                    // TODO: 19/12/2015 Cool Shit
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

        setCurrentIdea(new Idea("No idea", "Sorry, like you, we have no ideas", -1));
    }

    private void OutOfIdeas() {
        Toast.makeText(getContext(), "Sorry, we're out of ideas :(", Toast.LENGTH_LONG).show();
    }
}