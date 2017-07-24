package com.codepath.flicks.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by STEPHAN987 on 7/23/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    Long idMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView ivFullBackdrop = (ImageView) findViewById(R.id.ivFullBackdrop);
        ImageView ivOriginalBackdrop = (ImageView) findViewById(R.id.ivOriginalBackdrop);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);
        TextView tvRating = (TextView) findViewById(R.id.tvRating);
        idMovie = this.getIntent().getLongExtra("ID",0);
        String backdropPath = this.getIntent().getStringExtra("backdropPath");
        tvTitle.setText(this.getIntent().getStringExtra("originalTitle"));
        tvOverview.setText(this.getIntent().getStringExtra("overview"));
        tvRating.setText(String.valueOf(this.getIntent().getDoubleExtra("rating",0.0)));

        Context currentContext = getBaseContext();


        if(currentContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Picasso.with(currentContext).load(backdropPath)
                    .error(R.drawable.no_image_big)
                    .placeholder(R.drawable.loading_placeholder)
                    .transform(new RoundedCornersTransformation(20, 20)).into(ivFullBackdrop);
        }
        else if (currentContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Picasso.with(currentContext).load(backdropPath)
                    .error(R.drawable.no_image_big)
                    .placeholder(R.drawable.loading_placeholder)
                    .transform(new RoundedCornersTransformation(20, 20)).into(ivOriginalBackdrop);
        }
    }


}
