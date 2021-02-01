package com.ourdevelops.ornidsdriver.utils.api.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.activity.ChatActivity;
import com.ourdevelops.ornidsdriver.activity.MainActivity;
import com.ourdevelops.ornidsdriver.activity.NewOrderActivity;
import com.ourdevelops.ornidsdriver.activity.SplashActivity;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.SettingPreference;

import java.util.List;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MessagingService extends FirebaseMessagingService {
    Intent intent;
    public static final String BROADCAST_ACTION = "com.ourdevelops.ornidsdriver";
    public static final String BROADCAST_ORDER = "order";
    Intent intentOrder;
    long millisecond;

    SettingPreference sp;
    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        intentOrder = new Intent(BROADCAST_ORDER);
        millisecond = 20000;
        sp = new SettingPreference(this);

    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (!remoteMessage.getData().isEmpty()) {
            messageHandler(remoteMessage);
        }
    }

    private void messageHandler(RemoteMessage remoteMessage){
        User user = BaseApp.getInstance(this).getLoginUser();
        SettingPreference sp = new SettingPreference(this);
        if (Objects.requireNonNull(remoteMessage.getData().get("type")).equals("1")) {
            if (user != null) {
                String resp = remoteMessage.getData().get("response");
                if (resp == null ) {
                    if (sp.getSetting()[1].equals("Unlimited")) {
                        if (sp.getSetting()[2].equals("ON") && sp.getSetting()[3].equals("OFF")) {
                            notification(remoteMessage);
                        }
                    } else {
                        double uangbelanja = Double.parseDouble(sp.getSetting()[1]);
                        double price = Double.parseDouble(Objects.requireNonNull(remoteMessage.getData().get("price")));
                        if (uangbelanja > price && sp.getSetting()[2].equals("ON") && sp.getSetting()[3].equals("OFF")) {
                            notification(remoteMessage);
                        }
                    }
                } else {
                    playSound();
                    intentCancel();
                }
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

    private void intentCancel() {
        Intent toMain = new Intent(getBaseContext(), MainActivity.class);
        toMain.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toMain);
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

    private void otherHandler(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(remoteMessage.getData().get("title"));
        bigTextStyle.bigText(remoteMessage.getData().get("message"));
        mBuilder.setContentIntent(pIntent1);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
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

    private void notification(RemoteMessage remoteMessage) {
        Intent toOrder = new Intent(getBaseContext(), NewOrderActivity.class);
        toOrder.putExtra("transaction_id", remoteMessage.getData().get("transaction_id"));
        toOrder.putExtra("icon", remoteMessage.getData().get("icon"));
        toOrder.putExtra("layanan", remoteMessage.getData().get("layanan"));
        toOrder.putExtra("layanandesc", remoteMessage.getData().get("layanandesc"));
        toOrder.putExtra("pickup_address", remoteMessage.getData().get("pickup_address"));
        toOrder.putExtra("destination_address", remoteMessage.getData().get("destination_address"));
        toOrder.putExtra("estimate_time", remoteMessage.getData().get("estimate_time"));
        toOrder.putExtra("price", remoteMessage.getData().get("price"));
        toOrder.putExtra("cost", remoteMessage.getData().get("cost"));
        toOrder.putExtra("distance", remoteMessage.getData().get("distance"));
        toOrder.putExtra("wallet_payment", remoteMessage.getData().get("wallet_payment"));
        toOrder.putExtra("reg_id", remoteMessage.getData().get("reg_id_pelanggan"));
        toOrder.putExtra("service_order", remoteMessage.getData().get("service_order"));
        toOrder.putExtra("merchant_token", remoteMessage.getData().get("merchant_token"));
        toOrder.putExtra("customer_id", remoteMessage.getData().get("customer_id"));
        toOrder.putExtra("merchant_transaction_id", remoteMessage.getData().get("merchant_transaction_id"));
        toOrder.putExtra("order_time", remoteMessage.getData().get("order_time"));

        boolean inForeground = isAppOnForeground(getApplicationContext());
        timerplay.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !inForeground) {
            orderintent(remoteMessage, toOrder);
        } else {
            toOrder.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toOrder);
        }


    }

    @TargetApi(29)
    private void orderintent(RemoteMessage remoteMessage, Intent intent1) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_order");
        PendingIntent pIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("New Order");
        bigTextStyle.bigText("New Order "+remoteMessage.getData().get("layanan"));

        mBuilder.setContentIntent(pIntent1);
        mBuilder.setOngoing(true);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("New Order");
        mBuilder.setContentText("New Order "+remoteMessage.getData().get("layanan"));
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setFullScreenIntent(pIntent1, true);
        mBuilder.setCategory(NotificationCompat.CATEGORY_CALL);
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        mBuilder.setAutoCancel(false);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        playSound();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notify_order";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Order Notification",
                    NotificationManager.IMPORTANCE_HIGH);

            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        Objects.requireNonNull(notificationManager).notify(100, mBuilder.build());
    }

    CountDownTimer timerplay = new CountDownTimer(20000, 1000) {
        @SuppressLint("SetTextI18n")
        public void onTick(long millisUntilFinished) {
            Constant.duration = millisUntilFinished;
        }


        public void onFinish() {
            sp.updateNotif("OFF");
            cancelIncomingCallNotification();
        }
    }.start();


    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = Objects.requireNonNull(activityManager).getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
    private void playSound() {
        MediaPlayer BG = MediaPlayer.create(getBaseContext(), R.raw.notification);
        BG.setLooping(false);
        BG.setVolume(100, 100);
        BG.start();

        Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        Objects.requireNonNull(v).vibrate(2000);
    }

    public void cancelIncomingCallNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(100);
    }


}
