package com.anip.swamphacks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anip on 16/01/18.
 */

public class Reps {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    private String sponsor;
    @SerializedName("title")
    private String title;
    public Reps(String name, String image, String title){
        this.name = name;
        this.image = image;
        this.title = title;
    }

//    public Reps(String name, String image, String sponsor){
//        this.name = name;
//        this.image = image;
//        this.sponsor = sponsor;
//    }
    public Reps(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
