package com.gama.mutamer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.PilgrimData.Hotel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mustafa on 12/11/18.
 * Release the GEEK
 */
public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.HotelViewHolder> {

    /***
     * Vars
     */
    private ArrayList<Hotel> mHotels;
    private String mLang;

    public HotelsAdapter(ArrayList<Hotel> hotels, String lang) {
        this.mHotels = hotels;
        mLang = lang;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hotel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = mHotels.get(position);
        if (hotel != null) {
            holder.tvHotelName.setText(hotel.getDetail().getName(mLang));
            holder.tvHotelCity.setText(hotel.getDetail().getCityName(mLang));
            holder.tvHotelFromDate.setText(hotel.getStartDate());
            holder.tvHotelToDate.setText(hotel.getEndDate());
        }
    }

    @Override
    public int getItemCount() {
        return mHotels.size();
    }

    class HotelViewHolder extends RecyclerView.ViewHolder {


        /***
         * Views
         */
        @BindView(R.id.tv_hotel_name) TextView tvHotelName;
        @BindView(R.id.tv_hotel_city) TextView tvHotelCity;
        @BindView(R.id.tv_hotel_from_date) TextView tvHotelFromDate;
        @BindView(R.id.tv_hotel_to_date) TextView tvHotelToDate;
        @BindView(R.id.vCont) View mRootLayout;

        HotelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
