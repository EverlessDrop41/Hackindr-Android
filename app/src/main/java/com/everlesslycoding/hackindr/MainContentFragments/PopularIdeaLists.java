package com.everlesslycoding.hackindr.MainContentFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.everlesslycoding.hackindr.MainActivity;
import com.everlesslycoding.hackindr.Models.Idea;
import com.everlesslycoding.hackindr.PopularIdea;
import com.everlesslycoding.hackindr.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class PopularIdeaLists extends Fragment {

    private ListView popularListView;
    public static final String TAG = "Popular ListView";
    protected HashMap<String, String> mIdeas;
    protected ArrayList<String> titles;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_popular_ideas_list, container, false);
        popularListView = (ListView) rootView.findViewById(R.id.popularIdeasList);
        mIdeas = new HashMap<>();
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click
                String title = titles.get(position);
                String content = mIdeas.get(title);
                Intent intent = new Intent(getActivity(), PopularIdea.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                startActivity(intent);
            }
        };

        popularListView.setOnItemClickListener(mMessageClickedHandler);
        getPopular();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public void getPopular() {
        Request request = new Request.Builder().url(MainActivity.BASE_URL + "/ideas/top").build();
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
                            JSONArray ideas = data.getJSONArray("ideas");
                            updateDisplay(ideas);
                            //setCurrentIdea(new Idea(title, content, id));
                            return;

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

    public void updateDisplay(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject idea = jsonArray.getJSONObject(i);
            mIdeas.put(idea.getString("title"), idea.getString("content"));
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titles = new ArrayList<String>();
                titles.addAll(mIdeas.keySet());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, titles);
                popularListView.setAdapter(adapter);
            }
        });

    }

}
