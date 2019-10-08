package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gama.mutamer.R;
import com.gama.mutamer.helpers.CustomComparator;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.MapHelper;
import com.gama.mutamer.viewModels.webServices.GooglePlaceResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/11/17.
 * Release the GEEK
 */

public class NearByPlacesAdapter extends RecyclerView.Adapter<NearByPlacesAdapter.NearByPlaceViewHolder> {

    /***
     * Vars
     */
    private ArrayList<GooglePlaceResult> mPlaces;
    private Locale mLocale;
    private Activity mActivity;


    public NearByPlacesAdapter(ArrayList<GooglePlaceResult> places, Activity activity) {
        this.mPlaces = places;
        Collections.sort(mPlaces, new CustomComparator());
        this.mActivity = activity;
        mLocale = new LanguageHelper().getCurrentLocale(activity);
    }

    @NonNull
    @Override
    public NearByPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NearByPlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_near_by_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NearByPlaceViewHolder holder, int position) {
        GooglePlaceResult place = mPlaces.get(position);
        holder.ivPlaceType.setImageResource(0);
        holder.ivPlaceType.setImageResource(place.getFullIcon());
        holder.tvPlaceName.setText(place.getName());
        holder.tvPlaceAddress.setText(place.getAddress());
        if (place.getDistance() > 1000) {
            holder.tvPlaceDistance.setText(String.format(mLocale, "%.2f %s", place.getDistance() / 1000, mActivity.getString(R.string.km)));
        } else {
            holder.tvPlaceDistance.setText(String.format(mLocale, "%d %s", (int) place.getDistance(), mActivity.getString(R.string.meter)));
        }
        if (place.getLogo() != null && place.getLogo().length() > 0) {
            Glide.with(mActivity)
                    .load(place.getLogo())
                    .apply(new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(place.getFullIcon()))
                    .into(holder.ivPlaceType);
        }
        //holder.mRootLayout.setBackgroundColor(prayer.isCurrent() ? ResourcesCompat.getColor(mActivity.getResources(), R.color.colorPrimary, mActivity.getTheme()) : ResourcesCompat.getColor(mActivity.getResources(), R.color.white, mActivity.getTheme()));
        //setAnimation(holder.itemView, position);
    }


//    private void setAnimation(View viewToAnimate, int position) {
//        Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
//        viewToAnimate.startAnimation(animation);
//    }

//    @Override public void onViewDetachedFromWindow(NearByPlaceViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        holder.clearAnimation();
//    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class NearByPlaceViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.ivPlaceType) ImageView ivPlaceType;
        @BindView(R.id.tvPlaceName) TextView tvPlaceName;
        @BindView(R.id.tvPlaceAddress) TextView tvPlaceAddress;
        @BindView(R.id.tvPlaceDistance) TextView tvPlaceDistance;
        @BindView(R.id.vCont) View mRootLayout;

        NearByPlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressWarnings("unused")
        @OnClick(R.id.vCont)
        void parentViewClicked(View v) {
            GooglePlaceResult placeResult = mPlaces.get(getAdapterPosition());
            MapHelper.gotoLocation(mActivity, placeResult.getGeometry().getLocation().getLatitude(), placeResult.getGeometry().getLocation().getLongitude());
        }

        @SuppressWarnings("unused")
        @OnClick(R.id.btnNavigate)
        void navigateButtonClicked(View v) {

            GooglePlaceResult placeResult = mPlaces.get(getAdapterPosition());
            MapHelper.gotoLocation(mActivity, placeResult.getGeometry().getLocation().getLatitude(), placeResult.getGeometry().getLocation().getLongitude());
        }


    }
}
