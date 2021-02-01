package com.ourdevelops.ornidsdriver.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ourdevelops.ornidsdriver.activity.MainActivity;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.models.User;


public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "com.ourdevelops.ornidsdriver.utils.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null)
        {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)) {
                LocationEngineResult result = LocationEngineResult.extractResult(intent);
                User user = BaseApp.getInstance(context).getLoginUser();
                SettingPreference sp = new SettingPreference(context);
                if (result != null && user != null && sp.getSetting()[2].equals("ON")) {
                    Location location = result.getLastLocation();

                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    LatLng newlatlang = new LatLng(Double.parseDouble(sp.getSetting()[20]),Double.parseDouble(sp.getSetting()[21]));
                    Location newlocation = new Location("ourdevelops");
                    newlocation.setLatitude(location.getLatitude());
                    newlocation.setLongitude(location.getLongitude());
                    newlocation.setBearing(getBearing(latLng,newlatlang));

                    try {
                        MainActivity.getInstance().Updatelocationdata(newlocation);
                        sp.updateLatitude(String.valueOf(newlocation.getLatitude()));
                        sp.updateLongitude(String.valueOf(newlocation.getLongitude()));
                    } catch (Exception ex) {
                        BaseApp.getInstance(context).Updatelocationdata(newlocation);
                        sp.updateLatitude(String.valueOf(newlocation.getLatitude()));
                        sp.updateLongitude(String.valueOf(newlocation.getLongitude()));

                    }
                }
            }
        }
    }

    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.getLatitude() - end.getLatitude());
        double lng = Math.abs(begin.getLongitude() - end.getLongitude());

        final double v = Math.toDegrees(Math.atan(lng / lat));
        if (begin.getLatitude() < end.getLatitude() && begin.getLatitude() < end.getLatitude())
            return (float) v;
        else if (begin.getLatitude() >= end.getLatitude() && begin.getLatitude() < end.getLatitude())
            return (float) ((90 - v) + 90);
        else if (begin.getLatitude() >= end.getLatitude() && begin.getLatitude() >= end.getLatitude())
            return (float) (v + 180);
        else if (begin.getLatitude() < end.getLatitude() && begin.getLatitude() >= end.getLatitude())
            return (float) ((90 - v) + 270);
        return -1;
    }




}
