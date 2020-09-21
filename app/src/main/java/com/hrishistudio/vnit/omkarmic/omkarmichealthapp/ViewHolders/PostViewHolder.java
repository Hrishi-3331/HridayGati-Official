package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Home.PostDetailActivity;
import com.hrishistudio.vnit.omkarmic.omkarmichealthapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class PostViewHolder extends RecyclerView.ViewHolder{

    private View mView;
    private TextView post_title_view;
    private TextView poster_detail_view;
    private TextView post_text_view;
    private ImageView post_image_view;
    private RoundedImageView poster_image_view;
    private ImageButton like_button;
    private ImageButton share_button;
    private ImageButton save_button;
    private Button details_button;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        post_title_view = (TextView)mView.findViewById(R.id.post_title);
        poster_detail_view = (TextView)mView.findViewById(R.id.poster_details);
        post_text_view = (TextView)mView.findViewById(R.id.post_text);

        post_image_view = (ImageView)mView.findViewById(R.id.post_image);
        poster_image_view = (RoundedImageView)mView.findViewById(R.id.post_user_image);

        like_button = (ImageButton)mView.findViewById(R.id.btn_like);
        share_button = (ImageButton)mView.findViewById(R.id.btn_share);
        save_button = (ImageButton)mView.findViewById(R.id.btn_save);

        details_button = (Button)mView.findViewById(R.id.details_button);
    }

    public void setPostView(String tile, String details, String text){
        post_title_view.setText(tile);
        post_text_view.setText(text);
        poster_detail_view.setText(details);
    }

    public void setImages(String postImage, String posterImage){
        try{
            Picasso.get().load(postImage).into(post_image_view);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            Picasso.get().load(posterImage).into(poster_image_view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setListners(final Context context, final String ref){
        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("post_id", ref);
                context.startActivity(intent);
            }
        });
    }
}
