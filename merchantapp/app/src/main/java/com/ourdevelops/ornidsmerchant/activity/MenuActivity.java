package com.ourdevelops.ornidsmerchant.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.item.MenusItem;
import com.ourdevelops.ornidsmerchant.json.AddEditItemRequestJson;
import com.ourdevelops.ornidsmerchant.json.AddEditKategoriRequestJson;
import com.ourdevelops.ornidsmerchant.json.ItemRequestJson;
import com.ourdevelops.ornidsmerchant.json.ItemResponseJson;
import com.ourdevelops.ornidsmerchant.json.ResponseJson;
import com.ourdevelops.ornidsmerchant.models.ItemModel;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;

import java.util.List;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    ImageView editcategory, backbtn, hapuscategory;
    Button addmenu;
    RecyclerView menu;
    ShimmerFrameLayout shimmermenu;
    String idkategori,active,namakat;
    TextView namakategori;

    MenusItem menuItem;
    List<ItemModel> itemmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        editcategory = findViewById(R.id.editcategory);
        addmenu = findViewById(R.id.buttonaddmenu);
        backbtn = findViewById(R.id.back_btn);
        hapuscategory = findViewById(R.id.hapuscategory);
        menu = findViewById(R.id.menu);
        shimmermenu = findViewById(R.id.shimmermenu);
        namakategori = findViewById(R.id.namacategory);

        Intent intent = getIntent();
        idkategori = intent.getStringExtra("idkategori");
        active = intent.getStringExtra("active");
        namakat = intent.getStringExtra("nama");

        namakategori.setText(namakat);
        editcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editcategory();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, AddmenuActivity.class);
                i.putExtra("idkategori",idkategori);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        hapuscategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDonekat();
            }
        });

        menu.setHasFixedSize(true);
        menu.setNestedScrollingEnabled(false);
        menu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata();
    }

    private void shimmershow() {
        menu.setVisibility(View.GONE);
        shimmermenu.startShimmerAnimation();
        shimmermenu.setVisibility(View.VISIBLE);

    }

    private void shimmertutup() {
        shimmermenu.stopShimmerAnimation();
        shimmermenu.setVisibility(View.GONE);
        menu.setVisibility(View.VISIBLE);
    }

    private void getdata() {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        ItemRequestJson parameter = new ItemRequestJson();
        parameter.setNotelepon(loginUser.getNoTelepon());
        parameter.setIdmerchant(loginUser.getId_merchant());
        parameter.setIdkategori(idkategori);

        merchantService.itemlist(parameter).enqueue(new Callback<ItemResponseJson>() {
            @Override
            public void onResponse(Call<ItemResponseJson> call, Response<ItemResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        shimmertutup();
                        itemmenu = response.body().getData();
                        menuItem = new MenusItem(MenuActivity.this, itemmenu, R.layout.item_menus, new MenusItem.OnItemClickListener() {
                            @Override
                            public void onItemClick(ItemModel item, View v) {
                                clickDone(String.valueOf(item.getId_item()), item.getFoto_item());
                            }
                        });
                        menu.setAdapter(menuItem);
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemResponseJson> call, Throwable t) {

            }
        });
    }

    public void clickDone(final String iditem, final String fotolama) {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete?")
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteitem(iditem,fotolama);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void clickDonekat() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete?")
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletekat();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteitem(String iditem,String fotolama) {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AddEditItemRequestJson param = new AddEditItemRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setId(iditem);
        param.setFotolama(fotolama);
        merchantService.deleteitem(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        getdata();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {

            }
        });
    }

    private void deletekat() {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setId(idkategori);
        merchantService.deletekategori(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void editcategory() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_addcategory);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView close = dialog.findViewById(R.id.close);
        final EditText text = dialog.findViewById(R.id.textadd);
        SwitchCompat switchadd = dialog.findViewById(R.id.switchactive);
        final Button submit = dialog.findViewById(R.id.submit);

        text.setText(namakat);
        submit.setText("Edit Category");
        if (active.equals("1")) {
            switchadd.setChecked(true);
        } else {
            switchadd.setChecked(false);
        }

        switchadd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active = "1";
                } else {
                    active = "0";
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty()) {
                    Toast.makeText(MenuActivity.this, "Please input Category Name!", Toast.LENGTH_SHORT).show();
                } else {
                    submit.setEnabled(false);
                    submit.setText("Please Wait...");
                    submit.setBackground(getResources().getDrawable(R.drawable.button_round_3));
                    User loginUser = BaseApp.getInstance(MenuActivity.this).getLoginUser();
                    MerchantService merchantService = ServiceGenerator.createService(
                            MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
                    AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
                    param.setNotelepon(loginUser.getNoTelepon());
                    param.setId(idkategori);
                    param.setIdmerchant(loginUser.getId_merchant());
                    param.setNamakategori(text.getText().toString());
                    param.setStatus(active);
                    merchantService.editkategori(param).enqueue(new Callback<ResponseJson>() {
                        @Override
                        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                            if (response.isSuccessful()) {
                                if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                                    getdata();
                                    namakat = text.getText().toString();
                                    namakategori.setText(text.getText().toString());
                                    Toast.makeText(MenuActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                } else {
                                    submit.setEnabled(true);
                                    Toast.makeText(MenuActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    submit.setText("Edit Category");
                                    submit.setBackground(MenuActivity.this.getResources().getDrawable(R.drawable.button_round_1));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseJson> call, Throwable t) {
                            submit.setEnabled(true);
                            Toast.makeText(MenuActivity.this, "Error Connection!", Toast.LENGTH_SHORT).show();
                            submit.setText("Edit Category");
                            submit.setBackground(MenuActivity.this.getResources().getDrawable(R.drawable.button_round_1));

                        }
                    });

                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}