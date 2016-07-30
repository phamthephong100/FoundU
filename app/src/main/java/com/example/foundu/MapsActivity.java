package com.example.foundu;
/**
 * Created by duongthoai on 7/30/16.
 */

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.foundu.api.APIClient;
import com.example.foundu.api.ApiResponse;
import com.example.foundu.manager.FoundULocationManager;
import com.example.foundu.routing.AbstractRouting;
import com.example.foundu.routing.Route;
import com.example.foundu.routing.RouteException;
import com.example.foundu.routing.Routing;
import com.example.foundu.routing.RoutingListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    private static final int DEFAULT_MAP_ZOOM_LEVEL = 14;
    private static final int MAP_POLYLINE_WIDTH = 15;
    public static final String MAP_POLYLINE_COLOR = "#00b3fd";
    private GoogleMap mMap;
    private Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        FoundULocationManager.getInstance().startLocationUpdate();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        getFriendLocation();
        new GetToken().execute();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        Location location = FoundULocationManager.getInstance().getCurrentLocation();
        if (location != null) {
            moveMapToLocation(location);
            addMarker(location);
        }
//        LatLng currentLngLng = new LatLng(location.getLatitude(), location.getLongitude());

//        mMap.addMarker(new MarkerOptions().position(currentLngLng).title("You are here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLngLng));
    }

    private void moveMapToLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM_LEVEL);
        mMap.animateCamera(cameraUpdate);
    }

    private void addMarker(Location location) {
        MarkerOptions markerOption = new MarkerOptions().position(
                new com.google.android.gms.maps.model.LatLng(location.getLatitude(), location.getLongitude())).title("You're here");
        mMap.addMarker(markerOption);
    }

    /**
     * get data to draw polyline
     *
     * @param start
     * @param end
     */
    public void updateDirections(LatLng start, LatLng end) {
        if (start == null || end == null) {
            return;
        }
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(start, end)
                .build();
        routing.execute();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        Route item = route.get(0);
        //draw line here
        drawPolyLine(item);
    }

    @Override
    public void onRoutingCancelled() {

    }

    /**
     * draw poly line
     *
     * @param route
     */
    public void drawPolyLine(Route route) {

        PolylineOptions options = new PolylineOptions()
                .width(MAP_POLYLINE_WIDTH)
                .color(Color.parseColor(MAP_POLYLINE_COLOR)).geodesic(true);
        options.addAll(route.getPoints());
        if (line != null) {
            line.remove();
        }
        line = mMap.addPolyline(options);
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

    private void getFriendLocation() {
        Location location = FoundULocationManager.getInstance().getCurrentLocation();
        APIClient.getFriendLocation("user", "friend", String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), new APIClient.ResponseListener() {
            @Override
            public void onSuccess(ApiResponse response) {

            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
    }
}

