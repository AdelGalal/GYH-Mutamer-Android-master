package com.gama.mutamer.activities.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.Place;
import com.gama.mutamer.data.repositories.PlacesRepository;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.MapHelper;
import com.gama.mutamer.helpers.ShareHelper;
import com.gama.mutamer.managers.GeofensingManager;
import com.gama.mutamer.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewPlaceActivity extends AppCompatActivity {

    /***
     * Vars
     */
    private Place mModel;
    private String mLang = LanguageHelper.LANGUAGE_ENGLISH;

    /***
     * Views
     */
    @BindView(R.id.ibAddAlert) ImageButton ibAddAlert;
    @BindView(R.id.ibFavorites) ImageButton ibFavorites;
    @BindView(R.id.tvPlaceName) TextView tvPlaceName;
    @BindView(R.id.tvPlaceDescription) TextView tvPlaceDescription;
    @BindView(R.id.ivPlaceImage) ImageView ivPlaceImage;
    @BindView(R.id.vCont) View vCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.title_activity_view_place));
        }
        ButterKnife.bind(this);
        if (getIntent().hasExtra(Constants.ID)) {
            long placeId = getIntent().getLongExtra(Constants.ID, 0);
            new GetPlaceAsync(this)
                    .execute(placeId);
        } else {
            DialogsHelper.showToast(this, R.string.unexcepted_error);
            finish();
        }

    }

    private void bindUi(Place place) {
        if (place != null) {
            mModel = place;
            mLang = new LanguageHelper().getCurrentLanguage(this);
            tvPlaceName.setText(mModel.getName(mLang));
            tvPlaceDescription.setText(mModel.getBody(mLang));
            ibAddAlert.setImageDrawable(ContextCompat.getDrawable(this, mModel.isAlert() ? R.drawable.ic_notifications_active : R.drawable.ic_notifications_off));
            ibFavorites.setImageDrawable(ContextCompat.getDrawable(this, mModel.isFavorites() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border));
            Glide.with(this)
                    .load(getString(R.string.url_place) + mModel.getId() + ".png")
                    .into(ivPlaceImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.ibAddAlert)
    void addAlertButtonClicked(View v) {
        mModel.setAlert(!mModel.isAlert());
        new PlacesRepository().updatePlaceAlert(mModel.getId(), mModel.isAlert());
        ibAddAlert.setImageDrawable(ContextCompat.getDrawable(this, mModel.isAlert() ? R.drawable.ic_notifications_active : R.drawable.ic_notifications_off));
        if (mModel.isAlert()) {
            new GeofensingManager().addPlace(this, new LatLng(mModel.getLatitude(), mModel.getLongitude()), mModel.getId() + "", mModel.getName(mLang));
        } else {
            new GeofensingManager().removePlace(this, mModel.getId() + "");
        }
        Snackbar.make(vCont, mModel.isAlert() ? R.string.place_alert_on : R.string.place_alert_off, Snackbar.LENGTH_LONG).show();
    }


    @OnClick(R.id.ibFavorites)
    void addFavoritesButtonClicked(View v) {
        mModel.setFavorites(!mModel.isFavorites());
        new PlacesRepository()
                .updatePlaceBookmark(mModel.getId(), mModel.isFavorites());
        ibFavorites.setImageDrawable(ContextCompat.getDrawable(this, mModel.isFavorites() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border));
        Snackbar.make(vCont, mModel.isFavorites() ? R.string.place_favorites_on : R.string.place_favorites_off, Snackbar.LENGTH_LONG).show();
    }


    @OnClick(R.id.ibShare)
    void shareButtonClicked(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mModel.getName(mLang) + "\n" + mModel.getBody(mLang));
        sendIntent.setType("text/plain");
        startActivity(ShareHelper.customChooserIntentNoFb(sendIntent, this.getString(R.string.send_to), this));
    }


    @OnClick(R.id.ibNavigate)
    void navigateButtonClicked(View v) {
        MapHelper.gotoLocation(this, mModel.getLatitude(), mModel.getLongitude());
    }


    private static class GetPlaceAsync extends AsyncTask<Long, Void, Place> {


        private WeakReference<ViewPlaceActivity> activityReference;

        GetPlaceAsync(ViewPlaceActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPostExecute(Place place) {
            super.onPostExecute(place);
            ViewPlaceActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }


            activity.bindUi(place);
        }

        @Override
        protected Place doInBackground(Long... longs) {
            return new PlacesRepository()
                    .getPlaceById(longs[0]);
        }


    }
}
