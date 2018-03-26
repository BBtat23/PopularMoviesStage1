package com.example.bea.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bea.popularmoviesstage1.data.Movie;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    TextView mOriginalTitle;
    TextView mReleaseDate;
    TextView mOverview;
    TextView mRatingUser;
    ImageView imageViewPosterPath;
    ArrayList<Movie> list_movie;
    String originalTitle;
    String releaseDate;
    String ratingUser;
    String overView;
    String posterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mOriginalTitle = (TextView) findViewById(R.id.original_title);
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        mRatingUser = (TextView) findViewById(R.id.rating_user);
        mOverview = (TextView) findViewById(R.id.overview);
        imageViewPosterPath = (ImageView) findViewById(R.id.poster_path);

//        list_movie = (ArrayList<Movie>) getIntent().getSerializableExtra("movieObject");
//        Log.v("MovieDetailActivity", "OriginalTitle: " + originalTitle);
        Intent movieDetailIntent = getIntent();
        originalTitle = movieDetailIntent.getStringExtra("Original Title");
        releaseDate = movieDetailIntent.getStringExtra("Release Date");
        ratingUser = movieDetailIntent.getStringExtra("Rating User");
        overView = movieDetailIntent.getStringExtra("OverView");
        posterPath = movieDetailIntent.getStringExtra("PosterPath");
        String posterPathImage = NetworkUtils.buildUrlPosterPath(posterPath).toString();

        mOriginalTitle.setText(originalTitle);
        mReleaseDate.setText(releaseDate);
        mRatingUser.setText(ratingUser);
        mOverview.setText(overView);
        Picasso.with(this).load(posterPathImage).into(imageViewPosterPath);
    }

}
