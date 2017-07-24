package com.codepath.flicks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by STEPHAN987 on 7/23/2017.
 */


public class YoutubePlayerActivity extends YouTubeBaseActivity {
    Movie movieTrailer;
    Long movieID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtubeplayer);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.vvTrailer);

        movieID = this.getIntent().getLongExtra("ID",0);

        youTubePlayerView.initialize("AIzaSyCHHFG-n6uUMCme_zsLyvTc-nPEjdBb4SQ",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        String videoURL = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US", movieID);

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(videoURL, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                JSONArray moviesJsonResults = null;

                                try {
                                    moviesJsonResults = response.getJSONArray("results");
                                    movieTrailer = Movie.videoDetailsFromArray(moviesJsonResults);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }


                        });
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(movieTrailer.getVideoKey());
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });


    }
}
