package com.gama.mutamer.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.shared.Company;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyDetailsSheet extends BottomSheetDialogFragment {

    /***
     * Vars
     */
    Company mCompanyInfo;

    /***
     * Views
     */
    @BindView(R.id.tvCompanyName) TextView tvCompanyName;
    @BindView(R.id.tvPhoneNumber) TextView tvPhoneNumber;


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

    public static CompanyDetailsSheet newInstance(Company company) {

        Bundle args = new Bundle();
        args.putSerializable(Constants.MOTAWEF, company);
        CompanyDetailsSheet fragment = new CompanyDetailsSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_company_sheet, null);
        ButterKnife.bind(this, contentView);
        if (getArguments() != null && getArguments().containsKey(Constants.MOTAWEF)) {
            mCompanyInfo = (Company) getArguments().getSerializable(Constants.MOTAWEF);
            BindUi();
        }
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCallMotawef)
    void buttonCallClicked(View v) {
        if (getActivity() == null) return;
        CommunicationsHelper.Dial(getActivity(), mCompanyInfo.getPhone());
    }

    private void BindUi() {
        if (mCompanyInfo != null) {
            tvCompanyName.setText(mCompanyInfo.getName(new LanguageHelper().getCurrentLanguage(getActivity())));
            tvPhoneNumber.setText(mCompanyInfo.getPhone());
        }
    }

}
