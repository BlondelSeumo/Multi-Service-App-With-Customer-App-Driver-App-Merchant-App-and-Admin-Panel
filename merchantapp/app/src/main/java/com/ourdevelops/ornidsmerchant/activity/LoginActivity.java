package com.ourdevelops.ornidsmerchant.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.json.LoginRequestJson;
import com.ourdevelops.ornidsmerchant.json.LoginResponseJson;
import com.ourdevelops.ornidsmerchant.models.FirebaseToken;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.NetworkUtils;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    EditText phoneText, password, numOne, numTwo, numThree, numFour, numFive, numSix;
    TextView countryCode, sendTo, privacypolicy, textnotif, daftar, textnotif2, lupapass;
    Button buttonLogin, confirmButton;
    ImageView backButton, backButtonverify;
    ViewFlipper viewFlipper;
    String phoneNumber, disableback;
    FirebaseAuth mAuth;
    RelativeLayout rlprogress, rlnotif, rlnotif2, rldaftar;
    String verify;

    SharedPreferences sharedPreferences;
    String country_iso_code = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        numOne = findViewById(R.id.numone);
        numTwo = findViewById(R.id.numtwo);
        numThree = findViewById(R.id.numthree);
        numFour = findViewById(R.id.numfour);
        numFive = findViewById(R.id.numfive);
        numSix = findViewById(R.id.numsix);
        privacypolicy = findViewById(R.id.privacypolice);
        daftar = findViewById(R.id.clickdaftar);
        rldaftar = findViewById(R.id.rldaftar);
        lupapass = findViewById(R.id.lupapass);

        phoneText = findViewById(R.id.phonenumber);
        countryCode = findViewById(R.id.countrycode);
        buttonLogin = findViewById(R.id.buttonlogin);
        backButton = findViewById(R.id.back_btn);
        confirmButton = findViewById(R.id.buttonconfirm);
        sendTo = findViewById(R.id.sendtotxt);
        viewFlipper = findViewById(R.id.viewflipper);
        backButtonverify = findViewById(R.id.back_btn_verify);
        rlprogress = findViewById(R.id.rlprogress);
        password = findViewById(R.id.password);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);

        sharedPreferences = getSharedPreferences(Constant.PREF_NAME, MODE_PRIVATE);

        String priv = getResources().getString(R.string.privacy);
        privacypolicy.setText(Html.fromHtml(priv));


        verify = "false";

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = countryCode.getText().toString() + phoneText.getText().toString();
                String phonetext = phoneText.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(phonetext) || TextUtils.isEmpty(pass)) {
                    notif(getString(R.string.phonepass));
                } else if (TextUtils.isEmpty(phonetext)) {
                    notif(getString(R.string.phoneempty));
                } else if (TextUtils.isEmpty(pass)) {
                    notif(getString(R.string.passempty));
                } else {
                    if (NetworkUtils.isConnected(LoginActivity.this)) {
                        progressshow();
                        onSignInClick();
                    } else {
                        progresshide();
                        notif(getString(R.string.text_noInternet));
                    }

                }

            }
        });

        countryCode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        countryCode.setText(dialCode);
                        picker.dismiss();
                        country_iso_code = code;
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });

        lupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPassActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, PrivacyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backButtonverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(viewFlipper);
            }
        });
        disableback = "false";
        codenumber();

    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
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

    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        textnotif2.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif2.setVisibility(View.GONE);
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


    public void codenumber() {

        numOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numOne.getText().toString().length() == 0) {
                    numTwo.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numTwo.getText().toString().length() == 0) {
                    numThree.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numThree.getText().toString().length() == 0) {
                    numFour.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numFour.getText().toString().length() == 0) {
                    numFive.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numFive.getText().toString().length() == 0) {
                    numSix.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void Nextbtn(View view) {
        phoneNumber = countryCode.getText().toString() + phoneText.getText().toString();
        String ccode = countryCode.getText().toString();

        if ((!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(ccode)) && phoneNumber.length() > 5) {
            progressshow();
            Send_Number_tofirebase(phoneNumber);

        } else {
            notif(getString(R.string.wrongnumber));
        }
    }

    public void Send_Number_tofirebase(String phoneNumber) {
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                120,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progresshide();
                android.util.Log.d("respon", e.toString());
                notif2("Verifikasi Gagal!");
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    notif2("wrong code!");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    notif2("Too Many Requests, please try with other phone number!");
                    notif("Too Many Requests, please try with other phone number!");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                resendToken = token;
                sendTo.setText("Send To ( " + phoneNumber + " )");
                progresshide();
                viewFlipper.setInAnimation(LoginActivity.this, R.anim.from_right);
                viewFlipper.setOutAnimation(LoginActivity.this, R.anim.to_left);
                viewFlipper.setDisplayedChild(1);

            }
        };
    }


    public void verifyCode(View view) {
        backButton.setVisibility(View.GONE);
        rldaftar.setVisibility(View.GONE);
        String code = "" + numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();
        if (!code.equals("")) {
            progressshow();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            notif2("kode verifikasi masih kosong");
        }

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Getuser();
                            verify = "true";
                            onSignInClick();

                        } else {
                            progresshide();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                notif2("wrong code!");
                            } else if (task.getException() instanceof FirebaseTooManyRequestsException) {
                                notif2("Too Many Requests, please try with other phone number!");
                                notif("Too Many Requests, please try with other phone number!");
                            }
                        }
                    }
                });
    }


    public void resendCode(View view) {

        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                120,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    private void onSignInClick() {
        progressshow();
        LoginRequestJson request = new LoginRequestJson();
        request.setNotelepon(countryCode.getText().toString().replace("+", "") + phoneText.getText().toString());
        request.setPassword(password.getText().toString());
        FirebaseInstanceId token = FirebaseInstanceId.getInstance();
        request.setRegId(token.getToken());

        MerchantService service = ServiceGenerator.createService(MerchantService.class, request.getNotelepon(), request.getPassword());
        service.login(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        if (verify.equals("false")) {
                            Nextbtn(viewFlipper);
                        } else {
                            User user = response.body().getData().get(0);
                            saveUser(user);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterActivity.SIGNUP_ID) {
            if (resultCode == Activity.RESULT_OK) {
                if (verify.equals("true")) {
                    User user = (User) data.getSerializableExtra(RegisterActivity.USER_KEY);
                    saveUser(user);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        }
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(LoginActivity.this).setLoginUser(user);
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirebaseToken response) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(FirebaseToken.class);
        realm.copyToRealm(response);
        realm.commitTransaction();
    }


}
