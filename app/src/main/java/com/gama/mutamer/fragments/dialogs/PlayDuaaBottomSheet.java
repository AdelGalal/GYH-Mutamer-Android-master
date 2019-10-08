package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mustafa on 8/12/17.
 * Release the GEEK
 */

public class PlayDuaaBottomSheet extends BottomSheetDialogFragment {

    /***
     * Flags
     */
    private static final String DUAA_URL_FLAG = "DuaaIndex";
    private static final String URL = "url", TITLE = "title";
    /***
     * Views
     */
    @BindView(R.id.ivReaderImage)
    ImageView ivReaderImage;
    @BindView(R.id.tvReaderName)
    TextView tvReaderName;
    @BindView(R.id.tvDuaaName)
    TextView tvDuaaName;
    @BindView(R.id.prgBar)
    ProgressBar mBar;
    /***
     * Vars
     */
    private Duaa mDuaa;
    private MediaPlayer mPlayer;
    private Timer mTimer;
    private String mTitle;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public static PlayDuaaBottomSheet newInstance(Duaa duaa) {

        Bundle args = new Bundle();

        PlayDuaaBottomSheet fragment = new PlayDuaaBottomSheet();
        args.putSerializable(DUAA_URL_FLAG, duaa);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_duaa_sheet, null);
        ButterKnife.bind(this, contentView);
        if (getArguments() != null && getArguments().get(DUAA_URL_FLAG) != null) {
            mDuaa = (Duaa) getArguments().getSerializable(DUAA_URL_FLAG);
            if (mDuaa != null) {
                String mLang = new LanguageHelper().getCurrentLanguage(getActivity());
                tvReaderName.setText(mDuaa.getName(mLang));
                tvDuaaName.setText(mDuaa.getBody(mLang));
                if (getActivity() != null)
                    Glide.with(getActivity())
                            .load(String.format(new Locale("en-us"), getActivity().getString(R.string.url_duaa_image), mDuaa.getId()))
                            .into(ivReaderImage);
            }
        }
        dialog.setContentView(contentView);
        setupMedia();
        // selectCategory(mSelectedIndex, false);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }


    private void setupMedia() {
        try {
            mTimer = new Timer();
            mPlayer = new MediaPlayer();
            mPlayer.setOnPreparedListener(mp -> mp.start());
            Log.wtf("DDD", getString(R.string.url_duaa_path) + mDuaa.getId() + ".mp3");
            mPlayer.setDataSource(getString(R.string.url_duaa_path) + mDuaa.getId() + ".mp3");
            mPlayer.prepareAsync();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (mPlayer != null && mPlayer.isPlaying()) {
                                        mBar.setProgress(1);
                                    }
                                }catch (Exception ex){
                                    FirebaseErrorEventLog.SaveEventLog(ex);
                                }
                            }
                        });
                    }
                }
            });

            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            try {
                                if (mPlayer != null  && mPlayer.isPlaying()) {
                                    mBar.setProgress((int) (((double) mPlayer.getCurrentPosition() / (double) mPlayer.getDuration()) * 100));
                                }
                            }catch (Exception eex){
                                FirebaseErrorEventLog.SaveEventLog(eex);
                            }
                        });
                    }
                }
            }, 1000, 1000);

        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (mTimer != null)
                mTimer.cancel();
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
            }
        }catch (Exception e){
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}