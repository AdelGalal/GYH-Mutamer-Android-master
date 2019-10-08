package com.gama.mutamer.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.fragments.dialogs.NearByFilterSheet;
import com.gama.mutamer.fragments.dialogs.NearByListSheet;
import com.gama.mutamer.fragments.dialogs.ViewPlaceFragment;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.LocationHelper;
import com.gama.mutamer.helpers.PermissionHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.webService.ServicePost;
import com.gama.mutamer.helpers.webService.ServiceResult;
import com.gama.mutamer.interfaces.ISearchChangeListener;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.PlaceSearchViewModel;
import com.gama.mutamer.viewModels.webServices.GooglePlaceResult;
import com.gama.mutamer.webServices.requests.GooglePlaceRequest;
import com.gama.mutamer.webServices.responses.GooglePlacesResponse;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearByFragment extends BaseFragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, ISearchChangeListener {


    /***
     * Vars
     */
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(1000)
            .setFastestInterval(100)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    PlaceSearchViewModel mPlaceSearch = new PlaceSearchViewModel();
    ArrayList<GooglePlaceResult> placesList = new ArrayList<>();
    private boolean shouldAnimateCamera = true;
    private GoogleMap mMap;
    private HashMap<Marker, GooglePlaceResult> mMarkers = new HashMap<>();
    private Location mLocation;
    private String mLang = LanguageHelper.LANGUAGE_ENGLISH;
    LocationCallback callback;


    /***
     * Views
     */
    @BindView(R.id.mvNearBy)
    MapView mvNearBy;


    public NearByFragment() {
        // Required empty public constructor
    }

    public static NearByFragment newInstance() {
        NearByFragment fragment = new NearByFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() == null) return;
        mLang = new LanguageHelper().getCurrentLanguage(getContext());
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        PermissionHelper helper = new PermissionHelper();
        if (!helper.isPermissionsGranted(getActivity(), permissions)) {
            if (helper.shouldAskForPermission(getActivity(), permissions)) {
                helper.askForPermissions(getActivity(), permissions);
            }
        }

        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        if (helper.isPermissionsGranted(getActivity(), permissions)) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.getFusedLocationProviderClient(getActivity()).requestLocationUpdates(REQUEST, callback, Looper.myLooper());
            LocationHelper.checkLocationServiceStatus(getActivity());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setActivityTitle(getString(R.string.near_by));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() == null || callback == null) return;
        LocationServices.getFusedLocationProviderClient(getActivity()).removeLocationUpdates(callback);
    }

    @Override protected void dataChanged(String action) {
        if (action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE)) {
            //TODO: Update Data
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mvNearBy.onResume();
        mMap.setOnMarkerClickListener(this);
        updateMap();

    }

    private void updateMap() {
        if (mMap != null && mLocation != null && getActivity() != null) {

            mMap.clear();
            mMarkers.clear();
            Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude())).title(getString(R.string.you_are_here)));
            mMarkers.put(m, null);
            if (placesList != null) {
                for (GooglePlaceResult place : placesList) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(place.getGeometry().getLocation().getLatitude(), place.getGeometry().getLocation().getLongitude())).title(place.getName()).icon(BitmapDescriptorFactory.fromResource(place.getIcon())));
                    mMarkers.put(marker, place);
                }
            }


            if (shouldAnimateCamera) {
                shouldAnimateCamera = false;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), getCameraZoom()));
            }


        }
    }

    private float getCameraZoom() {
        if (mPlaceSearch.getDistance() <= 1000) return 14;
        if (mPlaceSearch.getDistance() <= 4000) return 13;
        if (mPlaceSearch.getDistance() <= 8000) return 12;
        if (mPlaceSearch.getDistance() <= 16000) return 11;
        return 9;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(mLocation == null) {
            mLocation = location;
            doSearch();
        }
    }

    private void doSearch() {
        if (mLocation == null) {
            return;
        }
        shouldAnimateCamera = true;
        placesList.clear();
        mMarkers.clear();

        if (mMap != null) {
            mMap.clear();
            //
        }
        getPlaces(mLocation.getLatitude(), mLocation.getLongitude(), mPlaceSearch.getType(), mPlaceSearch.getDistance(), mPlaceSearch.getImage(), mPlaceSearch.getImage());
    }

    public void getPlaces(double lat, double lng, String type, int meters, int icon, int fullIcon) {
        new GetPlacesAsync(getParentActivity(), this).execute(new GooglePlaceRequest(lat, lng, meters, type, icon, fullIcon, mLang));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (getActivity() == null) return false;
        GooglePlaceResult id = mMarkers.get(marker);

        if (id != null) {
            ViewPlaceFragment f = ViewPlaceFragment.newInstance(id);
            f.show(getActivity().getSupportFragmentManager(), "TAG");
        }
        return true;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_near_by, container, false);
        ButterKnife.bind(this, v);
        mvNearBy.onCreate(null);
        mvNearBy.getMapAsync(this);
        return v;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    void SearchButtonPressed(View v) {
        if (getActivity() == null) return;
        NearByFilterSheet bottomSheetDialogFragment = NearByFilterSheet.newInstance(mPlaceSearch);
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabList)
    void listButtonPressed(View v) {
        if (getActivity() == null) return;
        NearByListSheet bottomSheetDialogFragment = NearByListSheet.newInstance(placesList);
        bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


    @Override
    public void SearchChanged(PlaceSearchViewModel model) {
        mPlaceSearch = model;
        doSearch();
    }


    private static class GetPlacesAsync extends AsyncTask<GooglePlaceRequest, Void, GooglePlacesResponse> {

        private WeakReference<MainActivity> activityReference;
        private WeakReference<NearByFragment> fragmentReference;

        GetPlacesAsync(MainActivity context, NearByFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        protected GooglePlacesResponse doInBackground(GooglePlaceRequest... requests) {
            try {
                NearByFragment fragment = fragmentReference.get();
                if (fragment == null) return null;


                ServiceResult result = new ServicePost().DoPost(requests[0].getServiceUrl());

                if (result.isSuccess()) {
                    GooglePlacesResponse response = new Gson().fromJson(result.getResult(), GooglePlacesResponse.class);
                    response.UpdateTypes(requests[0].getType());
                    response.UpdateIcons(requests[0].getIcon());
                    response.UpdateDistance(fragment.mLocation);
                    response.UpdateFullIcons(requests[0].getFullIcon());
                    return response;
                }
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(GooglePlacesResponse result) {
            super.onPostExecute(result);
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            NearByFragment fragment = fragmentReference.get();
            if (fragment == null) return;


            if (result != null) {
                fragment.placesList.addAll(result.getPlaces());
                fragment.updateMap();
            }
        }

    }


}
