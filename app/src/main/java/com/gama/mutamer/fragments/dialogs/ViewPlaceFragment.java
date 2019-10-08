package com.gama.mutamer.fragments.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.MapHelper;
import com.gama.mutamer.viewModels.webServices.GooglePlaceResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewPlaceFragment extends BottomSheetDialogFragment {
    /***
     * Flags
     */
    public static final String VALUE_KEY = "key";

    /***
     * Views
     */
    @BindView(R.id.tvPlaceName)
    TextView tvPlaceName;
    @BindView(R.id.tvPlaceAddress)
    TextView tvPlaceAddress;
    @BindView(R.id.ivPlaceType)
    ImageView ivPlaceType;
    @BindView(R.id.tvPlaceDistance)
    TextView tvPlaceDistance;
    /***
     * Vars
     */
    private GooglePlaceResult mSchedule;


    public ViewPlaceFragment() {
        // Required empty public constructor
    }

    public static ViewPlaceFragment newInstance(GooglePlaceResult schedule) {

        Bundle args = new Bundle();
        args.putSerializable(VALUE_KEY, schedule);
        ViewPlaceFragment fragment = new ViewPlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_view_place, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null && getArguments().containsKey(VALUE_KEY)) {
            mSchedule = (GooglePlaceResult) getArguments().getSerializable(VALUE_KEY);
            if (mSchedule != null) {
                tvPlaceName.setText(mSchedule.getName());
                tvPlaceAddress.setText(mSchedule.getAddress());
                ivPlaceType.setImageResource(mSchedule.getFullIcon());
                tvPlaceDistance.setText(String.format(new LanguageHelper().getCurrentLocale(getActivity()), "%.2f %s", mSchedule.getDistance() / 1000, getString(R.string.km)));
            }
        }
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;
            View view = d.findViewById(R.id.design_bottom_sheet);
            if (view != null) {
                BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnNavigate)
    void buttonNavigateClicked(View v) {
        if (getActivity() == null) return;
        MapHelper.gotoLocation(getActivity(), mSchedule.getGeometry().getLocation().getLatitude(), mSchedule.getGeometry().getLocation().getLongitude());
    }


}
