package com.gama.mutamer.fragments;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.Jitl;
import com.gama.mutamer.libs.astro.Dms;
import com.gama.mutamer.viewModels.prayTimes.LocationVM;
import com.gama.mutamer.viewModels.prayTimes.Preferences;
import com.gama.mutamer.views.QiblaCompassView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Qibla compass fragment.
 */

// for SensorListener and SensorManager APIs
public class QiblaCompassFragment extends Fragment {

    private static final String TAG = QiblaCompassFragment.class.getSimpleName();
    private static final String PATTERN = "#.###";
    private static DecimalFormat sDecimalFormat;
    private static SensorManager sSensorManager;
    private static float sQiblaDirection = 0f;
    private static android.hardware.SensorListener sOrientationListener;
    private static boolean isTrackingOrientation = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        sSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        try {
            sDecimalFormat = new DecimalFormat(PATTERN);
        } catch (AssertionError ae) {
            Log.wtf(TAG, "Could not construct DecimalFormat", ae);
            Log.d(TAG, "Will try with Locale.US");
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            if (format instanceof DecimalFormat) {
                sDecimalFormat = (DecimalFormat) format;
                sDecimalFormat.applyPattern(PATTERN);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_qibla, container, false);
        try {
            Preferences.getInstance(getActivity()).initCalculationDefaults(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final QiblaCompassView qiblaCompassView = rootView
                .findViewById(R.id.qibla_compass);
        qiblaCompassView.setConstants();
        sOrientationListener = new android.hardware.SensorListener() {
            @Override
            public void onSensorChanged(int s, float v[]) {
                float northDirection = v[SensorManager.DATA_X];
                qiblaCompassView.setDirections(northDirection, sQiblaDirection);
            }

            @Override
            public void onAccuracyChanged(int s, int a) {
            }
        };

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDms();
        if (!isTrackingOrientation) {
            isTrackingOrientation = sSensorManager.registerListener(sOrientationListener,
                    SensorManager.SENSOR_ORIENTATION);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isTrackingOrientation) {
            sSensorManager.unregisterListener(sOrientationListener);
            isTrackingOrientation = false;
        }
    }

    /**
     * Add Latitude, Longitude and Qibla DMS location
     */
    private void updateDms() {
        LocationVM location = Preferences.getInstance(getActivity())
                .getJitlLocation(getActivity());
//        Dms latitude = new Dms(location.getDegreeLat());
//        Dms longitude = new Dms(location.getDegreeLong());
        Dms qibla = Jitl.getNorthQibla(location);
        sQiblaDirection = (float) qibla
                .getDecimalValue(com.gama.mutamer.libs.astro.Direction.NORTH);

        //       View rootView = getView();
//        TextView tv = (TextView) rootView.findViewById(R.id.current_latitude);
//        tv.setText(getString(R.string.degree_minute_second, latitude.getDegree(),
//                latitude.getMinute(), sDecimalFormat.format(latitude.getSecond())));

//        tv = (TextView) rootView.findViewById(R.id.current_longitude);
//        tv.setText(getString(R.string.degree_minute_second, longitude.getDegree(),
//                longitude.getMinute(), sDecimalFormat.format(longitude.getSecond())));

//        tv = (TextView) rootView.findViewById(R.id.current_qibla);
//        tv.setText(getString(R.string.degree_minute_second, qibla.getDegree(),
//                qibla.getMinute(), sDecimalFormat.format(qibla.getSecond())));
    }
}
