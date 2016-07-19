package com.esgi.teamst.dailyfeed.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by sylvainvincent on 12/07/16.
 */
public class MainApplication extends Application {

    public static final String PREF_SOURCES = "PREF_SOURCES";
    public static final String PREF_KEY_SOURCE_1 = "PREF_KEY_SOURCE_1";
    public static final String PREF_KEY_SOURCE_2 = "PREF_KEY_SOURCE_2";
    public static final String PREF_KEY_SOURCE_3 = "PREF_KEY_SOURCE_3";
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

    }
}
