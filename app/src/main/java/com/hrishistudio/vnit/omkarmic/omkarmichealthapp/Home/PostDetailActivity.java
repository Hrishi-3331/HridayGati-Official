package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models.Post;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {

    private RoundedImageView user_image;
    private TextView title_view;
    private TextView poster_name_view;
    private ImageView post_image_view;
    private TextView post_text_view;
    private DatabaseReference mRef;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        String post_id = intent.getStringExtra("post_id");
        mRef = FirebaseDatabase.getInstance().getReference().child("posts").child(post_id);

        user_image = (RoundedImageView)findViewById(R.id.post_detail_user_image);
        title_view = (TextView)findViewById(R.id.post_detail_title);
        poster_name_view = (TextView)findViewById(R.id.poster_details_view);
        post_image_view = (ImageView)findViewById(R.id.post_detail_image);
        post_text_view = (TextView)findViewById(R.id.post_detail_text);

        setPost();
    }

    private void setPost() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                  post = (Post) dataSnapshot.getValue(Post.class);

                  title_view.setText(post.getPost_title());
                  poster_name_view.setText(post.getPoster_detail());
                  post_text_view.setText(post.getPost_text());
                  try{
                      Picasso.get().load(post.getPoster_image()).into(user_image);
                  }
                  catch (Exception e){e.printStackTrace();}

                  try{
                      Picasso.get().load(post.getPost_image()).into(post_image_view);
                  }
                  catch (Exception e){e.printStackTrace();}
                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
