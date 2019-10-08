package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gama.mutamer.R;
import com.gama.mutamer.adapters.NearByPlacesAdapter;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.viewModels.webServices.GooglePlaceResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mustafa on 8/11/17.
 * Release the GEEK
 */

public class NearByListSheet extends BottomSheetDialogFragment {

    /***
     * Flags
     */
    private static final String PLACES_LIST_TAG = "placesList";

    /***
     * Views
     */
    @BindView(R.id.rvPlaces)
    RecyclerView rvPlaces;

    /***
     * Vars
     */
    private ArrayList<GooglePlaceResult> mPlaces;
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

    public static NearByListSheet newInstance(ArrayList<GooglePlaceResult> places) {

        Bundle args = new Bundle();
        args.putSerializable(PLACES_LIST_TAG, places);
        NearByListSheet fragment = new NearByListSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        // super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_places_sheet, null);
        ButterKnife.bind(this, contentView);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvPlaces.setHasFixedSize(true);
        if (getArguments() != null && getArguments().containsKey(PLACES_LIST_TAG)) {
            mPlaces = (ArrayList<GooglePlaceResult>) getArguments().getSerializable(PLACES_LIST_TAG);
            rvPlaces.setAdapter(new NearByPlacesAdapter(mPlaces, getActivity()));
        }
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }


    //TODO: Handle click back to fragment

}