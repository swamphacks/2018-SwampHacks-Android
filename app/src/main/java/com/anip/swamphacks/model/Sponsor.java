package com.anip.swamphacks.model;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("logoLink")
    private String logoLink;
    public Sponsor(){}
    public Sponsor(String name, String tier, String description, String link, String location, String logoLink){
        this.name = name;
        this.tier = tier;
        this.description = description;
        this.link = link;
        this.location = location;
        this.logoLink = logoLink;
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

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

}
