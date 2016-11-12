package com.example.mohamedali.movieapp.UI;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mohamedali.movieapp.API.FetchReviewsTask;
import com.example.mohamedali.movieapp.API.FetchTrailersTask;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.NetworkResponse;
import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.Adapter.ReviewsAdapter;
import com.example.mohamedali.movieapp.Adapter.TrailersAdapter;
import com.example.mohamedali.movieapp.data.MovieHelper;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Mohamed Ali on 8/31/2016.
 */
public class FragmentDetail extends Fragment {
    TextView movieName, movieRate, movieReleaseDate, movieOverview;
    ImageView imageView;
    CheckBox checkBoxFavorite;
    String name,releaseDate,overview,url;
    double rate;
    int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            name = bundle.getString("name");
            rate = bundle.getDouble("rate");
            releaseDate = bundle.getString("releaseDate");
            overview = bundle.getString("overview");
            url = bundle.getString("url");
            id = bundle.getInt("id");
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
         movieName = (TextView)view. findViewById(R.id.movie_name);
         movieName.setText(name);
         movieRate = (TextView)view. findViewById(R.id.rate);
         movieRate.setText(Double.toString(rate));
         movieReleaseDate = (TextView)view. findViewById(R.id.date);
         movieReleaseDate.setText(releaseDate);
         movieOverview = (TextView)view. findViewById(R.id.overview);
         movieOverview.setText(overview);
         imageView=(ImageView)view.findViewById(R.id.movie_image);
         Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+ url)
                .error(R.drawable.error).placeholder(R.drawable.loading).into(imageView);
         final MovieHelper movieHelper = new MovieHelper(getActivity());
         Boolean ceckInDB=movieHelper.checkMovie(id);
         checkBoxFavorite =(CheckBox)view.findViewById(R.id.checkBox);
         checkBoxFavorite.setChecked(ceckInDB);
         checkBoxFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox c=(CheckBox)view;
                if(c.isChecked()) {
                    SQLiteDatabase sql = movieHelper.getWritableDatabase();
                    if (sql.isOpen()) {
                        Log.d("DB", "OPEN");
                        MovieModel movieModel = new MovieModel();
                        movieModel.setUrl(url);
                        movieModel.setName(name);
                        movieModel.setReleaseDate(releaseDate);
                        movieModel.setOverview(overview);
                        movieModel.setRate(rate);
                        movieModel.setId(id);
                        Log.d("VALUE", movieHelper.insertMovies(movieModel,"favorite") + "");
                    } else
                        Log.d("DB", "Closed");

                    Toast.makeText(getActivity(),"Add to favorite",Toast.LENGTH_SHORT).show();
                }
                else {
                    movieHelper.deleteMovieFromFavorite(id);
                    Toast.makeText(getActivity(),"Remove from favorite",Toast.LENGTH_SHORT).show();
                }
            }
        });
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.trailers_recyvlerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (isNetworkConnected()) {
            final FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity());
            //this interface (NetworkResponse) implementation as a listener to parse json
            fetchTrailersTask.setNetworkResponse(new NetworkResponse() {
                @Override
                public void OnSuccess(final List<MovieModel> mstring) {
                    TrailersAdapter trailresAdapter = new TrailersAdapter(mstring, getActivity());
                    recyclerView.setAdapter(trailresAdapter);
                    if (mstring.isEmpty()){
                        LinearLayout myLayout = (LinearLayout)getActivity(). findViewById(R.id.trailer_layout);
                        myLayout.setVisibility(View.GONE);
                        new AlertDialog.Builder(getActivity()).setTitle("Alert").setMessage("No trailers!")
                                .setIcon(R.drawable.icon).setNeutralButton("Close", null).show();
                    }
                }

            });
            fetchTrailersTask.execute(Integer.toString(id));
        }
        else{
            MovieHelper movieHelper1=new MovieHelper(getActivity());
            TrailersAdapter trailresAdapter = new TrailersAdapter(movieHelper1.getTrailers(id), getActivity());
            recyclerView.setAdapter(trailresAdapter);
        }
        final RecyclerView recyclerView1 = (RecyclerView)view.findViewById(R.id.content_recyvlerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        final FetchReviewsTask fetchReviewsTask = new FetchReviewsTask(getActivity());
        if (isNetworkConnected()) {
            //this interface (NetworkResponse) implementation as a listener to parse json
            fetchReviewsTask.setNetworkResponse(new NetworkResponse() {
                @Override
                public void OnSuccess(final List<MovieModel> mstring) {
                    ReviewsAdapter reviewsAdapter = new ReviewsAdapter(mstring, getActivity());
                    recyclerView1.setAdapter(reviewsAdapter);
                    if (mstring.isEmpty()){
                        LinearLayout myLayout = (LinearLayout)getActivity(). findViewById(R.id.review_layout);
                        myLayout.setVisibility(View.GONE);
                        new AlertDialog.Builder(getActivity()).setTitle("Alert").setMessage("No reviews!").setIcon(R.drawable.icon).setNeutralButton("Close", null).show();
                    }
                }

            });
            fetchReviewsTask.execute(Integer.toString(id));
        }
        else{
            MovieHelper movieHelper1=new MovieHelper(getActivity());
            ReviewsAdapter trailresAdapter = new ReviewsAdapter(movieHelper1.getReviews(id), getActivity());
            recyclerView1.setAdapter(trailresAdapter);
        }
        return view;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
