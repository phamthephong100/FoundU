package com.example.foundu.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foundu.model.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by duongthoai on 7/30/16.
 */

public class SessionManager {
    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private static String KEY_USER_LIST = "user_list";

    // Constructor
    public SessionManager(Context context) {
        mContext = context;
        pref = mContext.getSharedPreferences("FoundU", Context.MODE_PRIVATE);
        editor = pref.edit();
        gson  = new Gson();
    }

    public void saveUserInfo(List<User> users) {
        editor.putString(KEY_USER_LIST, gson.toJson(users));
        editor.commit();
    }

    public List<User> getUserList() {
         return null;
    }


}
