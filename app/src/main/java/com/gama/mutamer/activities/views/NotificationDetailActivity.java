package com.gama.mutamer.activities.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gama.mutamer.R;
import com.gama.mutamer.data.models.general.Notification;
import com.gama.mutamer.data.repositories.NotificationsRepository;
import com.gama.mutamer.helpers.CommunicationsHelper;
import com.gama.mutamer.helpers.DateHelper;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationDetailActivity extends AppCompatActivity {

    /***
     * Vars
     */
    Notification item;

    /***
     * Views
     */
    @BindView(R.id.tvNotificationTitle) TextView tvTitle;
    @BindView(R.id.tvNotificationDate) TextView tvDate;
    @BindView(R.id.tvNotificationBody) TextView tvBody;
    @BindView(R.id.btnOpenWebsite) Button btnOpenWebsite;
    @BindView(R.id.ivImage) PhotoView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.notification));
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Locale mLocale = new LanguageHelper().getCurrentLocale(this);
        if (getIntent() != null && getIntent().hasExtra(Constants.NOTIFICATION_ITEM)) {
            int notificationId = getIntent().getIntExtra(Constants.NOTIFICATION_ITEM, 0);
            item = new NotificationsRepository().getNotificationById(notificationId);
            if (item != null) {
                switch (item.getType()) {
                    case Constants.NOTIFICATION_TYPE_TEXT:
                        tvBody.setText(item.getBody());
                        tvBody.setVisibility(View.VISIBLE);
                        break;
                    case Constants.NOTIFICATION_TYPE_URL:
                        btnOpenWebsite.setVisibility(View.VISIBLE);
                        break;
                    case Constants.NOTIFICATION_TYPE_IMAGE:
                        Glide.with(this)
                                .load(item.getBody())
                                .into(ivImage);
                        ivImage.setVisibility(View.VISIBLE);
                        break;
                }
                tvTitle.setText(item.getTitle());
                tvDate.setText(DateHelper.formatDateTime(item.getDate(), mLocale));
            } else {
                DialogsHelper.showToast(this, R.string.unexcepted_error);
                finish();
            }
        } else {
            DialogsHelper.showToast(this, R.string.unexpected_error);
            finish();
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


    @SuppressWarnings("unused")
    @OnClick(R.id.btnOpenWebsite)
    void btnOpenWebsiteClicked(View v) {
        if (item != null) {
            CommunicationsHelper.openUrl(this, item.getBody());
        }
    }
}
