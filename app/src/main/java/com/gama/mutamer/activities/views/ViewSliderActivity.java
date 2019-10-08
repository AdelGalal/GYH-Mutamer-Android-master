package com.gama.mutamer.activities.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.content.Slider;
import com.gama.mutamer.data.repositories.SlidersRepository;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.utils.Constants;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewSliderActivity extends AppCompatActivity {


    /***
     * Vars
     */
    private long mSliderId;
    private Slider mSlider;


    /***
     * Views
     */
    @BindView(R.id.ivSliderImage) ImageView ivSliderImage;
    @BindView(R.id.tvSliderTitle) TextView tvSliderTitle;
    @BindView(R.id.tvSliderBody) TextView tvSliderBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slider);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().hasExtra(Constants.ID)) {
            mSliderId = getIntent().getLongExtra(Constants.ID, 0);
            new GetSliderAsync(this)
                    .execute(mSliderId);
        }
    }

    private void bindUi() {
        if (mSlider != null) {
            Glide.with(this)
                    .load(String.format(getString(R.string.url_slide_image), String.valueOf(mSliderId)))
                    .into(ivSliderImage);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mSlider.getTitle());
            }
            tvSliderTitle.setText(mSlider.getTitle());
            tvSliderBody.setText(mSlider.getBody());
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnOpenWebsite)
    void buttonOpenWebsiteClicked(View v) {
        if (mSlider != null) {
            CommunicationsHelper.openUrl(this, mSlider.getUrl());
        }
    }


    public static class GetSliderAsync extends AsyncTask<Long, Void, Void> {

        private WeakReference<ViewSliderActivity> activityReference;

        GetSliderAsync(ViewSliderActivity context) {
            activityReference = new WeakReference<>(context);
        }


        @Override
        protected Void doInBackground(Long... ids) {

            ViewSliderActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;


            activity.mSlider = new SlidersRepository()
                    .getSliderById(ids[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ViewSliderActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;


            activity.bindUi();
        }
    }
}
