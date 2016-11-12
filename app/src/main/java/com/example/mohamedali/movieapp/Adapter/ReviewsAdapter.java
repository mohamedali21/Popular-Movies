package com.example.mohamedali.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.UI.FragmentDetail;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Ali on 8/31/2016.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    List<MovieModel> reviewsNum;
    Context context;
    public ReviewsAdapter(List<MovieModel> trailersNum, Context context){
        this.reviewsNum =new ArrayList<>();
        this.reviewsNum =trailersNum;
        this.context =context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MovieModel current= reviewsNum.get(position);
        holder.textView.setText(current.getContent());
        holder.textView1.setText(current.getUrlContent());
        holder.textView2.setText(current.getAuthor());
        Bundle bundle=new Bundle();
        FragmentDetail detailFragment = new FragmentDetail();
        detailFragment.setArguments(bundle);
        holder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MovieModel current= reviewsNum.get(position);
                String url = current.getUrlContent();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return reviewsNum.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,textView1,textView2;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.content);
            textView1 =(TextView)itemView.findViewById(R.id.url_content);
            textView2=(TextView)itemView.findViewById(R.id.author);
        }
    }
}
