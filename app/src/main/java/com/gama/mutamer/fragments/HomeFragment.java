package com.gama.mutamer.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.activities.views.ViewSliderActivity;
import com.gama.mutamer.data.models.content.Slider;
import com.gama.mutamer.data.repositories.SlidersRepository;
import com.gama.mutamer.fragments.dialogs.PraysDialogSheet;
import com.gama.mutamer.helpers.DateHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.Schedule;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.services.StartNotificationReceiver;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.prayTimes.Preferences;
import com.gama.mutamer.viewModels.shared.Pray;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {


    /***
     * Vars
     */

    String mLang = LanguageHelper.LANGUAGE_ENGLISH;
    private ArrayList<Slider> mSliders = new ArrayList<>();
    static boolean working = false;
    private final ArrayList<Pray> mTimeTable = new ArrayList<>(7);
    private Locale mLocale;

    /***
     * Views
     */

    @BindView(R.id.tvCurrentPray) TextView tvCurrentPray;
    @BindView(R.id.tvCurrentPrayTime) TextView tvCurrentPrayTime;
    @BindView(R.id.tvPrayRemainingTime) TextView tvPrayRemainingTime;
    @BindView(R.id.ivPrayTime) ImageView ivPrayTime;
    @BindView(R.id.prgPrayRemainingTime) ColorfulRingProgressView prgPrayRemainingTime;
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.vData) View vData;
    @BindView(R.id.view_no_data) View vNoData;
    @BindView(R.id.slider) SliderLayout mSliderLayout;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        setActivityTitle(getString(R.string.home));
        new UpdateDataAsync(getParentActivity(), this)
                .execute();
        return v;
    }


    private void switchUi(int state) {
        vData.setVisibility(state == Constants.STATE_DATA ? View.VISIBLE : View.GONE);
        vLoading.setVisibility(state == Constants.STATE_LOADING ? View.VISIBLE : View.GONE);
        vNoData.setVisibility(state == Constants.STATE_NO_DATA ? View.VISIBLE : View.GONE);
    }




    @OnClick(R.id.cvPrays)
    void praysCardClicked(View v) {
        try {
            if (getActivity() == null) {
                return;
            }
            if (PraysDialogSheet.isShown) {
                return;
            }

            PraysDialogSheet  bottomSheetDialogFragment = PraysDialogSheet.newInstance();
            bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


    private void updateTodayTimetable() {
        try {
            if (getActivity() == null || mLocale == null) return;
            Context context = getActivity();

            StartNotificationReceiver.setNext(context);
            int prayerCalc = SharedPrefHelper.getSharedInt(getActivity(), SharedPrefHelper.PRAYER_CALC);
            Schedule today = Schedule.today(context, prayerCalc);
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
                    setCurrentPray(mTimeTable.get(i), (i != 0) ? mTimeTable.get(i - 1) : null);
                }
                mTimeTable.get(i).setEnabled(true);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        //mTimetableView.notifyDataSetChanged();
    }

    private void setCurrentPray(Pray pray, Pray previousPray) {
        try {
            if (getActivity() == null || pray == null || pray.getDateTime() == null) return;
            ivPrayTime.setImageDrawable(ContextCompat.getDrawable(getActivity(), getResources().obtainTypedArray(R.array.prays_icons).getResourceId(pray.getId(), R.drawable.ic_pray)));
            tvCurrentPray.setText(pray.getName());
            long diffTime = (pray.getDateTime().getTime() - new Date().getTime());
            tvCurrentPrayTime.setText(DateHelper.formatJustTime(pray.getDateTime(), mLocale));
            tvPrayRemainingTime.setText(DateHelper.formatTime(diffTime, mLocale));
            float maxTime = (previousPray != null) ? (pray.getDateTime().getTime() - previousPray.getDateTime().getTime()) : (10 * 60 * 60 * 1000);
            float result = 1 - (diffTime / maxTime);
            prgPrayRemainingTime.setPercent((result) * 100);
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override public void onPause() {
        super.onPause();
        if (mSliderLayout != null) {
            mSliderLayout.stopAutoCycle();
        }
    }

    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) && mSliders.size() == 0){
            new UpdateDataAsync(getParentActivity(),this)
                    .execute();
        }
    }

    public void LocationUpdated() {
        if (!working) {
            working = true;
        }
    }

    @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override public void onPageSelected(int position) {

    }

    @Override public void onPageScrollStateChanged(int state) {

    }

    @Override public void onSliderClick(BaseSliderView slider) {
        long id = mSliders.get(mSliderLayout.getCurrentPosition()).getId();
        Intent intent = new Intent(getActivity(), ViewSliderActivity.class);
        intent.putExtra(Constants.ID, id);
        startActivity(intent);
    }


    public static class UpdateDataAsync extends AsyncTask<Void, Void, Void> {

        private WeakReference<MainActivity> activityReference;
        private WeakReference<HomeFragment> fragmentReference;

        UpdateDataAsync(MainActivity context, HomeFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                MainActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) {
                    return null;
                }

                HomeFragment fragment = fragmentReference.get();
                if (fragment == null) {
                    return null;
                }

                fragment.mSliders = new SlidersRepository()
                        .getData();


                try {
                    Preferences.getInstance(activity).initCalculationDefaults(activity);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    // FirebaseErrorEventLog.SaveEventLog(e);
                }


                fragment.mLocale = new LanguageHelper().getCurrentLocale(activity);
                fragment.mLang = new LanguageHelper().getCurrentLanguage(activity);
                for (short i = Constants.FAJR; i <= Constants.NEXT_FAJR; i++) {
                    Pray item = new Pray();
                    item.setId(i);
                    item.setName(activity.getString(Constants.TIME_NAMES[i]));
                    item.setDate("");
                    fragment.mTimeTable.add(i, item);
                }


            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }


            HomeFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return;
            }

            fragment.updateTodayTimetable();
            fragment.switchUi(Constants.STATE_DATA);

            try {
                if (fragment.mSliders != null && fragment.mSliders.size() > 0) {
                    for (Slider slider : fragment.mSliders) {
                        TextSliderView textSliderView = new TextSliderView(activity);

                        // initialize a SliderLayout
                        textSliderView
                                .description(slider.getTitle())
                                .image(String.format(fragment.getString(R.string.url_slide_image), String.valueOf(slider.getId())))
                                .setOnSliderClickListener(fragment);

                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putLong(Constants.ID, slider.getId());

                        fragment.mSliderLayout.addSlider(textSliderView);
                    }
                    fragment.mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
                    fragment.mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    fragment.mSliderLayout.setCustomAnimation(new DescriptionAnimation());
                    fragment.mSliderLayout.setDuration(4000);
                    fragment.mSliderLayout.addOnPageChangeListener(fragment);
                }
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
        }
    }

}
