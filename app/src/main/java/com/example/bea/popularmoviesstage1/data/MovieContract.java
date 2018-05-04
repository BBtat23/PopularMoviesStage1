package com.example.bea.popularmoviesstage1.data;


import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MovieContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.bea.popularmoviesstage1";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.favourites/favourites/ is a valid path for
     * looking at favourites data. content://com.example.android.favourites/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_FAVOURITES = "favourites";

    public static final class MovieEntry implements BaseColumns{

        /** Name of database table for pets */
        public final static String TABLE_NAME = "favourites";

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVOURITES);

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Id of the movie.
         *
         * Type: Integer
         */
        public final static String COLUMN_MOVIE_ID = "id";

        /**
         * Title of the movie.
         *
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_TITLE ="title";

        /**
         * PosterPath of the movie.
         *
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_POSTER ="poster";

        /**
         * Reviews of the movie.
         *
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_OVERVIEWS ="overviews";

        /**
         * Release Date of the movie.
         *
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_RELEASE_DATE ="release";

        /**
         * Rating of the movie.
         *
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_RATING ="rating";
    }
}

