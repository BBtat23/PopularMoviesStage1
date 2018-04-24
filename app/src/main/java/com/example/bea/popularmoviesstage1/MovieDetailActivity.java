package com.example.bea.popularmoviesstage1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bea.popularmoviesstage1.data.Movie;
import com.example.bea.popularmoviesstage1.data.MovieContract;
import com.example.bea.popularmoviesstage1.data.MovieDbHelper;
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
    ListView listViewReviewMovie;
    ArrayList<Movie> list_movie;
    String originalTitle;
    String releaseDate;
    String ratingUser;
    String overView;
    String posterPath;
    String idMovie;
    ArrayList<String> movieKeyStringList;
    ArrayList<String> movieKeyReviewList;
    String movieKeyString;
    String reviewString;
    Button favouriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mOriginalTitle = (TextView) findViewById(R.id.original_title);
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        mRatingUser = (TextView) findViewById(R.id.rating_user);
        mOverview = (TextView) findViewById(R.id.overview);
        imageViewPosterPath = (ImageView) findViewById(R.id.poster_path);
        favouriteButton = (Button) findViewById(R.id.favourite_button);
//        videoTextView = (TextView) findViewById(R.id.trailer1);
//        reviewTextView = (TextView) findViewById(R.id.reviews);

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

       listViewVideoMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               //Here I take the text from the listView depending on the position
               String videoString = (String)adapterView.getItemAtPosition(i);
               //I make an intent to launch youtube app and make the url
               Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/watch?v=" + videoString));
               //Launch the intent
               startActivity(youtubeIntent);
           }
       });
        try {
            movieKeyReviewList = new MainActivity.ReviewMovieAsyncTask().execute(idMovie).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String [] listReviews = new String[movieKeyReviewList.size()];
        for (int i = 0; i < movieKeyReviewList.size(); i++){
            reviewString = movieKeyReviewList.get(i);
            listReviews[i] = reviewString;
        }

        ArrayAdapter<String> adapterReview = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listReviews);
        listViewReviewMovie = (ListView) findViewById(R.id.reviews_list_view);
        listViewReviewMovie.setAdapter(adapterReview);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMovie();
            }
        });
    }

    private void insertMovie(){

        //Create database Helper
        MovieDbHelper movieDbHelper = new MovieDbHelper(this);

        //Gets the database in write mode
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        //Create a ContentValues object where column names are the keys,
        //and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, idMovie);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, originalTitle);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, posterPath);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING, ratingUser);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, releaseDate);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS,reviewString);

        //Insert a new row for favourite movie in the database, returning the ID of that new row.
        long newRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null,values);

        //Show a Toast message depending on whether or not the insertion was successful
        if (newRowId == -1){
            //If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this,"Error with saving pet",Toast.LENGTH_LONG).show();
        }else{
            //Otherwise, the insertion was successful and we can display a toast with the row ID
            Toast.makeText(this,"Pet saved with row id: " + newRowId, Toast.LENGTH_LONG).show();
        }
    }

//    public void watchVideoTrailer(String movieKey) {
//        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("https://www.youtube.com/watch?v=" + movieKey));
//        startActivity(youtubeIntent);
//    }
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
//}
