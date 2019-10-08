package com.gama.mutamer.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.ImportantNumberCategory;
import com.gama.mutamer.data.repositories.NumbersRepository;
import com.gama.mutamer.fragments.lists.NumbersListFragment;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportantNumbersActivity extends AppCompatActivity {


    /***
     * Vars
     */
    List<ImportantNumberCategory> categories = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_numbers);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.important_numbers));
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mLang = new LanguageHelper().getCurrentLanguage(this);
        new GetImportantNumbersCategoriesAsync(this).execute();
    }

    private void renderData() {
        vLoading.setVisibility(View.GONE);
        if (categories != null && categories.size() > 0) {

            mViewPager.removeAllViews();
            tabLayout.removeAllTabs();

            for (ImportantNumberCategory city : categories) {
                TabLayout.Tab tab = tabLayout.newTab().setText(city.getName(mLang));
                tabLayout.addTab(tab);
            }
            mSectionsPagerAdapter.notifyDataSetChanged();

            vData.setVisibility(View.VISIBLE);
        }
        vNoData.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static class GetImportantNumbersCategoriesAsync extends AsyncTask<Void, Void, ArrayList<ImportantNumberCategory>> {

        private WeakReference<ImportantNumbersActivity> activityReference;

        GetImportantNumbersCategoriesAsync(ImportantNumbersActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPostExecute(ArrayList<ImportantNumberCategory> response) {
            super.onPostExecute(response);
            ImportantNumbersActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;


            if (response != null) {
                activity.categories = response;
                activity.renderData();
            } else {
                DialogsHelper.showToast(activity, R.string.unexcepted_error);
            }
        }

        @Override
        protected ArrayList<ImportantNumberCategory> doInBackground(Void... voids) {
            try {
                return new NumbersRepository().getData();
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
                return null;
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NumbersListFragment.newInstance(categories.get(position).getId());
        }

        @Override
        public int getCount() {
            return categories.size();
        }
    }
}
