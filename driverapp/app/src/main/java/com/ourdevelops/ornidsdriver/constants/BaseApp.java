package com.ourdevelops.ornidsdriver.constants;

import android.Manifest;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.ourdevelops.ornidsdriver.activity.MainActivity;
import com.ourdevelops.ornidsdriver.json.UpdateLocationRequestJson;
import com.ourdevelops.ornidsdriver.utils.MyLocationService;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.ourdevelops.ornidsdriver.json.ResponseJson;
import com.ourdevelops.ornidsdriver.models.FirebaseToken;
import com.ourdevelops.ornidsdriver.models.User;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class BaseApp extends Application {

    private static final int SCHEMA_VERSION = 0;
    private static BaseApp mApp = null;
    private User loginUser;

    private Realm realmInstance;
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 4000;

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
        FirebaseMessaging.getInstance().subscribeToTopic("driver");
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



    public void Updatelocationdata(final Location location) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                onLocationChanged(location);
            }
        });

    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            User loginUser = getLoginUser();
            DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
            UpdateLocationRequestJson param = new UpdateLocationRequestJson();

            param.setId(loginUser.getId());
            param.setLatitude(String.valueOf(location.getLatitude()));
            param.setLongitude(String.valueOf(location.getLongitude()));
            param.setBearing(String.valueOf(location.getBearing()));

            service.updatelocation(param).enqueue(new Callback<ResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                    if (response.isSuccessful()) {
                        Log.e("location", response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<ResponseJson> call, @NonNull Throwable t) {

                }
            });
        }
    }

    private void getLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
    }

    public static Context context() {
        return mApp.getApplicationContext();
    }


}
