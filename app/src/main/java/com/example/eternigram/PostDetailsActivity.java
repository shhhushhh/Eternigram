package com.example.eternigram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eternigram.models.Post;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    Context context;
    Post post;
    private ImageView ivProfileImg;
    private TextView tvUsername;
    private TextView tvTimeStamp;
    private ImageView ivPostImg;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivProfileImg = findViewById(R.id.ivProfileImg);
        tvUsername = findViewById(R.id.tvUsername);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);
        ivPostImg = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        // Implement profile image
        tvUsername.setText(post.getUser().getUsername());
        tvTimeStamp.setText(post.getRelativeTimeAgo(post.getCreatedAt().toString()));
        if (post.getImage() != null) {
            ivPostImg.setVisibility(View.VISIBLE);
//            Glide.with(context).load(post.getImage().getUrl()).into(ivPostImg);
        } else {
            ivPostImg.setVisibility(View.GONE);
        }
        tvDescription.setText(post.getDescription());
    }
}