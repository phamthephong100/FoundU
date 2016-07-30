package com.example.foundu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duongthoai on 7/30/16.
 */

public class LatLngModel {
    @SerializedName("lat")
    private String lat;

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @SerializedName("lon")
    private String lng;
}
