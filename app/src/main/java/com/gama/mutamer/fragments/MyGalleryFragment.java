package com.gama.mutamer.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.gama.mutamer.R;
import com.gama.mutamer.activities.MainActivity;
import com.gama.mutamer.activities.views.ViewGalleryImage;
import com.gama.mutamer.activities.views.ViewGalleryVideo;
import com.gama.mutamer.adapters.ReportsAdapter;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.GeneralUtils;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.PermissionHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.interfaces.IGalleryClickListener;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.ImageItem;
import com.gama.mutamer.viewModels.utils.MediaItem;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGalleryFragment extends BaseFragment implements IGalleryClickListener {

    /***
     * Vars
     */
    final ArrayList<ImageItem> items = new ArrayList<>();
    private File file;
    private ArrayList<MediaItem> mItems = new ArrayList<>();
    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private PermissionHelper helper = new PermissionHelper();

    /***
     * Views
     */
    @BindView(R.id.rvImages) RecyclerView rvImages;
    @BindView(R.id.view_loading) View vLoading;


    public MyGalleryFragment() {
        // Required empty public constructor
    }

    public static MyGalleryFragment newInstance() {
        return new MyGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        askFormPermissions();
    }


    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle(getString(R.string.nav_my_gallery));
    }

    @Override protected void dataChanged(String action) {
        if(action.equalsIgnoreCase(Constants.BROARD_CAST_DATA_CHANGE) ){
            //TODO: Update Data
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(getActivity());
        View v = inflater.inflate(R.layout.fragment_my_gallery, container, false);
        ButterKnife.bind(this, v);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.gallery_grid));
        rvImages.setLayoutManager(layoutManager);
        rvImages.setHasFixedSize(true);
        new GetReportGalleryAsync(getParentActivity(), this).execute();
        return v;
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gallery, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_image:
                captureImageFromCamera(Constants.CAPTURE_IMAGE_FLAG);
                return true;
            case R.id.action_video:
                captureVideoFromCamera(Constants.CAPTURE_VIDEO_FLAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeStatue(int state) {
        rvImages.setVisibility(state == Constants.STATE_DATA ? View.VISIBLE : View.GONE);
        vLoading.setVisibility(state == Constants.STATE_LOADING ? View.VISIBLE : View.GONE);
    }


    private void askFormPermissions() {
        if (getActivity() == null) {
            return;
        }

        if(!helper.isPermissionsGranted(getActivity(),permissions)){
            if(helper.shouldAskForPermission(getActivity(),permissions)){
                helper.askForPermissions(getActivity(),permissions);
            }
        }
    }

    private void captureImageFromCamera(int requestCode) {
        if (getActivity() == null){
            return;}

        if (!helper.isPermissionsGranted(getActivity(),permissions)) {
            DialogsHelper.showToast(getActivity(), R.string.please_enable_camera_storage_permission);
            return;
        }


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = GeneralUtils.getOutputMediaFile(getActivity(), Constants.MEDIA_TYPE_IMAGE, Constants.GALLERY_FOLDER);
        if (file != null)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.gama.mutamer.provider", file));
        startActivityForResult(intent, requestCode);
    }

    private void captureVideoFromCamera(int requestCode) {
        if (getActivity() == null) {
            return;
        }

        if (!helper.isPermissionsGranted(getActivity(),permissions)) {
            DialogsHelper.showToast(getActivity(), R.string.please_enable_camera_storage_permission);
            return;
        }

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        file = GeneralUtils.getOutputMediaFile(getActivity(), Constants.MEDIA_TYPE_VIDEO, Constants.GALLERY_FOLDER);

        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".com.gama.mutamer.provider", file));
        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 36);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, requestCode);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            new GetReportGalleryAsync(getParentActivity(), this).execute();
        }
    }


    @Override public void itemClicked(boolean isVideo, String path) {
        Intent intent = new Intent(getActivity(), isVideo ? ViewGalleryVideo.class : ViewGalleryImage.class);
        intent.putExtra(Constants.PATH, path);
        startActivity(intent);
    }

    @Override
    public void deleteIconClicked(String path) {
        File file = new File(path);
        if(file.exists())
        {
            file.delete();
        }
        new GetReportGalleryAsync(getParentActivity(), this).execute();
    }


    private static class GetReportGalleryAsync extends AsyncTask<Void, Void, ArrayList<ImageItem>> {


        private WeakReference<MainActivity> activityReference;
        private WeakReference<MyGalleryFragment> fragmentReference;

        GetReportGalleryAsync(MainActivity context, MyGalleryFragment fragment) {
            activityReference = new WeakReference<>(context);
            fragmentReference = new WeakReference<>(fragment);
            fragment.changeStatue(Constants.STATE_LOADING);
        }

        @Override
        protected void onPostExecute(ArrayList<ImageItem> imageItems) {
            super.onPostExecute(imageItems);
            if (imageItems != null) {
                MainActivity activity = activityReference.get();
                if (activity == null || activity.isFinishing()) return;

                MyGalleryFragment fragment = fragmentReference.get();
                if (fragment == null) return;

                fragment.rvImages.setAdapter(new ReportsAdapter(fragment.items, fragment, R.layout.row_report));
                fragment.changeStatue(Constants.STATE_DATA);
            }
        }

        @Override
        protected ArrayList<ImageItem> doInBackground(Void... params) {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return null;

            MyGalleryFragment fragment = fragmentReference.get();
            if (fragment == null) return null;

            try {
                fragment.items.clear();
                fragment.mItems.clear();
                File imagesStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.GALLERY_FOLDER);
                float scale = activity.getResources().getDisplayMetrics().density;
                int hundredDp = (int) scale * 100;
                if (imagesStorageDir.exists()) {
                    for (File currentFile : imagesStorageDir.listFiles()) {
                        if (currentFile.getName().substring(currentFile.getName().length() - 4).equalsIgnoreCase(".jpg")) {
                            try {
                                //TODO: Back Here
                                Bitmap bitmap = Glide.with(activity).asBitmap().load(currentFile).into(hundredDp, hundredDp).get();
                                fragment.items.add(new ImageItem(bitmap, currentFile.getAbsolutePath(), false, currentFile.lastModified()));
                                fragment.mItems.add(new MediaItem(currentFile.getAbsolutePath(), false, currentFile.lastModified(), currentFile.getName(), Environment.getExternalStorageDirectory() + "/" + Constants.GALLERY_FOLDER));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (currentFile.getName().substring(currentFile.getName().length() - 4).equalsIgnoreCase(".mp4")) {
                            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(currentFile.getAbsolutePath(),
                                    MediaStore.Images.Thumbnails.MINI_KIND);
                            fragment.items.add(new ImageItem(bitmap, currentFile.getAbsolutePath(), true, currentFile.lastModified()));
                            fragment.mItems.add(new MediaItem(currentFile.getAbsolutePath(), true, currentFile.lastModified(), currentFile.getName(), Environment.getExternalStorageDirectory() + "/" + Constants.GALLERY_FOLDER));
                        }
                    }
                }
            } catch (Exception e) {
                FirebaseErrorEventLog.SaveEventLog(e);
            }
            return fragment.items;
        }


    }
}
