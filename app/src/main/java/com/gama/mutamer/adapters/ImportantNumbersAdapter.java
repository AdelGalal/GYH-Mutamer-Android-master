package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.ImportantNumber;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.IImportantNumberClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/22/17.
 * Release the GEEK
 */

public class ImportantNumbersAdapter extends RecyclerView.Adapter<ImportantNumbersAdapter.NumberViewHolder> {


    /***
     * Vars
     */
    private ArrayList<ImportantNumber> mNumbers;
    private IImportantNumberClickListener mListener;
    private String mLang;
    private int lastPosition = -1;
    private Activity mActivity;

    public ImportantNumbersAdapter(ArrayList<ImportantNumber> numbers, Activity activity, IImportantNumberClickListener listener) {
        this.mNumbers = numbers;
        this.mListener = listener;
        this.mActivity = activity;
        mLang = new LanguageHelper().getCurrentLanguage(activity);
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_important_number, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        ImportantNumber number = mNumbers.get(position);
        if (number == null) {
            return;
        }
        holder.tvName.setText(number.getName(mLang));
        holder.tvNumber.setText(number.getNumber());
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
    public void onViewDetachedFromWindow(@NonNull NumberViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mNumbers.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {


        /***
         * Views
         */
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvNumber) TextView tvNumber;
        @BindView(R.id.vCont) View mRootLayout;

        NumberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vCont)
        void itemPressed(View v) {
            if (mListener != null) {
                mListener.ItemClicked(mNumbers.get(getAdapterPosition()).getNumber());
            }
        }


        void clearAnimation() {
            mRootLayout.clearAnimation();
        }

    }
}
