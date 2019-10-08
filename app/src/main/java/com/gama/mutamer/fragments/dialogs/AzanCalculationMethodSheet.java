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

public class AzanCalculationMethodSheet extends BottomSheetDialogFragment {

    /***
     * Views
     */
    @BindView(R.id.cvUmmQura) CardView cvUmmQura;
    @BindView(R.id.cvMuslimLeage) CardView cvMuslimLeage;
    @BindView(R.id.cvNorthAmerica) CardView cvNorthAmerica;
    @BindView(R.id.cvEgyptionMethod) CardView cvEgyptionMethod;

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

    public static AzanCalculationMethodSheet newInstance(int selectedFilter) {

        Bundle args = new Bundle();
        args.putInt(SELECTED_FILTER_FLAG, selectedFilter);
        AzanCalculationMethodSheet fragment = new AzanCalculationMethodSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_azan_calculation_sheet, null);
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
        cvUmmQura.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_MECCA_METHOD ? R.color.colorAccent : R.color.white));
        cvMuslimLeage.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_MUSLIM_METHOD ? R.color.colorAccent : R.color.white));
        cvNorthAmerica.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_ISLAMIC_METHOD ? R.color.colorAccent : R.color.white));
        cvEgyptionMethod.setCardBackgroundColor(getResources().getColor(selectedFilter == Utils.PRAY_EGYPT_METHOD ? R.color.colorAccent : R.color.white));
        new SettingsRepository().updatePrayerCalculation(getActivity(), selectedFilter);
        if (informListener && getActivity() != null) {
            ((IFilterChangeListener) getActivity()).filterChanged(Constants.UNITS_FILTER_FLAG, selectedFilter);
            dismiss();
        }
    }

    @OnClick(R.id.btnCancel)
    void buttonCancelPressed(View v) {
        dismiss();
    }

    @OnClick(R.id.cvUmmQura)
    void ummQuraButtonPressed(View v) {
        updateSelectedFilter(Utils.PRAY_MECCA_METHOD, true);
    }

    @OnClick(R.id.cvMuslimLeage)
    void muslimLegaButtonClicked(View v) {
        updateSelectedFilter(Utils.PRAY_MUSLIM_METHOD, true);
    }

    @OnClick(R.id.cvNorthAmerica)
    void northAmericaButtonClicked(View v) {
        updateSelectedFilter(Utils.PRAY_ISLAMIC_METHOD, true);
    }

    @OnClick(R.id.cvEgyptionMethod)
    void egyptionMethodButtonClicked(View v) {
        updateSelectedFilter(Utils.PRAY_EGYPT_METHOD, true);
    }
}
