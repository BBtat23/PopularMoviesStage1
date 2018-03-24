package com.example.bea.popularmoviesstage1.utils;

import com.example.bea.popularmoviesstage1.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JSONUtils {

    public static List<Movie> JSONUtilsMovie(String jsonMovie) throws JSONException {
        String originalTitle = null;
        String posterPath = null;
        String overView = null;
        Double ratingUser = null;
        String releaseDate = null;

        JSONObject jsonObjectMovie = new JSONObject(jsonMovie);
        JSONArray jsonArrayMovie = jsonObjectMovie.getJSONArray("results");
        Movie objectMovie = null;
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArrayMovie.length(); i++) {
            JSONObject movieObject = jsonArrayMovie.getJSONObject(i);
            originalTitle = movieObject.getString("original_title");
            posterPath = movieObject.getString("poster_path");
            overView = movieObject.getString("overview");
            ratingUser = movieObject.getDouble("vote_average");
            releaseDate = movieObject.getString("release_date");

            objectMovie = new Movie(originalTitle, posterPath, overView, ratingUser, releaseDate);
            movies.add(objectMovie);
        }
        return movies;
    }
}
