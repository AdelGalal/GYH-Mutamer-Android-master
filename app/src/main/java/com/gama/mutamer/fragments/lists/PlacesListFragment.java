package com.gama.mutamer.fragments.lists;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.views.ViewPlaceActivity;
import com.gama.mutamer.adapters.IslamicPlacesAdapter;
import com.gama.mutamer.data.models.content.Place;
import com.gama.mutamer.data.repositories.PlacesRepository;
import com.gama.mutamer.interfaces.IClickListener;
import com.gama.mutamer.interfaces.IIslamicPlaceListener;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlacesListFragment extends Fragment implements IIslamicPlaceListener, IClickListener {


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

    public PlacesListFragment() {
        // Required empty public constructor
    }


    public static PlacesListFragment newInstance(long categoryId) {
        PlacesListFragment fragment = new PlacesListFragment();
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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_places_list, container, false);
        ButterKnife.bind(this, v);
        rvPlaces.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_col_count), LinearLayoutManager.VERTICAL, false));
        rvPlaces.setHasFixedSize(true);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.FRAGMENT_DATA)) {
            mCategoryId = savedInstanceState.getLong(Constants.FRAGMENT_DATA);
        }
        if (mCategoryId != 0) {
            new GetPlacesAsync(getActivity(), this)
                    .execute(mCategoryId);
        }
        return v;
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCategoryId > 0) {
            outState.putLong(Constants.FRAGMENT_DATA, mCategoryId);
        }
    }


    @Override
    public void PlaceAlert(boolean alert) {
        Snackbar.make(vCont, alert ? R.string.place_alert_on : R.string.place_alert_off, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void PlaceBookmarked(boolean favorites) {
        Snackbar.make(vCont, favorites ? R.string.place_favorites_on : R.string.place_favorites_off, Snackbar.LENGTH_LONG).show();
    }

    private void renderData(ArrayList<Place> places) {
        vLoading.setVisibility(View.GONE);
        if (places == null || places.size() == 0) {
            vNoData.setVisibility(View.VISIBLE);
            return;
        }

        rvPlaces.setAdapter(new IslamicPlacesAdapter(places, getActivity(), this));
        rvPlaces.setVisibility(View.VISIBLE);
    }

    @Override public void ItemClicked(Long id) {
        Intent i = new Intent(getActivity(), ViewPlaceActivity.class);
        i.putExtra(Constants.ID, id);
        startActivity(i);
    }


    private static class GetPlacesAsync extends AsyncTask<Long, Void, ArrayList<Place>> {


        private WeakReference<Activity> activityReference;
        private WeakReference<PlacesListFragment> fragmentReference;

        GetPlacesAsync(Activity context, PlacesListFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }


        @Override
        protected ArrayList<Place> doInBackground(Long... categories) {
            PlacesListFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return null;
            }


            return new PlacesRepository()
                    .getPlacesByCategory(categories[0]);

        }

        @Override
        protected void onPostExecute(ArrayList<Place> data) {
            super.onPostExecute(data);
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            PlacesListFragment fragment = fragmentReference.get();
            if (fragment == null) {
                return;
            }

            fragment.renderData(data);
        }


    }

}
