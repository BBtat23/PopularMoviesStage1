package com.example.bea.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bea.popularmoviesstage1.data.Movie;
import com.example.bea.popularmoviesstage1.data.MovieContract;
import com.example.bea.popularmoviesstage1.data.MovieDbHelper;
import com.example.bea.popularmoviesstage1.utils.JSONUtils;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    private static final int URL_LOADER = 0;
    private List<Movie> movies = new ArrayList<>();
    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(this,movies,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this,MovieDetailActivity.class);
        String mOriginalTitle = movies.get(clickedItemIndex).getOriginalTitle();
        String mReleaseDate = movies.get(clickedItemIndex).getReleaseDate();
        String mRatingUser = String.valueOf(movies.get(clickedItemIndex).getRatingUser());
        String mOverView = movies.get(clickedItemIndex).getOverview();
        String mPosterPath = movies.get(clickedItemIndex).getPosterPath();
        String mIdMovie = movies.get(clickedItemIndex).getIdMovie();
//        Log.v("MainActivity","list_movie value:" + movies.size());
        intent.putExtra("Original Title",mOriginalTitle);
        intent.putExtra("Release Date",mReleaseDate);
        intent.putExtra("Rating User",mRatingUser);
        intent.putExtra("OverView", mOverView);
        intent.putExtra("PosterPath",mPosterPath);
        intent.putExtra("Id Movie",mIdMovie);

        startActivity(intent);
    }

    public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String apiSortByString = strings[0];

            URL urlApi = NetworkUtils.buildUrlApi(apiSortByString);

            try {
                String urlApiConnection = NetworkUtils.getResponseFromHttpUrl(urlApi);

                List<Movie> movieJson = JSONUtils.JSONUtilsMovie(urlApiConnection);

                return movieJson;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieObjectArrayList) {

            mAdapter.swapData(movieObjectArrayList);

        }
    }

    public static class VideoMovieAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String idMovie = strings[0];//This is the idMovie String that I will pass from JSONUtils

            URL urlVideoMovie = NetworkUtils.buildUrlVideoMovie(idMovie);//I build URL videoMovie
            try {
                String videoMovieConnection = NetworkUtils.getResponseFromHttpUrl(urlVideoMovie);//I get the connection with the API in the video's section
                ArrayList<String> videoMovieStringList = JSONUtils.movieVideoKey(videoMovieConnection);
                return videoMovieStringList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> movieVideoArrayList) {
            super.onPostExecute(movieVideoArrayList);

        }
    }

    public static class ReviewMovieAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPostExecute(ArrayList<String> reviewMovieArrayList) {
            super.onPostExecute(reviewMovieArrayList);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String idMovie = strings[0];

            URL urlReviewMovie = NetworkUtils.buildUrlReviewMovie(idMovie);
            try {
                String reviewMovieConnection =NetworkUtils.getResponseFromHttpUrl(urlReviewMovie);
                ArrayList<String> reviewMovieStringList = JSONUtils.movieReview(reviewMovieConnection);
                return reviewMovieStringList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void queryFavourite(){
        ContentResolver resolver = getContentResolver();
        List<Movie> movieArrayList = new ArrayList<Movie>();
        Cursor cursor = resolver.query(MovieContract.MovieEntry.CONTENT_URI, null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
           movieArrayList.add(new Movie(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE))
                   ,cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER))
                   ,cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEWS))
                   ,cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATING))
                   ,cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE))
                   ,cursor.getString(cursor.getColumnIndex(String.valueOf(MovieContract.MovieEntry.COLUMN_MOVIE_ID)))));
            cursor.moveToNext();
        }
        mAdapter.swapData(movieArrayList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.popular) {
            new MovieAsyncTask().execute("popular");
        } else if (itemId == R.id.topRated) {
            new MovieAsyncTask().execute("top_rated");
        }else if (itemId == R.id.favourites){
            queryFavourite();
        }
        return super.onOptionsItemSelected(item);
    }
}
