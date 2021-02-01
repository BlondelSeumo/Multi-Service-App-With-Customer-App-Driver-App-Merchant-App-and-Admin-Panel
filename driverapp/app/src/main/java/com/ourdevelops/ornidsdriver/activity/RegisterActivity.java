package com.ourdevelops.ornidsdriver.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.json.JobResponseJson;
import com.ourdevelops.ornidsdriver.json.RegisterRequestJson;
import com.ourdevelops.ornidsdriver.json.RegisterResponseJson;
import com.ourdevelops.ornidsdriver.models.JobModel;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.exifinterface.media.ExifInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView photo, gantifoto, backbtn, backButtonverify, fotosim, fotoktp;
    EditText phone, nama, email, numOne, numTwo, numThree, numFour, numFive, numSix, alamat, brand, type, vehiclenumber, color, idcardtext, driverlicensetext;
    TextView tanggal, countryCode, sendTo, textnotif, textnotif2, privacypolicy;
    Button submit, confirmButton;
    RelativeLayout rlnotif, rlprogress, rlnotif2;
    Spinner gender, job;
    private SimpleDateFormat dateFormatter, dateFormatterview;
    String phoneNumber;
    FirebaseUser firebaseUser;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    FirebaseAuth mAuth;
    byte[] imageByteArray, imageByteArrayktp, imageByteArraysim;
    Bitmap decoded, decodedktp, decodedsim;
    String dateview, disableback;
    String[] spinnergender;
    String[] spinnerjob;
    ViewFlipper viewFlipper;
    String country_iso_code = "en";
    String verify, token;
    List<JobModel> joblist;
    ArrayList<JobModel> fiturlist;
    ArrayList<String> jobdata;
    public static final int SIGNUP_ID = 110;
    public static final String USER_KEY = "UserKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fiturlist = new ArrayList<>();
        jobdata = new ArrayList<>();
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        photo = findViewById(R.id.photo);
        gantifoto = findViewById(R.id.editfoto);
        backbtn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumber);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        tanggal = findViewById(R.id.tanggal);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycode);
        viewFlipper = findViewById(R.id.viewflipper);
        backButtonverify = findViewById(R.id.back_btn_verify);
        rlprogress = findViewById(R.id.rlprogress);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);
        confirmButton = findViewById(R.id.buttonconfirm);
        token = FirebaseInstanceId.getInstance().getToken();
        numOne = findViewById(R.id.numone);
        numTwo = findViewById(R.id.numtwo);
        numThree = findViewById(R.id.numthree);
        numFour = findViewById(R.id.numfour);
        numFive = findViewById(R.id.numfive);
        numSix = findViewById(R.id.numsix);
        sendTo = findViewById(R.id.sendtotxt);
        privacypolicy = findViewById(R.id.privacypolice);
        gender = findViewById(R.id.gender);
        alamat = findViewById(R.id.address);
        brand = findViewById(R.id.brand);
        type = findViewById(R.id.type);
        vehiclenumber = findViewById(R.id.nomorkendaraan);
        color = findViewById(R.id.color);
        idcardtext = findViewById(R.id.noktp);
        driverlicensetext = findViewById(R.id.sim);
        fotosim = findViewById(R.id.fotosim);
        fotoktp = findViewById(R.id.fotoktp);

        spinnergender = getResources().getStringArray(R.array.gendertype);
        job = findViewById(R.id.job);
        spinnerjob = getResources().getStringArray(R.array.jobtype);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gantifoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        fotosim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImagesim();
            }
        });

        fotoktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImagektp();
            }
        });

        String priv = getResources().getString(R.string.privacy);
        privacypolicy.setText(Html.fromHtml(priv));

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
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormatterview = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, PrivacyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        ArrayAdapter<String> genderSpinner = new ArrayAdapter<>(this, R.layout.spinner, spinnergender);
        genderSpinner.setDropDownViewResource(R.layout.spinner);
        gender.setAdapter(genderSpinner);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailvalidate = email.getText().toString();

                if (imageByteArray == null) {
                    notif("please add photo!");
                } else if (TextUtils.isEmpty(phone.getText().toString())) {
                    notif(getString(R.string.phoneempty));
                } else if (TextUtils.isEmpty(nama.getText().toString())) {
                    notif("Name cant be empty");
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    notif(getString(R.string.emailempty));
                } else if (TextUtils.isEmpty(tanggal.getText().toString())) {
                    notif("birthday cant be empty!");
                } else if (!emailvalidate.matches(emailPattern)) {
                    notif("wrong email format!");
                } else if (gender.getSelectedItemPosition() == 0) {
                    notif("please select gender!");
                } else if (job.getSelectedItemPosition() == 0) {
                    notif("please select job!");
                } else if (TextUtils.isEmpty(alamat.getText().toString())) {
                    notif("please enter andress!");
                } else if (TextUtils.isEmpty(brand.getText().toString())) {
                    notif("please enter vehicle brand!");
                } else if (TextUtils.isEmpty(type.getText().toString())) {
                    notif("please enter vehicle type!");
                } else if (TextUtils.isEmpty(vehiclenumber.getText().toString())) {
                    notif("please enter vehicle number!");
                } else if (TextUtils.isEmpty(color.getText().toString())) {
                    notif("please enter vehicle color!");
                } else if (TextUtils.isEmpty(idcardtext.getText().toString())) {
                    notif("please enter ID Card number!");
                } else if (TextUtils.isEmpty(driverlicensetext.getText().toString())) {
                    notif("please enter Driver License!");
                } else if (imageByteArrayktp == null) {
                    notif("please upload Image ID Card!");
                } else if (imageByteArraysim == null) {
                    notif("please upload Image SIM Card!");
                } else {
                    upload("true");
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(viewFlipper);
            }
        });
        backButtonverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTanggal();
            }
        });
        disableback = "false";
        codenumber();
        verify = "false";
        get();
    }

    private void get() {
        JobModel jobs = new JobModel();
        jobs.setId(0);
        jobs.setJob("Select Job");
        fiturlist.add(jobs);
        jobdata.add("Select Job");
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "12345");
        service.job().enqueue(new Callback<JobResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<JobResponseJson> call, @NonNull Response<JobResponseJson> response) {

                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        joblist = response.body().getData();
                        for (int i = 0; i < joblist.size(); i++) {
                            JobModel jobber = new JobModel();
                            jobber.setId(joblist.get(i).getId());
                            jobber.setJob(joblist.get(i).getJob());
                            fiturlist.add(jobber);
                            jobdata.add(joblist.get(i).getJob());
                        }
                        ArrayAdapter<String> jobSpinner = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner, jobdata);
                        jobSpinner.setDropDownViewResource(R.layout.spinner);
                        job.setAdapter(jobSpinner);
                        job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                // TODO Auto-generated method stub
                                if (position == 0) {
                                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                                    Log.e("tes",String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));
                                } else {
                                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                                    Log.e("tes",String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JobResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showTanggal() {

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        tanggal.setText(dateFormatterview.format(date_ship_millis));
                        dateview = dateFormatter.format(date_ship_millis);
                    }
                }
        );
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Datepickerdialog");
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

    public void Nextbtn(View view) {
        phoneNumber = countryCode.getText().toString() + phone.getText().toString();
        String ccode = countryCode.getText().toString();

        if ((!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(ccode)) && phoneNumber.length() > 5) {
            progressshow();
            Send_Number_tofirebase(phoneNumber);

        } else {
            notif("Please enter phone correctly");
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


    /**
     * uploadfoto-------------start.
     */
    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constant.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    private void selectImage() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    private void selectImagektp() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }

    private void selectImagesim() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 4);
        }
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(imagebitmap, (int) (imagebitmap.getWidth() * 0.1), (int) (imagebitmap.getHeight()*0.1) ,  true);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(scaleBitmap, 0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                photo.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
            if (requestCode == 3) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(imagebitmap, (int) (imagebitmap.getWidth() * 0.1), (int) (imagebitmap.getHeight()*0.1) ,  true);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(scaleBitmap, 0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                fotoktp.setImageBitmap(rotatedBitmap);
                imageByteArrayktp = baos.toByteArray();
                decodedktp = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
            if (requestCode == 4) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(imagebitmap, (int) (imagebitmap.getWidth() * 0.1), (int) (imagebitmap.getHeight()*0.1) ,  true);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Bitmap rotatedBitmap = Bitmap.createBitmap(scaleBitmap, 0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                fotosim.setImageBitmap(rotatedBitmap);
                imageByteArraysim = baos.toByteArray();
                decodedsim = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArray = baos.toByteArray();
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public String getStringImagektp(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArrayktp = baos.toByteArray();
        return Base64.encodeToString(imageByteArrayktp, Base64.DEFAULT);
    }

    public String getStringImagesim(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArraysim = baos.toByteArray();
        return Base64.encodeToString(imageByteArraysim, Base64.DEFAULT);
    }

    /**
     * uploadfoto-------------end.
     */

//sendcode-----------------------
    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        textnotif2.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif2.setVisibility(View.GONE);
            }
        }, 3000);
    }

    public void Send_Number_tofirebase(String phoneNumber) {
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
                verify = "true";
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
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
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                resendToken = token;
                sendTo.setText("Send to ( " + phoneNumber + " )");
                progresshide();
                viewFlipper.setInAnimation(RegisterActivity.this, R.anim.from_right);
                viewFlipper.setOutAnimation(RegisterActivity.this, R.anim.to_left);
                viewFlipper.setDisplayedChild(1);

            }
        };
    }


    public void verifyCode(View view) {
        String code = "" + numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();
        if (!code.equals("")) {
            progressshow();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);

        } else {
            notif2("Enter your verification code!");
        }

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            upload("false");
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
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
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

    private void upload(final String check) {
        progressshow();
        RegisterRequestJson request = new RegisterRequestJson();
        request.setNamadriver(nama.getText().toString());
        request.setNoktp(idcardtext.getText().toString());
        request.setTglLahir(dateview);
        request.setNoTelepon(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setPhone(phone.getText().toString());
        request.setEmail(email.getText().toString());
        request.setFoto(getStringImage(decoded));
        request.setJob(String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));
        request.setGender(String.valueOf(gender.getSelectedItem()));
        request.setAlamat(alamat.getText().toString());
        request.setMerek(brand.getText().toString());
        request.setTipe(type.getText().toString());
        request.setNomorkendaraan(vehiclenumber.getText().toString());
        request.setWarna(color.getText().toString());
        request.setFotoktp(getStringImagektp(decodedktp));
        request.setFotosim(getStringImagesim(decodedsim));
        request.setIdsim(driverlicensetext.getText().toString());
        request.setCountrycode(countryCode.getText().toString());
        request.setChecked(check);

        DriverService service = ServiceGenerator.createService(DriverService.class, request.getEmail(), request.getNoTelepon());
        service.register(request).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponseJson> call, @NonNull Response<RegisterResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("next")) {
                        Nextbtn(viewFlipper);

                    } else if (response.body().getMessage().equalsIgnoreCase("success")) {

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();

                    } else {
                        notif(response.body().getMessage());
                    }
                } else {
                    notif("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error!");
            }
        });
    }


}
