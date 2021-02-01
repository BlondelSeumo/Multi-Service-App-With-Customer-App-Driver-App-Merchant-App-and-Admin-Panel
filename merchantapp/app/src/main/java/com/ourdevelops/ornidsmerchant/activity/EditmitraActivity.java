package com.ourdevelops.ornidsmerchant.activity;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.json.EditProfileRequestJson;
import com.ourdevelops.ornidsmerchant.json.LoginResponseJson;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.util.Objects;

public class EditmitraActivity extends AppCompatActivity {

    ImageView backbtn;
    EditText phonenumber, namamitra, email, address;
    TextView countrycode;
    String country_iso_code = "en";
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmitra);

        backbtn = findViewById(R.id.back_btn);
        countrycode = findViewById(R.id.countrycode);
        phonenumber = findViewById(R.id.phonenumber);
        namamitra = findViewById(R.id.namamitra);
        email = findViewById(R.id.email);
        address =findViewById(R.id.address);
        submit = findViewById(R.id.submit);

        User user = BaseApp.getInstance(this).getLoginUser();
        countrycode.setText(user.getCountrycode());
        phonenumber.setText(user.getPhone());
        namamitra.setText(user.getNamamitra());
        email.setText(user.getEmail());
        address.setText(user.getAlamat_mitra());

        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                editprofile();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        countrycode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        countrycode.setText(dialCode);
                        picker.dismiss();
                        country_iso_code = code;
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void editprofile() {
        submit.setText("Please Wait...");
        submit.setBackground(getResources().getDrawable(R.drawable.button_round_3));

        User loginUser = BaseApp.getInstance(this).getLoginUser();
        EditProfileRequestJson request = new EditProfileRequestJson();
        request.setIdmitra(loginUser.getId());
        request.setNoteleponlama(loginUser.getNoTelepon());
        request.setNotelepon(countrycode.getText().toString().replace("+", "") + phonenumber.getText().toString());
        request.setNama(namamitra.getText().toString());
        request.setPhone(phonenumber.getText().toString());
        request.setEmail(email.getText().toString());
        request.setCountrycode(countrycode.getText().toString());

        request.setAlamat(address.getText().toString());


        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        service.editprofile(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(Call<LoginResponseJson> call, Response<LoginResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        User user = response.body().getData().get(0);
                        saveUser(user);
                        finish();

                    } else {
                        submit.setText("Submit");
                        submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                        Toast.makeText(EditmitraActivity.this, "response.body().getMessage()", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    submit.setText("Submit");
                    submit.setBackground(getResources().getDrawable(R.drawable.button_round_1));
                    Toast.makeText(EditmitraActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditmitraActivity.this, "Error Connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(EditmitraActivity.this).setLoginUser(user);
    }
}
