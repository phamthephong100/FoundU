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
}
