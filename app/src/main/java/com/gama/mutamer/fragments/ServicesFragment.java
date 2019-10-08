package com.gama.mutamer.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.CompassActivity;
import com.gama.mutamer.activities.ExchangeActivity;
import com.gama.mutamer.activities.ImportantNumbersActivity;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ServicesFragment extends BaseFragment {


    /***
     * Constructor
     */
    public ServicesFragment() {
        // Required empty public constructor
    }


    /***
     * Life cycle
     */


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_services, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.services));
    }

    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) ){
            //TODO: Update Data
        }
    }

    /***
     * Events
     */

    @OnClick(R.id.llQibla)
    void qiblaButtonPressed(View v) {
        launchActivity(CompassActivity.class);
    }

    @OnClick(R.id.llExchange)
    void exchangeButtonPressed(View v) {
        launchActivity(ExchangeActivity.class);
    }


    @OnClick(R.id.llImportantNumbers)
    void importantNumbersPressed(View v) {
        launchActivity(ImportantNumbersActivity.class);
    }


}
