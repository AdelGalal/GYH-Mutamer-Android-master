package com.gama.mutamer.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.LoginActivity;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.adapters.HotelsAdapter;
import com.gama.mutamer.adapters.TransportationAdapter;
import com.gama.mutamer.data.models.PilgrimData.Arrival;
import com.gama.mutamer.data.models.PilgrimData.Departure;
import com.gama.mutamer.data.models.PilgrimData.Hotel;
import com.gama.mutamer.data.models.PilgrimData.Transportation;
import com.gama.mutamer.data.repositories.CommonRepository;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.uiHelpers.ResourcesHelper;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.uiRelated.MutamerProgram;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyProgramFragment extends BaseFragment {

    /***
     * Vars
     */
    private MutamerProgram mProgram;
    private String mLang;


    /***
     * Views
     */
    @BindView(R.id.view_no_data) View vNoData;
    @BindView(R.id.vData) View vData;
    @BindView(R.id.view_loading) View vLoading;

    //Arrival
    @BindView(R.id.vArrival) View vArrival;
    @BindView(R.id.tv_arrival_date) TextView tvArrivalDate;
    @BindView(R.id.tv_arrival_time) TextView tvArrivalTime;
    @BindView(R.id.iv_arrival_from_city_flag) ImageView ivArrivalFromCityCountryFlag;
    @BindView(R.id.tv_arrival_from_city) TextView tvArrivalFromCity;
    @BindView(R.id.iv_arrival_to_city_flag) ImageView ivArrivalToCityCountryFlag;
    @BindView(R.id.tv_arrival_to_city) TextView tvArrivalToCity;
    @BindView(R.id.tv_arrival_carrier) TextView tvArrivalCarrier;
    @BindView(R.id.tv_arrival_terminal) TextView tvArrivalTerminal;
    @BindView(R.id.tv_arrival_driver_name) TextView tvArrivalDriverName;
    @BindView(R.id.tv_arrival_driver_mobile) TextView tvArrivalDriverMobile;
    @BindView(R.id.tv_arrival_driver_plate_number) TextView tvArrivalPlateNumber;

    //Departure
    @BindView(R.id.vDeparture) View vDeparture;
    @BindView(R.id.tv_departure_date) TextView tvDepartureDate;
    @BindView(R.id.tv_departure_time) TextView tvDepartureTime;
    @BindView(R.id.iv_departure_from_city_flag) ImageView ivDepartureFromCityCountryFlag;
    @BindView(R.id.tv_departure_from_city) TextView tvDepartureFromCity;
    @BindView(R.id.iv_departure_to_city_flag) ImageView ivDepartureToCityCountryFlag;
    @BindView(R.id.tv_departure_to_city) TextView tvDepartureToCity;
    @BindView(R.id.tv_departure_carrier) TextView tvDepartureCarrier;
    @BindView(R.id.tv_departure_terminal) TextView tvDepartureTerminal;
    @BindView(R.id.tv_departure_driver_name) TextView tvDepartureDriverName;
    @BindView(R.id.tv_departure_driver_mobile) TextView tvDepartureDriverMobile;
    @BindView(R.id.tv_departure_driver_plate_number) TextView tvDeparturePlateNumber;

    //Hotels
    @BindView(R.id.vHotels) View vHotels;
    @BindView(R.id.rv_hotels) RecyclerView rvHotels;

    //Transportation
    @BindView(R.id.vTransportations) View vTransportation;
    @BindView(R.id.rv_transportations) RecyclerView rvTransportation;

    public MyProgramFragment() {
        // Required empty public constructor
    }


    public static MyProgramFragment newInstance() {
        MyProgramFragment fragment = new MyProgramFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLang = new LanguageHelper().getCurrentLanguage(getActivity());
    }

    @Override protected void dataChanged(String action) {
        if (action.equalsIgnoreCase(Constants.MUTAMER_PROFILE_FETCHED) || action.equalsIgnoreCase(Constants.USER_LOGGED_OFF)) {
            new GetMyProgramAsync(getParentActivity(), this)
                    .execute();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_my_program, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.nav_my_program));
        if (mProgram == null) {
            new GetMyProgramAsync(getParentActivity(), this)
                    .execute();
        }
    }

    private void bindUi() {

        if (getParentActivity() == null) {
            return;
        }
        vLoading.setVisibility(View.GONE);
        if (mProgram != null) {
            vNoData.setVisibility(View.GONE);
            vData.setVisibility(View.VISIBLE);


            Arrival arrival = mProgram.getArrival();
            vArrival.setVisibility(arrival == null ? View.GONE : View.VISIBLE);
            if (arrival != null) {
                tvArrivalDate.setText(arrival.getDate());
                tvArrivalTime.setText(arrival.getTime());
                if(arrival.getSource() != null) {
                    ivArrivalFromCityCountryFlag.setImageResource(ResourcesHelper.getFlagIcon(getParentActivity(), arrival.getSource().getCountryId()));
                    tvArrivalFromCity.setText(arrival.getSource().getName(mLang));
                }
                if(arrival.getDestination() != null) {
                    ivArrivalToCityCountryFlag.setImageResource(ResourcesHelper.getFlagIcon(getParentActivity(), arrival.getDestination().getCountryId()));
                    tvArrivalToCity.setText(arrival.getDestination().getName(mLang));
                }
                if(arrival.getAirLine().getCarrier() != null) {
                    tvArrivalCarrier.setText(arrival.getAirLine().getCarrier().getName(mLang));
                }
                if(arrival.getAirLine() != null && arrival.getAirLine().getTerminal() != null) {
                    tvArrivalTerminal.setText(arrival.getAirLine().getTerminal().getName(mLang));
                }
                if(arrival.getDriver() != null) {
                    tvArrivalDriverName.setText(arrival.getDriver().getName(mLang));
                    tvArrivalDriverMobile.setText(arrival.getDriver().getNumber());
                    tvArrivalPlateNumber.setText(arrival.getVehicle().getPlateNumber());
                }

            }

            ArrayList<Hotel> hotels = mProgram.getHotels();
            vHotels.setVisibility(hotels != null && hotels.size() > 0 ? View.VISIBLE : View.GONE);
            if (hotels != null && hotels.size() > 0) {
                rvHotels.setLayoutManager(new LinearLayoutManager(getParentActivity(), LinearLayoutManager.VERTICAL, false));
                rvHotels.setAdapter(new HotelsAdapter(hotels, mLang));
            }

            ArrayList<Transportation> transportation = mProgram.getTransportations();
            vTransportation.setVisibility(transportation != null && transportation.size() > 0 ? View.VISIBLE : View.GONE);
            if (transportation != null && transportation.size() > 0) {
                rvTransportation.setLayoutManager(new LinearLayoutManager(getParentActivity(), LinearLayoutManager.VERTICAL, false));
                rvTransportation.setAdapter(new TransportationAdapter(transportation, getParentActivity(), mLang));
            }

            Departure departure = mProgram.getDeparture();
            vDeparture.setVisibility(departure == null ? View.GONE : View.VISIBLE);
            if (departure != null) {
                tvDepartureDate.setText(departure.getDate());
                tvDepartureTime.setText(departure.getTime());

                if(departure.getSource() != null) {
                    ivDepartureFromCityCountryFlag.setImageResource(ResourcesHelper.getFlagIcon(getParentActivity(), departure.getSource().getCountryId()));
                    tvDepartureFromCity.setText(departure.getSource().getName(mLang));
                }
                if(departure.getDestination() != null) {
                    ivDepartureToCityCountryFlag.setImageResource(ResourcesHelper.getFlagIcon(getParentActivity(), departure.getDestination().getCountryId()));
                    tvDepartureToCity.setText(departure.getDestination().getName(mLang));
                }
                if(departure.getAirLine() != null) {
                    if(departure.getAirLine().getCarrier() != null) {
                        tvDepartureCarrier.setText(departure.getAirLine().getCarrier().getName(mLang));
                    }
                    if(departure.getAirLine().getTerminal() != null) {
                        tvDepartureTerminal.setText(departure.getAirLine().getTerminal().getName(mLang));
                    }
                }
                if(departure.getDriver() != null) {
                    tvDepartureDriverName.setText(departure.getDriver().getName(mLang));
                    tvDepartureDriverMobile.setText(departure.getDriver().getNumber());
                    tvDeparturePlateNumber.setText(departure.getVehicle().getPlateNumber());
                }

            }
            vNoData.setVisibility(View.GONE);
        } else {
            vData.setVisibility(View.GONE);
            vNoData.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_login)
    void buttonLoginClicked(View v) {
        startActivityForResult(new Intent(getParentActivity(), LoginActivity.class), Constants.LOGIN_SUCCESS);
    }


    @OnClick(R.id.ivCallArrivalDriver)
    void buttonCallArrivalDriverClicked(View v) {
        if (mProgram != null && mProgram.getArrival() != null && mProgram.getArrival().getDriver() != null) {
            CommunicationsHelper.Dial(getParentActivity(), mProgram.getArrival().getDriver().getNumber());
        }
    }

    @OnClick(R.id.ivCallDepartureDriver)
    void buttonCallDepartureDriverClicked(View v) {
        if (mProgram != null && mProgram.getDeparture() != null && mProgram.getDeparture().getDriver() != null) {
            CommunicationsHelper.Dial(getParentActivity(), mProgram.getDeparture().getDriver().getNumber());
        }
    }


    private static class GetMyProgramAsync extends AsyncTask<Void, Void, MutamerProgram> {


        private WeakReference<MainActivity> mActivityWeakReference;
        private WeakReference<MyProgramFragment> mFragmentWeakReference;

        GetMyProgramAsync(MainActivity activity, MyProgramFragment fragment) {
            mActivityWeakReference = new WeakReference<>(activity);
            mFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected MutamerProgram doInBackground(Void... params) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            MyProgramFragment fragment = mFragmentWeakReference.get();
            if (fragment == null) {
                return null;
            }

            return new CommonRepository()
                    .getProgram();
        }

        @Override
        protected void onPostExecute(MutamerProgram response) {
            super.onPostExecute(response);
            MainActivity activity = mActivityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            MyProgramFragment fragment = mFragmentWeakReference.get();
            if (fragment == null) {
                return;
            }

            fragment.mProgram = response;
            fragment.bindUi();

        }
    }

}
