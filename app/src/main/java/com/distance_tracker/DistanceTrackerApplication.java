package com.distance_tracker;

import android.app.Application;
import android.content.Intent;

import com.distance_tracker.location.LocationTrackingService;

public class DistanceTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent serviceIntent = new Intent(this, LocationTrackingService.class);
        startService(serviceIntent);
    }

}
