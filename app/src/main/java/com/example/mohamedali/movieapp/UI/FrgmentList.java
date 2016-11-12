package com.example.mohamedali.movieapp.UI;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamedali.movieapp.API.FetchMovieTask;
import com.example.mohamedali.movieapp.Adapter.MovieAdapter;
import com.example.mohamedali.movieapp.Adapter.ReviewsAdapter;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.NetworkResponse;
import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.data.MovieHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Ali on 8/27/2016.
 */
public class FrgmentList extends Fragment {
    List<MovieModel> moviesList=new ArrayList<>();
    MovieAdapter movieAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movies_recyvlerView);
        createLayoutManger(recyclerView);
        if (isNetworkConnected())
            fetchMovies(recyclerView);
        else {
            MovieHelper movieHelper1=new MovieHelper(getActivity());
            MovieAdapter movieAdapter = new MovieAdapter(movieHelper1.getMovies("popular"), getActivity());
            recyclerView.setAdapter(movieAdapter);
        }



        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.movies_recyvlerView);
            createLayoutManger(recyclerView);
            if (isNetworkConnected())
                fetchMovies(recyclerView);
            else {
                MovieHelper movieHelper1=new MovieHelper(getActivity());
                MovieAdapter movieAdapter = new MovieAdapter(movieHelper1.getMovies("popular"), getActivity());
                recyclerView.setAdapter(movieAdapter);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("STATE_MOVEIS", (ArrayList<? extends Parcelable>) moviesList);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }
    private void fetchMovies(final RecyclerView recyclerView) {
        FetchMovieTask fetchMovieTask = new FetchMovieTask(getActivity());
        fetchMovieTask.setNetworkResponse(new NetworkResponse() {
            @Override
            public void OnSuccess(final List<MovieModel> mstring) {
                moviesList.clear();
                moviesList = mstring;
                movieAdapter = new MovieAdapter(moviesList, getActivity());
                recyclerView.setAdapter(movieAdapter);
            }
        });
        fetchMovieTask.execute("popular");
    }

    public void createLayoutManger(RecyclerView recyclerView){
        if (MainActivity.mTwoPane) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }
            }
    }
}
