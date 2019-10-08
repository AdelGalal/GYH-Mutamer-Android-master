package com.gama.mutamer.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.data.models.content.DuaaCategory;
import com.gama.mutamer.data.repositories.PrayersRepository;
import com.gama.mutamer.fragments.lists.DuaaListFragment;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DuaaFragment extends BaseFragment {


    /***
     * Vars
     */
    List<DuaaCategory> categories = new ArrayList<>();
    private String mLang;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /***
     * Views
     */
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.vData) View vData;
    @BindView(R.id.view_no_data) View vNoData;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.container) ViewPager mViewPager;


    public DuaaFragment() {

    }

    public static DuaaFragment newInstance() {
        return new DuaaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_duaa, container, false);
        ButterKnife.bind(this, v);
        if (getActivity() != null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

            mLang = new LanguageHelper().getCurrentLanguage(getActivity());
        }
        new GetDuaaAsync(getParentActivity(), this).execute();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.duaa));
    }

    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) ){
            //TODO: Update Data
        }
    }


    private void renderData() {
        vLoading.setVisibility(View.GONE);
        if (categories != null && categories.size() > 0) {

            mViewPager.removeAllViews();
            tabLayout.removeAllTabs();

            for (DuaaCategory city : categories) {
                TabLayout.Tab tab = tabLayout.newTab().setText(city.getName(mLang));
                tabLayout.addTab(tab);
            }
            mSectionsPagerAdapter.notifyDataSetChanged();

            vData.setVisibility(View.VISIBLE);
            return;
        }
        vNoData.setVisibility(View.VISIBLE);
    }


    public static class GetDuaaAsync extends AsyncTask<Void, Void, Void> {

        private WeakReference<MainActivity> activityReference;
        private WeakReference<DuaaFragment> fragmentReference;

        GetDuaaAsync(MainActivity context, DuaaFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }


        @Override
        protected Void doInBackground(Void... voids) {

            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }


            DuaaFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return null;
            }

            if (fragment.categories.size() > 0) {
                return null;
            }

            fragment.mLang = new LanguageHelper().getCurrentLanguage(activity);
            fragment.categories = new PrayersRepository().getAllCategories();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            DuaaFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return;
            }

            fragment.renderData();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Map<Integer, String> mMap;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DuaaListFragment.newInstance(categories.get(position).getId());
        }

        @Override public long getItemId(int position) {
            return new Date().getTime();
        }


        @Override
        public int getCount() {
            return categories.size();
        }
    }
}
