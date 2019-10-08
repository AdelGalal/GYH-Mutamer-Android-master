package com.gama.mutamer.activities.views;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.data.repositories.PrayersRepository;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.ShareHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.services.Alarm;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DuaaDetailsActivity extends AppCompatActivity {

    /***
     * Vars
     */
    private Duaa mModel;
    private MediaPlayer mPlayer;
    private Timer mTimer;
    private String mLang = LanguageHelper.LANGUAGE_ENGLISH;
    private int playerPosition;

    /***
     * Views
     */
    @BindView(R.id.btnPlayPause) ImageView btnPlayPause;
    @BindView(R.id.sbProgress) ProgressBar sbProgress;
    @BindView(R.id.tvPlaceName) TextView tvPlaceName;
    @BindView(R.id.tvPlaceDescription) TextView tvPlaceDescription;
    @BindView(R.id.ivPlaceImage) ImageView ivPlaceImage;
    @BindView(R.id.ibAddAlert) ImageButton ibAddAlert;
    @BindView(R.id.ibFavorites) ImageButton ibFavorites;
    @BindView(R.id.vCont) View vCont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duaa_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.duaa));
        }
        mLang = new LanguageHelper().getCurrentLanguage(this);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.PLAYER_POSITION)) {
            playerPosition = savedInstanceState.getInt(Constants.PLAYER_POSITION);
        }
        if (getIntent().hasExtra(Constants.ID)) {
            long duaaId = getIntent().getLongExtra(Constants.ID, 0);
            new GetDuaaAsync(this)
                    .execute(duaaId);

        } else {
            DialogsHelper.showToast(this, R.string.unexcepted_error);
            finish();
        }

    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPlayer != null) {
            playerPosition = mPlayer.getCurrentPosition();
            outState.putInt(Constants.PLAYER_POSITION, playerPosition);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        togglePlaying(false);
    }

    private void bindUi(Duaa duaa) {
        if (duaa != null) {
            mModel = duaa;
            String lang = new LanguageHelper().getCurrentLanguage(this);
            tvPlaceName.setText(mModel.getName(lang));
            tvPlaceDescription.setText(mModel.getBody(lang));
            ibAddAlert.setImageDrawable(ContextCompat.getDrawable(this, (mModel.isAlert()) ? R.drawable.ic_notifications_active : R.drawable.ic_notifications_off));
            ibFavorites.setImageDrawable(ContextCompat.getDrawable(this, (mModel.isFavorites()) ? R.drawable.ic_favorite : R.drawable.ic_favorite_border));
            Glide.with(this)
                    .load(String.format(getString(R.string.url_duaa_image), mModel.getId()))
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.mipmap.ic_launcher_round))
                    .into(ivPlaceImage);
            setupMedia();
        }
    }

    private void setupMedia() {
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(getString(R.string.url_duaa_path) + mModel.getId() + ".mp3");
            mPlayer.setOnPreparedListener(mp -> {
                if (DuaaDetailsActivity.this.isFinishing()) {
                    mPlayer.stop();
                    return;
                }
                if (playerPosition > 0) {
                    mp.seekTo(playerPosition);

                }
                mp.start();
            });

            mPlayer.setOnCompletionListener(mp -> {
                sbProgress.setProgress(100);
                btnPlayPause.setImageDrawable(mPlayer.isPlaying() ? ContextCompat.getDrawable(DuaaDetailsActivity.this, R.drawable.ic_pause_circle_filled) : ContextCompat.getDrawable(DuaaDetailsActivity.this, R.drawable.ic_play_circle_filled));
            });

            mPlayer.setScreenOnWhilePlaying(true);
            mPlayer.prepareAsync();


            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        try {
                            if (mPlayer != null && mPlayer.isPlaying()) {
                                sbProgress.setProgress((int) (((double) mPlayer.getCurrentPosition() / (double) mPlayer.getDuration()) * 100));
                            }
                        } catch (Exception eex) {
                            FirebaseErrorEventLog.SaveEventLog(eex);
                        }
                    });
                }
            }, 1000, 1000);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @OnClick(R.id.btnPlayPause) void buttonPlayPausePressed(View v) {
        togglePlaying(true);
    }

    private void togglePlaying(boolean resumeIfStopped) {
        try {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            } else {
                if (resumeIfStopped) {
                    mPlayer.start();
                }
            }
            btnPlayPause.setImageDrawable(mPlayer.isPlaying() ? ContextCompat.getDrawable(this, R.drawable.ic_pause_circle_filled) : ContextCompat.getDrawable(this, R.drawable.ic_play_circle_filled));
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


    @Override protected void onStop() {
        super.onStop();
        if (mTimer != null)
            mTimer.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.ibDownload)
    void downloadButtonPressed(View v) {
        CommunicationsHelper.openUrl(this, getString(R.string.url_duaa_path) + mModel.getId() + ".mp3");
    }


    @OnClick(R.id.ibAddAlert)
    void addAlertButtonClicked(View v) {
        mModel.setAlert(!mModel.isAlert());
        new PrayersRepository().updateDuaaAlert(mModel.getId(), mModel.isAlert());

        if (mModel.isAlert()) {
            Alarm.setAlarm(this, mModel.getTime(), (int) mModel.getId());
        } else {
            Alarm.cancelAlarm(this, (int) mModel.getId());
        }
        ibAddAlert.setImageDrawable(ContextCompat.getDrawable(this, (mModel.isAlert()) ? R.drawable.ic_notifications_active : R.drawable.ic_notifications_off));
        Snackbar.make(vCont, mModel.isAlert() ? R.string.duaa_alert_on : R.string.duaa_alert_off, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.ibFavorites)
    void addFavoritesButtonClicked(View v) {
        mModel.setFavorites(!mModel.isFavorites());
        new PrayersRepository().updateBookmark(mModel.getId(), mModel.isFavorites());
        ibFavorites.setImageDrawable(ContextCompat.getDrawable(this, (mModel.isFavorites()) ? R.drawable.ic_favorite : R.drawable.ic_favorite_border));
        Snackbar.make(vCont, mModel.isFavorites() ? R.string.duaa_bookmark_on : R.string.duaa_bookmark_off, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.ibShare)
    void shareButtonClicked(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mModel.getName(mLang) + " " + mModel.getBody(mLang));
        sendIntent.setType("text/plain");
        startActivity(ShareHelper.customChooserIntentNoFb(sendIntent, getString(R.string.send_to), this));
    }


    public static class GetDuaaAsync extends AsyncTask<Long, Void, Duaa> {

        private WeakReference<DuaaDetailsActivity> activityReference;

        GetDuaaAsync(DuaaDetailsActivity context) {
            activityReference = new WeakReference<>(context);
        }


        @Override
        protected Duaa doInBackground(Long... longs) {

            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }


            return new PrayersRepository()
                    .getDuaaById(longs[0]);
        }

        @Override
        protected void onPostExecute(Duaa data) {
            super.onPostExecute(data);

            DuaaDetailsActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }


            activity.bindUi(data);
        }
    }


}
