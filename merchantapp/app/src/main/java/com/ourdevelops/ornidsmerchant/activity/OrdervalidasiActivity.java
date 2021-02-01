package com.ourdevelops.ornidsmerchant.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.item.MenuItem;
import com.ourdevelops.ornidsmerchant.json.DetailRequestJson;
import com.ourdevelops.ornidsmerchant.json.DetailTransResponseJson;
import com.ourdevelops.ornidsmerchant.models.CustomerModel;
import com.ourdevelops.ornidsmerchant.models.DriverModel;
import com.ourdevelops.ornidsmerchant.models.TransModel;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.models.fcm.DriverResponse;
import com.ourdevelops.ornidsmerchant.utils.Utility;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class OrdervalidasiActivity extends AppCompatActivity {

    ImageView backbtn, telpdriver, chatdriver, telpbuyer, chatbuyer;
    LinearLayout dialogedit, lldriver, llchatdriver, llpelanggan, llchatpelanggan, llprice, llverify;
    TextView nomorinvoice, date, namadriver, iddriver, namabuyer, alamatbuyer, metodepembayaran, totalharga, kodevalidasi,status;
    ShimmerFrameLayout shimmeritem, shimmerdriver, shimmerpelanggan, shimmerprice;
    String invoice, transaction_id, idpelanggan, driver_id, time;
    RecyclerView itemmenu;
    MenuItem menuitem;
    private static final int REQUEST_PERMISSION_CALL = 992;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordervalidasi);
        backbtn = findViewById(R.id.back_btn);
        dialogedit = findViewById(R.id.detailorder);
        telpdriver = findViewById(R.id.telpdriver);
        chatdriver = findViewById(R.id.chatdriver);
        telpbuyer = findViewById(R.id.telpbuyer);
        chatbuyer = findViewById(R.id.chatbuyer);
        nomorinvoice = findViewById(R.id.nomorinvoice);
        date = findViewById(R.id.date);
        namadriver = findViewById(R.id.namadriver);
        iddriver = findViewById(R.id.iddriver);
        namabuyer = findViewById(R.id.namabuyer);
        alamatbuyer = findViewById(R.id.alamatbuyer);
        metodepembayaran = findViewById(R.id.metodepembayaran);
        totalharga = findViewById(R.id.totalharga);
        kodevalidasi = findViewById(R.id.kodevalidasi);
        shimmeritem = findViewById(R.id.shimmeritem);
        lldriver = findViewById(R.id.lldriver);
        llpelanggan = findViewById(R.id.llpelanggan);
        llchatdriver = findViewById(R.id.llchatdriver);
        llchatpelanggan = findViewById(R.id.llchatpelanggan);
        llprice = findViewById(R.id.llprice);
        llverify = findViewById(R.id.codeverify);
        shimmerdriver = findViewById(R.id.shimmerdriver);
        shimmerpelanggan = findViewById(R.id.shimmerpelanggan);
        shimmerprice = findViewById(R.id.shimmerprice);
        itemmenu = findViewById(R.id.itemmenu);
        status = findViewById(R.id.status);

        Intent intent = getIntent();
        invoice = intent.getStringExtra("invoice");
        time = intent.getStringExtra("ordertime");
        transaction_id = intent.getStringExtra("id");
        driver_id = intent.getStringExtra("iddriver");
        idpelanggan = intent.getStringExtra("idpelanggan");

        nomorinvoice.setText(invoice);
        date.setText(time);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        itemmenu.setHasFixedSize(true);
        itemmenu.setNestedScrollingEnabled(false);
        itemmenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        getdata();
    }

    private void shimmershow() {
        shimmeritem.startShimmerAnimation();
        shimmerdriver.startShimmerAnimation();
        shimmerpelanggan.startShimmerAnimation();
        shimmerprice.startShimmerAnimation();
        lldriver.setVisibility(View.GONE);
        llpelanggan.setVisibility(View.GONE);
        llprice.setVisibility(View.GONE);
        llverify.setVisibility(View.GONE);

    }

    private void shimmertutup() {
        lldriver.setVisibility(View.VISIBLE);
        llpelanggan.setVisibility(View.VISIBLE);
        llprice.setVisibility(View.VISIBLE);
        llverify.setVisibility(View.VISIBLE);

        shimmeritem.stopShimmerAnimation();
        shimmerdriver.stopShimmerAnimation();
        shimmerpelanggan.stopShimmerAnimation();
        shimmerprice.stopShimmerAnimation();

        itemmenu.setVisibility(View.VISIBLE);

        shimmeritem.setVisibility(View.GONE);
        shimmerdriver.setVisibility(View.GONE);
        shimmerpelanggan.setVisibility(View.GONE);
        shimmerprice.setVisibility(View.GONE);
    }

    private void getdata() {
        shimmershow();
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        DetailRequestJson request = new DetailRequestJson();
        request.setNotelepon(loginUser.getNoTelepon());
        request.setId(transaction_id);
        request.setIdDriver(driver_id);
        request.setIdpelanggan(idpelanggan);


        MerchantService service = ServiceGenerator.createService(MerchantService.class, loginUser.getEmail(), loginUser.getPassword());
        service.detailtrans(request).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DetailTransResponseJson> call, Response<DetailTransResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        shimmertutup();
                        final DriverModel driver = response.body().getDriver().get(0);
                        final CustomerModel pelanggan = response.body().getPelanggan().get(0);
                        TransModel transaksi = response.body().getData().get(0);

                        namadriver.setText(driver.getNamaDriver());
                        iddriver.setText(driver.getMerek()+" "+getString(R.string.text_with_bullet)+" "+driver.getNomor_kendaraan());
                        namabuyer.setText(pelanggan.getFullnama());
                        alamatbuyer.setText(transaksi.getAlamatTujuan());

                        if (transaksi.status == 4 || transaksi.status == 5) {
                            llchatdriver.setVisibility(View.GONE);
                            llchatpelanggan.setVisibility(View.GONE);
                        } else {
                            llchatdriver.setVisibility(View.VISIBLE);
                            llchatpelanggan.setVisibility(View.VISIBLE);
                        }

                        if (transaksi.isPakaiWallet()) {
                            metodepembayaran.setText("Total Price (Wallet)");
                        } else {
                            metodepembayaran.setText("Total Price (Cash)");
                        }

                        status.setVisibility(View.VISIBLE);

                        if (transaksi.status == 2) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.colorgradient), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.colorgradient));
                            status.setText("New Order");
                        } else if (transaksi.status ==3) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.yellow));
                            status.setText("Delivery");
                        } else if (transaksi.status ==4) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.green));
                            status.setText("Finish");
                        } else if (transaksi.status ==5) {
                            status.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                            status.setTextColor(getResources().getColor(R.color.red));
                            status.setText("Cancel");
                        }

                        chatdriver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkReadStoragepermission()) {
                                    Intent intent = new Intent(OrdervalidasiActivity.this, ChatActivity.class);
                                    intent.putExtra("senderid", loginUser.getId_merchant());
                                    intent.putExtra("receiverid", driver.getId());
                                    intent.putExtra("name", driver.getNamaDriver());
                                    intent.putExtra("tokendriver", loginUser.getToken_merchant());
                                    intent.putExtra("tokenku", driver.getRegId());
                                    intent.putExtra("pic", Constant.IMAGESDRIVER+driver.getFoto());
                                    startActivity(intent);
                                }

                            }
                        });

                        chatbuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkReadStoragepermission()) {
                                    Intent intent = new Intent(OrdervalidasiActivity.this, ChatActivity.class);
                                    intent.putExtra("senderid", loginUser.getId_merchant());
                                    intent.putExtra("receiverid", pelanggan.getId());
                                    intent.putExtra("name", pelanggan.getFullnama());
                                    intent.putExtra("tokendriver", loginUser.getToken_merchant());
                                    intent.putExtra("tokenku", pelanggan.getToken());
                                    intent.putExtra("pic", Constant.IMAGESPELANGGAN+pelanggan.getFoto());
                                    startActivity(intent);
                                }

                            }
                        });

                        telpdriver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrdervalidasiActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Driver");
                                alertDialogBuilder.setMessage("You want to call " + driver.getNamaDriver() + "(" + driver.getNoTelepon() + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(OrdervalidasiActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(OrdervalidasiActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + driver.getNoTelepon()));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        telpbuyer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrdervalidasiActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Customer");
                                alertDialogBuilder.setMessage("You want to call " + pelanggan.getFullnama() + "(" + pelanggan.getNoTelepon() + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(OrdervalidasiActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(OrdervalidasiActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + pelanggan.getNoTelepon()));
                                                startActivity(callIntent);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            }
                        });

                        Utility.currencyTXT(totalharga,transaksi.getTotal_biaya(),OrdervalidasiActivity.this);
                        kodevalidasi.setText(transaksi.getStruk());

                        menuitem = new MenuItem(OrdervalidasiActivity.this, response.body().getItem(), R.layout.item_menu);
                        itemmenu.setAdapter(menuitem);


                    }
                }
            }

            @Override
            public void onFailure(Call<DetailTransResponseJson> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(OrdervalidasiActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
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

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("IN PROGRESS", response.getResponse());
        if (response.getIdTransaksi().equals(transaction_id)) {
            switch (response.getResponse()) {
                case "2":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.colorgradient), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.colorgradient));
                    status.setText("New Order");
                    break;
                case "3":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.yellow));
                    status.setText("Delivery");
                    break;
                case "4":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.green));
                    status.setText("Finish");
                    break;
                case "5":
                    status.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    status.setTextColor(getResources().getColor(R.color.red));
                    status.setText("Cancel");
                    break;
            }
        }

    }
}
