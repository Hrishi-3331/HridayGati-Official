package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models;

public class Post {

    private String post_title;
    private String poster_detail;
    private String poster_image;
    private String post_image;
    private String post_text;

    public Post() {
    }

    public Post(String post_title, String poster_detail, String poster_image, String post_image, String post_text) {
        this.post_title = post_title;
        this.poster_detail = poster_detail;
        this.poster_image = poster_image;
        this.post_image = post_image;
        this.post_text = post_text;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPoster_detail() {
        return poster_detail;
    }

    public void setPoster_detail(String poster_detail) {
        this.poster_detail = poster_detail;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }
}
