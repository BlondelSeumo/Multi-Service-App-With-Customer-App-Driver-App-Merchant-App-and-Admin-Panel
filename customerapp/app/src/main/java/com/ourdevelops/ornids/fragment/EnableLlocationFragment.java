package com.ourdevelops.ornids.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.labo.kaji.fragmentanimations.MoveAnimation;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.activity.IntroActivity;
import com.ourdevelops.ornids.activity.MainActivity;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.models.User;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class EnableLlocationFragment extends Fragment {

    View getView;
    Context context;
    Button enableLocation;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.fragment_enablelocation, container, false);
        context = getContext();

        sharedPreferences = context.getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE);

        enableLocation = getView.findViewById(R.id.enable_location_btn);
        enableLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationPermission();

            }
        });


        return getView;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            Animation anim = MoveAnimation.create(MoveAnimation.LEFT, enter, 300);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    GPSStatus();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            return anim;

        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 300);
        }
    }


    private void getLocationPermission() {

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                123);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetCurrentlocation();
                } else {

                    Toast.makeText(context, "Please Grant permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    public void GPSStatus() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ignored) {}

        try {
            network_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ignored) {}

        if(!gps_enabled && !network_enabled) {
            Toast.makeText(context, "On Location in High Accuracy", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            GPSStatus();
        }
    }

    private void GetCurrentlocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            getLocationPermission();
            return;
        }
        GoToNext_Activty();
    }


    public void GoToNext_Activty() {
        final User user = BaseApp.getInstance(context).getLoginUser();
        if (user != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Intent intent = new Intent(getActivity(), IntroActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }


}

