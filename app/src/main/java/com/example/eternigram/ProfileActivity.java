package com.example.eternigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.elevation.SurfaceColors;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {

    // Begin the transaction
    FragmentManager ft = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackgroundColor(SurfaceColors.SURFACE_2.getColor(this));
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_capture:
                        intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.action_home:
                        intent = new Intent(ProfileActivity.this, FeedActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                }
//                ft.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

    }
}