package com.everlesslycoding.hackindr.MainContentFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everlesslycoding.hackindr.R;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class PopularIdeaLists extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_idea_view, container, false);
    }

}
