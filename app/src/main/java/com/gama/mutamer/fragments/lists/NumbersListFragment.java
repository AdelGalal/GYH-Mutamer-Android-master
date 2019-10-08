package com.gama.mutamer.fragments.lists;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.adapters.ImportantNumbersAdapter;
import com.gama.mutamer.data.models.content.ImportantNumber;
import com.gama.mutamer.data.repositories.NumbersRepository;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.interfaces.IImportantNumberClickListener;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NumbersListFragment extends Fragment implements IImportantNumberClickListener {

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

    public NumbersListFragment() {
        // Required empty public constructor
    }

    public static NumbersListFragment newInstance(long categoryId) {
        NumbersListFragment fragment = new NumbersListFragment();
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
        View v = inflater.inflate(R.layout.fragment_numbers_list, container, false);
        ButterKnife.bind(this, v);
        rvPlaces.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rvPlaces.setHasFixedSize(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.FRAGMENT_DATA)) {
            mCategoryId = savedInstanceState.getLong(Constants.FRAGMENT_DATA);
        }
        if (mCategoryId > 0) {
            new GetImportantNumbersAsync(getActivity(), this)
                    .execute(mCategoryId);
        }
        return v;
    }

    private void renderData(ArrayList<ImportantNumber> numbers) {
        vLoading.setVisibility(View.GONE);
        if (numbers == null || numbers.size() == 0) {
            vNoData.setVisibility(View.VISIBLE);
            return;
        }
        rvPlaces.setAdapter(new ImportantNumbersAdapter(numbers, getActivity(), this));
        rvPlaces.setVisibility(View.VISIBLE);
    }


    @Override public void ItemClicked(String id) {
        if (getActivity() == null) {
            return;
        }
        CommunicationsHelper.Dial(getActivity(), id);
    }


    private static class GetImportantNumbersAsync extends AsyncTask<Long, Void, ArrayList<ImportantNumber>> {

        private WeakReference<Activity> activityReference;
        private WeakReference<NumbersListFragment> mFragmentWeakReferences;

        GetImportantNumbersAsync(Activity context, NumbersListFragment fragment) {
            activityReference = new WeakReference<>(context);
            mFragmentWeakReferences = new WeakReference<>(fragment);
        }

        @Override
        protected void onPostExecute(ArrayList<ImportantNumber> response) {
            super.onPostExecute(response);
            Activity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            NumbersListFragment fragment = mFragmentWeakReferences.get();
            if (fragment == null) {
                return;
            }


            if (response != null) {
                fragment.renderData(response);
            } else {
                DialogsHelper.showToast(activity, R.string.unexcepted_error);
            }
        }

        @Override
        protected ArrayList<ImportantNumber> doInBackground(Long... categories) {
            try {
                Activity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) {
                    return null;
                }

                NumbersListFragment fragment = mFragmentWeakReferences.get();
                if (fragment == null) {
                    return null;
                }
                return new NumbersRepository()
                        .getNumbersByCategory(categories[0]);
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
                return null;
            }
        }
    }
}
