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
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.json.AddEditItemRequestJson;
import com.ourdevelops.ornidsmerchant.json.ResponseJson;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.Log;
import com.ourdevelops.ornidsmerchant.utils.SettingPreference;
import com.ourdevelops.ornidsmerchant.utils.Utility;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;
import com.ourdevelops.ornidsmerchant.utils.PicassoTrustAll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditmenuActivity extends AppCompatActivity {

    ImageView backbtn, menuimage;
    EditText namamenu, descmenu, hargamenu, hargapromo;
    SwitchCompat activepromo;
    Button submit, addimage;
    String idkategori, nama, deskripsi, price, hargapromosi, actived, fotolama;
    int id;
    byte[] imageByteArray;
    Bitmap decoded;
    SettingPreference sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmenu);

        backbtn = findViewById(R.id.back_btn);
        menuimage = findViewById(R.id.menuimage);
        namamenu = findViewById(R.id.namamenu);
        descmenu = findViewById(R.id.descmenu);
        hargamenu = findViewById(R.id.hargamenu);
        hargapromo = findViewById(R.id.hargapromo);
        activepromo = findViewById(R.id.activepromo);
        submit = findViewById(R.id.buttonsavemenu);
        addimage = findViewById(R.id.addimage);

        sp = new SettingPreference(this);

        Intent intent = getIntent();
        idkategori = intent.getStringExtra("idkategori");
        nama = intent.getStringExtra("nama");
        deskripsi = intent.getStringExtra("deskripsi");
        price = intent.getStringExtra("price");
        hargapromosi = intent.getStringExtra("hargapromo");
        actived = intent.getStringExtra("active");
        fotolama = intent.getStringExtra("fotolama");
        id = intent.getIntExtra("id",0);

        Log.e("katid",idkategori);

        namamenu.setText(nama);
        descmenu.setText(deskripsi);
        Utility.currencyTXT(hargamenu, price, this);


        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESITEM + fotolama)
                .resize(250, 250)
                .placeholder(R.drawable.image_placeholder)
                .into(menuimage);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hargamenu.addTextChangedListener(Utility.currencyTW(hargamenu, this));


        if (actived.equals("1")) {
            activepromo.setChecked(true);
            hargapromo.setEnabled(true);
            Utility.currencyTXT(hargapromo, hargapromosi, this);
            hargapromo.addTextChangedListener(Utility.currencyTW(hargapromo, this));
        } else {
            activepromo.setChecked(false);
            hargapromo.setEnabled(false);
        }
        activepromo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hargapromo.setEnabled(true);
                    hargapromo.addTextChangedListener(Utility.currencyTW(hargapromo, EditmenuActivity.this));
                    actived = "1";
                } else {
                    actived = "0";
                    hargapromo.setText("");
                    hargapromo.setEnabled(false);
                }
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namamenu.getText().toString().isEmpty()) {
                    Toast.makeText(EditmenuActivity.this, "Menu name cant be empty!", Toast.LENGTH_SHORT).show();
                } else if (descmenu.getText().toString().isEmpty()) {
                    Toast.makeText(EditmenuActivity.this, "Description cant be empty", Toast.LENGTH_SHORT).show();
                } else if (hargamenu.getText().toString().isEmpty()) {
                    Toast.makeText(EditmenuActivity.this, "Price cant be empty!", Toast.LENGTH_SHORT).show();
                } else if (activepromo.isChecked() && hargapromo.getText().toString().isEmpty()) {
                    Toast.makeText(EditmenuActivity.this, "Promo price cant be empty!", Toast.LENGTH_SHORT).show();
                } else if (activepromo.isChecked() && Long.parseLong(hargamenu.getText().toString().replace(".", "").replace(sp.getSetting()[0], "")) < Long.parseLong(hargapromo.getText().toString().replace(".", "").replace(sp.getSetting()[0], ""))) {
                    Toast.makeText(EditmenuActivity.this, "promo price cannot be higher than the base price!", Toast.LENGTH_SHORT).show();
                } else {
                    addmenu();
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

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

                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                menuimage.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private void addmenu() {
        submit.setEnabled(false);
        submit.setText("Please Wait");
        submit.setBackground(getResources().getDrawable(R.drawable.button_round_3));
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        AddEditItemRequestJson request = new AddEditItemRequestJson();
        request.setNotelepon(loginUser.getNoTelepon());
        request.setId(String.valueOf(id));
        request.setNama(namamenu.getText().toString());
        request.setHarga(hargamenu.getText().toString().replace(".", "").replace(sp.getSetting()[0], ""));
        request.setHargapromo(hargapromo.getText().toString().replace(".", "").replace(sp.getSetting()[0], ""));
        request.setKategori(idkategori);
        request.setDeskripsi(descmenu.getText().toString());
        request.setStatus(actived);
        if (imageByteArray != null) {
            request.setFoto(getStringImage(decoded));
            request.setFotolama(fotolama);
        }


        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        service.edititem(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        finish();

                    } else {
                        submit.setEnabled(true);
                        submit.setText("Save");
                        submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                        Toast.makeText(EditmenuActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    submit.setEnabled(true);
                    submit.setText("Save");
                    submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                    Toast.makeText(EditmenuActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                t.printStackTrace();
                submit.setEnabled(true);
                submit.setText("Save");
                submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                Toast.makeText(EditmenuActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
