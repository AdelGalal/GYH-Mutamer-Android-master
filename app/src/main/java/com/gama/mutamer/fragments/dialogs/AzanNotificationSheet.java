package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.view.View;

import com.gama.mutamer.R;
import com.gama.mutamer.data.repositories.SettingsRepository;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.Utils;
import com.gama.mutamer.interfaces.IFilterChangeListener;
import com.gama.mutamer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gama.mutamer.utils.Constants.SELECTED_FILTER_FLAG;

/**
 * Created by mustafa on 8/7/17.
 * Release the GEEK
 */

public class AzanNotificationSheet extends BottomSheetDialogFragment {


    /***
     * Views
     */
    @BindView(R.id.cvPrayNotification) CardView cvPrayNotification;
    @BindView(R.id.cvPrayAzan) CardView cvPrayAzan;
    @BindView(R.id.cvPrayVibrate) CardView cvPrayVibrate;
    @BindView(R.id.cvPrayMute) CardView cvPrayMute;


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

    public static AzanNotificationSheet newInstance(int selectedFilter) {

        Bundle args = new Bundle();
        args.putInt(SELECTED_FILTER_FLAG, selectedFilter);
        AzanNotificationSheet fragment = new AzanNotificationSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_azan_notification_sheet, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        if (getArguments() != null && getArguments().get(SELECTED_FILTER_FLAG) != null) {
            updateSelectedFilter(getArguments().getInt(SELECTED_FILTER_FLAG), false);
        }
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void updateSelectedFilter(int selectedFilter, boolean informListener) {
        cvPrayNotification.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_NOTIFICATION ? R.color.colorAccent : R.color.white));
        cvPrayAzan.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_AZAN ? R.color.colorAccent : R.color.white));
        cvPrayVibrate.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_VIBRATE ? R.color.colorAccent : R.color.white));
        cvPrayMute.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_MUTE ? R.color.colorAccent : R.color.white));
        new SettingsRepository().updatePrayerNotification(getActivity(), selectedFilter);
        if (informListener && getActivity() != null) {
            ((IFilterChangeListener) getActivity()).filterChanged(Constants.UNITS_FILTER_FLAG, selectedFilter);
            dismiss();
        }
    }

    @OnClick(R.id.btnCancel)
    void buttonCancelPressed(View v) {
        dismiss();
    }

    @OnClick(R.id.cvPrayNotification)
    void notificationButtonPressed(View v) {
        updateSelectedFilter(Utils.PRAY_NOTIFICATION, true);
    }

    @OnClick(R.id.cvPrayAzan)
    void azanButtonClicked(View v) {
        updateSelectedFilter(Utils.PRAY_AZAN, true);
    }

    @OnClick(R.id.cvPrayVibrate)
    void vibrateButtonClicked(View v) {
        updateSelectedFilter(Utils.PRAY_VIBRATE, true);
    }

    @OnClick(R.id.cvPrayMute)
    void muteButtonClicked(View v) {
        updateSelectedFilter(Utils.PRAY_MUTE, true);
    }
}
