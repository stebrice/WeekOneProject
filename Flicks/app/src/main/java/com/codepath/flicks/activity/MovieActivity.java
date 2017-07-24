package com.codepath.flicks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flicks.R;
import com.codepath.flicks.adapters.MovieArrayAdapter;
import com.codepath.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        setupListViewListener();

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&page=2";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray moviesJsonResults = null;

                try {
                    moviesJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(moviesJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }


        });


    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        Movie movieToUse = movies.get(pos);
                        if (movieToUse.getMoviePopularity().ordinal() == Movie.PopularityValues.POPULAR.ordinal()){
                            launchVideoView(movieToUse);
                        }
                        else{
                            launchDetailsView(movieToUse);
                        }
                    }
                }
        );
    }

    public void launchDetailsView(Movie movieToUse) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MovieActivity.this, DetailsActivity.class);
        i.putExtra("id", movieToUse.getID());
        i.putExtra("backdropPath", movieToUse.getFullBackdropPath());
        i.putExtra("originalBackdropPath", movieToUse.getOriginalBackdropPath());
        i.putExtra("originalTitle", movieToUse.getOriginalTitle());
        i.putExtra("overview", movieToUse.getOverview());
        i.putExtra("rating", movieToUse.getVoteAverage());
        startActivity(i); // brings up the second activity
    }

    public void launchVideoView(Movie movieToUse) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MovieActivity.this, YoutubePlayerActivity.class);
        i.putExtra("id", movieToUse.getID());
        startActivity(i); // brings up the second activity
    }



}
