package com.launch.rammaxx.App;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RammaxxApp extends Application {

    public static AtomicInteger ledID = new AtomicInteger();
    public static AtomicInteger CfgID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable (Realm realm, Class<T> anyclass) {

        RealmResults<T> results = realm.where(anyclass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("ID").intValue()) : new AtomicInteger();
    }

}
