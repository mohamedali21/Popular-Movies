package com.example.mohamedali.movieapp.Adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mohamedali.movieapp.MovieModel;
import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.UI.FragmentDetail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mohamed Ali on 8/31/2016.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.MyViewHolder> {
        List<MovieModel> trailersNum;
        Context context;
        public TrailersAdapter(List<MovieModel> trailersNum, Context context){
            this.trailersNum =new ArrayList<>();
            this.trailersNum =trailersNum;
            this.context =context;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailers_row,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final MovieModel current = trailersNum.get(position);
            holder.textView.setText(current.getTrailer());
            Picasso.with(context).load("http://img.youtube.com/vi/" + trailersNum.get(position).getKey() + "/0.jpg")
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                      holder.playLogo.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                                  }
                              }
                              @Override
                              public void onBitmapFailed(Drawable errorDrawable) {
                                  Log.d("TAG", "FAILED");
                              }
                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {
                                  Log.d("TAG", "Prepare Load");
                              }
                          });
            holder.playLogo.setScaleType(ImageView.ScaleType.FIT_XY);
            Bundle bundle=new Bundle();
            FragmentDetail detailFragment = new FragmentDetail();
            detailFragment.setArguments(bundle);
            holder.playLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final MovieModel current = trailersNum.get(position);
                    String key = current.getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + key));
                    context.startActivity(intent);
                }
                });
        }

        @Override
        public int getItemCount()
        {
            return trailersNum.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView playLogo;
            public TextView textView;
            public MyViewHolder(View itemView) {
                super(itemView);
                textView=(TextView)itemView.findViewById(R.id.text_trailers);
                playLogo =(ImageView)itemView.findViewById(R.id.image_trailers);
            }
        }
}
