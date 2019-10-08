package com.gama.mutamer.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import java.util.List;

/**
 * Created by mustafa on 8/21/17.
 * Release the GEEK
 */

public class CompassActivity extends AppCompatActivity implements LocationListener {

    LocationManager locMan;
    Location gpsLocation;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.qibla));
        }
        initControlLayout();
        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControlLayout() {

    }


    protected void onResume() {
        try {
            super.onResume();
            setTitle(R.string.qibla);
            List<String> providers = locMan.getProviders(false);
            if (providers.contains(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    protected void onPause() {
        super.onPause();
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locMan.removeUpdates(this);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }




    @Override
    public void onLocationChanged(Location location) {
        gpsLocation = location;
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}