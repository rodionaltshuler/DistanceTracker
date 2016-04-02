package com.distance_tracker.location;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.distance_tracker.events.ChangeTrackingStatusEvent;
import com.distance_tracker.events.TrackedInfoUpdatedEvent;
import com.distance_tracker.events.TrackingStatusChangedEvent;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import de.greenrobot.event.EventBus;


public class LocationTrackingService extends Service {

    private LocationProvider locationProvider;

    private TrackedInfo trackedInfo;

    private static final String TRACKED_INFO_KEY = "tracked_info";

    private static final String TRACKING_ON_OFF_KEY = "on_off_key";

    private Gson gson = new Gson();

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (trackedInfo == null) {
                trackedInfo = new TrackedInfo(new LatLng(location.getLatitude(), location.getLongitude()), 0);
            } else {
                trackedInfo.update(location);
            }
            save(trackedInfo);
            bus.postSticky(new TrackedInfoUpdatedEvent(trackedInfo));
        }
    };

    EventBus bus = EventBus.getDefault();

    @Override
    public void onCreate() {
        super.onCreate();
        locationProvider = new LocationProvider(getApplicationContext(), locationListener);
        trackedInfo = load();
        bus.register(this);
        restoreTrackingStatus();
    }

    public void onEvent(ChangeTrackingStatusEvent event) {
        switch (event.getStatus()) {
            case ON:
                locationProvider.startTracking();
                saveTrackingStatus(true);
                break;
            case OFF:
                locationProvider.stopTracking();
                saveTrackingStatus(false);
                break;
        }

        bus.postSticky(new TrackingStatusChangedEvent(locationProvider.getStatus()));
    }

    private void saveTrackingStatus(boolean isOn) {
        getPrefs().edit().putBoolean(TRACKING_ON_OFF_KEY, isOn).apply();
    }

    private void restoreTrackingStatus() {
        boolean on = getPrefs().getBoolean(TRACKING_ON_OFF_KEY, false);
        if (on) {
            locationProvider.startTracking();
        }
        bus.postSticky(new TrackingStatusChangedEvent(on ?
                LocationUpdatesStatus.ON :
                LocationUpdatesStatus.OFF));
    }

    private
    @Nullable
    TrackedInfo load() {
        if (getPrefs().contains(TRACKED_INFO_KEY)) {
            String trackedJson = getPrefs().getString(TRACKED_INFO_KEY, null);
            return gson.fromJson(trackedJson, TrackedInfo.class);
        }
        return null;
    }

    private void save(@NonNull TrackedInfo trackedInfo) {
        getPrefs()
                .edit()
                .putString(TRACKED_INFO_KEY, gson.toJson(trackedInfo))
                .apply();
    }

    private SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
