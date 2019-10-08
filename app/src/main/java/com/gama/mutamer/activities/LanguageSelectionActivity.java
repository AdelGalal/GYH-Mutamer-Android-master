package com.gama.mutamer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gama.mutamer.R;
import com.gama.mutamer.adapters.LanguagesAdapter;
import com.gama.mutamer.data.repositories.SettingsRepository;
import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.firebase.FirebaseSubscribeHelper;
import com.gama.mutamer.interfaces.ILanguageChangeListener;
import com.gama.mutamer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageSelectionActivity extends AppCompatActivity implements ILanguageChangeListener {


    /***
     * Views
     */
    @BindView(R.id.rvLanguages) RecyclerView rvLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.language));
        }
        ButterKnife.bind(this);
        rvLanguages.setLayoutManager(new GridLayoutManager(this, 2));
        rvLanguages.setHasFixedSize(true);
        rvLanguages.setAdapter(new LanguagesAdapter(Constants.APP_LANGUAGES, this, this));
    }

    private void setLanguage(int languageIndex) {
        try {
            new SettingsRepository().updateLanguage(languageIndex);
            new LanguageHelper().changeLanguage(this, languageIndex);
            SharedPrefHelper.setSharedInt(this, Constants.USER_CHOOSE_LANGUAGE, 1);
            FirebaseSubscribeHelper.SubScribeToLanguage(null, new LanguageHelper().getCurrentLanguage(this));
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }


    @Override public void LanguageChanged() {

    }

    @Override public void LanguageChanged(int languageIndex) {
        setLanguage(languageIndex);
    }
}
