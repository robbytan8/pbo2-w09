package com.robby.entity;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Robby
 */
public class Team {

    @SerializedName("strTeam")
    private String name;
    @SerializedName("strAlternate")
    private String alias;
    @SerializedName("strStadium")
    private String stadium;

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getStadium() {
        return stadium;
    }

}
