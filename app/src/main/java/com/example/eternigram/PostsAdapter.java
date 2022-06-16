package com.example.eternigram;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.eternigram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivProfile;
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivLike;
        private TextView tvLikes;
        private TextView tvTimeStamp;

        public ViewHolder(@NonNull View itemView) throws NullPointerException {
            super(itemView);
            itemView.setOnClickListener(this);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivLike = itemView.findViewById(R.id.ivLike);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, this won't work if the class is static
                        Post post = posts.get(position);
//                        Log.i("post_likes", post.getLikes());
                        int newLikes = Integer.parseInt(post.getLikes());
                        if (!post.getBoolean("liked")) {
                            // if has not been liked, change heart to red
                            ivLike.setImageResource(R.drawable.ig_heart_red);
                            // Add one to the post's likes
                            newLikes++;
                            post.put("liked", true);
                        } else {
                            // otherwise, change back to heart with black outline
                            ivLike.setImageResource(R.drawable.ufi_heart);
                            if (newLikes > 0) {
                                newLikes--;
                            }
                            post.put("liked", false);
                        }
                        post.setLikes(String.valueOf(newLikes));
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.e("likes_saved_failed", "Likes count has not been saved", e);
                                } else {
                                    Log.i("likes_saved_succeed", "Likes count has been saved!");
                                }
                            }
                        });
                        tvLikes.setText(post.getLikes());
                    }
                }
            });
        }

        public void bind(Post post) {
            ParseFile profile = (ParseFile) post.getUser().get("profilePic");
            Uri uri = Uri.parse(profile.getUrl());
            Glide.with(context).load(uri).centerCrop().transform(new RoundedCorners(360)).into(ivProfile);
            tvUsername.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            if (post.getImage() != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
                ivImage.setVisibility(View.VISIBLE);
            } else {
                ivImage.setVisibility(View.GONE);
            }
            if (post.getBoolean("liked")) {
                ivLike.setImageResource(R.drawable.ig_heart_red);
            } else {
                ivLike.setImageResource(R.drawable.ufi_heart);
            }
            tvLikes.setText(post.getLikes());
            tvTimeStamp.setText(post.getRelativeTimeAgo(post.getCreatedAt().toString()));
        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = posts.get(position);
                Log.i("post_user", post.getUser().getUsername());
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                Log.i("post_user_2", post.getUser().getUsername());
                // show the activity
                context.startActivity(intent);
                Log.i("adapter_to_details", "Did intent sending fail?");
            }
        }
    }
}
