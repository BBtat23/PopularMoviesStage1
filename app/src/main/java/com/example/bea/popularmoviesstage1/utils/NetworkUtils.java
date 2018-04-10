package com.example.bea.popularmoviesstage1.utils;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static String BASE_API = "http://api.themoviedb.org/3/movie/";
    private static String apiKey = "api_key";
    private static String API_KEY = "ce4ec44da09975b3a3ade0a8cc61562c";
    private static String BASE_URL = "http://image.tmdb.org/t/p/";
    private static String SIZE_POSTER = "w500";
    private static String VIDEO_MOVIE = "videos";
    private static String REVIEWS_MOVIE = "reviews";


    public static URL buildUrlApi(String sortByString) {
        Uri builtUri = Uri.parse(BASE_API).buildUpon()
                .appendEncodedPath(sortByString)
                .appendQueryParameter(apiKey,API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlPosterPath(String posterPathString) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(SIZE_POSTER)
                .appendEncodedPath(posterPathString)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlVideoMovie(String idMovieString){
        Uri builtUri = Uri.parse(BASE_API).buildUpon()
                .appendPath(idMovieString)
                .appendPath(VIDEO_MOVIE)
                .appendQueryParameter(API_KEY,apiKey)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlReviewMovie(String idMovieString){
        Uri builtUri = Uri.parse(BASE_API).buildUpon()
                .appendEncodedPath(idMovieString)
                .appendEncodedPath(REVIEWS_MOVIE)
                .appendQueryParameter(apiKey,API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
