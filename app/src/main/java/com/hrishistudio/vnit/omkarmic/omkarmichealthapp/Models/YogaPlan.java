package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.Models;

public class YogaPlan {

    private String id;
    private String title;
    private String age_group;
    private String image;
    private String level;
    private String duration;
    private String fee;

    public YogaPlan() {
    }

    public YogaPlan(String id, String title, String age_group, String image, String level, String duration, String fee) {
        this.id = id;
        this.title = title;
        this.age_group = age_group;
        this.image = image;
        this.level = level;
        this.duration = duration;
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
