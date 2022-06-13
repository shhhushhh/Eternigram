package com.example.eternigram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eternigram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button bLogout;
    private EditText etDescription;
    private Button bTakePhoto;
    private ImageView ivPhoto;
    private Button bSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogout = findViewById(R.id.bLogout);
        etDescription = findViewById(R.id.etDescription);
        bTakePhoto = findViewById(R.id.bTakePicture);
        ivPhoto = findViewById(R.id.ivTakenPhoto);
        bSubmit = findViewById(R.id.bSubmit);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        queryPosts();

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    savePost(description, currentUser);
                }
            }
        });
    }

    private void savePost(String description, ParseUser currentUser) {
        Post post = new Post();
        post.setDescription(description);
//        post.setImage();
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("post_saved_failed", "Post has not been saved", e);
                    Toast.makeText(MainActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("post_saved_succeed", "Post has been saved!");
                    etDescription.setText("");
                }
            }
        });
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e("query_posts_error", "Issue with getting posts", e);
                } else {
                    for (Post post : posts) {
                        Log.i("list_of_posts", "Post: " + post.getDescription() + ", username: " + post.getUser());
                    }
                }
            }
        });
    }
}