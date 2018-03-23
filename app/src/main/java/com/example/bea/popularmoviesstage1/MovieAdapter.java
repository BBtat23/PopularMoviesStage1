package com.example.bea.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bea.popularmoviesstage1.data.Movie;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static URL mPosterPathImage;
    private static String mOriginalPoster;
    private static List<Movie> mMovie;
    private Context context;

    public MovieAdapter(List<Movie> movies) {

        mMovie = movies;
        this.context = context;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
        Movie movieObject = mMovie.get(position);
//    String mPosterPathImageString = movieObject.getPosterPath();
//    mPosterPathImage = NetworkUtils.buildUrlPosterPath(mPosterPathImageString);
//        Picasso.with(context).load(String.valueOf(mPosterPathImage)).into(holder.imageViewPosterPath);
        mOriginalPoster = movieObject.getOriginalTitle();
        holder.mTextView.setText(mOriginalPoster);
    }

    @Override
    public int getItemCount() {

        return mMovie.size();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewPosterPath;
        public TextView mTextView;

        public MovieViewHolder(View itemView) {

            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text);
            imageViewPosterPath = (ImageView) itemView.findViewById(R.id.posterPath_imageview);
        }

        }
    public void swapData(List<Movie> movieObjectArrayList) {
        if (movieObjectArrayList == null || movieObjectArrayList.size() == 0)
            return;
        if (mMovie != null && mMovie.size() > 0)
            mMovie.clear();
        mMovie.addAll(movieObjectArrayList);
        notifyDataSetChanged();
    }
}
