package com.gama.mutamer.activities.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gama.mutamer.utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;
import com.gama.mutamer.R;
import com.gama.mutamer.helpers.LanguageHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewGalleryImage extends AppCompatActivity {


    /***
     * Views
     */
    @BindView(R.id.ivImage) PhotoView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new LanguageHelper().initLanguage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gallery_image);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.image_viewer));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent() != null && getIntent().hasExtra(Constants.PATH)) {
            String dir = getIntent().getStringExtra(Constants.PATH);
            File imagesStorageDir = new File(dir);
            Glide.with(ViewGalleryImage.this).asBitmap().load(imagesStorageDir).into(ivImage);
        } else {
            Toast.makeText(this, R.string.unexcepted_error, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
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

}
