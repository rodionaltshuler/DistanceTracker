package com.distance_tracker.location;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

class LocationProvider {

    public static final String TAG = LocationProvider.class.getSimpleName();

    private GoogleApiClient googleApiClient;

    private static final int LOCATION_UPDATE_INTERVAL = 10000;

    private LocationUpdatesStatus locationUpdatesStatus = LocationUpdatesStatus.OFF;

    private LocationListener locationListener;

    public LocationProvider(Context context, LocationListener locationListener) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionCallbacks)
                .build();
        this.locationListener = locationListener;
    }

    public void startTracking() {
        locationUpdatesStatus = LocationUpdatesStatus.ON;
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        } else if (googleApiClient.isConnecting()) {
            //do nothing
        } else {
            googleApiClient.connect();
        }
    }

    public void stopTracking() {
        locationUpdatesStatus = LocationUpdatesStatus.OFF;
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationListener);
        }
    }


    private GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            Log.d(TAG, "googleApiClient connected");
            requestLocationUpdates();
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    private void requestLocationUpdates() {
        if (locationUpdatesStatus.equals(LocationUpdatesStatus.ON)) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
        }
    }

    public LocationUpdatesStatus getStatus() {
        return locationUpdatesStatus;
    }
}