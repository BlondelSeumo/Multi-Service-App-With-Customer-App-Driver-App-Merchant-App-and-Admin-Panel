package com.ourdevelops.ornidsmerchant.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.json.EditMerchantRequestJson;
import com.ourdevelops.ornidsmerchant.json.LoginResponseJson;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;
import com.ourdevelops.ornidsmerchant.utils.PicassoTrustAll;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EditstoreActivity extends AppCompatActivity {

    ImageView backbtn, bannermerchant;
    EditText namamerchant;
    Button submit,addimage;
    TextView merchantloc, opentime, closetime;
    private final int DESTINATION_ID = 1;
    String latitude, longitude;
    byte[] imageByteArray;
    Bitmap decoded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstore);

        backbtn = findViewById(R.id.back_btn);
        bannermerchant = findViewById(R.id.bannermerchant);
        namamerchant = findViewById(R.id.namamerchant);
        merchantloc = findViewById(R.id.merchantloc);
        opentime = findViewById(R.id.opentime);
        closetime = findViewById(R.id.closetime);
        submit = findViewById(R.id.buttonupdatemerchant);
        addimage = findViewById(R.id.addimage);

        User user = BaseApp.getInstance(this).getLoginUser();
        namamerchant.setText(user.getNamamerchant());
        merchantloc.setText(user.getAlamat_merchant());
        opentime.setText(user.getJam_buka());
        closetime.setText(user.getJam_tutup());
        latitude = user.getLatitude_merchant();
        longitude = user.getLongitude_merchant();

        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESMERCHANT + user.getFoto_merchant())
                .placeholder(R.drawable.image_placeholder)
                .resize(250, 250)
                .into(bannermerchant);

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

        merchantloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditstoreActivity.this, PicklocationActivity.class);
                intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, DESTINATION_ID);
                startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namamerchant.getText().toString().isEmpty()) {
                    Toast.makeText(EditstoreActivity.this, "ornidsmerchant name cant be empty!", Toast.LENGTH_SHORT).show();
                } else if (merchantloc.getText().toString().isEmpty()) {
                    Toast.makeText(EditstoreActivity.this, "Location cant be empty!", Toast.LENGTH_SHORT).show();
                }  else  {
                    editprofile();
                }
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

                } catch (Exception ignored) {}
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

                } catch (Exception ignored) {}
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

    private void selectImage() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
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

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(EditstoreActivity.this).setLoginUser(user);
    }

    @SuppressLint("SetTextI18n")
    private void editprofile() {
        submit.setEnabled(false);
        submit.setText("Please Wait");
        submit.setBackground(getResources().getDrawable(R.drawable.button_round_3));
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        EditMerchantRequestJson request = new EditMerchantRequestJson();
        request.setNotelepon(loginUser.getNoTelepon());
        request.setAlamat(merchantloc.getText().toString());
        request.setLatitude_merchant(latitude);
        request.setLongitude_merchant(longitude);
        request.setJam_buka(opentime.getText().toString());
        request.setJam_tutup(closetime.getText().toString());
        request.setNamamerchant(namamerchant.getText().toString());
        if (imageByteArray != null) {
            request.setFoto_lama(loginUser.getFoto_merchant());
            request.setFoto_merchant(getStringImage(decoded));
        }


        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        service.editmerchant(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseJson> call,@NonNull Response<LoginResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        User user = response.body().getData().get(0);
                        saveUser(user);
                        finish();

                    } else {
                        submit.setEnabled(true);
                        submit.setText("Save");
                        submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                        Toast.makeText(EditstoreActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    submit.setEnabled(true);
                    submit.setText("Save");
                    submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                    Toast.makeText(EditstoreActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseJson> call, Throwable t) {
                t.printStackTrace();
                submit.setEnabled(true);
                submit.setText("Save");
                submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                Toast.makeText(EditstoreActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
