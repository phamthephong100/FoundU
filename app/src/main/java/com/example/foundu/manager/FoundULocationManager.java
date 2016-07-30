package com.example.foundu.manager;
/**
 * Created by duongthoai on 7/30/16.
 */
import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.foundu.FoundUApplication;

public class FoundULocationManager {

    public interface OnTrackingLocationFinished {
        void onTrackingLocationFinished(double distance, long time, Location currentLocation);
    }

    private final String TAG = FoundULocationManager.class.getSimpleName();

    private LocationManager locationManager;
    private Context context;
    private Location currentLocation;
    private long minDistanceChangeForUpdates = 10; // in meters
    private long minTimeBetweenUpdates = 1000 * 5; // in milliseconds

    private double totalDistance; // in meters
    private long totalTime; // in seconds
    private long anchorTime; // in milliseconds
    private long currentTime; // in milliseconds
    private boolean isTracking;

    private static FoundULocationManager instance;

    public static FoundULocationManager getInstance() {
        if (instance == null) {
            synchronized (FoundULocationManager.class) {
                if (instance == null) {
                    instance = new FoundULocationManager();
                }
            }
        }
        return instance;
    }

    public FoundULocationManager() {
        context = FoundUApplication.getInstance();
    }

    public void startLocationUpdate() {

        if (locationManager != null) {
            return;
        }

        Log.i(TAG, "Start location update");

        locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            Log.w(TAG, "GPS and Wifi are unavailable");
            return;
        }

        //if (isNetworkEnabled && Utils.isNetworkOnline(context)) {
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTimeBetweenUpdates,
                minDistanceChangeForUpdates, locationListener);
        //} else {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTimeBetweenUpdates,
                minDistanceChangeForUpdates, locationListener);
        //}

        Criteria criteria = new Criteria();
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        currentLocation = locationManager.getLastKnownLocation(provider);

    }

    public void stopLocationUpdate() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        currentLocation = null;
    }

    public void startTracking() {
        if (!isTracking) {
            isTracking = true;
            totalDistance = 0;
            totalTime = 0;
            anchorTime = System.currentTimeMillis();
        }
    }

    public void stopTracking(OnTrackingLocationFinished listener) {
        isTracking = false;
        listener.onTrackingLocationFinished(totalDistance, totalTime / 1000, currentLocation);
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, String.format("onLocationChanged - (%f, %f)", location.getLongitude(), location.getLatitude()));
            if (isTracking && currentLocation != null) {
                totalDistance += location.distanceTo(currentLocation);
                currentTime = System.currentTimeMillis();
                totalTime += (currentTime - anchorTime);
                anchorTime = currentTime;
            }
            currentLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled");
        }
    };

    public void setMinDistanceChangeForUpdates(long minDistanceChangeForUpdates) {
        this.minDistanceChangeForUpdates = minDistanceChangeForUpdates;
    }

    public void setMinTimeBetweenUpdates(long minTimeBetweenUpdates) {
        this.minTimeBetweenUpdates = minTimeBetweenUpdates;
    }
}
