package com.everlesslycoding.hackindr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PopularIdea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_idea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView titleView = (TextView) findViewById(R.id.ideaTitle);
        TextView contentView = (TextView) findViewById(R.id.ideaDescription);

        Intent intent = getIntent();
        titleView.setText(intent.getStringExtra("title"));

        contentView.setText(intent.getStringExtra("content"));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
