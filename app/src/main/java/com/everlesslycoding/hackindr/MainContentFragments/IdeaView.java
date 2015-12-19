package com.everlesslycoding.hackindr.MainContentFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.everlesslycoding.hackindr.Models.Idea;
import com.everlesslycoding.hackindr.R;

public class IdeaView extends Fragment {

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



        return rootView;
    }

    public void setCurrentIdea(Idea currentIdea) {
        this.currentIdea = currentIdea;
        updateIdeaView();
    }

    public void updateIdeaView() {
        IdeaTitle.setText(currentIdea.getTitle());
        IdeaContent.setText(currentIdea.getContent());
    }
}