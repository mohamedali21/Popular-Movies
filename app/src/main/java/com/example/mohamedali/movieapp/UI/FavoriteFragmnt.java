package com.example.mohamedali.movieapp.UI;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamedali.movieapp.Adapter.MovieAdapter;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.data.MovieHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Ali on 9/17/2016.
 */
public class FavoriteFragmnt extends Fragment {
    RecyclerView recyclerView;
    List<MovieModel>movieModels=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.movies_recyvlerView);
        displayFavorite(recyclerView);
        return view;
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
            displayFavorite(recyclerView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void displayFavorite(RecyclerView recyclerView){
        if (MainActivity.mTwoPane)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else {
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }
        }
        MovieHelper m = new MovieHelper(getActivity());
        movieModels=m.getMovies("favorite");
        MovieAdapter movieAdapter=new MovieAdapter(movieModels,getActivity());
        recyclerView.setAdapter(movieAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        displayFavorite(recyclerView);
    }
}
