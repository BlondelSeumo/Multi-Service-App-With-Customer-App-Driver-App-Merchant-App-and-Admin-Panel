package com.ourdevelops.ornidsdriver.utils.api.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.activity.MainActivity;
import com.ourdevelops.ornidsdriver.activity.NewOrderActivity;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.json.ResponseJson;
import com.ourdevelops.ornidsdriver.json.UpdateLocationRequestJson;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.SettingPreference;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;


public class LocService extends Service {
    LocationEngine locationEngine;
    SettingPreference sp;
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    Notification notification;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback();
    @Override
    public void onCreate() {
        super.onCreate();
        sp = new SettingPreference(this);
        initLocationEngine();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(Objects.requireNonNull(notificationManager)) : "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("your working now! this app will use location in background to send driver location to server")
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(321, notification, FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(321, notification);
        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

    public void initLocationEngine() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationEngine = LocationEngineProvider.getBestLocationEngine(this);
            LocationEngineRequest request = new LocationEngineRequest.Builder(4000L)
                    .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY).build();
            locationEngine.requestLocationUpdates(request, callback, getMainLooper());
            locationEngine.getLastLocation(callback);
        }
    }
    Location newlocation;
    private class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        LocationChangeListeningActivityLocationCallback() {
        }

        @Override
        public void onSuccess(LocationEngineResult result) {
            Location location = result.getLastLocation();
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            LatLng newlatlang = new LatLng(Double.parseDouble(sp.getSetting()[20]),Double.parseDouble(sp.getSetting()[21]));
            newlocation = new Location("ourdevelops");
            newlocation.setLatitude(location.getLatitude());
            newlocation.setLongitude(location.getLongitude());
            newlocation.setBearing(getBearing(latLng,newlatlang));
            Updatelocationdata(newlocation);

        }

        @Override
        public void onFailure(@NonNull Exception exception) {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_STOP_FOREGROUND_SERVICE.equals(action)) {
                stopForeground(true);
                stopSelf();
            } else if (ACTION_START_FOREGROUND_SERVICE.equals(action)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(321, notification, FOREGROUND_SERVICE_TYPE_LOCATION);
                } else {
                    startForeground(321, notification);
                }
            }
        }
        return START_STICKY;
    }

    public void Updatelocationdata(final Location location) {
    User user = BaseApp.getInstance(this).getLoginUser();
        if (sp.getSetting()[2].equals("ON") && user != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    onLocationChanged(location);
                }
            });
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

    public void onLocationChanged(Location location) {
        if (location != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();
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

}
