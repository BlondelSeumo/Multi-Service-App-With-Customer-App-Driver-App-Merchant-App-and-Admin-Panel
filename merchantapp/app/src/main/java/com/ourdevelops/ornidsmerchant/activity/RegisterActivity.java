package com.ourdevelops.ornidsmerchant.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.json.GetServiceResponseJson;
import com.ourdevelops.ornidsmerchant.json.HistoryRequestJson;
import com.ourdevelops.ornidsmerchant.json.RegisterRequestJson;
import com.ourdevelops.ornidsmerchant.json.RegisterResponseJson;
import com.ourdevelops.ornidsmerchant.models.ServiceModel;
import com.ourdevelops.ornidsmerchant.models.CategoryModel;
import com.ourdevelops.ornidsmerchant.utils.Log;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    ImageView back_btn, backButtonverify, fotoktp, bannermerchant;
    EditText idtype, phone, namamitra, email, numOne, numTwo, numThree, numFour, numFive, numSix, alamat, idcardtext, merchantname;
    TextView countryCode, sendTo, textnotif, textnotif2, privacypolicy, opentime, closetime, merchantloc;
    Button submit, confirmButton, addimage, addimagektp;
    RelativeLayout rlnotif, rlprogress, rlnotif2;
    Spinner merchantype, merchantcat;
    String phoneNumber;
    FirebaseUser firebaseUser;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    FirebaseAuth mAuth;
    byte[] imageByteArray, imageByteArrayktp;
    Bitmap decoded, decodedktp;
    String disableback;
    ViewFlipper viewFlipper;
    String country_iso_code = "en";
    String verify, token;
    public static final int SIGNUP_ID = 110;
    public static final String USER_KEY = "UserKey";
    String latitude, longitude;
    private final int DESTINATION_ID = 1;

    ArrayList<ServiceModel> fiturlist;
    ArrayList<String> jobsdata;
    private List<ServiceModel> fiturdata;

    ArrayList<CategoryModel> katlist;
    private List<CategoryModel> katdata;
    ArrayList<String> jobskatdata;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        realm = Realm.getDefaultInstance();
        fiturlist = new ArrayList<>();
        jobskatdata = new ArrayList<>();
        jobsdata = new ArrayList<>();
        katlist = new ArrayList<>();
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        back_btn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumber);
        namamitra = findViewById(R.id.namamitra);
        email = findViewById(R.id.email);
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
        alamat = findViewById(R.id.address);
        idcardtext = findViewById(R.id.noktp);
        fotoktp = findViewById(R.id.fotoktp);
        idtype = findViewById(R.id.idtype);
        bannermerchant = findViewById(R.id.bannermerchant);
        merchantype = findViewById(R.id.merchanttype);
        merchantname = findViewById(R.id.merchantname);
        merchantcat = findViewById(R.id.merchantcat);
        merchantloc = findViewById(R.id.merchantloc);
        opentime = findViewById(R.id.opentime);
        closetime = findViewById(R.id.closetime);
        addimage = findViewById(R.id.addimage);
        addimagektp = findViewById(R.id.addfotoktp);


        String priv = getResources().getString(R.string.privacy);
        privacypolicy.setText(Html.fromHtml(priv));

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, PrivacyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        merchantloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, PicklocationActivity.class);
                intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, DESTINATION_ID);
                startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
            }
        });

        opentime.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                opentanggal();
            }
        });

        closetime.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                closetanggal();
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        addimagektp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImagektp();
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
        spinner();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailvalidate = email.getText().toString();

                if (TextUtils.isEmpty(phone.getText().toString())) {
                    notif(getString(R.string.phoneempty));
                } else if (TextUtils.isEmpty(namamitra.getText().toString())) {
                    notif("Name cant be empty!");
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    notif(getString(R.string.emailempty));
                } else if (TextUtils.isEmpty(alamat.getText().toString())) {
                    notif("please enter andress!");
                } else if (TextUtils.isEmpty(idcardtext.getText().toString())) {
                    notif("please enter ID Card number!");
                } else if (TextUtils.isEmpty(idtype.getText().toString())) {
                    notif("please enter ID Card type!");
                } else if (imageByteArrayktp == null) {
                    notif("please upload Image ID Card!");
                } else if (imageByteArray == null) {
                    notif("please add photo!");
                }  else if (!emailvalidate.matches(emailPattern)) {
                    notif("wrong email format!");
                } else if (merchantype.getSelectedItemPosition() == 0) {
                    notif("Please select service");
                } else if (TextUtils.isEmpty(merchantname.getText().toString())) {
                    notif("ornidsmerchant name cant be empty!");
                } else if (merchantcat.getSelectedItemPosition() == 0) {
                    notif("category cant be empty");
                } else if (TextUtils.isEmpty(merchantloc.getText().toString())) {
                    notif("location cant be empty!");
                } else if (TextUtils.isEmpty(opentime.getText().toString())) {
                    notif("open time cant be empty!");
                } else if (TextUtils.isEmpty(closetime.getText().toString())) {
                    notif("close time cant be empty!");
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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        disableback = "false";
        codenumber();
        verify = "false";

    }

    private void spinner() {
        ServiceModel jobs = new ServiceModel();
        jobs.setFitur("0");
        jobs.setIdFitur(-1);
        fiturlist.add(jobs);
        jobsdata.add("Select Service");
        MerchantService userService = ServiceGenerator.createService(MerchantService.class,
                "admin", "admin");
        userService.getFitur().enqueue(new Callback<GetServiceResponseJson>() {
            @Override
            public void onResponse(Call<GetServiceResponseJson> call, Response<GetServiceResponseJson> response) {
                if (response.isSuccessful()) {
                    fiturdata = Objects.requireNonNull(response.body()).getData();
                    for (int i = 0; i < fiturdata.size(); i++) {
                        ServiceModel jobss = new ServiceModel();
                        jobss.setFitur(fiturdata.get(i).getFitur());
                        jobss.setIdFitur(fiturdata.get(i).getIdFitur());
                        fiturlist.add(jobss);
                        jobsdata.add(fiturdata.get(i).getFitur());
                    }

                    ArrayAdapter<String> citySpinner = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner, jobsdata);
                    citySpinner.setDropDownViewResource(R.layout.spinner);
                    merchantype.setAdapter(citySpinner);
                    merchantype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            // TODO Auto-generated method stub

                            if (position == 0) {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                spinner2(String.valueOf(fiturlist.get(merchantype.getSelectedItemPosition()).getIdFitur()));
                            } else {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                spinner2(String.valueOf(fiturlist.get(merchantype.getSelectedItemPosition()).getIdFitur()));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<GetServiceResponseJson> call, Throwable t) {

            }
        });
    }


    private void spinner2(String id) {
        if (jobskatdata != null) {
            jobskatdata.clear();
        }
        if (katlist != null) {
            katlist.clear();
        }

        if (katdata != null) {
            katdata.clear();
        }
        CategoryModel job = new CategoryModel();
        job.setNama("0");
        job.setIdkategori(0);
        katlist.add(job);
        jobskatdata.add("Select Category");
        MerchantService userService = ServiceGenerator.createService(MerchantService.class,
                "admin", "admin");
        HistoryRequestJson request = new HistoryRequestJson();
        request.setIdmerchant(id);
        userService.getKategori(request).enqueue(new Callback<GetServiceResponseJson>() {
            @Override
            public void onResponse(Call<GetServiceResponseJson> call, Response<GetServiceResponseJson> response) {
                if (response.isSuccessful()) {
                    katdata = Objects.requireNonNull(response.body()).getKategori();
                    for (int j = 0; j < katdata.size(); j++) {
                        CategoryModel jobs = new CategoryModel();
                        jobs.setNama(katdata.get(j).getNama());
                        jobs.setIdkategori(katdata.get(j).getIdkategori());
                        katlist.add(jobs);
                        jobskatdata.add(katdata.get(j).getNama());
                    }
                    ArrayAdapter<String> citySpinner = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner, jobskatdata);
                    citySpinner.setDropDownViewResource(R.layout.spinner);
                    merchantcat.setAdapter(citySpinner);
                    merchantcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            // TODO Auto-generated method stub

                            if (position == 0) {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                Log.e("",String.valueOf(katlist.get(merchantcat.getSelectedItemPosition()).getIdkategori()));
                            } else {
                                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                ((TextView) parent.getChildAt(0)).setTextSize(14);
                                Log.e("",String.valueOf(katlist.get(merchantcat.getSelectedItemPosition()).getIdkategori()));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetServiceResponseJson> call, Throwable t) {

            }
        });
    }

    private void closetanggal() {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                try {

                    String timeString = hourOfDay + ":" + minute;
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                    Date time = sdf.parse(timeString);

                    sdf = new SimpleDateFormat("HH:mm", Locale.US);
                    String formatedTime = sdf.format(Objects.requireNonNull(time));
                    closetime.setText(formatedTime);

                } catch (Exception ignored) {
                }
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Timepickerdialog");
    }

    private void opentanggal() {

        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                try {

                    String timeString = hourOfDay + ":" + minute;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date time = sdf.parse(timeString);

                    sdf = new SimpleDateFormat("HH:mm");
                    String formatedTime = sdf.format(Objects.requireNonNull(time));
                    opentime.setText(formatedTime);

                } catch (Exception ignored) {
                }
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Timepickerdialog");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PicklocationActivity.LOCATION_PICKER_ID) {
                String addressset = data.getStringExtra(PicklocationActivity.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(PicklocationActivity.LOCATION_LATLNG);
                merchantloc.setText(addressset);
                latitude = String.valueOf(Objects.requireNonNull(latLng).getLatitude());
                longitude = String.valueOf(latLng.getLongitude());
            } else if (requestCode == 1) {
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

            } else if (requestCode == 2) {
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
                bannermerchant.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
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
            startActivityForResult(intent, 1);
        }
    }

    ///

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
                verify = "true";
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
                120,
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
        request.setNama_mitra(namamitra.getText().toString());
        request.setJenis_identitas(idtype.getText().toString());
        request.setNo_ktp(idcardtext.getText().toString());
        request.setNo_telepon(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setEmail(email.getText().toString());
        request.setPhone(phone.getText().toString());
        request.setAlamat_mitra(alamat.getText().toString());
        request.setCountrycode(countryCode.getText().toString());
        request.setId_fitur(String.valueOf(fiturlist.get(merchantype.getSelectedItemPosition()).getIdFitur()));
        request.setNama_merchant(merchantname.getText().toString());
        request.setAlamat_merchant(merchantloc.getText().toString());
        request.setLatitude_merchant(latitude);
        request.setLongitude_merchant(longitude);
        request.setJam_buka(opentime.getText().toString());
        request.setJam_tutup(closetime.getText().toString());
        request.setCategory_merchant(String.valueOf(katlist.get(merchantcat.getSelectedItemPosition()).getIdkategori()));
        request.setFoto(getStringImage(decoded));
        request.setFoto_ktp(getStringImagektp(decodedktp));
        request.setChecked(check);


        MerchantService service = ServiceGenerator.createService(MerchantService.class, request.getEmail(), request.getNo_telepon());
        service.register(request).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(Call<RegisterResponseJson> call, Response<RegisterResponseJson> response) {
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
            public void onFailure(Call<RegisterResponseJson> call, Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error!");
            }
        });
    }


}
