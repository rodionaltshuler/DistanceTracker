package com.distance_tracker.location;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class TrackedInfo {

    private LatLng lastLocation;

    private float distance;

    private boolean on;

    public TrackedInfo(LatLng lastLocation, float distance) {
        this.lastLocation = lastLocation;
        this.distance = distance;
    }

    public LatLng getLastLocation() {
        return lastLocation;
    }

    public float getDistance() {
        return distance;
    }

    public void update(Location newLocation) {
        Location oldLocation = new Location("old");
        oldLocation.setLatitude(lastLocation.latitude);
        oldLocation.setLongitude(lastLocation.longitude);
        float addDistance = newLocation.distanceTo(oldLocation);
        distance += addDistance;
        lastLocation = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());
    }
}
