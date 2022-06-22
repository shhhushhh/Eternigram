package com.example.eternigram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eternigram.models.Post;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    Post post;
    private TextView tvUsername;
    private TextView tvTimeStamp;
    private TextView tvDescription;
    private TextView tvLikes;
    private ImageView ivPostImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsername = findViewById(R.id.tvUsername);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);
        tvDescription = findViewById(R.id.tvDescription);
        tvLikes = findViewById(R.id.tvLikes);
        ivPostImg = findViewById(R.id.ivPostImg);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        tvUsername.setText(post.getUser().getUsername());
        tvTimeStamp.setText(post.getRelativeTimeAgo(post.getCreatedAt().toString()));
        if (post.getImage() != null) {
            Glide.with(this).load(post.getImage().getUrl()).into(ivPostImg);
            ivPostImg.setVisibility(View.VISIBLE);
        } else {
            ivPostImg.setVisibility(View.GONE);
        }
        tvDescription.setText(post.getDescription());
        String likes;
        if (post.getLikes().equals("1")) {
            likes = "Liked by " + post.getLikes() + " user";
        } else {
            likes = "Liked by " + post.getLikes() + " users";
        }
        tvLikes.setText(likes);
    }
}