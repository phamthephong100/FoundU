package com.example.foundu.api;

import com.google.gson.annotations.SerializedName;
/**
 * Created by duongthoai on 7/30/16.
 */
public class ApiResponse<T> {
    @SerializedName("status")
    protected String status;

    @SerializedName("message")
    protected String message;

    @SerializedName("result")
    protected T result;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public T getResult() {
        return result;
    }

}
