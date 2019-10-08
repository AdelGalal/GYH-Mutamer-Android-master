package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.IClickListener;
import com.gama.mutamer.interfaces.ILanguageChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class PrayersSheet extends BottomSheetDialogFragment implements IClickListener {

    /***
     * Flags
     */
    private static final String LANGUAGE_INDEX_KEY = "LanguageIndex";

    /***
     * Views
     */
    @BindView(R.id.rvPrayers) RecyclerView rvPrayers;

    /***
     * Vars
     */
    private int mLanguageIndex = LanguageHelper.LANGUAGE_ENGLISH_INDEX;
    
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

    public static LanguageSelectorSheet newInstance(int languageIndex) {

        Bundle args = new Bundle();

        LanguageSelectorSheet fragment = new LanguageSelectorSheet();
        args.putInt(LANGUAGE_INDEX_KEY, languageIndex);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_prayer_sheet, null);
        ButterKnife.bind(this, contentView);
        rvPrayers.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvPrayers.setHasFixedSize(true);


        dialog.setContentView(contentView);
        if (getArguments() != null && getArguments().get(LANGUAGE_INDEX_KEY) != null) {
            mLanguageIndex = getArguments().getInt(LANGUAGE_INDEX_KEY);
        }
        setLanguage(mLanguageIndex, false);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void setLanguage(int languageIndex, boolean informListener) {
        mLanguageIndex = languageIndex;

        if (informListener && getActivity() != null && getActivity() instanceof ILanguageChangeListener) {
            new LanguageHelper().changeLanguage(getActivity(), languageIndex);
            ((ILanguageChangeListener) getActivity()).LanguageChanged();
            dismiss();
        }
    }


    @Override public void ItemClicked(Long id) {
        //setLanguage(position, true);
        //TODO: Back here
    }
}
