package com.ourdevelops.ornids.utils.api.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.activity.ChatActivity;
import com.ourdevelops.ornids.activity.MainActivity;
import com.ourdevelops.ornids.activity.ProgressActivity;
import com.ourdevelops.ornids.activity.SplashActivity;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.json.fcm.DriverResponse;
import com.ourdevelops.ornids.models.User;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {
    Intent intent;
    public static final String BROADCAST_ACTION = "com.ourdevelops.ourjek";
    public static final String BROADCAST_ORDER = "order";
    Intent intentOrder;

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        intentOrder = new Intent(BROADCAST_ORDER);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        User user = BaseApp.getInstance(this).getLoginUser();
        if (!remoteMessage.getData().isEmpty() && user != null) {
            parseAndSendMessage(remoteMessage.getData());

        }
        messageHandler(remoteMessage);
    }

    private void parseAndSendMessage(Map<String, String> mapResponse) {
        if (Objects.requireNonNull(mapResponse.get("type")).equals("1")) {
            DriverResponse response = new DriverResponse();
            response.setId(mapResponse.get("driver_id"));
            response.setIdTransaksi(mapResponse.get("transaction_id"));
            response.setResponse(mapResponse.get("response"));
            EventBus.getDefault().postSticky(response);
        }

    }


    private void messageHandler(RemoteMessage remoteMessage) {
        User user = BaseApp.getInstance(this).getLoginUser();
        if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("1")) {
            if (user != null) {
                orderHandler(remoteMessage);
            }
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("3")) {
            if (user != null) {
                otherHandler(remoteMessage);
            }
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("4")) {
                otherHandler2(remoteMessage);
        } else if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("2")) {
            if (user != null) {
                chat(remoteMessage);
            }
        }
    }

    private void otherHandler(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void otherHandler2(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(remoteMessage.getData().get("title"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void orderHandler(RemoteMessage remoteMessage) {
        Bundle data = new Bundle();
        intentToOrder(data);

        if (Objects.equals(remoteMessage.getData().get("response"), "5")) {
            notificationOrderBuilderCancel(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "2")) {
            playSound1();
            notificationOrderBuilderAccept(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "3")) {
            playSound1();
            notificationOrderBuilderStart(remoteMessage);
        } else if (Objects.equals(remoteMessage.getData().get("response"), "4")) {
            playSound1();
            notificationOrderBuilderFinish(remoteMessage);
        }
    }

    private void intentToOrder(Bundle bundle) {
        intentOrder.putExtras(bundle);
        sendBroadcast(intentOrder);
    }


    private void notificationOrderBuilderCancel(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ProgressActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("transaction_id", remoteMessage.getData().get("transaction_id"));
        intent1.putExtra("driver_id", remoteMessage.getData().get("driver_id"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Cancel");
        mBuilder.setContentText(getString(R.string.notification_cancel));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderStart(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ProgressActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("transaction_id", remoteMessage.getData().get("transaction_id"));
        intent1.putExtra("driver_id", remoteMessage.getData().get("driver_id"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Driver Start");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderAccept(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ProgressActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("transaction_id", remoteMessage.getData().get("transaction_id"));
        intent1.putExtra("driver_id", remoteMessage.getData().get("driver_id"));
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Driver Accept");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }

    private void notificationOrderBuilderFinish(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ProgressActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("transaction_id", remoteMessage.getData().get("transaction_id"));
        intent1.putExtra("driver_id", remoteMessage.getData().get("driver_id"));
        intent1.putExtra("complete", "true");
        intent1.putExtra("response", remoteMessage.getData().get("response"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Finish");
        mBuilder.setContentText(remoteMessage.getData().get("desc"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());

    }

    private void chat(RemoteMessage remoteMessage) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), ChatActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("senderid", remoteMessage.getData().get("receiverid"));
        intent1.putExtra("receiverid", remoteMessage.getData().get("senderid"));
        intent1.putExtra("name", remoteMessage.getData().get("name"));
        intent1.putExtra("tokenku", remoteMessage.getData().get("tokendriver"));
        intent1.putExtra("tokendriver", remoteMessage.getData().get("tokenuser"));
        intent1.putExtra("pic", remoteMessage.getData().get("pic"));
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("name"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(remoteMessage.getData().get("name"));
        mBuilder.setContentText(remoteMessage.getData().get("message"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "customer";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel customer",
                    NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());
    }


    private void playSound1() {
        MediaPlayer BG = MediaPlayer.create(getBaseContext(), R.raw.finishsound);
        BG.setLooping(false);
        BG.setVolume(100, 100);
        BG.start();

        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Objects.requireNonNull(v).vibrate(2000);
    }

}
