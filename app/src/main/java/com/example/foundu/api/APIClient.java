package com.example.foundu.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foundu.FoundUApplication;
import com.example.foundu.model.BaseModel;
import com.example.foundu.model.FriendLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by duongthoai on 7/30/16.
 */

public class APIClient {
    public static String DEVICE_ID = "0";
    public static String BASE_URL = "http://192.168.3.1";
    public static String REGISTER_NEW_USER = BASE_URL + "/api/register?phone=%s&device=%s&token=%s";
    public static String REQUEST_FRIEND_LOCATION = BASE_URL + "/api/request/?userid=%s&friendid=%s&lat=%s&lon=%s";
    public static String FRIEND_ACCEPT = BASE_URL + "/api/accept";
    public static String REQUEST_ADS = BASE_URL + "/api/ads";
    private static String STATUS_CODE_OK = "true";

    public interface ResponseListener {
        void onSuccess(ApiResponse response);

        void onFailed(VolleyError error);
    }

    public static void registerUser(String phone, String device, String token_device, ResponseListener responseListener) {
        final String url = String.format(REGISTER_NEW_USER, phone, device, token_device);

        Type type = new TypeToken<ApiResponse<BaseModel>>() {
        }.getType();
        makeStringRequest(Request.Method.GET, url, type, responseListener);
    }

    public static void getFriendLocation(String userid, String friendId, String lat, String lng, ResponseListener responseListener) {
        final String url = String.format(REGISTER_NEW_USER, userid, friendId, lat, lng, responseListener);

        Type type = new TypeToken<ApiResponse<FriendLocation>>() {
        }.getType();
        makeStringRequest(Request.Method.GET, url, type, responseListener);
    }

    public static void requestFriendLocation() {

    }


    private static void makeStringRequest(int requestMethod, String url, final Type resultType, final ResponseListener responseListener) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ApiResponse responseObj = gson.fromJson(response, resultType);
                if (responseObj.getStatus().equals(STATUS_CODE_OK)) {
                    responseListener.onSuccess(responseObj);
                } else {
                    if (responseObj.getStatus() != null) {
                    }
                    if (responseObj.getMessage() != null) {
                        VolleyError error = new VolleyError(responseObj.getMessage());
                        responseListener.onFailed(error);
                    }
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onFailed(error);
            }
        };
        StringRequest request = new StringRequest(requestMethod, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();

            }
        };
        Volley.newRequestQueue(FoundUApplication.getInstance()).add(request);
    }

}
