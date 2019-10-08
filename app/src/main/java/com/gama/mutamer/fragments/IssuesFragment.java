package com.gama.mutamer.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.AddIssueActivity;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.adapters.IssuesAdapter;
import com.gama.mutamer.data.models.Issue;
import com.gama.mutamer.data.repositories.IssuesRepository;
import com.gama.mutamer.data.repositories.UsersRepository;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.KeyboardHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.interfaces.IClickListener;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IssuesFragment extends BaseFragment implements IClickListener {

    /***
     * Views
     */
    @BindView(R.id.rvIssues) RecyclerView rvIssues;
    @BindView(R.id.view_loading) View vLoading;
    @BindView(R.id.view_no_data) View vNoData;

    /***
     * Vars
     */
    private ArrayList<Issue> mIssues = new ArrayList<>();

    public IssuesFragment() {
        // Required empty public constructor
    }


    public static IssuesFragment newInstance() {

        Bundle args = new Bundle();

        IssuesFragment fragment = new IssuesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_issues, container, false);
        ButterKnife.bind(this, v);
        rvIssues.setLayoutManager(new GridLayoutManager(getParentActivity(), getResources().getInteger(R.integer.issues_grid_count), LinearLayoutManager.VERTICAL, false));
        new GetIssuesAsync(getParentActivity(), this).execute();
        return v;
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_issues, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_issue) {
            addIssue();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        setActivityTitle(getString(R.string.issues));
    }

    void addIssue() {
        if (getActivity() == null) {
            return;
        }
        new KeyboardHelper().Hide(getActivity());
        if (new UsersRepository().getLoggedUser() == null) {
            DialogsHelper.showToast(getActivity(), R.string.please_login_to_able_to_submit_issue);
            return;
        }
        startActivityForResult(new Intent(getActivity(), AddIssueActivity.class), Constants.CODE_ADD_ISSUE);
    }


    private void switchUi(int state) {
        rvIssues.setVisibility(state == Constants.STATE_DATA ? View.VISIBLE : View.GONE);
        vLoading.setVisibility(state == Constants.STATE_LOADING ? View.VISIBLE : View.GONE);
        vNoData.setVisibility(state == Constants.STATE_NO_DATA ? View.VISIBLE : View.GONE);
    }

    @Override public void ItemClicked(Long id) {
        //TODO: Open Details
    }


    @Override protected void dataChanged(String action) {
        if (action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE)) {
            new GetIssuesAsync(getParentActivity(), this)
                    .execute();
        }
    }


    public static class GetIssuesAsync extends AsyncTask<Void, Void, Void> {


        private WeakReference<MainActivity> activityReference;
        private WeakReference<IssuesFragment> fragmentReference;

        GetIssuesAsync(MainActivity context, IssuesFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            IssuesFragment fragment = fragmentReference.get();
            if (fragment == null) return;

            fragment.rvIssues.setAdapter(new IssuesAdapter(fragment.mIssues, activity, fragment));
            fragment.switchUi(fragment.mIssues.size() > 0 ? Constants.STATE_DATA : Constants.STATE_NO_DATA);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                IssuesFragment fragment = fragmentReference.get();
                if (fragment == null) return null;

                fragment.mIssues = new IssuesRepository()
                        .getAllPendingIssues();

            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
            return null;
        }
    }


}
