package com.example.bea.popularmoviesstage1.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.bea.popularmoviesstage1.utils.JSONUtils;
import com.example.bea.popularmoviesstage1.utils.NetworkUtils;

import java.util.List;

import static android.content.ContentUris.*;

public class MovieProvider extends ContentProvider{

    /**Tag for the log messages*/
    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    /**Database helper object*/
    private MovieDbHelper movieDbHelper;

    /**URI matcher code for the content URI for the favourites table*/
    private static final int FAVOURITES = 100;

    /**URI matcher code for the content URI for a single favourite movie in the favourites table*/
    private static final int FAVOURITES_ID = 101;

    static final String _ID = "_id";

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // Add 2 content URIs to URI matcher
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_FAVOURITES,FAVOURITES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_FAVOURITES,FAVOURITES_ID);
    }
    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());

        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        //Get readable database
        SQLiteDatabase database = movieDbHelper.getReadableDatabase();

        //This cursor will hold the result of the query
        Cursor cursor = null;

        //Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match){
            case FAVOURITES:
                // For the FAVOURITES code, query the favourites table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the favourites table.
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            case FAVOURITES_ID:
                // For the FAVOURITES_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.popularmoviesstage1/favourites/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(parseId(uri))};

                // This will perform a query on the favourites table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        //Get readable database
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVOURITES:
               long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
               if (id > 0){
                   Uri newUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                   getContext().getContentResolver().notifyChange(newUri,null);
                   return newUri;
               }
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    @Override
    public int delete(Uri uri,
                      String selection, String[] selectionArgs) {
        int count = 0;
        //Get readable database
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVOURITES:
                count = database.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case FAVOURITES_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                count = database.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        //Get readable database
        SQLiteDatabase database = movieDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVOURITES:
                count = database.update(MovieContract.MovieEntry.TABLE_NAME,values,selection,selectionArgs);
                break;

            case FAVOURITES_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                count = database.update(MovieContract.MovieEntry.TABLE_NAME,values,selection,selectionArgs);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
