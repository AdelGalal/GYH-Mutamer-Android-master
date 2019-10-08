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
import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.IDuaaClickListener;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/14/16.
 * Release the GEEK
 */
public class DuaaAdapter extends RecyclerView.Adapter<DuaaAdapter.DuaaViewHolder> {

    /***
     * Vars
     */
    private ArrayList<Duaa> mDuaas;
    private IDuaaClickListener mListener;
    private int lastPosition = -1;
    private Activity mActivity;
    private String mLang;

    public DuaaAdapter(ArrayList<Duaa> duaas, Activity activity, IDuaaClickListener listener) {
        this.mDuaas = duaas;
        this.mListener = listener;
        this.mActivity = activity;
        mLang = new LanguageHelper().getCurrentLanguage(activity);
    }

    @NonNull
    @Override
    public DuaaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DuaaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_duaa, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DuaaViewHolder holder, int position) {
        Duaa duaa = mDuaas.get(position);
        if (duaa == null) return;
        holder.tvDuaaTitle.setText(duaa.getName(mLang));
        setAnimation(holder.itemView, position);

        Glide.with(mActivity)
                .load(String.format(new Locale("en-us"), mActivity.getString(R.string.url_duaa_image), duaa.getId()))
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.ic_launcher_round))
                .into(holder.ivDuaaImage);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull DuaaViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mDuaas.size();
    }

    class DuaaViewHolder extends RecyclerView.ViewHolder {


        /***
         * Views
         */
        @BindView(R.id.tvDuaaTitle) TextView tvDuaaTitle;
        @BindView(R.id.vCont) View mRootLayout;
        @BindView(R.id.ivDuaaImage) ImageView ivDuaaImage;


        DuaaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vCont)
        void itemPressed(View v) {
            if (mListener != null)
                mListener.PlayDuaa(mDuaas.get(getAdapterPosition()));
        }



        void clearAnimation() {
            mRootLayout.clearAnimation();
        }

    }
}