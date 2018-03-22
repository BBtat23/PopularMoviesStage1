package com.example.bea.popularmoviesstage1;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bea.popularmoviesstage1.data.Movie;
import com.example.bea.popularmoviesstage1.utils.JSONUtils;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies = new ArrayList<>();
    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(movies);
        mRecyclerView.setAdapter(mAdapter);
        new MovieAsyncTask().execute("popular");
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

                    mAdapter = new MovieAdapter(movieObjectArrayList);
                 }
            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.popular){
            new MovieAsyncTask().execute("popular");
        }
        else if (itemId == R.id.topRated){
            new MovieAsyncTask().execute("top_rated");
        }
        return super.onOptionsItemSelected(item);
    }
}