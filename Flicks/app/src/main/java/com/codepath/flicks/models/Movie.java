package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by STEPHAN987 on 7/22/2017.
 */

public class Movie {
    public enum PopularityValues {
        POPULAR, LESSPOPULAR
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

     public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getFullBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1280/%s", backdropPath);
    }

    public String getOriginalBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/original/%s", backdropPath);
    }

    public boolean isBackdropPathNull() {
        return (backdropPath == null);
    }

    public boolean isPosterPathNull() {
        return (posterPath == null);
    }

    public long getID() {
        return ID;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public PopularityValues getMoviePopularity() {
        if (this.isBackdropPathNull())
        {
            return moviePopularity.LESSPOPULAR;
        }
        else{
            if(voteAverage >= 5){
                return moviePopularity.POPULAR;
            }
            else{
                return moviePopularity.LESSPOPULAR;
            }
        }
    }

    public String getVideoKey() {
        return videoKey;
    }

    long ID;
    String posterPath;
    String videoKey;
    String originalTitle;
    String overview;
    String backdropPath;
    Double voteAverage;
    public PopularityValues moviePopularity;

    public Movie(JSONObject jsonObject) throws JSONException{
        this.ID = jsonObject.getLong("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public static Movie videoDetailsFromArray(JSONArray array) throws JSONException {
        Movie result = null;

        for (int i = 0; i < array.length(); i++){
            try {
                result.videoKey = array.getJSONObject(i).getString("key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}
