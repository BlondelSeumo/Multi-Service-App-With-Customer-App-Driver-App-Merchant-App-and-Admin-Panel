package com.ourdevelops.ornids.constants;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ourdevelops.ornids.models.FirebaseToken;
import com.ourdevelops.ornids.models.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class BaseApp extends Application {

    private static final int SCHEMA_VERSION = 0;

    private User loginUser;

    private Realm realmInstance;

    public static BaseApp getInstance(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();

        FirebaseToken token = new FirebaseToken(FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("ouride");
        FirebaseMessaging.getInstance().subscribeToTopic("customer");
        Realm.setDefaultConfiguration(config);

//        realmInstance = Realm.getInstance(config);
        realmInstance = Realm.getDefaultInstance();
        realmInstance.beginTransaction();
        realmInstance.delete(FirebaseToken.class);
        realmInstance.copyToRealm(token);
        realmInstance.commitTransaction();

        start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public final Realm getRealmInstance() {
        return realmInstance;
    }

    private void start() {
        Realm realm = getRealmInstance();
        User user = realm.where(User.class).findFirst();
        if (user != null) {
            setLoginUser(user);
        }
    }

}
