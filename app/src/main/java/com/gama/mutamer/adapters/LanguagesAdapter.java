package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.interfaces.ILanguageChangeListener;
import com.gama.mutamer.viewModels.shared.Language;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.LanguageViewHolder> {

    /***
     * Vars
     */
    private ArrayList<Language> mLanguages;
    private ILanguageChangeListener mListener;
    private Activity mActivity;
    private int lastPosition = -1;

    public LanguagesAdapter(ArrayList<Language> languages, Activity activity, ILanguageChangeListener listener) {
        this.mLanguages = languages;
        this.mListener = listener;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_language, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        Language language = mLanguages.get(position);
        holder.tvLanguageName.setText(mActivity.getString(language.getNameResource()));
        holder.ivLanguageFlag.setImageDrawable(ContextCompat.getDrawable(mActivity, language.getImage()));
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
    public void onViewDetachedFromWindow(@NonNull LanguageViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.ivLanguageFlag) ImageView ivLanguageFlag;
        @BindView(R.id.tvLanguageName) TextView tvLanguageName;
        @BindView(R.id.vCont) View mRootLayout;

        LanguageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vCont)
        void ItemPressed(View v) {
            if (mListener != null) {
                mListener.LanguageChanged(getAdapterPosition());
            }
        }

        void clearAnimation() {
            mRootLayout.clearAnimation();
        }

    }
}
