package com.gama.mutamer.fragments.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.gama.mutamer.R;
import com.gama.mutamer.adapters.CountriesAdapter;
import com.gama.mutamer.data.repositories.NationalitiesRepository;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.ICountryChangeListener;
import com.gama.mutamer.interfaces.ICountryClicked;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.NameViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class CountryFilterFragment extends BottomSheetDialogFragment implements ICountryClicked {

    /***
     * Flags
     */
    private static final String COUNTRY_INDEX_KEY = "DuaaIndex",
            COUNTRY_CATEGORIES_KEY = "categoriesIndex",
            CODE_FLAG = "codeFlag";

    /***
     * Views
     */
    @BindView(R.id.rvCountries) RecyclerView rvCountries;
    @BindView(R.id.etSearch) EditText etSearch;

    /***
     * Vars
     */
    private int mSelectedIndex = 0, code = 0;
    private ArrayList<NameViewModel> mResponse;
    ArrayList<NameViewModel> result = new ArrayList<>();
    private String mLang = LanguageHelper.LANGUAGE_ENGLISH;


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

    public static CountryFilterFragment newInstance(ArrayList<NameViewModel> response, int selectedIndex, int code, String lang) {

        Bundle args = new Bundle();

        CountryFilterFragment fragment = new CountryFilterFragment();
        args.putSerializable(COUNTRY_CATEGORIES_KEY, response);
        args.putInt(COUNTRY_INDEX_KEY, selectedIndex);
        args.putString(Constants.LANGUAGE, lang);
        args.putInt(CODE_FLAG, code);

        fragment.setArguments(args);
        return fragment;
    }

    @OnTextChanged(R.id.etSearch)
    void searchEditChanged(Editable editable) {
        String search = editable.toString().toLowerCase();
        result = new ArrayList<>();
        if (search.length() == 0) {
            result = mResponse;
        } else {
            for (NameViewModel model : mResponse) {
                if (model.getEnglishName().toLowerCase().contains(search) ||
                        model.getArabicName().toLowerCase().contains(search) ||
                        model.getCurrencyCode().toLowerCase().contains(search)) {
                    result.add(model);
                }
            }
        }
        rvCountries.setAdapter(new CountriesAdapter(result, getActivity(), this));
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        new LanguageHelper().initLanguage(getActivity());
        View contentView = View.inflate(getContext(), R.layout.fragment_country_filter, null);
        ButterKnife.bind(this, contentView);
        rvCountries.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvCountries.setHasFixedSize(true);
        if (getArguments() != null && getArguments().get(COUNTRY_INDEX_KEY) != null) {
            mSelectedIndex = getArguments().getInt(COUNTRY_INDEX_KEY);
            mResponse = new NationalitiesRepository().getExchangeList(mLang);
            mLang = getArguments().getString(Constants.LANGUAGE, LanguageHelper.LANGUAGE_ENGLISH);
            result = (ArrayList<NameViewModel>) mResponse.clone();
            code = getArguments().getInt(CODE_FLAG);
        }
        rvCountries.setAdapter(new CountriesAdapter(mResponse, getActivity(), this));
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private void selectCategory(int selectedIndex) {
        mSelectedIndex = selectedIndex;
        if (getActivity() != null && getActivity() instanceof ICountryChangeListener) {
            NameViewModel item = result.get(mSelectedIndex);
            int looper = 0;
            for (NameViewModel record : mResponse) {
                if (record.getId() == item.getId()) {
                    mSelectedIndex = looper;
                }
                looper++;
            }
            ((ICountryChangeListener) getActivity()).CountryChanged(mSelectedIndex, code);
        }
        dismiss();
    }

    @OnClick(R.id.btnCancel) void buttonCancelPressed(View v) {
        dismiss();
    }


    @Override public void CountryClicked(int index) {
        selectCategory(index);

    }
}

