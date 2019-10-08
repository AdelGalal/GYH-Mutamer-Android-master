package com.gama.mutamer.fragments.lists;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.views.DuaaDetailsActivity;
import com.gama.mutamer.adapters.DuaaAdapter;
import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.data.repositories.PrayersRepository;
import com.gama.mutamer.fragments.BaseFragment;
import com.gama.mutamer.interfaces.IDuaaClickListener;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DuaaListFragment extends BaseFragment implements IDuaaClickListener {


    /***
     * Vars
     */
    private long mCategoryId;


    /***
     * Views
     */
    @BindView(R.id.rvPlaces) RecyclerView rvPlaces;
    @BindView(R.id.vCont) View vCont;
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.view_no_data) View vNoData;


    public DuaaListFragment() {
        // Required empty public constructor
    }

    public static DuaaListFragment newInstance(long categoryId) {
        DuaaListFragment fragment = new DuaaListFragment();
        Bundle args = new Bundle();
        args.putLong(Constants.FRAGMENT_DATA, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getLong(Constants.FRAGMENT_DATA);
        }
    }

    @Override protected void dataChanged(String action) {
            //TODO: Update Date
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_duaa_list, container, false);
        ButterKnife.bind(this, v);

        rvPlaces.setLayoutManager(new GridLayoutManager(getActivity(),getResources().getInteger( R.integer.grid_col_count), LinearLayoutManager.VERTICAL, false));
        rvPlaces.setHasFixedSize(true);

        if(savedInstanceState != null && savedInstanceState.containsKey(Constants.FRAGMENT_DATA)){
            mCategoryId = savedInstanceState.getLong(Constants.FRAGMENT_DATA);
        }
        if (mCategoryId != 0) {
            new GetDuaaAsync(getActivity(), this)
                    .execute(mCategoryId);
        }
        return v;
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mCategoryId > 0){
            outState.putLong(Constants.FRAGMENT_DATA,mCategoryId);
        }
    }

    @Override
    public void PlayDuaa(Duaa duaa) {
        if (getActivity() == null){
            return;
        }
        Intent intent = new Intent(getActivity(), DuaaDetailsActivity.class);
        intent.putExtra(Constants.ID, duaa.getId());
        startActivity(intent);
    }

    private void renderData(ArrayList<Duaa> list) {
        vLoading.setVisibility(View.GONE);
        if (list == null || list.size() == 0) {
            vNoData.setVisibility(View.VISIBLE);
            return;
        }
        rvPlaces.setAdapter(new DuaaAdapter(list, getActivity(), this));
        rvPlaces.setVisibility(View.VISIBLE);
    }

    @Override public void ItemClicked(Long id) {

    }


    public static class GetDuaaAsync extends AsyncTask<Long, Void, ArrayList<Duaa>> {

        private WeakReference<Activity> activityReference;
        private WeakReference<DuaaListFragment> fragmentReference;

        GetDuaaAsync(Activity context, DuaaListFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }


        @Override
        protected ArrayList<Duaa> doInBackground(Long... categories) {

            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;


            DuaaListFragment fragment = fragmentReference.get();
            if (fragment == null) return null;

            return new PrayersRepository().getListByCategory(categories[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Duaa> data) {
            super.onPostExecute(data);

            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            DuaaListFragment fragment = fragmentReference.get();
            if (fragment == null) return;

            fragment.renderData(data);
        }
    }


}
