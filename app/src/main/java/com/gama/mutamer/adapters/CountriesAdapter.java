package com.gama.mutamer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.interfaces.ICountryClicked;
import com.gama.mutamer.viewModels.utils.NameViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mustafa on 8/22/17.
 * Release the GEEK
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {

    /***
     * Vars
     */
    private ArrayList<NameViewModel> mResponse;
    private ICountryClicked mListener;
    private Activity mActivity;
    private String mLang;

    public CountriesAdapter(ArrayList<NameViewModel> response, Activity activity, ICountryClicked listener) {
        this.mResponse = response;
        this.mListener = listener;
        this.mActivity = activity;
        mLang = new LanguageHelper().getCurrentLanguage(activity);
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_country, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        NameViewModel language = mResponse.get(position);
        holder.tvCountryName.setText(String.format("%s - %s", language.getName(mLang), language.getCurrencyCode()));
        try {
            holder.ivFlag.setImageDrawable(ContextCompat.getDrawable(mActivity, mActivity.getResources().getIdentifier("a" + language.getId(), "drawable", mActivity.getPackageName())));
        } catch (Exception ex) {
            holder.ivFlag.setImageResource(0);
            ex.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mResponse.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        /***
         * Views
         */
        @BindView(R.id.tvCountryName) TextView tvCountryName;
        @BindView(R.id.ivFlag) ImageView ivFlag;
        @BindView(R.id.vCont) View mRootLayout;

        CountryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.vCont)
        void ItemPressed(View v) {
            if (mListener != null) {
                mListener.CountryClicked(getAdapterPosition());
            }
        }

    }
}
