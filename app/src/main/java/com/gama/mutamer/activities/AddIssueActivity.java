package com.gama.mutamer.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.gama.mutamer.R;
import com.gama.mutamer.adapters.ReportsAdapter;
import com.gama.mutamer.data.models.User;
import com.gama.mutamer.data.repositories.UsersRepository;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.GeneralUtils;
import com.gama.mutamer.helpers.webService.ServicePost;
import com.gama.mutamer.services.SyncService;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.ImageItem;
import com.gama.mutamer.viewModels.utils.MediaItem;
import com.gama.mutamer.webServices.params.SendIssueParam;
import com.gama.mutamer.webServices.requests.SendIssueRequest;
import com.gama.mutamer.webServices.responses.BaseServiceResponse;
import com.google.gson.Gson;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddIssueActivity extends AppCompatActivity {


    /***
     * Vars
     */
    private File file;
    private ArrayList<MediaItem> mItems = new ArrayList<>();
    private Date mDate = new Date();


    /***
     * Views
     */
    @BindView(R.id.rvReport) RecyclerView rvReport;
    @BindView(R.id.etBody) EditText etBody;
    @BindView(R.id.etTitle) EditText etTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue_acitivty);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.report_issue));
        }
        rvReport.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvReport.setHasFixedSize(true);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_issue, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            SendIssue();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void captureImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = GeneralUtils.getOutputMediaFile(this, Constants.MEDIA_TYPE_IMAGE, getString(R.string.dir_report_images));

        if (file != null)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".com.gama.mutamer.provider", file));
        startActivityForResult(intent, Constants.CAPTURE_IMAGE_REPORT_ACTIVITY_REQUEST_CODE);
    }

    private void captureVideoFromCamera() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        file = GeneralUtils.getOutputMediaFile(this, Constants.MEDIA_TYPE_VIDEO, getString(R.string.dir_report_videos));
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".com.gama.mutamer.provider", file));
        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 36);

        if (takeVideoIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, Constants.CAPTURE_VIDEO_REPORT_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            new getReportGallery(this, mItems, mDate)
                    .execute();
        }
    }

    @OnClick(R.id.btnCaptureImage)
    void captureImageButtonClicked(View v) {
        captureImageFromCamera();
    }

    @OnClick(R.id.btnCaptureVideo)
    void captureVideoButtonClicked(View v) {
        captureVideoFromCamera();
    }

    @Override protected void onResume() {
        super.onResume();
        askFormPermissions();
    }

    private void askFormPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            8);
                }
            }
        }
    }


    private void SendIssue() {
        if (etBody.getText().length() < 3) {
            DialogsHelper.getAlert(this, getString(R.string.error), getString(R.string.enter_issue_body), getString(R.string.ok)).show();
            return;
        }
        if (etTitle.getText().length() < 3) {
            DialogsHelper.getAlert(this, getString(R.string.error), getString(R.string.enter_issue_title), getString(R.string.ok)).show();
            return;
        }
        SendIssueParam param = new SendIssueParam();
        param.setTitle(etTitle.getText().toString());
        param.setBody(etBody.getText().toString());
        new sendIssueAsync(this, mItems)
                .execute(param);
    }


    private static class sendIssueAsync extends AsyncTask<SendIssueParam, Void, BaseServiceResponse> {

        private ProgressDialog mDialog;

        private WeakReference<AddIssueActivity> mWeakReference;
        private ArrayList<MediaItem> mItems;

        sendIssueAsync(AddIssueActivity activity, ArrayList<MediaItem> items) {
            mWeakReference = new WeakReference<>(activity);
            mItems = items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AddIssueActivity activity = mWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                mDialog = DialogsHelper.getProgressDialog(activity, R.string.please_wait);
                mDialog.show();
            }
        }

        @Override
        protected BaseServiceResponse doInBackground(SendIssueParam... params) {
            try {
                AddIssueActivity activity = mWeakReference.get();
                if (activity != null && !activity.isFinishing()) {
                    User user = new UsersRepository().getLoggedUser();
                    SendIssueRequest request = new SendIssueRequest(user.getAccessToken(), params[0]);
                    String result = new ServicePost().DoPost(request, activity, mItems);
                    BaseServiceResponse response = new Gson().fromJson(result, BaseServiceResponse.class);
                    if (response.isSuccess()) {
                        activity.startService(new Intent(activity, SyncService.class));
                    }
                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(BaseServiceResponse response) {
            super.onPostExecute(response);
            mDialog.cancel();
            AddIssueActivity activity = mWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                if (response == null) {
                    DialogsHelper.showToast(activity, R.string.send_fail);
                    return;
                }
                if (!response.isSuccess()) {
                    DialogsHelper.showToast(activity, R.string.send_fail);
                    return;
                }

                DialogsHelper.showToast(activity, R.string.send_success);
                activity.setResult(RESULT_OK);
                activity.finish();

            }
        }
    }

    private static class getReportGallery extends AsyncTask<Void, Void, ArrayList<ImageItem>> {

        private WeakReference<AddIssueActivity> mWeakReference;

        private ArrayList<MediaItem> mItems;
        private Date mDate;

        getReportGallery(AddIssueActivity activity, ArrayList<MediaItem> items, Date date) {
            mWeakReference = new WeakReference<>(activity);
            mItems = items;
            mDate = date;
        }

        @Override
        protected void onPostExecute(ArrayList<ImageItem> imageItems) {
            super.onPostExecute(imageItems);
            AddIssueActivity activity = mWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                activity.rvReport.setAdapter(new ReportsAdapter(imageItems, null, R.layout.row_report));
            }
        }

        @Override
        protected ArrayList<ImageItem> doInBackground(Void... params) {
            final ArrayList<ImageItem> items = new ArrayList<>();
            AddIssueActivity activity = mWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                mItems.clear();
                File imagesStorageDir = new File(Environment.getExternalStorageDirectory(), activity.getString(R.string.dir_report_images));

                float scale = activity.getResources().getDisplayMetrics().density;
                int hundredDp = (int) scale * 100;
                if (imagesStorageDir.exists()) {

                    for (File imageFile : imagesStorageDir.listFiles()) {
                        if (imageFile.getName().substring(imageFile.getName().length() - 4).equalsIgnoreCase(".jpg")) {
                            try {
                                Calendar thatDay = Calendar.getInstance();
                                thatDay.setTime(new Date(imageFile.lastModified()));
                                if (thatDay.getTime().getTime() > mDate.getTime()) {
                                    Bitmap bitmap = Glide.with(activity).asBitmap()
                                            .load(imageFile).into(hundredDp, hundredDp)
                                            .get();

                                    items.add(new ImageItem(bitmap, imageFile.getAbsolutePath(), false, imageFile.lastModified()));
                                    mItems.add(new MediaItem(imageFile.getAbsolutePath(), false, imageFile.lastModified(), imageFile.getName(), Environment.getExternalStorageDirectory() + activity.getString(R.string.dir_report_images)));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                File videosStorageDir = new File(Environment.getExternalStorageDirectory(), activity.getString(R.string.dir_report_videos));
                if (videosStorageDir.exists()) {

                    for (File videoFile : videosStorageDir.listFiles()) {
                        Calendar thatDay = Calendar.getInstance();
                        thatDay.setTime(new Date(videoFile.lastModified()));
                        if (thatDay.getTime().getTime() > mDate.getTime()) {
                            if (videoFile.getName().substring(videoFile.getName().length() - 4).equalsIgnoreCase(".mp4")) {
                                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(),
                                        MediaStore.Images.Thumbnails.MINI_KIND);


                                items.add(new ImageItem(bitmap, videoFile.getAbsolutePath(), true, videoFile.lastModified()));
                                mItems.add(new MediaItem(videoFile.getAbsolutePath(), true, videoFile.lastModified(), videoFile.getName(), Environment.getExternalStorageDirectory() + activity.getString(R.string.dir_report_videos)));
                            }
                        }
                    }
                }

            }
            return items;
        }
    }
}
