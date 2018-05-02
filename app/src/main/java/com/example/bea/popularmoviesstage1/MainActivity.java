package com.example.bea.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
import com.example.bea.popularmoviesstage1.data.MovieProvider;
import com.example.bea.popularmoviesstage1.utils.JSONUtils;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Object> {

    private static final int URL_LOADER = 0;
    private List<Movie> movies = new ArrayList<>();
    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(URL_LOADER,null,MainActivity.this);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(this,movies,this);
        mRecyclerView.setAdapter(mAdapter);
        new MovieAsyncTask().execute("popular");
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

    @Override
    public Loader<Object> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mFavouriteMovie = null;

            @Override
            protected void onStartLoading() {
                if (mFavouriteMovie != null) {
                    deliverResult(mFavouriteMovie);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data) {
                mFavouriteMovie = data;
                super.deliverResult(data);
            }

        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

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
        }else if (itemId == R.id.favourite){

        }
        return super.onOptionsItemSelected(item);
    }
}
