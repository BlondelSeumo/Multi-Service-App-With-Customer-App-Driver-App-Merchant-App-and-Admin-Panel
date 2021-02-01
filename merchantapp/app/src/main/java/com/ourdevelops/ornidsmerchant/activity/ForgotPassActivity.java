package com.ourdevelops.ornidsmerchant.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.json.LoginRequestJson;
import com.ourdevelops.ornidsmerchant.json.LoginResponseJson;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPassActivity extends AppCompatActivity {

    ImageView backbtn;
    Button submit;
    TextView email, notiftext;
    RelativeLayout rlnotif, rlprogress;
    String disableback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapassword);

        backbtn = findViewById(R.id.back_btn_verify);
        submit = findViewById(R.id.buttonconfirm);
        email = findViewById(R.id.email);
        notiftext = findViewById(R.id.textnotif2);
        rlnotif = findViewById(R.id.rlnotif2);
        rlprogress = findViewById(R.id.rlprogress);

        disableback = "false";

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    notif(getString(R.string.emailempty));
                } else {
                    get();
                }

            }
        });

    }

    private void get() {
        progressshow();
        LoginRequestJson request = new LoginRequestJson();
        request.setEmail(email.getText().toString());

        MerchantService service = ServiceGenerator.createService(MerchantService.class, request.getEmail(), "12345");
        service.forgot(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        notif("email send!");

                    } else {
                        notif(getString(R.string.phoneemailwrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseJson> call, Throwable t) {
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

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notiftext.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }


}
