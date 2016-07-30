package com.example.foundu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by duongthoai on 7/30/16.
 */

public class BaseModel implements Serializable {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
