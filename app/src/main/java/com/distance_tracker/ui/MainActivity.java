package com.distance_tracker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.distance_tracker.R;
import com.distance_tracker.events.ChangeTrackingStatusEvent;
import com.distance_tracker.events.TrackingStatusChangedEvent;
import com.distance_tracker.location.LocationUpdatesStatus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.start_button)
    TextView changeTrackingStatusButton;

    EventBus eventBus = EventBus.getDefault();

    private LocationUpdatesStatus updatesStatus = LocationUpdatesStatus.OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        changeTrackingStatusButton.setText(updatesStatus.getButtonTitle());
    }

    @OnClick(R.id.show_distance)
    void onShowDistance() {
        DistanceActivity.start(this);
    }

    @OnClick(R.id.show_location)
    void onShowLocation() {
        LocationActivity.start(this);
    }

    @OnClick(R.id.start_button)
    void onChangeTrackingStatus() {
        eventBus.postSticky(new ChangeTrackingStatusEvent(updatesStatus.inverse()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.registerSticky(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    public void onEventMainThread(TrackingStatusChangedEvent event) {
        updatesStatus = event.getStatus();
        changeTrackingStatusButton.setText(event.getStatus().getButtonTitle());
    }
}
