package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.Place;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.IIslamicPlaceListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/3/17.
 * Release the GEEK
 */

public class IslamicPlacesAdapter extends RecyclerView.Adapter<IslamicPlacesAdapter.CityViewHolder> {

    /***
     * Vars
     */
    private ArrayList<Place> mPlaces;
    private IIslamicPlaceListener mListener;
    private int lastPosition = -1;
    private Activity mActivity;
    private String mLang;

    public IslamicPlacesAdapter(ArrayList<Place> places, Activity activity, IIslamicPlaceListener listener) {
        this.mPlaces = places;
        this.mListener = listener;
        this.mActivity = activity;
        mLang = new LanguageHelper().getCurrentLanguage(activity);
    }

    @NonNull
    @Override
    public IslamicPlacesAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        Place place = mPlaces.get(position);
        if (place != null) {
            holder.tvPlaceName.setText(place.getName(mLang));
            Glide.with(mActivity)
                    .load(mActivity.getString(R.string.url_place) + place.getId() + ".png")
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.mipmap.ic_launcher_round))
                    .into(holder.ivPlaceImage);
            setAnimation(holder.itemView, position);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CityViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.tvPlaceName) TextView tvPlaceName;
        @BindView(R.id.ivPlaceImage) ImageView ivPlaceImage;
        @BindView(R.id.vCont) View mRootLayout;

        CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /***
         * Uis Actions
         */

        @OnClick(R.id.vCont)
        void itemPressed(View v) {
            if (mListener != null)
                mListener.ItemClicked(mPlaces.get(getAdapterPosition()).getId());
        }


        void clearAnimation() {
            mRootLayout.clearAnimation();
        }

    }
}
