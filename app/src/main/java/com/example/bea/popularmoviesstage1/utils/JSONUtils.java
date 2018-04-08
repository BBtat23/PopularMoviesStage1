

public class JSONUtils {

    public static List<Movie> JSONUtilsMovie(String jsonMovie) throws JSONException {
        String originalTitle = null;
        String posterPath = null;
        String overView = null;
        Double ratingUser = null;
        String releaseDate = null;
        String idMovieString = null;


        JSONObject jsonObjectMovie = new JSONObject(jsonMovie);
        JSONArray jsonArrayMovie = jsonObjectMovie.getJSONArray("results");
        Movie objectMovie = null;
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArrayMovie.length(); i++) {
            JSONObject movieObject = jsonArrayMovie.getJSONObject(i);
            originalTitle = movieObject.getString("original_title");
            posterPath = movieObject.getString("poster_path");
            overView = movieObject.getString("overview");
            ratingUser = movieObject.getDouble("vote_average");
            releaseDate = movieObject.getString("release_date");
            int idMovie = movieObject.getInt("id");
            idMovieString = String.valueOf(idMovie);
            objectMovie = new Movie(originalTitle, posterPath, overView, ratingUser, releaseDate,idMovieString);
            movies.add(objectMovie);
        }
        return movies;
    }

    public static String movieVideoKey (String movieVideoString) throws JSONException {
        String videoKeyString = null;
        JSONObject jsonObjectIdMovie = new JSONObject(movieVideoString);
        JSONArray idMovieJsonArray = jsonObjectIdMovie.getJSONArray("results");
        for (int i = 0; i < idMovieJsonArray.length(); i++){
            JSONObject idMovieObject = idMovieJsonArray.getJSONObject(i);
            videoKeyString = idMovieObject.getString("key");
        }
        return videoKeyString;
    }
}
