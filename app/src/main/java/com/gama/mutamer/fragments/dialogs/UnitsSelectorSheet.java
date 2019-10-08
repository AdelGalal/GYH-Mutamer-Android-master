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

public class UnitsSelectorSheet extends BottomSheetDialogFragment {

    /***
     * Views
     */
    @BindView(R.id.cvMetricUnits)
    CardView cvMetricUnits;

    @BindView(R.id.cvUsUnit)
    CardView cvUsUnit;


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

    public static UnitsSelectorSheet newInstance(int selectedFilter) {

        Bundle args = new Bundle();
        args.putInt(Constants.SELECTED_FILTER_FLAG, selectedFilter);
        UnitsSelectorSheet fragment = new UnitsSelectorSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_units_sheet, null);
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
        cvMetricUnits.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.UNITS_METRIC ? R.color.colorAccent : R.color.white));
        cvUsUnit.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.UNITS_US ? R.color.colorAccent : R.color.white));
        new SettingsRepository().updateUnit(selectedFilter);
        if (informListener && getActivity() != null) {
            ((IFilterChangeListener) getActivity()).filterChanged(Constants.UNITS_FILTER_FLAG, selectedFilter);
            dismiss();
        }
    }



    @OnClick(R.id.btnCancel)
    void buttonCancelPressed(View v) {
        dismiss();
    }

    @OnClick(R.id.cvMetricUnits)
    void MetricUnitsButtonPressed(View v) {
        updateSelectedFilter(Utils.UNITS_METRIC, true);
    }

    @OnClick(R.id.cvUsUnit)
    void UsUnitButtonClicked(View v) {
        updateSelectedFilter(Utils.UNITS_US, true);
    }
}
