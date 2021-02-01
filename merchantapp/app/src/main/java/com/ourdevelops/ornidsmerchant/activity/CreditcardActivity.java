package com.ourdevelops.ornidsmerchant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.json.TopupRequestJson;
import com.ourdevelops.ornidsmerchant.json.TopupResponseJson;
import com.ourdevelops.ornidsmerchant.json.fcm.FCMMessage;
import com.ourdevelops.ornidsmerchant.models.Notif;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.NetworkUtils;
import com.ourdevelops.ornidsmerchant.utils.api.FCMHelper;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;

import java.io.IOException;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditcardActivity extends AppCompatActivity {

    EditText cardnumber, holdername, expiratedate, cvc;
    TextView cardnumbertext, holdernametext, expiratedatetext, notif, back;
    ImageView logo;
    Button submit;
    RelativeLayout rlnotif, rlprogress;
    private String current = "", currentname = "XXXXX XXXXXXX", currentcard = "";
    String a, disableback, price;
    int keyDel;

    public static final String VISA_PREFIX = "4";
    public static final String MASTERCARD_PREFIX = "51,52,53,54,55,";
    public static final String DISCOVER_PREFIX = "6011";
    public static final String DISCOVER2_PREFIX = "65";
    public static final String DINERS_PREFIX = "300,301,302,303,304,305,";
    public static final String DINERSS_PREFIX = "36,38,";
    public static final String JCB_PREFIX = "2131,1800,";
    public static final String JCBS_PREFIX = "35";
    public static final String AMEX_PREFIX = "34,37,";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditcard);
        Intent i = getIntent();
        price = i.getStringExtra("price");


        cardnumber = findViewById(R.id.cardnumber);
        holdername = findViewById(R.id.holdername);
        expiratedate = findViewById(R.id.expdate);
        cvc = findViewById(R.id.cvc);
        cardnumbertext = findViewById(R.id.cardnumbertext);
        holdernametext = findViewById(R.id.holdernametext);
        expiratedatetext = findViewById(R.id.expdatetext);
        logo = findViewById(R.id.logo);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        notif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        back = findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        disableback = "false";

        final String ddmmyyyy = "MMYY";
        final Calendar cal = Calendar.getInstance();

        expiratedate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 5) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(2, 4));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);

                        clean = String.format("%02d%02d", mon, year);
                    }

                    clean = String.format("%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(2, 4));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    expiratedate.setText(current);
                    expiratedate.setSelection(sel < current.length() ? sel : current.length());

                    expiratedatetext.setText(current);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holdername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(currentname)) {
                    holdernametext.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean flag = true;
                String eachBlock[] = cardnumber.getText().toString().split("-");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {

                    cardnumber.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {

                        if (((cardnumber.getText().length() + 1) % 5) == 0) {

                            if (s.toString().split("-").length <= 3) {
                                cardnumber.setText(cardnumber.getText() + "-");
                                cardnumber.setSelection(cardnumber.getText().length());
                                if (s.toString().substring(0, 1).equals(VISA_PREFIX) && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.visa_logo));
                                } else if (MASTERCARD_PREFIX.contains(s.toString().substring(0, 2) + ",") && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.mastercard));
                                } else if (s.toString().substring(0, 4).equals(DISCOVER_PREFIX) && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.discover));
                                } else if (s.toString().substring(0, 2).equals(DISCOVER2_PREFIX) && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.discover));
                                } else if (DINERS_PREFIX.contains(s.toString().substring(0, 3) + ",") && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.diners_club));
                                } else if (DINERSS_PREFIX.contains(s.toString().substring(0, 2) + ",") && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.diners_club));
                                } else if (JCB_PREFIX.contains(s.toString().substring(0, 4) + ",") && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.jcb));
                                } else if (s.toString().substring(0, 2).equals(JCBS_PREFIX) && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.jcb));
                                } else if (AMEX_PREFIX.contains(s.toString().substring(0, 2) + ",") && s.length() >= 1) {
                                    logo.setImageDrawable(getResources().getDrawable(R.drawable.american_express));
                                }

                            }
                        }
                        a = cardnumber.getText().toString();

                    } else {
                        a = cardnumber.getText().toString();
                        keyDel = 0;
                    }


                    cardnumbertext.setText(s.toString());

                } else {
                    cardnumber.setText(a);
                }

                if (s.toString().length() <= 3) {
                    logo.setImageDrawable(getResources().getDrawable(R.color.transparent));
                }

            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardnumber.getText().toString().isEmpty()) {
                    notif("card number cant be empty!");
                } else if (holdername.getText().toString().isEmpty()) {
                    notif("holder name cant be empty!");
                } else if (expiratedate.getText().toString().isEmpty()) {
                    notif("exp date cant be empty!");
                } else if (cvc.getText().toString().isEmpty()) {
                    notif("CVC cant be empty!");
                } else {
                    if (NetworkUtils.isConnected(CreditcardActivity.this)) {
                        submit();
                    } else {
                        progresshide();
                        notif(getString(R.string.text_noInternet));
                    }
                }
            }
        });

    }

    private void submit() {
        progressshow();
        final User user = BaseApp.getInstance(this).getLoginUser();
        TopupRequestJson request = new TopupRequestJson();
        request.setId(user.getId());
        request.setName(holdername.getText().toString());
        request.setEmail(user.getEmail());
        request.setCardnum(cardnumber.getText().toString().replaceAll("-", ""));
        request.setCvc(cvc.getText().toString());
        request.setExpired(expiratedate.getText().toString());
        request.setProduct("topup");
        request.setNumber("1");
        request.setPrice(price);

        MerchantService service = ServiceGenerator.createService(MerchantService.class, user.getNoTelepon(), user.getPassword());
        service.topup(request).enqueue(new Callback<TopupResponseJson>() {
            @Override
            public void onResponse(Call<TopupResponseJson> call, Response<TopupResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        Intent intent = new Intent(CreditcardActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        Notif notif = new Notif();
                        notif.title = "Topup";
                        notif.message = "topup has been successful";
                        sendNotif(user.getToken_merchant(), notif);

                    } else {
                        notif("there is an error!");
                    }
                } else {
                    notif("error, please check your card data!");
                }
            }

            @Override
            public void onFailure(Call<TopupResponseJson> call, Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error");
            }
        });
    }

    public void onBackPressed() {
        if (disableback.equals("true")) {
            return;
        } else {
            finish();
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
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
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }


}
