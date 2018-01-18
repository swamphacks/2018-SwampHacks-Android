package com.anip.swamphacks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anip on 16/01/18.
 */

public class User {
    @SerializedName("email")
    private String email;
    @SerializedName("team")
    private String team;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
