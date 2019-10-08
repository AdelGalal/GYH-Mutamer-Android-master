package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;

import com.gama.mutamer.R;
import com.gama.mutamer.adapters.PrayerAdapter;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.Schedule;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.services.StartNotificationReceiver;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.prayTimes.Preferences;
import com.gama.mutamer.viewModels.shared.Pray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/2/17.
 * Release the GEEK
 */

public class PraysDialogSheet extends BottomSheetDialogFragment {

    /***
     * Vars
     */
    private final ArrayList<Pray> mTimeTable = new ArrayList<>(6);
    private Locale mLocale;
    public static boolean isShown = false;


    /***
     * Views
     */
    @BindView(R.id.rvPrays) RecyclerView rvPrays;


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

    public static PraysDialogSheet newInstance() {
        return new PraysDialogSheet();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        isShown = true;
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_prays_sheet, null);
        ButterKnife.bind(this, contentView);
        rvPrays.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.gallery_grid), LinearLayoutManager.VERTICAL, false));
        rvPrays.setHasFixedSize(true);
        mLocale = new LanguageHelper().getCurrentLocale(getActivity());
        for (short i = Constants.FAJR; i <= Constants.NEXT_FAJR; i++) {
            Pray item = new Pray();
            item.setId(i);
            item.setName(getString(Constants.TIME_NAMES[i]));
            item.setDate("");
            mTimeTable.add(i, item);
        }
        try {
            Preferences.getInstance(getActivity()).initCalculationDefaults(getActivity());
        } catch (NullPointerException npe) {
            FirebaseErrorEventLog.SaveEventLog(npe);
        }
        updateTodayTimetable();
        if (getActivity() == null) return;
        rvPrays.setAdapter(new PrayerAdapter(mTimeTable, getActivity()));
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

    }

    @Override public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        isShown = false;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCancel)
    void buttonCancelClicked(View v) {
        dismiss();
    }

    private void updateTodayTimetable() {
        try {
            if (getActivity() == null) return;
            Context context = getActivity();
            StartNotificationReceiver.setNext(context);
            //Setting setting = new CommonRepository().getSettings();
            int prayerMethod = SharedPrefHelper.getSharedInt(getActivity(), SharedPrefHelper.PRAYER_CALC);
            Schedule today = Schedule.today(context, prayerMethod);
            GregorianCalendar[] schedule = today.getTimes();
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a",
                    mLocale);
            if (DateFormat.is24HourFormat(context)) {
                timeFormat = new SimpleDateFormat("HH:mm ", mLocale);
            }
            boolean foundNext = false;
            for (short i = Constants.FAJR; i <= Constants.NEXT_FAJR; i++) {
                String fullTime = timeFormat.format(schedule[i].getTime());
                mTimeTable.get(i).setDateTime(schedule[i].getTime());
                mTimeTable.get(i).setDate(today.isExtreme(i) ? fullTime.concat(" *") : fullTime);
                if (!foundNext && mTimeTable.get(i).getDateTime().getTime() > new Date().getTime()) {
                    foundNext = true;
                    mTimeTable.get(i).setCurrent(true);
                }
                mTimeTable.get(i).setEnabled(true);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

}
