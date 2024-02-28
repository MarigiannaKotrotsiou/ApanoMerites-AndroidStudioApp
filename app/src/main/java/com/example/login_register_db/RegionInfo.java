package com.example.login_register_db;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegionInfo {

    private final Object imageIds;
    private final int regionNumber;
    private int imageId; // Resource ID of the associated ImageView
    private String difficulty;
    private String terrain;
    private String tasks;
    private String emblems;

    // Constructor with the ImageView resource ID
    public RegionInfo(int imageId, String difficulty, String terrain, String tasks, String emblems, int regionNumber) {
        this.imageIds = imageId;
        this.difficulty = difficulty;
        this.terrain = terrain;
        this.tasks = tasks;
        this.emblems = emblems;
        this.regionNumber = regionNumber;
    }

    // Προσθέστε τη μέθοδο getRegionNumber
    public int getRegionNumber() {
        return regionNumber;
    }
    // Getter for the ImageView resource ID
    public int getimageIds() {
        return imageId;
    }

    // Getters
    public String getDifficulty() {
        return difficulty;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getTasks() {
        return tasks;
    }

    public String getEmblems() {
        return emblems;
    }

}