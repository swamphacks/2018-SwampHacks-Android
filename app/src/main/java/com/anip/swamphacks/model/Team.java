package com.anip.swamphacks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anip on 20/01/18.
 */

public class Team {
    @SerializedName("team")
    private String team;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
