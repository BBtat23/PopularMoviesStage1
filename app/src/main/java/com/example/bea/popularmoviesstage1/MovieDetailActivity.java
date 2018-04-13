package com.example.bea.popularmoviesstage1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bea.popularmoviesstage1.data.Movie;
import com.example.bea.popularmoviesstage1.utils.JSONUtils;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetailActivity extends AppCompatActivity {

    TextView mOriginalTitle;
    TextView mReleaseDate;
    TextView mOverview;
    TextView mRatingUser;
    ImageView imageViewPosterPath;
    TextView videoTextView;
    TextView reviewTextView;
    ListView listViewVideoMovie;
    ArrayList<Movie> list_movie;
    String originalTitle;
    String releaseDate;
    String ratingUser;
    String overView;
    String posterPath;
    String idMovie;
    ArrayList<String> movieKeyStringList;
    String movieKeyString;
    String reviewString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mOriginalTitle = (TextView) findViewById(R.id.original_title);
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        mRatingUser = (TextView) findViewById(R.id.rating_user);
        mOverview = (TextView) findViewById(R.id.overview);
        imageViewPosterPath = (ImageView) findViewById(R.id.poster_path);
//        videoTextView = (TextView) findViewById(R.id.trailer1);
        reviewTextView = (TextView) findViewById(R.id.reviews);

//        list_movie = (ArrayList<Movie>) getIntent().getSerializableExtra("movieObject");
//        Log.v("MovieDetailActivity", "OriginalTitle: " + originalTitle);
        Intent movieDetailIntent = getIntent();
        originalTitle = movieDetailIntent.getStringExtra("Original Title");
        releaseDate = movieDetailIntent.getStringExtra("Release Date");
        ratingUser = movieDetailIntent.getStringExtra("Rating User");
        overView = movieDetailIntent.getStringExtra("OverView");
        posterPath = movieDetailIntent.getStringExtra("PosterPath");
        idMovie = movieDetailIntent.getStringExtra("Id Movie");

        String posterPathImage = NetworkUtils.buildUrlPosterPath(posterPath).toString();

        mOriginalTitle.setText(originalTitle);
        mReleaseDate.setText(releaseDate);
        mRatingUser.setText(ratingUser);
        mOverview.setText(overView);
        Picasso.with(this).load(posterPathImage).into(imageViewPosterPath);


        try {Log.v("MovieDetailActivity","AsyncTask " + movieKeyStringList);
            movieKeyStringList = new MainActivity.VideoMovieAsyncTask().execute(idMovie).get();//Ejecutamos la MovieAsyncTask para conseguir la key del video
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[] listVideos = new String[movieKeyStringList.size()];
        for (int i = 0; i < movieKeyStringList.size(); i++) {
            movieKeyString = movieKeyStringList.get(i);
            listVideos[i] = movieKeyString;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MovieDetailActivity.this, android.R.layout.simple_list_item_1, listVideos);
        listViewVideoMovie = (ListView) findViewById(R.id.trailer_list_view);
        listViewVideoMovie.setAdapter(adapter);

//        listViewVideoMovie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//
//        });
    }
    public void watchVideoTrailer(String[] movieKey) {
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + movieKey[0]));
        startActivity(youtubeIntent);
    }
}

//        listViewVideoMovie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    movieKeyStringList = new MainActivity.VideoMovieAsyncTask().execute(idMovie).get();//Ejecutamos la MovieAsyncTask para conseguir la key del video
//                    for (int i = 0; i< movieKeyStringList.size(); i++){
//                        movieKeyString = movieKeyStringList.get(i);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                watchVideoTrailer(movieKeyString);
//
//            }
//        });}
//        try {
//            reviewString = new MainActivity.ReviewMovieAsyncTask().execute(idMovie).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        reviewTextView.setText(reviewString);
//    }


