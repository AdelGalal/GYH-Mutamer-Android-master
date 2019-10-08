package com.gama.mutamer.activities.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gama.mutamer.R;
import com.gama.mutamer.helpers.DialogsHelper;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewGalleryVideo extends AppCompatActivity {


    /***
     * Vars
     */
    private File toDelete;


    /***
     * Views
     */
    @BindView(R.id.vvVideo) VideoView vvVideo;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gallery_video);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.video_viewer));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent() != null && getIntent().hasExtra(Constants.PATH)) {
            try {
                String dir = getIntent().getStringExtra(Constants.PATH);
                File imagesStorageDir = new File(dir);
                String dstPath = Environment.getExternalStorageDirectory() + File.separator + "umrati" + File.separator;
                File dst = new File(dstPath);

                toDelete = exportFile(imagesStorageDir, dst);
                if (toDelete == null) {
                    DialogsHelper.showToast(this, R.string.unexcepted_error);
                    finish();
                }
                vvVideo.setVideoPath(dir);

                MediaController mediaController = new
                        MediaController(this);
                mediaController.setAnchorView(vvVideo);

                vvVideo.setMediaController(mediaController);
                vvVideo.start();

            } catch (Exception e) {
                e.printStackTrace();
                DialogsHelper.showToast(this, R.string.unexcepted_error);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            //noinspection ResultOfMethodCallIgnored
            toDelete.delete();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("image/*");
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".com.gama.mutamer.provider", new File(getIntent().getStringExtra(Constants.PATH)));
            sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        }

        return super.onOptionsItemSelected(item);
    }

    private File exportFile(File src, File dst) {
        try {
            //if folder does not exist
            if (!dst.exists()) {
                if (!dst.mkdir()) {
                    return null;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(new Date());
            File expFile = new File(dst.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            FileChannel inChannel = null;
            FileChannel outChannel = null;

            try {
                inChannel = new FileInputStream(src).getChannel();
                outChannel = new FileOutputStream(expFile).getChannel();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (inChannel != null && outChannel != null) {
                try {
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                } catch (Exception e) {
                    FirebaseErrorEventLog.SaveEventLog(e);
                } finally {
                    inChannel.close();
                    outChannel.close();
                }
            }

            return expFile;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            return null;
        }
    }
}
