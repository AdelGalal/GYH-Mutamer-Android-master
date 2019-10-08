package com.gama.mutamer.utils;

import com.crashlytics.android.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.gama.mutamer.data.models.general.Setting;
import com.gama.mutamer.data.repositories.CommonRepository;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public class MutamerApp extends android.support.multidex.MultiDexApplication {

    public static boolean FORCE_USER_LOGIN = false;
    public static int UserChooseLanguage = 0;
    public static boolean DEBUG = true;


    @Override
    public void onCreate() {
        super.onCreate();

        if (DEBUG) {
            //StrictMode.enableDefaults();
        }

        Crashlytics crashConfig = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashConfig, new Crashlytics());
        Realm.init(MutamerApp.this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Thread t = new Thread(this::firstRunPreparation);
        t.start();
    }

    private void firstRunPreparation() {
        //Check if this this the first run and add default data
        Setting setting = new CommonRepository().getSettings();
        if (setting == null) {
            new CommonRepository().setDefaultSetting();
            new CommonRepository().loadNationalities(this);
        }
    }
}

