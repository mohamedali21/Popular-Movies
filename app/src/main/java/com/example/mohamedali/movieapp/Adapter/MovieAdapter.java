package com.example.mohamedali.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.UI.FragmentDetail;
import com.example.mohamedali.movieapp.UI.DetailActivity;
import com.example.mohamedali.movieapp.UI.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Ali on 8/19/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    public List<MovieModel> movieModels;
    Context context;

    public MovieAdapter(List<MovieModel> movieModels, Context context) {
        this.movieModels = new ArrayList<>();
        this.movieModels = movieModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MovieModel current = movieModels.get(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + current.getUrl())
                .placeholder(R.drawable.loading)
                .into(holder.movieImage);
        holder.movieImage.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mTwoPane) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", movieModels.get(position).getName());
                    bundle.putDouble("rate", movieModels.get(position).getRate());
                    bundle.putString("url", movieModels.get(position).getUrl());
                    bundle.putString("releaseDate", movieModels.get(position).getReleaseDate());
                    bundle.putString("overview", movieModels.get(position).getOverview());
                    bundle.putInt("id", movieModels.get(position).getId());
                    FragmentDetail detailFragment = new FragmentDetail();
                    detailFragment.setArguments(bundle);
                    ((MainActivity) context).getFragmentManager().beginTransaction().replace(R.id.movie_detail_container, detailFragment).commit();
                }
                else {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("name", movieModels.get(position).getName());
                    intent.putExtra("rate", movieModels.get(position).getRate());
                    intent.putExtra("url", movieModels.get(position).getUrl());
                    intent.putExtra("releaseDate", movieModels.get(position).getReleaseDate());
                    intent.putExtra("overview", movieModels.get(position).getOverview());
                    intent.putExtra("id", movieModels.get(position).getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}