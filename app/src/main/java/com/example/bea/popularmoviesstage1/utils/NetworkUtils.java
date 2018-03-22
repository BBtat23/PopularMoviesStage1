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
    private static String TOP_RATED = "top_rated";
    private static String POPULAR = "popular";
    private static String API_KEY = "?api_key=xxxxxxxxxxx";
    private static String BASE_URL = "http://image.tmdb.org/t/p/";
    private static String SIZE_POSTER = "w185";



    public static URL buildUrlApi(String sortByString){
        Uri builtUri = Uri.parse(BASE_API).buildUpon()
                .appendEncodedPath(sortByString)
                .appendEncodedPath(API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlPosterPath(String posterPathString){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(SIZE_POSTER)
                .appendPath(posterPathString)
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
