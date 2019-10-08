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
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.firebase.FirebaseSubscribeHelper;
import com.gama.mutamer.interfaces.ILanguageChangeListener;
import com.gama.mutamer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gama.mutamer.utils.Constants.SELECTED_FILTER_FLAG;

/**
 * Created by mustafa on 3/31/17.
 * Release the GEEK
 */

public class LanguageSelectorSheet extends BottomSheetDialogFragment {

    /***
     * Views
     */
    @BindView(R.id.cvEnglish) CardView cvEnglish;
    @BindView(R.id.cvArabic) CardView cvArabic;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            try {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public static LanguageSelectorSheet newInstance(int languageIndex) {
        Bundle args = new Bundle();
        LanguageSelectorSheet fragment = new LanguageSelectorSheet();
        args.putInt(Constants.SELECTED_FILTER_FLAG, languageIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_language_selector, null);
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
        cvEnglish.setCardBackgroundColor(getResources().getColor(selectedFilter == LanguageHelper.LANGUAGE_ENGLISH_INDEX ? R.color.colorAccent : R.color.white));
        cvArabic.setCardBackgroundColor(getResources().getColor(selectedFilter == LanguageHelper.LANGUAGE_ARABIC_INDEX ? R.color.colorAccent : R.color.white));
        if (informListener && getActivity() != null && getActivity() instanceof ILanguageChangeListener) {
            new SettingsRepository()
                    .updateLanguage(selectedFilter);
            String oldLang = new LanguageHelper().getCurrentLanguage(getActivity());
            new LanguageHelper().changeLanguage(getActivity(), selectedFilter);
            FirebaseSubscribeHelper.SubScribeToLanguage(oldLang, new LanguageHelper().getCurrentLanguage(getActivity()));
            ((ILanguageChangeListener) getActivity()).LanguageChanged();
            dismiss();
        }
    }


    @OnClick(R.id.btnCancel)
    void buttonCancelPressed(View v) {
        dismiss();
    }

    @OnClick(R.id.cvEnglish)
    void englishButtonPressed(View v) {
        updateSelectedFilter(LanguageHelper.LANGUAGE_ENGLISH_INDEX, true);
    }

    @OnClick(R.id.cvArabic)
    void arabicButtonClicked(View v) {
        updateSelectedFilter(LanguageHelper.LANGUAGE_ARABIC_INDEX, true);
    }
}
