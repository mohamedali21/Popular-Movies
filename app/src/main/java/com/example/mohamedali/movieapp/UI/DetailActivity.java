package com.example.mohamedali.movieapp.UI;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohamedali.movieapp.R;
import com.example.mohamedali.movieapp.UI.FragmentDetail;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = this.getIntent();
        FragmentDetail detailFragment=new FragmentDetail();
        FragmentManager fragmentManager=getFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putString("name",  intent.getExtras().getString("name"));
        bundle.putDouble("rate", intent.getExtras().getDouble("rate"));
        bundle.putString("url",intent.getExtras().getString("url"));
        bundle.putString("releaseDate",intent.getExtras().getString("releaseDate"));
        bundle.putString("overview",intent.getExtras().getString("overview"));
        bundle.putInt("id",intent.getExtras().getInt("id"));
        detailFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_container,detailFragment);
        fragmentTransaction.commit();
    }
}
