package com.gama.mutamer.fragments;

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
import com.gama.mutamer.fragments.lists.NotificationsListFragment;
import com.gama.mutamer.utils.Constants;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationsFragment extends BaseFragment {


    /***
     * Vars
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    /***
     * Views
     */
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.vData) View vData;
    @BindView(R.id.view_no_data) View vNoData;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.container) ViewPager mViewPager;


    public NotificationsFragment() {
        // Required empty public constructor
    }


    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, v);
        if (getActivity() != null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

            renderData();
        }
        return v;
    }



    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) ){
            //TODO: Update
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.notifications));
    }

    private void renderData() {
        vLoading.setVisibility(View.GONE);

        mViewPager.removeAllViews();
        tabLayout.removeAllTabs();
        TabLayout.Tab tab = tabLayout.newTab().setText(getString(R.string.unread));
        tabLayout.addTab(tab);
        tab = tabLayout.newTab().setText(getString(R.string.read));
        tabLayout.addTab(tab);
        tab = tabLayout.newTab().setText(getString(R.string.all));
        tabLayout.addTab(tab);
        mSectionsPagerAdapter.notifyDataSetChanged();

        vData.setVisibility(View.VISIBLE);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NotificationsListFragment.newInstance(Constants.FilterType.values()[position]);
        }

        @Override public long getItemId(int position) {
            return new Date().getTime();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
