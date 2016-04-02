package com.distance_tracker.ui;

import android.app.Activity;
import android.content.Intent;

import com.distance_tracker.location.TrackedInfo;

public class LocationActivity extends BaseInfoActivity {

    public static void start(Activity src) {
        Intent i = new Intent(src, LocationActivity.class);
        src.startActivity(i);
    }

    @Override
    protected void showTrackedInfo(TrackedInfo info) {
        infoView.setText(info.getLastLocation().toString());
    }
}
