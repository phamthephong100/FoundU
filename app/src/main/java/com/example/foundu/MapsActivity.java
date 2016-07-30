package com.example.foundu;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.foundu.manager.FoundULocationManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int DEFAULT_MAP_ZOOM_LEVEL = 14;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        FoundULocationManager.getInstance().startLocationUpdate();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                new com.google.android.gms.maps.model.LatLng(location.getLatitude(),location.getLongitude())).title("You're here");
        mMap.addMarker(markerOption);
    }
}

