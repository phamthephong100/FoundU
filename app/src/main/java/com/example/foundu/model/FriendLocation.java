package com.example.foundu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duongthoai on 7/30/16.
 */

public class FriendLocation extends BaseModel {
    public LatLngModel getLocation() {
        return location;
    }

    @SerializedName("lat")

    private LatLngModel location;
}
