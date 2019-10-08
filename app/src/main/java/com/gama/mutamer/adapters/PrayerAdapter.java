package com.gama.mutamer.adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.viewModels.shared.Pray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.PrayerViewHolder> {


    /***
     * Vars
     */
    private ArrayList<Pray> mPrayers;
    private TypedArray Images;
    private Activity mActivity;
    private int lastPosition = -1;

    public PrayerAdapter(ArrayList<Pray> prayers, Activity activity) {
        this.mPrayers = prayers;
        this.mActivity = activity;
        Images = activity.getResources().obtainTypedArray(R.array.prays_icons);
    }

    @NonNull
    @Override
    public PrayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrayerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prayer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrayerViewHolder holder, int position) {
        Pray prayer = mPrayers.get(position);
        holder.tvPrayerName.setText(prayer.getName());
        holder.tvPrayerTime.setText(prayer.getDate());
        holder.ivPrayIcon.setImageResource(Images.getResourceId(prayer.getId(), R.drawable.ic_pray));


        holder.mRootLayout.setBackgroundColor(prayer.isCurrent() ? ResourcesCompat.getColor(mActivity.getResources(), R.color.colorAccent, mActivity.getTheme()) : ResourcesCompat.getColor(mActivity.getResources(), R.color.white, mActivity.getTheme()));
        setAnimation(holder.itemView, position);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PrayerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mPrayers.size();
    }

    class PrayerViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.tvPrayerName) TextView tvPrayerName;
        @BindView(R.id.tvPrayerTime) TextView tvPrayerTime;
        @BindView(R.id.vCont) CardView mRootLayout;
        @BindView(R.id.ivPrayIcon) ImageView ivPrayIcon;

        PrayerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void clearAnimation() {
            mRootLayout.clearAnimation();
        }

    }
}

