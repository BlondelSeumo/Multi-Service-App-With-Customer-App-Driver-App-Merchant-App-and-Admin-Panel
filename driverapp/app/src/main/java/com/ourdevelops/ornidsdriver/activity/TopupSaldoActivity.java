package com.ourdevelops.ornidsdriver.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.json.ResponseJson;
import com.ourdevelops.ornidsdriver.json.WithdrawRequestJson;
import com.ourdevelops.ornidsdriver.json.fcm.FCMMessage;
import com.ourdevelops.ornidsdriver.models.Notif;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.SettingPreference;
import com.ourdevelops.ornidsdriver.utils.Utility;
import com.ourdevelops.ornidsdriver.utils.api.FCMHelper;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Objects;

public class TopupSaldoActivity extends AppCompatActivity {

    EditText nominal;
    ImageView text1, text2, text3, text4;
    RelativeLayout rlnotif, rlprogress;
    TextView textnotif;
    String disableback;
    LinearLayout banktransfer, creditcard, paypal,payumoney;
    private String paymentAmount;
    SettingPreference sp;
    ImageView backBtn;
    boolean debug;

    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration configpaypal;

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        View bottom_sheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior.from(bottom_sheet);
        sp = new SettingPreference(this);
        configpaypal = new PayPalConfiguration();


        nominal = findViewById(R.id.balance);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        backBtn = findViewById(R.id.back_btn);
        banktransfer = findViewById(R.id.banktransfer);
        creditcard = findViewById(R.id.creditcard);
        paypal = findViewById(R.id.paypal);
        payumoney = findViewById(R.id.payumoney);

        nominal.addTextChangedListener(Utility.currencyTW(nominal,this));

        if (sp.getSetting()[10].equals("1")) {
            paypal.setVisibility(View.VISIBLE);
        } else {
            paypal.setVisibility(View.GONE);
        }

        if (sp.getSetting()[11].equals("1")) {
            creditcard.setVisibility(View.VISIBLE);
        } else {
            creditcard.setVisibility(View.GONE);
        }

        if (sp.getSetting()[18].equals("1")) {
            payumoney.setVisibility(View.VISIBLE);
        } else {
            payumoney.setVisibility(View.GONE);
        }



        payumoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nominal.getText().toString().isEmpty()) {
                    launchPayUMoneyFlow();
                } else {
                    notif("nominal cant be empty!");
                }
            }
        });

        banktransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nominal.getText().toString().isEmpty()) {
                    sheetlist();
                } else {
                    notif("nominal cant be empty!");
                }
            }
        });

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nominal.getText().toString().isEmpty()) {
                    if (sp.getSetting()[12].equals("1")) {
                        configpaypal.environment(PayPalConfiguration.ENVIRONMENT_SANDBOX);
                    } else {
                        configpaypal.environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION);
                    }
                    configpaypal.clientId(sp.getSetting()[9]);
                    Intent intent = new Intent(TopupSaldoActivity.this, PayPalService.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configpaypal);
                    startService(intent);
                    getPaypal();
                } else {
                    notif("nominal cant be empty!");
                }
            }
        });

        creditcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nominal.getText().toString().isEmpty()) {
                    Intent i = new Intent(TopupSaldoActivity.this, StripePaymentActivity.class);
                    i.putExtra("price", convertAngka(nominal.getText().toString()));
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    notif("nominal cant be empty!");
                }
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("1000");
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("2000");
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("5000");
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                nominal.setText("10000");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        disableback = "false";
    }


    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }


    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public String convertAngka(String value) {
        return (((((value + "")
                .replaceAll(sp.getSetting()[4], ""))
                .replaceAll(" ", ""))
                .replaceAll(",", ""))
                .replaceAll("[$.]", ""));
    }

    private void sheetlist() {
        Intent i = new Intent(TopupSaldoActivity.this, WithdrawActivity.class);
        i.putExtra("type","topup");
        i.putExtra("nominal", convertAngka(nominal.getText().toString()));
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void getPaypal() {
        Double Amount = Double.valueOf(convertAngka(nominal.getText().toString().replace(sp.getSetting()[4],"")));
        DecimalFormat formatter = new DecimalFormat("#,############,##");
        paymentAmount = formatter.format(Amount);
        PayPalPayment payment = new PayPalPayment(new BigDecimal(paymentAmount),
                sp.getSetting()[13], "Topup "+getString(R.string.app_name),
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configpaypal);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("payment", paymentDetails);
                        submit();

                    } catch (JSONException e) {
                        Log.e("payment", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("payment", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("payment", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }

        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    Intent i = new Intent(TopupSaldoActivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    User user = BaseApp.getInstance(this).getLoginUser();
                    Notif notif = new Notif();
                    notif.title = "Topup";
                    notif.message = "topup has been successful";
                    sendNotif(user.getToken(), notif);
                }


            } else if (resultModel != null && resultModel.getError() != null) {
                Log.e("TAG", "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.e("TAG", "Both objects are null!");
            }
        }
    }

    private void submit() {
        progressshow();
        paymentAmount = nominal.getText().toString();
        final User user = BaseApp.getInstance(this).getLoginUser();
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank("paypal");
        request.setName(user.getFullnama());
        request.setAmount(convertAngka(paymentAmount.replace(sp.getSetting()[4],"")));
        request.setCard("1234");
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());

        DriverService service = ServiceGenerator.createService(DriverService.class, user.getNoTelepon(), user.getPassword());
        service.topuppaypal(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Intent i = new Intent(TopupSaldoActivity.this,MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        Notif notif = new Notif();
                        notif.title = "Topup";
                        notif.message = "topup has been successful";
                        sendNotif(user.getToken(), notif);

                    } else {
                        notif("error, please check your account data!");
                    }
                } else {
                    notif("error!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error");
            }
        });
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
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

    private void launchPayUMoneyFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setDoneButtonText("done");
        Double Amount = Double.valueOf(convertAngka(nominal.getText().toString().replace(sp.getSetting()[4],"")));
        DecimalFormat formatter = new DecimalFormat("#,############,##");
        paymentAmount = formatter.format(Amount);
        String payment = String.valueOf(new BigDecimal(paymentAmount));

        payUmoneyConfig.setPayUmoneyActivityTitle(getString(R.string.app_name));

        payUmoneyConfig.disableExitConfirmation(false);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(payment);

        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = BaseApp.getInstance(this).getLoginUser();
        String txnId = "INV-"+System.currentTimeMillis();
        String phone = user.getPhone();
        String productName = "topup";
        String firstName = user.getFullnama();
        String email = user.getEmail();
        String udf1 = user.getId();
        String udf2 = "PayuMoney";
        String udf3 = user.getPassword();
        String udf4 = "driver";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        debug = sp.getSetting()[14].equals("0");
        Log.e("", String.valueOf(amount));

        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(Constant.CONNECTION+"payumoney/payu")
                .setfUrl(Constant.CONNECTION+"payumoney/payu")
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(debug)
                .setKey(sp.getSetting()[15])
                .setMerchantId(sp.getSetting()[16]);

        try {
            PayUmoneySdkInitializer.PaymentParam mPaymentParams = builder.build();
            calculateServerSideHashAndInitiatePayment1(mPaymentParams);
            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,TopupSaldoActivity.this, R.style.Payutheme, true);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    private void calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5)).append("||||||");

        stringBuilder.append(sp.getSetting()[17]);

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte[] messageDigest = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }


}
