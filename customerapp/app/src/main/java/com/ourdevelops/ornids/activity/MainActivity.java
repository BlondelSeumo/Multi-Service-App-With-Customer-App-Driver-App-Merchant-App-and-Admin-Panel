package com.ourdevelops.ornids.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.constants.VersionChecker;
import com.ourdevelops.ornids.fragment.EnableLlocationFragment;
import com.ourdevelops.ornids.fragment.FavouriteFragment;
import com.ourdevelops.ornids.fragment.HistoryFragment;
import com.ourdevelops.ornids.fragment.HomeFragment;
import com.ourdevelops.ornids.fragment.MessageFragment;
import com.ourdevelops.ornids.fragment.ProfileFragment;
import com.ourdevelops.ornids.models.ServiceModel;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.Log;
import com.ourdevelops.ornids.utils.SettingPreference;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.UserService;
import com.ourdevelops.ornids.json.GetServiceResponseJson;


import java.util.Objects;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    long mBackPressed;

    LinearLayout mAdViewLayout;

    @SuppressLint("StaticFieldLeak")
    public static MainActivity mainActivity;
    private FragmentManager fragmentManager;
    BottomNavigationView navigation;
    int previousSelect = 0;
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 999999999999999999L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback();

    public static MainActivity getInstance() {
        return mainActivity;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Menu menu = navigation.getMenu();
            menu.findItem(R.id.home).setIcon(R.drawable.ic_home);
            menu.findItem(R.id.order).setIcon(R.drawable.ic_transaksi);
            menu.findItem(R.id.favourite).setIcon(R.drawable.ic_favourites);
            menu.findItem(R.id.chat).setIcon(R.drawable.ic_pesan);
            menu.findItem(R.id.profile).setIcon(R.drawable.ic_profil);
            switch (item.getItemId()) {
                case R.id.home:
                    GPSStatus();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = getWindow().getDecorView().getSystemUiVisibility();
                        flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        getWindow().getDecorView().setSystemUiVisibility(flags);
                        getWindow().setStatusBarColor(getResources().getColor(R.color.colorgradient));
                    }
                    HomeFragment homeFragment = new HomeFragment();
                    navigationItemSelected(0);
                    item.setIcon(R.drawable.ic_home_s);
                    loadFrag(homeFragment, getString(R.string.menu_home), fragmentManager);

                    return true;
                case R.id.order:
                    GPSStatus();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    HistoryFragment listFragment = new HistoryFragment();
                    navigationItemSelected(1);
                    item.setIcon(R.drawable.ic_transaksi_s);
                    loadFrag(listFragment, getString(R.string.menu_home), fragmentManager);
                    return true;
                case R.id.favourite:
                    GPSStatus();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    FavouriteFragment favFragment = new FavouriteFragment();
                    navigationItemSelected(1);
                    item.setIcon(R.drawable.ic_favourite);
                    loadFrag(favFragment, getString(R.string.menu_favourite), fragmentManager);
                    return true;
                case R.id.chat:
                    GPSStatus();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    MessageFragment pesanFragment = new MessageFragment();
                    navigationItemSelected(2);
                    item.setIcon(R.drawable.ic_pesan_s);
                    loadFrag(pesanFragment, getString(R.string.menu_home), fragmentManager);
                    return true;
                case R.id.profile:
                    GPSStatus();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    ProfileFragment profilFragment = new ProfileFragment();
                    navigationItemSelected(3);
                    item.setIcon(R.drawable.ic_profil_s);
                    loadFrag(profilFragment, getString(R.string.menu_home), fragmentManager);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLocationEngine();
        mAdViewLayout = findViewById(R.id.adView);
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.home).setIcon(R.drawable.ic_home_s);
        HomeFragment homeFragment = new HomeFragment();
        loadFrag(homeFragment, getString(R.string.menu_home), fragmentManager);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        Constant.TOKEN = loginUser.getToken();
        Constant.USERID = loginUser.getId();
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Constant.versionname = Objects.requireNonNull(packageInfo).versionName;


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Check_version();
        update();
        GPSStatus();
    }

    public void initLocationEngine() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationEngine = LocationEngineProvider.getBestLocationEngine(this);
            LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                    .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                    .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
            locationEngine.requestLocationUpdates(request, callback, getMainLooper());
            locationEngine.getLastLocation(callback);
        } else {
            enable_location();
        }
    }

    private void enable_location() {
        EnableLlocationFragment enable_llocationFragment = new EnableLlocationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right);
        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(R.id.MainFragment, enable_llocationFragment).addToBackStack(null).commit();

    }



    private class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        LocationChangeListeningActivityLocationCallback() {
        }

        @Override
        public void onSuccess(LocationEngineResult result) {
            SettingPreference sp = new SettingPreference(MainActivity.this);
            sp.updatelatitude(String.valueOf(result.getLastLocation().getLatitude()));
            sp.updatelongitude(String.valueOf(result.getLastLocation().getLongitude()));
        }

        @Override
        public void onFailure(@NonNull Exception exception) {

        }
    }

    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }

    @Override
    public void onBackPressed() {
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                clickDone();

            }
        } else {
            super.onBackPressed();
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Container, f1, name);
        ft.commit();
    }

    public void navigationItemSelected(int position) {
        previousSelect = position;
    }

    private void update() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(UserService.class,
                loginUser.getEmail(), loginUser.getPassword());
        userService.getFitur().enqueue(new Callback<GetServiceResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetServiceResponseJson> call, @NonNull Response<GetServiceResponseJson> response) {
                if (response.isSuccessful()) {
                    Realm realm = BaseApp.getInstance(MainActivity.this).getRealmInstance();
                    realm.beginTransaction();
                    realm.delete(ServiceModel.class);
                    realm.copyToRealm(Objects.requireNonNull(response.body()).getData());
                    realm.commitTransaction();
                    Constant.CURRENCY = response.body().getCurrencyModel();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetServiceResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    public void GPSStatus() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ignored) {}

        try {
            network_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ignored) {}

        if(!gps_enabled && !network_enabled) {
            Toast.makeText(this, "On Location in High Accuracy", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);
        }
    }


}
