package com.ourdevelops.ornidsmerchant.activity;


import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.fragment.EnableLlocationFragment;
import com.ourdevelops.ornidsmerchant.models.User;

import java.util.Objects;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final User user = BaseApp.getInstance(this).getLoginUser();
        removeNotif();
        sharedPreferences = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (user != null) {

                    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        enable_location();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        enable_location();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        }, 3000);


    }

    public void GPSStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GpsStatus = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GpsStatus) {
            Toast.makeText(this, "On Location in High Accuracy", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            GPSStatus();
        }
    }




    private void enable_location() {
        EnableLlocationFragment enable_llocationFragment = new EnableLlocationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.from_right, R.anim.to_left, R.anim.from_left, R.anim.to_right);
        getSupportFragmentManager().popBackStackImmediate();
        transaction.replace(R.id.splash, enable_llocationFragment).addToBackStack(null).commit();

    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }


}
