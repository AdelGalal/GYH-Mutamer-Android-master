package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.ISearchChangeListener;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.PlaceSearchViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 4/2/17.
 * Release the GEEK
 */

public class NearByFilterSheet extends BottomSheetDialogFragment {
    /***
     * Flags
     */
    private static final String SEARCH_FILTER_FLAG = "searchFilter";

    /***
     * Vars
     */
    PlaceSearchViewModel mPlaceSearch = new PlaceSearchViewModel();
    int units = 0;
    int minDistance = 500;
    private Locale mLocale;

    /***
     * Views
     */
    @BindView(R.id.sbDistance) SeekBar sbDistance;
    @BindView(R.id.tvCurrentRadius) TextView tvCurrentRadius;
    @BindView(R.id.tvMinDistance) TextView tvMinDistance;
    @BindView(R.id.tvMaxDistance) TextView tvMaxDistance;

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

    public static NearByFilterSheet newInstance(PlaceSearchViewModel searchFilter) {

        Bundle args = new Bundle();
        args.putSerializable(SEARCH_FILTER_FLAG, searchFilter);
        NearByFilterSheet fragment = new NearByFilterSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_nearby_filter, null);
        ButterKnife.bind(this, contentView);

        dialog.setContentView(contentView);


        units = MainActivity.setting.getUnitType();
        mLocale = new LanguageHelper().getCurrentLocale(getActivity());

        minDistance = 300;
        tvCurrentRadius.setText(String.format(mLocale, "%d %s", (units == 0) ? 500 : 300, getString((units == 0) ? R.string.meter : R.string.ft)));
        tvMinDistance.setText(String.format(mLocale, "%d %s", (units == 0) ? 500 : 300, getString((units == 0) ? R.string.meter : R.string.ft)));
        tvMaxDistance.setText(String.format(mLocale, "%d %s", (units == 0) ? 40 : 25, getString((units == 0) ? R.string.km : R.string.mile)));

        sbDistance.setMax(40000);

        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPlaceSearch.setDistance(seekBar.getProgress() > 0 ? seekBar.getProgress() : 500);
                setDistanceLabel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlaceSearch.setDistance(seekBar.getProgress() >= minDistance ? seekBar.getProgress() : minDistance);
                setDistanceLabel();
            }
        });
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        if (getArguments() != null && getArguments().get(SEARCH_FILTER_FLAG) != null) {
            mPlaceSearch = (PlaceSearchViewModel) getArguments().getSerializable(SEARCH_FILTER_FLAG);
            sbDistance.setProgress(mPlaceSearch.mDistance);
            setDistanceLabel();
        }
    }

    private void setDistanceLabel() {
        try {
            if (mPlaceSearch.getDistance() > 0) {
                if (units == 0)
                    tvCurrentRadius.setText(mPlaceSearch.getDistance() < 1000 ? String.format(mLocale, "%d ", mPlaceSearch.getDistance()) + getString(R.string.meter) : String.format(mLocale, "%1$,.2f ", (double) mPlaceSearch.getDistance() / 1000) + getString(R.string.km));
                else
                    tvCurrentRadius.setText(mPlaceSearch.getDistance() < 1600 ? String.format(mLocale, "%d ", mPlaceSearch.getDistance()) + getString(R.string.ft) : String.format(mLocale, "%1$,.2f ", ((double) mPlaceSearch.getDistance() / 1600)) + getString(R.string.mile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.btn_health)
    void buttonHealthPressed(View v) {
        bindModel(0);
    }

    @OnClick(R.id.btn_hotels)
    void buttonHotelsPressed(View v) {
        bindModel(1);
    }

    @OnClick(R.id.btn_services)
    void buttonServicesPressed(View v) {
        bindModel(2);
    }

    @OnClick(R.id.btn_restaurants)
    void buttonRestaurantsPressed(View v) {
        bindModel(3);
    }

    @OnClick(R.id.btn_banking)
    void buttonBankingPressed(View v) {
        bindModel(4);
    }

    @OnClick(R.id.btn_shopping_mall)
    void buttonShoppingMallPressed(View v) {
        bindModel(5);
    }

    private void bindModel(int position) {
        mPlaceSearch.setType(getResources().getStringArray(R.array.places_types)[position]);
        mPlaceSearch.setImage(Constants.icons[position]);
        if (getActivity() != null) {
            ((ISearchChangeListener) getActivity()).SearchChanged(mPlaceSearch);
        }
        dismiss();
    }

}