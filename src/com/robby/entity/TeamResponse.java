package com.robby.entity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author Robby
 */
public class TeamResponse {

    @SerializedName("teams")
    private ArrayList<Team> teams;

    public ArrayList<Team> getTeams() {
        return teams;
    }

}
