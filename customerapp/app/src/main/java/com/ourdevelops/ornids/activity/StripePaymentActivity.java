package com.ourdevelops.ornids.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.json.ResponseJson;
import com.ourdevelops.ornids.json.StripeRequestJson;
import com.ourdevelops.ornids.json.fcm.FCMMessage;
import com.ourdevelops.ornids.models.Notif;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.SettingPreference;
import com.ourdevelops.ornids.utils.api.FCMHelper;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.UserService;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripePaymentActivity extends AppCompatActivity {
    private Button mSubmitBt;
    private CardInputWidget cardInputWidget;
    private Stripe stripe;
    private String price;
    RelativeLayout rlnotif, rlprogress;
    TextView notif;
    ImageView back_btn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);
        Intent i = getIntent();
        price = i.getStringExtra("price");
        initView();
        rlprogress.setVisibility(View.VISIBLE);
        startCheckout();
        getinitAction();

    }

    private void getinitAction() {
        final User user = BaseApp.getInstance(this).getLoginUser();
        StripeRequestJson request = new StripeRequestJson();
        request.setUserid(user.getId());
        request.setPhone_number(user.getNoTelepon());
        request.setPrice(price);

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.intentstripe(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        rlprogress.setVisibility(View.GONE);
                        initAction(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    private void initAction(String client) {
        mSubmitBt.setOnClickListener((View view) -> {
            Card card = cardInputWidget.getCard();
            if (card != null) {
                if (!card.validateCard()) {
                    notif("please check your card data!");
                    return;
                }
            } else {
                notif("please check your card data!");
                return;
            }
            rlprogress.setVisibility(View.VISIBLE);
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, client);
                final Context context = getApplicationContext();
                stripe = new Stripe(
                        context,
                        PaymentConfiguration.getInstance(context).getPublishableKey()
                );
                stripe.confirmPayment(this, confirmParams);
            }
        });
    }

    private void initView() {
        mSubmitBt = findViewById(R.id.submit_bt);
        cardInputWidget = findViewById(R.id.card_iw);
        notif = findViewById(R.id.textnotif);
        rlnotif = findViewById(R.id.rlnotif);
        rlprogress = findViewById(R.id.rlprogress);
        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startCheckout() {
        SettingPreference sp = new SettingPreference(this);
        PaymentConfiguration.init(
                getApplicationContext(),
                sp.getSetting()[15]
        );
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull(sp.getSetting()[15]
                ));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<StripePaymentActivity> activityRef;

        PaymentResultCallback(@NonNull StripePaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final StripePaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String data = gson.toJson(paymentIntent);
                try {
                    JSONObject respone = new JSONObject(data);
                    success(respone.get("id").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                rlprogress.setVisibility(View.GONE);
                notif("Payment failed");
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final StripePaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            rlprogress.setVisibility(View.GONE);
            notif("Error " + e.getMessage());
        }
    }

    private void success(String id) {
        final User user = BaseApp.getInstance(this).getLoginUser();
        StripeRequestJson request = new StripeRequestJson();
        request.setUserid(user.getId());
        request.setPhone_number(user.getNoTelepon());
        request.setId(id);
        request.setName(user.getFullnama());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.actionstripe(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Intent intent = new Intent(StripePaymentActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        Notif notif = new Notif();
                        notif.title = "Topup";
                        notif.message = "topup has been successful";
                        sendNotif(user.getToken(), notif);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void sendNotif(final String regIDTujuan, final Notif notif) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(notif);

        FCMHelper.sendMessage(Constant.FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }


}
