package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.PilgrimData.Transportation;
import com.gama.mutamer.helpers.CommunicationsHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 12/11/18.
 * Release the GEEK
 */
public class TransportationAdapter extends RecyclerView.Adapter<TransportationAdapter.TransportationViewHolder> {

    /***
     * Vars
     */
    private ArrayList<Transportation> mTransportations;
    private Activity mActivity;
    private String mLang;

    public TransportationAdapter(ArrayList<Transportation> transportations, Activity activity, String lang) {
        this.mTransportations = transportations;
        this.mActivity = activity;
        mLang = lang;
    }

    @NonNull
    @Override
    public TransportationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransportationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transportation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransportationViewHolder holder, int position) {
        Transportation transportation = mTransportations.get(position);
        if (transportation != null) {
            holder.tvTransportationDate.setText(transportation.getDate());
            holder.tvTransportationTime.setText(transportation.getTime());
            holder.tvSource.setText(transportation.getSource().getName(mLang));
            holder.tvDestination.setText(transportation.getDestination().getName(mLang));
            holder.tvDriverName.setText(transportation.getDriver().getName(mLang));
            holder.tvDriverMobile.setText(transportation.getDriver().getNumber());
            holder.tvPlateNumber.setText(transportation.getVehicle().getPlateNumber());
        }
    }


    @Override
    public int getItemCount() {
        return mTransportations.size();
    }

    class TransportationViewHolder extends RecyclerView.ViewHolder {


        /***
         * Views
         */
        @BindView(R.id.tv_transportation_date) TextView tvTransportationDate;
        @BindView(R.id.tv_transportation_time) TextView tvTransportationTime;
        @BindView(R.id.tv_transportation_source) TextView tvSource;
        @BindView(R.id.tv_transportation_to_destination) TextView tvDestination;
        @BindView(R.id.tv_driver_name) TextView tvDriverName;
        @BindView(R.id.tv_driver_mobile) TextView tvDriverMobile;
        @BindView(R.id.tv_driver_plate_number) TextView tvPlateNumber;
        @BindView(R.id.vCont) View mRootLayout;

        TransportationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ivCallDriver)
        void callDriverButtonClicked(View v) {
            Transportation transportation = mTransportations.get(getAdapterPosition());
            if (transportation != null) {
                //TODO: Change this implementation
                CommunicationsHelper.Dial(mActivity, transportation.getDriver().getNumber());
            }
        }

    }
}
