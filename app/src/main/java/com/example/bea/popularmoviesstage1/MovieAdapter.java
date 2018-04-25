import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static String mPosterPathImage;
    private static String mOriginalPoster;
    private static List<Movie> mMovie;
    private Context context;
    final private ListItemClickListener mOnclicklistener;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(Context context, List<Movie> movies, ListItemClickListener listener) {

        mOnclicklistener = listener;
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
        String mPosterPathImageString = movieObject.getPosterPath();
        mPosterPathImage = NetworkUtils.buildUrlPosterPath(mPosterPathImageString).toString();
        Log.v("MovieAdapter.java", "poster_path: " + context);
        Picasso.with(context).load(mPosterPathImage).into(holder.imageViewPosterPath);
    }

    @Override
    public int getItemCount() {
//        Log.v("MovieAdapter.java", "movie list size: " + mMovie.size());
        return mMovie.size();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageViewPosterPath;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageViewPosterPath = (ImageView) itemView.findViewById(R.id.posterPath_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnclicklistener.onListItemClick(clickedPosition);
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
