package com.gama.mutamer.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.PilgrimData.MutamerInfo;
import com.gama.mutamer.data.models.User;
import com.gama.mutamer.data.models.general.Setting;
import com.gama.mutamer.data.repositories.CommonRepository;
import com.gama.mutamer.data.repositories.NotificationsRepository;
import com.gama.mutamer.data.repositories.UsersRepository;
import com.gama.mutamer.fragments.DuaaFragment;
import com.gama.mutamer.fragments.HomeFragment;
import com.gama.mutamer.fragments.IslamicPlacesFragment;
import com.gama.mutamer.fragments.IssuesFragment;
import com.gama.mutamer.fragments.MyGalleryFragment;
import com.gama.mutamer.fragments.MyInfoFragment;
import com.gama.mutamer.fragments.MyProgramFragment;
import com.gama.mutamer.fragments.NearByFragment;
import com.gama.mutamer.fragments.NotificationsFragment;
import com.gama.mutamer.fragments.ServicesFragment;
import com.gama.mutamer.fragments.SettingsFragment;
import com.gama.mutamer.helpers.BroadcastHelper;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.LocationHelper;
import com.gama.mutamer.helpers.PermissionHelper;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.uiHelpers.WeakReferencesHelper;
import com.gama.mutamer.helpers.webService.ServicePost;
import com.gama.mutamer.helpers.webService.ServiceResult;
import com.gama.mutamer.interfaces.ICityChangeListener;
import com.gama.mutamer.interfaces.IDuaaChangeListener;
import com.gama.mutamer.interfaces.IFilterChangeFragmentListener;
import com.gama.mutamer.interfaces.IFilterChangeListener;
import com.gama.mutamer.interfaces.ILanguageChangeListener;
import com.gama.mutamer.interfaces.ISearchChangeListener;
import com.gama.mutamer.services.FccRegistrationService;
import com.gama.mutamer.services.StartNotificationService;
import com.gama.mutamer.services.SyncService;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.PlaceSearchViewModel;
import com.gama.mutamer.viewModels.webServices.LocationViewModel;
import com.gama.mutamer.webServices.requests.PanicRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IFilterChangeListener, ILanguageChangeListener, ISearchChangeListener, LocationListener, IDuaaChangeListener, ICityChangeListener {


    /***
     * Vars
     */
    Fragment fragment = null;
    private int mLastFragmentId = R.id.nav_home;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(1000)
            .setFastestInterval(100)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    public static Location mLocation;
    public static Setting setting;
    LocationCallback callback;


    /***
     * Views
     */
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private TextView tvNotifications, tvUserName, tvUserEmail;
    private ImageView ivUserImage;
    private Button btnLogin, btnLogout;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            new LanguageHelper().initLanguage(this);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            //drawer.setDrawerListener(toggle);
            toggle.syncState();
            new getSettingsAsync(this, false).execute();

            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(0).setChecked(true);
            tvNotifications = (TextView) navigationView.getMenu().findItem(R.id.nav_notifications).getActionView();
            tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
            ivUserImage = navigationView.getHeaderView(0).findViewById(R.id.ivUserImage);
            tvUserEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmailAddress);
            btnLogin = navigationView.getHeaderView(0).findViewById(R.id.btnLogin);
            btnLogout = navigationView.getHeaderView(0).findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(view -> logOut());

            btnLogin.setOnClickListener(view -> startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Constants.LOGIN_SUCCESS));


            navigateToFragment((mLastFragmentId != 0) ? mLastFragmentId : R.id.nav_home);


            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startService(new Intent(this, FccRegistrationService.class));
                startService(new Intent(this, SyncService.class));
                startService(new Intent(this, StartNotificationService.class));
            });
            t.start();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        askFormPermissions();
        updateLoginRelatedUI();
        //NotificationsHelper.showNotification(this,"Demo","Demo",1);
    }


    @Override
    protected void onResume() {
        try {
            super.onResume();

            new UpdateCountersAsync(this).execute();
            callback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    onLocationChanged(locationResult.getLastLocation());
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(REQUEST, callback, Looper.myLooper());

        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    /***
     * Update login relared UIs
     * Like login/logout button visibility , user name view and user image
     */
    void updateLoginRelatedUI() {
        try {
            User user = new UsersRepository()
                    .getLoggedUser();
            MutamerInfo info = new CommonRepository().getInfo();
            btnLogin.setVisibility(user != null ? View.GONE : View.VISIBLE);
            btnLogout.setVisibility(user != null ? View.VISIBLE : View.GONE);
            tvUserName.setText(user != null ? user.getDisplayName() : getString(R.string.not_logged));
            tvUserEmail.setText(user != null ? user.getUserName() : getString(R.string.empty_string));
            ivUserImage.setImageResource(R.mipmap.ic_launcher_round);
            if (info != null && user != null) {

                Glide.with(this)
                        .load(String.format(Locale.UK, getString(R.string.url_pilgrim_image), info.getCompany().getId(), info.getPilgrim().getId()))
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.ic_launcher_round))
                        .into(ivUserImage);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.get(Constants.LAST_FRAGMENT_ID) != null) {
            mLastFragmentId = savedInstanceState.getInt(Constants.LAST_FRAGMENT_ID);
        }
        navigateToFragment(mLastFragmentId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.LAST_FRAGMENT_ID, mLastFragmentId);
    }


    private void startLocationServices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(REQUEST, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onLocationChanged(locationResult.getLastLocation());
            }
        }, Looper.myLooper());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Constants.LOGIN_SUCCESS) {
                updateLoginRelatedUI();
            }


        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (callback == null) return;
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(callback);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            if (location == null) return;

            float diff = 1001;
            if (mLocation != null) {
                diff = location.distanceTo(mLocation);
            } else {
                mLocation = location;
                if (fragment != null && fragment.getClass() == HomeFragment.class) {
                    ((HomeFragment) fragment).LocationUpdated();
                }
            }
            if (diff > 1000) {
                mLocation = location;
                SharedPrefHelper.setSharedFloat(this, Constants.KEY_LATITUDE, (float) mLocation.getLatitude());
                SharedPrefHelper.setSharedFloat(this, Constants.KEY_LONGITUDE, (float) mLocation.getLongitude());
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.close_umra_app)).setMessage(getString(R.string.close_app_message)).setPositiveButton(getString(R.string.close), (dialog, which) -> {
                dialog.cancel();
                finish();
            }).setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void navigateToFragment(Fragment fragment) {

        if (fragment != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            //To avoid the issue caused by view pager caching fragment and display an empty list
//            for (Fragment item : getSupportFragmentManager().getFragments()) {
//                ft.remove(item);
//            }

            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.fade_out);
            ft.replace(R.id.vPlaceHolder, fragment, String.valueOf(new Date().getTime()));
            ft.commitAllowingStateLoss();
        }

    }

    public void setMainTitle(String title) {
        setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        navigateToFragment(item.getItemId());
        return true;
    }

    public void navigateToFragment(int id) {
        drawer.closeDrawer(GravityCompat.START);
        if (mLastFragmentId != 0) {
            Fragment lastFragment = getSupportFragmentManager().findFragmentById(mLastFragmentId);
            if (lastFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(lastFragment).commit();
            }
        }
        mLastFragmentId = id;
        if (id == R.id.nav_home) {
            fragment = HomeFragment.newInstance();

        } else if (id == R.id.nav_notifications) {
            fragment = NotificationsFragment.newInstance();
        } else if (id == R.id.nav_services) {
            fragment = new ServicesFragment();

        } else if (id == R.id.nav_issues) {
            fragment = IssuesFragment.newInstance();

//        } else if (id == R.id.nav_report_issue) {
//            fragment = ReportIssueFragment.newInstance();
//            addToBackStack = true;
        } else if (id == R.id.nav_near_by) {
            fragment = NearByFragment.newInstance();

        } else if (id == R.id.nav_duaa) {
            fragment = DuaaFragment.newInstance();

        } else if (id == R.id.nav_islamic_places) {
            fragment = IslamicPlacesFragment.newInstance();
//
        } else if (id == R.id.nav_gallery) {
            fragment = MyGalleryFragment.newInstance();

        } else if (id == R.id.nav_my_info) {
            fragment = new MyInfoFragment();
        } else if (id == R.id.nav_my_program) {
            fragment = new MyProgramFragment();
        } else if (id == R.id.nav_settings) {
            fragment = SettingsFragment.newInstance();
        }
        navigateToFragment(fragment);
    }


    private void askFormPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            };
            PermissionHelper helper = new PermissionHelper();
            if (!helper.isPermissionsGranted(this, permissions)) {
                if (helper.shouldAskForPermission(this, permissions)) {
                    helper.askForPermissions(this, permissions);
                }
            }
        }
        LocationHelper.checkLocationServiceStatus(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //navigateToFragment(mLastFragmentId);
        //askFormPermissions();
        new UpdateCountersAsync(this).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(getString(R.string.logout), (dialog, which) -> new LogoffAsync(this).execute())
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void filterChanged(int Code, int newFilter) {
        switch (Code) {
            case Constants.Notification_FILTER_FLAG:
                if (fragment != null && fragment.getClass() == NotificationsFragment.class)
                    ((IFilterChangeFragmentListener) fragment).filterChanged(newFilter);
                break;
            case Constants.UNITS_FILTER_FLAG:
                if (fragment != null && fragment.getClass() == SettingsFragment.class)
                    ((SettingsFragment) fragment).UpdateSettings();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startLocationServices();
    }

    @Override
    public void LanguageChanged() {
        new LanguageHelper().initLanguage(this);
        recreate();
    }

    @Override public void LanguageChanged(int languageIndex) {
        //No Need
    }

    @Override
    public void SearchChanged(PlaceSearchViewModel model) {
        if (fragment != null && fragment.getClass() == NearByFragment.class) {
            ((ISearchChangeListener) fragment).SearchChanged(model);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabPanic)
    void buttonPanicPressed(View v) {
        if (mLocation != null)
            new SendPanicAsync(this).execute();
//        else
////            DialogsHelper.getAlert(this, getString(R.string.error), getString(R.string.enable_gps), getString(R.string.ok)).show();

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabImportantNumbers)
    void buttonImportantNumbersClicked(View v) {
        startActivity(new Intent(this, ImportantNumbersActivity.class));
    }


    @Override
    public void DuaaFilterChanged(int groupIndex) {
        if (fragment != null && fragment.getClass() == DuaaFragment.class)
            ((IDuaaChangeListener) fragment).DuaaFilterChanged(groupIndex);
    }

    @Override
    public void HajjCityFilterChanged(int cityIndex) {
        if (fragment != null && fragment.getClass() == IslamicPlacesFragment.class)
            ((ICityChangeListener) fragment).HajjCityFilterChanged(cityIndex);
    }

    public void updateNavigation() {
        setting = new CommonRepository().getSettings();
    }

    public static class getSettingsAsync extends AsyncTask<Void, Void, Void> {

        private WeakReference<MainActivity> activityWeakReference;
        private boolean navigateToInfo;

        getSettingsAsync(MainActivity activity, boolean navigateToInfo) {
            activityWeakReference = new WeakReference<>(activity);
            this.navigateToInfo = navigateToInfo;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return null;

            setting = new CommonRepository().getSettings();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;

            if (navigateToInfo)
                activity.navigateToFragment(R.id.nav_my_info);
        }
    }

    private static class LogoffAsync extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<Activity> activityReference;

        LogoffAsync(MainActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Activity activity = WeakReferencesHelper.getActivity(activityReference);
            if (activity instanceof MainActivity) {
                if (aBoolean) {
                    ((MainActivity) activity).updateLoginRelatedUI();
                }
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Activity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) {
                    return null;
                }

                new UsersRepository()
                        .logOut();
                BroadcastHelper.sendInform(activity, Constants.USER_LOGGED_OFF);
                return true;
            } catch (Exception ex) {
                FirebaseErrorEventLog.SaveEventLog(ex);
                return null;
            }
        }


    }

    private static class UpdateCountersAsync extends AsyncTask<Void, Void, Long> {

        private WeakReference<MainActivity> activityReference;

        UpdateCountersAsync(MainActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPostExecute(Long counts) {
            super.onPostExecute(counts);
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            if (counts != null)
                activity.tvNotifications.setText(String.valueOf(counts));
        }

        @Override
        protected Long doInBackground(Void... params) {
            try {
                return new NotificationsRepository().getUnreadNotificationsCount();
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
                return null;
            }
        }
    }

    private static class SendPanicAsync extends AsyncTask<Void, Void, ServiceResult> {

        private WeakReference<MainActivity> activityReference;

        SendPanicAsync(MainActivity activity) {
            activityReference = new WeakReference<>(activity);
        }


        @Override
        protected ServiceResult doInBackground(Void... params) {
            try {
                MainActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) return null;
                PanicRequest request = new PanicRequest(new LocationViewModel(mLocation.getLatitude(), mLocation.getLongitude()));
                return new ServicePost().DoPost(request, false, activity);
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ServiceResult response) {
            super.onPostExecute(response);
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            if (response != null)
                DialogsHelper.showToast(activity, response.isSuccess() ? R.string.send_panic_success : R.string.send_panic_fail);
        }
    }


}
