package com.example.foundu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duongthoai on 7/30/16.
 */

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("location")
    LatLngModel location;

    public User(String id, LatLngModel model) {
        this.id = id;
        this.location = model;
    }

    public String getId() {
        return id;
    }

    public LatLngModel getLocation() {
        return location;
    }
}
