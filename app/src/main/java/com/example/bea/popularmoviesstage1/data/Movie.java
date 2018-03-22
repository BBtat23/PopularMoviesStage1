package com.example.bea.popularmoviesstage1.data;


public class Movie {

    String mOriginalTitle;
    String mPosterPath;
    String mOverview;
    String mReleaseDate;
    Double mRatingUser;

    public Movie(String originalTitle, String posterPath, String overView, Double ratingUser, String releaseDate){
        this.mOriginalTitle = originalTitle;
        this.mPosterPath = posterPath;
        this.mOverview = overView;
        this.mRatingUser = ratingUser;
        this.mReleaseDate = releaseDate;
    }

    public String getOriginalTitle(){return mOriginalTitle;}
    public String getPosterPath(){return mPosterPath;}
    public String getOverview(){return mOverview;}
    public Double getRatingUser(){return mRatingUser;}
    public String getReleaseDate(){return mReleaseDate;}
}
