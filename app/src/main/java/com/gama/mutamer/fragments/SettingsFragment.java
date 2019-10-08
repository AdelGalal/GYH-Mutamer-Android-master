package com.gama.mutamer.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.general.Setting;
import com.gama.mutamer.data.repositories.CommonRepository;
import com.gama.mutamer.fragments.dialogs.AzanCalculationMethodSheet;
import com.gama.mutamer.fragments.dialogs.AzanNotificationSheet;
import com.gama.mutamer.fragments.dialogs.LanguageSelectorSheet;
import com.gama.mutamer.fragments.dialogs.UnitsSelectorSheet;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsFragment extends BaseFragment {

    /***
     * Views
     */
    @BindView(R.id.tvLanguage) TextView tvLanguage;
    @BindView(R.id.tvUnits) TextView tvUnits;
    @BindView(R.id.tvPrayCalcType) TextView tvPrayCalcType;
    @BindView(R.id.tvNotificationType) TextView tvNotificationType;


    /***
     * Vars
     */
    Setting setting;

    /***
     * Constructor
     */

    public SettingsFragment() {
        // Required empty public constructor
    }

    /***
     * Constructor factory method
     * @return new Setting fragment instance
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    /***
     * Life Cycle
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.settings));
        UpdateSettings();
    }

    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) ){
            //TODO: Update Data
        }
    }


    public void UpdateSettings() {
        if (getActivity() == null) {
            return;
        }

        setting = new CommonRepository().getSettings();
        Resources resources = getResources();
        tvLanguage.setText(resources.getStringArray(R.array.languages)[setting.getLanguage()]);
        tvUnits.setText(resources.getStringArray(R.array.units)[setting.getUnitType()]);
        tvNotificationType.setText(resources.getStringArray(R.array.notifications_methods)[setting.getPrayerNotificationType()]);
        tvPrayCalcType.setText(resources.getStringArray(R.array.calculation_methods)[setting.getPrayerMethod()]);
    }


    /***
     * Events Handling
     */


    @OnClick(R.id.llLanguage)
    void languageCardClicked(View v) {
        if (getActivity() == null){
            return;
        }
        LanguageSelectorSheet bottomSheetDialogFragment = LanguageSelectorSheet.newInstance(new LanguageHelper().getCurrentLanguageIndex(getActivity()));
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


    @OnClick(R.id.llUnits)
    void unitsCardClicked(View v) {
        if (getActivity() == null){
            return;
        }
        UnitsSelectorSheet bottomSheetDialogFragment = UnitsSelectorSheet.newInstance(setting.getUnitType());
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


    @OnClick(R.id.llAzanCalculationMethod)
    void prayCalculationCardClicked(View v) {
        if (getActivity() == null){
            return;
        }
        AzanCalculationMethodSheet bottomSheetDialogFragment = AzanCalculationMethodSheet.newInstance(setting.getPrayerMethod());
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


    @OnClick(R.id.llAzanNotificationType)
    void praysNotificationsCardClicked(View v) {
        if (getActivity() == null){
            return;
        }
        AzanNotificationSheet bottomSheetDialogFragment = AzanNotificationSheet.newInstance(setting.getPrayerNotificationType());
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


}
