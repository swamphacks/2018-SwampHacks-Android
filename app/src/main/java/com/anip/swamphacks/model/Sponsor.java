package com.anip.swamphacks.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by anip on 13/01/18.
 */

public class Sponsor {

    @SerializedName("name")
    private String name;
    @SerializedName("tier")
    private String tier;
    @SerializedName("description")
    private String description;
    @SerializedName("link")
    private String link;
    @SerializedName("location")
    private String location;
    @SerializedName("logo")
    private String logo;
    public Sponsor(){}
    public Sponsor(String name, String tier, String description, String link, String location, String logo){
        this.name = name;
        this.tier = tier;
        this.description = description;
        this.link = link;
        this.location = location;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
