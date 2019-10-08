package com.gama.mutamer.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.Schedule;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.libs.PanningView;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.shared.Pray;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class AzanActivity extends AppCompatActivity {


    /***
     * Vars
     */
    PanningView panningView;
    PanningView nightPanning;
    ImageView mosIView;
    RelativeLayout containerLayout;
    TextView azanName;
    TextView cityName;

    private MediaPlayer sMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azan);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        try {
            sMediaPlayer = MediaPlayer.create(this, R.raw.azan);
            sMediaPlayer.setScreenOnWhilePlaying(true);
            sMediaPlayer.start();
            //TODO: Use ButterKnife
            panningView = findViewById(R.id.backgroundView);
            nightPanning = findViewById(R.id.backgroundNightView);
            mosIView = findViewById(R.id.mosque_image);
            containerLayout = findViewById(R.id.azan_layout);
            azanName = findViewById(R.id.text_azan_name);
            cityName = findViewById(R.id.text_city_name);
            //Setting setting = new CommonRepository().getSettings();
            int prayerCalc = SharedPrefHelper.getSharedInt(this, SharedPrefHelper.PRAYER_CALC);
            Schedule today = Schedule.today(this, prayerCalc);
            final ArrayList<Pray> mTimeTable = new ArrayList<>(7);
            GregorianCalendar[] schedule = today.getTimes();
            for (short i = Constants.FAJR; i <= Constants.NEXT_FAJR; i++) {
                Pray item = new Pray();
                item.setId(i);
                item.setName(getString(Constants.TIME_NAMES[i]));
                item.setDate("");
                mTimeTable.add(i, item);
            }
            boolean foundNext = false;
            int nextIndex = 0;
            for (short i = Constants.FAJR; i <= Constants.NEXT_FAJR; i++) {
                mTimeTable.get(i).setDateTime(schedule[i].getTime());
                if (!foundNext && mTimeTable.get(i).getDateTime().getTime() > new Date().getTime()) {
                    foundNext = true;
                    nextIndex = i;
                    mTimeTable.get(i).setCurrent(true);
                }
                mTimeTable.get(i).setEnabled(true);
            }
            int azanIndex = getIntent().getIntExtra("azan", 0);
            if (foundNext) {
                if (nextIndex == 0)
                    nextIndex++;
                azanIndex = nextIndex - 1;

            }
            if (azanIndex == Constants.FAJR || azanIndex == Constants.MAGHRIB || azanIndex == Constants.ISHAA) {
                mosIView.setImageResource(R.drawable.adhan);
            } else {
                mosIView.setImageResource(R.drawable.adhan);
                nightPanning.setVisibility(View.GONE);
            }

            String name = getString(Constants.TIME_NAMES[azanIndex]);
            azanName.setText(name);

            nightPanning.startPanning();
            panningView.startPanning();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            sMediaPlayer.stop();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


}
