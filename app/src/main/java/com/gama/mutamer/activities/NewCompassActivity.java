package com.gama.mutamer.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.libs.views.MyCompassView;
import com.gama.mutamer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCompassActivity extends AppCompatActivity implements SensorEventListener {

    /***
     * Vars
     */
    SensorManager sensorManager;
    double angle;
    private Sensor sensor_orientation;


    /***
     * Vars
     */
    @BindView(R.id.SurfaceView01) MyCompassView compassView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compass);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.qibla));
        }
        ButterKnife.bind(this);
        angle = Constants.finals(Constants.myCurLat, Constants.myCurLng, Constants.makkahLat,
                Constants.makkahLng);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager == null)
            return;

        sensor_orientation = sensorManager
                .getDefaultSensor(Sensor.TYPE_ORIENTATION);


        int n = Integer.parseInt(compassView.getTag().toString());
        compassView.resetAminVal(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, sensor_orientation,
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, sensor_orientation);
        super.onPause();
    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        updateCanvas(-degree, angle);
    }

    private void updateCanvas(float deg, double angle) {
        compassView.updateData(angle, deg);
    }
}
