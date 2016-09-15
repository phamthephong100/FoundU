package com.example.foundu;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.foundu.api.APIClient;
import com.example.foundu.api.ApiResponse;
import com.example.foundu.manager.FoundULocationManager;
import com.example.foundu.manager.SessionManager;
import com.example.foundu.model.LatLngModel;
import com.example.foundu.model.User;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by duongthoai on 7/30/16.
 */

public class MainActivity extends FragmentActivity {
    private ListView listView;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoundULocationManager.getInstance().startLocationUpdate();
        new GetToken().execute();
        setContentView(R.layout.main_layout);
        listView = (ListView) findViewById(R.id.friend_list);
        SessionManager.getInstance(this).clearData();
//        for(int i = 0; i < 4; i++) {
            SessionManager.getInstance(this).addUser(new User("thanghc", new LatLngModel("10.782380", "106.689588")));
            SessionManager.getInstance(this).addUser(new User("0987788681", new LatLngModel("10.782664", "106.686756")));
//            SessionManager.getInstance(this).addUser(new User("345", new LatLngModel("10.783855", "106.691079")));
//            SessionManager.getInstance(this).addUser(new User("456", new LatLngModel("10.783960", "106.687968")));
//        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog = new ProgressDialog(MainActivity.this);
                if(!dialog.isShowing()) {
                    dialog.show();
                }

                getFriendLocation(SessionManager.getInstance(MainActivity.this).getUserList().get(position).getId());
            }
        });
        listView.setAdapter(new UserAdapter(this,SessionManager.getInstance(this).getUserList()));

    }

    private void getFriendLocation(String id) {
        Location location = FoundULocationManager.getInstance().getCurrentLocation();
        APIClient.getFriendLocation("0987788681", id, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), new APIClient.ResponseListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                if(dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                if(dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }

    class GetToken extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            return getDeviceToken();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            registerUser(s);
        }
    }
    private String getDeviceToken() {

        try {
            InstanceID instanceID = InstanceID.getInstance(this);

            String token = instanceID.getToken("81226496245",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("GCM", "GCM Registration Token: " + token);
            return token;

        } catch (Exception e) {
            Log.d("GCM", "Failed to complete token refresh", e);
            return "";
        }
    }

    private void registerUser(String token) {
        APIClient.registerUser("0987788681", APIClient.DEVICE_ID, token, new APIClient.ResponseListener() {
            @Override
            public void onSuccess(ApiResponse response) {
                if (response != null) {

                }
            }

            @Override
            public void onFailed(VolleyError error) {
                if (error != null) {

                }
            }
        });
    }






}
