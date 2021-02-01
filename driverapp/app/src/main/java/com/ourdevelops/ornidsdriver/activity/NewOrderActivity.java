package com.ourdevelops.ornidsdriver.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.json.AcceptRequestJson;
import com.ourdevelops.ornidsdriver.json.AcceptResponseJson;
import com.ourdevelops.ornidsdriver.utils.AudioPlayer;
import com.ourdevelops.ornidsdriver.utils.api.FCMHelper;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.ourdevelops.ornidsdriver.json.fcm.FCMMessage;
import com.ourdevelops.ornidsdriver.models.OrderFCM;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.SettingPreference;
import com.ourdevelops.ornidsdriver.utils.Utility;
import com.ourdevelops.ornidsdriver.utils.PicassoTrustAll;

import java.io.IOException;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewOrderActivity extends AppCompatActivity {
    @BindView(R.id.layanan)
    TextView layanantext;
    @BindView(R.id.layanandes)
    TextView layanandesctext;
    @BindView(R.id.pickUpText)
    TextView pickuptext;
    @BindView(R.id.destinationText)
    TextView destinationtext;
    @BindView(R.id.service)
    TextView estimatetext;
    @BindView(R.id.distance)
    TextView distancetext;
    @BindView(R.id.cost)
    TextView costtext;
    @BindView(R.id.price)
    TextView pricetext;
    @BindView(R.id.totaltext)
    TextView totaltext;
    @BindView(R.id.image)
    ImageView icon;
    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.distancetext)
    TextView distancetextes;
    @BindView(R.id.costtext)
    TextView costtextes;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.order)
    Button order;
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @BindView(R.id.lldestination)
    LinearLayout lldestination;
    @BindView(R.id.lldistance)
    LinearLayout lldistance;

    String waktuorder,iconfitur, layanan, layanandesc, alamatasal, alamattujuan, estimasitime, hargatotal, cost, distance, idtrans, regid, orderfitur,tokenmerchant,idpelanggan,idtransmerchant;
    String wallett, bg;
    SettingPreference sp;
    AudioPlayer audioPlayer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.bind(this);
        removeNotif();
        setScreenOnFlags();
        sp = new SettingPreference(this);
        sp.updateNotif("ON");
        audioPlayer = new AudioPlayer(this);
        audioPlayer.playRingtone();
        Intent intent = getIntent();
        iconfitur = intent.getStringExtra("icon");
        layanan = intent.getStringExtra("layanan");
        layanandesc = intent.getStringExtra("layanandesc");
        alamatasal = intent.getStringExtra("pickup_address");
        alamattujuan = intent.getStringExtra("destination_address");
        estimasitime = intent.getStringExtra("estimate_time");
        hargatotal = intent.getStringExtra("price");
        cost = intent.getStringExtra("cost");
        distance = intent.getStringExtra("distance");
        idtrans = intent.getStringExtra("transaction_id");
        regid = intent.getStringExtra("reg_id");
        wallett = intent.getStringExtra("wallet_payment");
        orderfitur = intent.getStringExtra("service_order");
        tokenmerchant = intent.getStringExtra("merchant_token");
        idpelanggan = intent.getStringExtra("customer_id");
        idtransmerchant = intent.getStringExtra("merchant_transaction_id");
        waktuorder = intent.getStringExtra("order_time");
        if (orderfitur.equalsIgnoreCase("3")) {
            lldestination.setVisibility(View.GONE);
            lldistance.setVisibility(View.GONE);

        }
        if (orderfitur.equalsIgnoreCase("4")) {

            estimatetext.setText(estimasitime);
            time.setText("Merchant");
            distancetextes.setText("Delivery Fee");
            costtextes.setText("Order Cost");
            Utility.currencyTXT(distancetext, distance, this);
            Utility.currencyTXT(costtext, cost, this);
        } else {

            estimatetext.setText(estimasitime);
            distancetext.setText(distance);
            costtext.setText(cost);
        }
        layanantext.setText(layanan);
        layanandesctext.setText(layanandesc);
        pickuptext.setText(alamatasal);
        destinationtext.setText(alamattujuan);
        Utility.currencyTXT(pricetext, hargatotal, this);
        if (wallett.equalsIgnoreCase("true")) {
            totaltext.setText("Total (WALLET)");
        } else {
            totaltext.setText("Total (CASH)");
        }


        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESFITUR + iconfitur)
                .placeholder(R.drawable.logo)
                .resize(100, 100)
                .into(icon);

        timerplay.start();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioPlayer.stopRingtone();
                sp.updateNotif("OFF");
                timerplay.cancel();
                Intent toOrder = new Intent(NewOrderActivity.this, MainActivity.class);
                toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toOrder);

            }
        });

        if (new SettingPreference(this).getSetting()[0].equals("OFF")) {
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getaccept();

                }
            });
        } else {
            getaccept();
        }


    }

    CountDownTimer timerplay = new CountDownTimer(Constant.duration, 1000) {

        @SuppressLint("SetTextI18n")
        public void onTick(long millisUntilFinished) {
            timer.setText("" + millisUntilFinished / 1000);
        }


        public void onFinish() {
            audioPlayer.stopRingtone();
            sp.updateNotif("OFF");
            timer.setText("0");
            Intent toOrder = new Intent(NewOrderActivity.this, MainActivity.class);
            toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toOrder);
        }
    }.start();




    @Override
    public void onBackPressed() {
    }

    private void getaccept() {
        audioPlayer.stopRingtone();
        timerplay.cancel();
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.accept(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful()) {
                    sp.updateNotif("OFF");
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("berhasil")) {
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.driver_id = loginUser.getId();
                        orderfcm.transaction_id = idtrans;
                        orderfcm.response = "2";
                        if (orderfitur.equalsIgnoreCase("4")) {
                            orderfcm.desc = "the driver is buying an order";
                            orderfcm.customer_id = idpelanggan;
                            orderfcm.invoice = "INV-"+idtrans+idtransmerchant;
                            orderfcm.ordertime = waktuorder;
                            sendMessageToDriver(tokenmerchant, orderfcm);
                        } else {
                            orderfcm.desc = getString(R.string.notification_start);
                        }
                        sendMessageToDriver(regid, orderfcm);
                    } else {
                        sp.updateNotif("OFF");
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(NewOrderActivity.this, "Order is no longer available!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(NewOrderActivity.this, "Error Connection!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(View.GONE);
                sp.updateNotif("OFF");
                rlprogress.setVisibility(View.GONE);
                Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });
    }

    private void sendMessageToDriver(final String regIDTujuan, final OrderFCM response) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(response);

        FCMHelper.sendMessage(Constant.FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.updateNotif("OFF");
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(100);
    }

    private void setScreenOnFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            Objects.requireNonNull(keyguardManager).requestDismissKeyguard(this, null);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }
    }
}
