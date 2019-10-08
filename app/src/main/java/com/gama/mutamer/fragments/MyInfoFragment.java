package com.gama.mutamer.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gama.mutamer.R;
import com.gama.mutamer.activities.LoginActivity;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.data.models.PilgrimData.Agent;
import com.gama.mutamer.data.models.PilgrimData.MutamerInfo;
import com.gama.mutamer.data.models.PilgrimData.Pilgrim;
import com.gama.mutamer.data.models.PilgrimData.UmrahCompany;
import com.gama.mutamer.data.repositories.CommonRepository;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyInfoFragment extends BaseFragment {

    /***
     * Views
     */
    @BindView(R.id.view_no_data) View vNoData;
    @BindView(R.id.vData) View vData;
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.ivCountry) ImageView ivCountry;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.iv_mutamer_image) ImageView ivMutamerImage;
    @BindView(R.id.tv_age) TextView tvAge;
    @BindView(R.id.tvBloodType) TextView tvBloodType;
    @BindView(R.id.tv_address) TextView tvAddress;
    @BindView(R.id.tv_passport_number) TextView tvPassportNumber;
    @BindView(R.id.tv_visa_number) TextView tvVisaNumber;
    @BindView(R.id.tv_mofa_number) TextView tv_mofa_number;
    @BindView(R.id.tv_moi_number) TextView tv_moi_number;

    @BindView(R.id.iv_umrah_company) ImageView ivUmrahCompany;
    @BindView(R.id.tv_umrah_company) TextView tvUmrahCompany;
    @BindView(R.id.tv_umrah_address) TextView tvUmrahCompanyAddress;
    @BindView(R.id.tv_umrah_phone) TextView tvUmraCompanyPhone;
    @BindView(R.id.tv_umrah_email_address) TextView tvUmraCompanyEmailAddress;
    @BindView(R.id.tv_umrah_website) TextView tvUmraCompanyWebSite;

    @BindView(R.id.iv_agent) ImageView IvAgent;
    @BindView(R.id.tv_agent) TextView tvAgent;
    @BindView(R.id.tv_agent_responsible) TextView tvAgentPerson;
    @BindView(R.id.tv_agent_phone) TextView tvAgentPhone;
    @BindView(R.id.tv_agent_mobile_number) TextView tvAgentMobileNumber;
    @BindView(R.id.tv_agent_email) TextView tvAgentEmailAddress;


    /***
     * Vars
     */
    private MutamerInfo info;
    private String mLang;


    public MyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        super.onCreate(savedInstanceState);
        mLang = new LanguageHelper().getCurrentLanguage(getActivity());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_info, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.nav_my_info));
        if (info == null) {
            new GetMyInfoAsync(getParentActivity(), this)
                    .execute();
        }
    }

    @Override protected void dataChanged(String action) {
        if (action.equalsIgnoreCase(Constants.MUTAMER_PROFILE_FETCHED) || action.equalsIgnoreCase(Constants.USER_LOGGED_OFF)) {
            new GetMyInfoAsync(getParentActivity(), this)
                    .execute();
        }
    }

    private void bindUi() {
        if (getParentActivity() == null) {
            return;
        }
        vLoading.setVisibility(View.GONE);

        if (info != null && info.getPilgrim() != null) {
            Pilgrim pilgrim = info.getPilgrim();

            tvName.setText(pilgrim.getName(mLang));
            tvAge.setText(pilgrim.getAge());
            tvBloodType.setText(pilgrim.getBloodType());
            tvAddress.append(pilgrim.getCity());
            if (pilgrim.getNationality() != null) {
                tvAddress.append(" - " + pilgrim.getNationality().getName(mLang));
            }
            tvPassportNumber.setText(pilgrim.getPassportNumber());
            tv_mofa_number.setText(pilgrim.getMofaNumber());
            tvVisaNumber.setText(pilgrim.getVisaNumber());
            tv_moi_number.setText(pilgrim.getMoiNumber());
            final int resourceId = getResources().getIdentifier(String.format("a%s", pilgrim.getNationality().getId()), "drawable",
                    getParentActivity().getPackageName());
            ivCountry.setImageResource(resourceId);

            Glide.with(getActivity())
                    .load(String.format(Locale.UK, getString(R.string.url_pilgrim_image),
                            info.getCompany().getId(), info.getPilgrim().getId()))
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_contact))
                    .into(ivMutamerImage);


            UmrahCompany company = info.getCompany();
            if (company != null) {
                tvUmrahCompany.setText(company.getName(mLang));
                tvUmrahCompanyAddress.setText(company.getAddress());
                tvUmraCompanyPhone.setText(company.getPhoneNumber());
                tvUmraCompanyEmailAddress.setText(company.getEmailAddress());
                tvUmraCompanyWebSite.setText(company.getWebSite());
                Glide.with(getActivity())
                        .load(String.format(Locale.UK,getString(R.string.url_company_logo), company.getId()))
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.ic_contact))
                        .into(ivUmrahCompany);
            }

            Agent agent = info.getAgent();
            if (agent != null) {
                tvAgent.setText(agent.getName(mLang));
                tvAgentPerson.setText(agent.getContactPerson());
                tvAgentPhone.setText(agent.getPhoneNumber());
                tvAgentMobileNumber.setText(agent.getMobileNumber());
                tvAgentEmailAddress.setText(agent.getEmailAddress());
            }

            vData.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.btn_call_umra_company)
    void callUmraCompanyButtonClicked(View v) {
        if (getParentActivity() == null) {
            return;
        }
        if (info != null && info.getCompany() != null) {
            CommunicationsHelper.Dial(getParentActivity(), info.getCompany().getPhoneNumber());
        }
    }

    @OnClick(R.id.btn_call_agent_phone)
    void callAgentPhoneButtonClicked(View v) {
        if (getParentActivity() == null) {
            return;
        }
        if (info != null && info.getAgent() != null) {
            CommunicationsHelper.Dial(getParentActivity(), info.getAgent().getPhoneNumber());
        }
    }

    @OnClick(R.id.btn_email_agent_mobile)
    void callAgentMobileButtonClicked(View v) {
        if (getParentActivity() == null) {
            return;
        }
        if (info != null && info.getAgent() != null) {
            CommunicationsHelper.Dial(getParentActivity(), info.getAgent().getMobileNumber());
        }
    }

    @OnClick(R.id.btn_email_umra_company)
    void emailUmraCompanyButtonClicked(View v) {
        if (getParentActivity() == null) {
            return;
        }
        if (info != null && info.getCompany() != null) {
            CommunicationsHelper.openUrl(getParentActivity(), "mailto:" + info.getCompany().getEmailAddress());
        }
    }

    @OnClick(R.id.btn_agent_email)
    void emailAgentButtonClicked(View v) {
        if (getParentActivity() == null) {
            return;
        }
        if (info != null && info.getAgent() != null) {
            CommunicationsHelper.openUrl(getParentActivity(), "mailto:" + info.getAgent().getEmailAddress());
        }
    }

    @OnClick(R.id.btn_umra_company_website)
    void umraCompanyWebSiteButtonClicked(View v) {
        if (getParentActivity() == null) {
            return;
        }
        if (info != null && info.getCompany() != null) {
            CommunicationsHelper.openUrl(getParentActivity(), info.getCompany().getWebSite());
        }
    }


    private static class GetMyInfoAsync extends AsyncTask<Void, Void, MutamerInfo> {


        private WeakReference<MainActivity> mActivityWeakReference;
        private WeakReference<MyInfoFragment> mFragmentWeakReference;

        GetMyInfoAsync(MainActivity activity, MyInfoFragment fragment) {
            mActivityWeakReference = new WeakReference<>(activity);
            mFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        protected MutamerInfo doInBackground(Void... params) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            MyInfoFragment fragment = mFragmentWeakReference.get();
            if (fragment == null) {
                return null;
            }

            return new CommonRepository()
                    .getInfo();
        }

        @Override
        protected void onPostExecute(MutamerInfo response) {
            super.onPostExecute(response);
            MainActivity activity = mActivityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            MyInfoFragment fragment = mFragmentWeakReference.get();
            if (fragment == null) {
                return;
            }

            fragment.info = response;
            fragment.bindUi();

        }
    }

}
