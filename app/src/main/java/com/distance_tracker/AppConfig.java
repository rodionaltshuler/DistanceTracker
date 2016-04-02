package com.distance_tracker;

import android.content.res.Resources;
import android.support.annotation.StringRes;

import java.text.DecimalFormat;

public class AppConfig {

    public enum DistanceFormatter {

        KM(1000f, R.string.km),
        MI(1609f, R.string.miles);

        private static final DecimalFormat decimalFormat = new DecimalFormat("#.#");

        float koef;
        int measureUnit;

        DistanceFormatter(float koef, @StringRes int measureUnit) {
            this.koef = koef;
            this.measureUnit = measureUnit;
        }

        public String getFormatted(Resources res, float distanceMeters) {
            return res.getString(measureUnit, decimalFormat.format(distanceMeters / koef));
        }

    }

    public static DistanceFormatter FORMATTER;
}
