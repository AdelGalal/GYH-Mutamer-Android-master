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
import com.gama.mutamer.data.models.content.PlaceCategory;
import com.gama.mutamer.data.repositories.PlacesRepository;
import com.gama.mutamer.fragments.lists.PlacesListFragment;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IslamicPlacesFragment extends BaseFragment {


    /***
     * Vars
     */
    List<PlaceCategory> mCategories = new ArrayList<>();
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


    public IslamicPlacesFragment() {

    }

    public static IslamicPlacesFragment newInstance() {
        return new IslamicPlacesFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_islamic_places, container, false);
        ButterKnife.bind(this, v);
        if (getActivity() != null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

            mLang = new LanguageHelper().getCurrentLanguage(getActivity());
        }
        new GetCategoriesAsync(getParentActivity(), this).execute();
        return v;
    }

    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) ){
            //TODO: Update Data
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.islamic_places));
    }


    private void renderData() {
        vLoading.setVisibility(View.GONE);
        if (mCategories != null && mCategories.size() > 0) {

            mViewPager.removeAllViews();
            tabLayout.removeAllTabs();

            for (PlaceCategory city : mCategories) {
                TabLayout.Tab tab = tabLayout.newTab().setText(city.getName(mLang));
                tabLayout.addTab(tab);
            }
            mSectionsPagerAdapter.notifyDataSetChanged();

            vData.setVisibility(View.VISIBLE);
            return;
        }
        vNoData.setVisibility(View.VISIBLE);
    }


    private static class GetCategoriesAsync extends AsyncTask<Void, Void, Void> {


        private WeakReference<MainActivity> activityReference;
        private WeakReference<IslamicPlacesFragment> fragmentReference;

        GetCategoriesAsync(MainActivity context, IslamicPlacesFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            IslamicPlacesFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return;
            }

            fragment.renderData();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            IslamicPlacesFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return null;
            }

            if (fragment.mCategories != null && fragment.mCategories.size() > 0) {
                return null;
            }

            fragment.mCategories = new PlacesRepository().getCategories();

            return null;
        }


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlacesListFragment.newInstance(mCategories.get(position).getId());
        }

        @Override public long getItemId(int position) {
            return new Date().getTime();
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }
    }
}
