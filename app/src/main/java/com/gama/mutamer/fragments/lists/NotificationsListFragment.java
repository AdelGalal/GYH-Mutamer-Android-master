package com.gama.mutamer.fragments.lists;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.activities.views.NotificationDetailActivity;
import com.gama.mutamer.adapters.NotificationsAdapter;
import com.gama.mutamer.data.models.general.Notification;
import com.gama.mutamer.data.repositories.NotificationsRepository;
import com.gama.mutamer.fragments.BaseFragment;
import com.gama.mutamer.interfaces.IClickListener;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationsListFragment extends BaseFragment implements IClickListener {


    /***
     * Vars
     */
    private Constants.FilterType mFilterType;


    /***
     * Views
     */
    @BindView(R.id.rvPlaces) RecyclerView rvPlaces;
    @BindView(R.id.vCont) View vCont;
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.view_no_data) View vNoData;


    public static NotificationsListFragment newInstance(Constants.FilterType filterType) {
        NotificationsListFragment fragment = new NotificationsListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.FRAGMENT_DATA, filterType.ordinal());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFilterType = Constants.FilterType.values()[getArguments().getInt(Constants.FRAGMENT_DATA)];
        }
    }

    @Override protected void dataChanged(String action) {
        //TODO: Update Date
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notifications_list, container, false);
        ButterKnife.bind(this, v);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.FRAGMENT_DATA)) {
            mFilterType = Constants.FilterType.values()[savedInstanceState.getInt(Constants.FRAGMENT_DATA)];
        }
        rvPlaces.setHasFixedSize(true);
        if (mFilterType != null) {
            new GetNotificationsAsync(getParentActivity(), this)
                    .execute(mFilterType);
        }
        return v;
    }


    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getInt(Constants.FRAGMENT_DATA, mFilterType.ordinal());
    }

    private void renderData(ArrayList<Notification> notifications) {
        vLoading.setVisibility(View.GONE);
        if (notifications == null || notifications.size() == 0) {
            vNoData.setVisibility(View.VISIBLE);
            return;
        }

        rvPlaces.setAdapter(new NotificationsAdapter(notifications, getActivity(), this));
        rvPlaces.setVisibility(View.VISIBLE);
    }

    @Override public void ItemClicked(Long id) {
        Intent intent = new Intent(getActivity(), NotificationDetailActivity.class);
        intent.putExtra(Constants.FRAGMENT_DATA, id);
        startActivity(intent);
    }

    private static class GetNotificationsAsync extends AsyncTask<Constants.FilterType, Void, ArrayList<Notification>> {


        private WeakReference<MainActivity> activityReference;
        private WeakReference<NotificationsListFragment> fragmentReference;


        GetNotificationsAsync(MainActivity context, NotificationsListFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        protected ArrayList<Notification> doInBackground(Constants.FilterType... params) {
            NotificationsListFragment fragment = fragmentReference.get();
            if (fragment == null) return null;

            return new NotificationsRepository()
                    .getFiltered(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Notification> notifications) {
            super.onPostExecute(notifications);

            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;


            NotificationsListFragment fragment = fragmentReference.get();
            if (fragment == null) return;

            fragment.renderData(notifications);
        }
    }

}
