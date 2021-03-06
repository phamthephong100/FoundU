package com.example.foundu.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foundu.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
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
    private static SessionManager sessionManager;

    public static SessionManager getInstance(Context ctx) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(ctx);
        }
        return sessionManager;
    }

    // Constructor
    private SessionManager(Context context) {
        mContext = context;
        pref = mContext.getSharedPreferences("FoundU", Context.MODE_PRIVATE);
        editor = pref.edit();
        gson = new Gson();
    }

    public void saveUserInfo(List<User> users) {
        editor.putString(KEY_USER_LIST, gson.toJson(users));
        editor.commit();
    }

    public List<User> getUserList() {
        List<User> userList = gson.fromJson(pref.getString(KEY_USER_LIST, null),
                new TypeToken<List<User>>() {
                }.getType());
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    public void addUser(User user) {
        List<User> users = getUserList();
        users.add(user);
        saveUserInfo(users);

    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }


}
