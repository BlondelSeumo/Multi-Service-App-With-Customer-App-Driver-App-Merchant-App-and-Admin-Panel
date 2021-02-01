package com.ourdevelops.ornids.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.item.ItemItem;
import com.ourdevelops.ornids.json.CheckStatusTransRequest;
import com.ourdevelops.ornids.json.CheckStatusTransResponse;
import com.ourdevelops.ornids.json.GetNearRideCarRequestJson;
import com.ourdevelops.ornids.json.GetNearRideCarResponseJson;
import com.ourdevelops.ornids.json.ItemRequestJson;
import com.ourdevelops.ornids.json.PromoRequestJson;
import com.ourdevelops.ornids.json.PromoResponseJson;
import com.ourdevelops.ornids.json.RideCarResponseJson;
import com.ourdevelops.ornids.json.fcm.DriverRequest;
import com.ourdevelops.ornids.json.fcm.DriverResponse;
import com.ourdevelops.ornids.json.fcm.FCMMessage;
import com.ourdevelops.ornids.models.DriverModel;
import com.ourdevelops.ornids.models.ServiceModel;
import com.ourdevelops.ornids.models.ItemModel;
import com.ourdevelops.ornids.models.PesananMerchant;
import com.ourdevelops.ornids.models.TransModel;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.SettingPreference;
import com.ourdevelops.ornids.utils.Utility;
import com.ourdevelops.ornids.utils.api.FCMHelper;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.BookService;
import com.ourdevelops.ornids.utils.api.service.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.ourdevelops.ornids.json.fcm.FCMType.ORDER;

public class DetailOrderActivity extends AppCompatActivity implements ItemItem.OnCalculatePrice {

    TextView location, orderprice, deliveryfee, diskon, total, diskontext, topuptext, textnotif, saldotext;
    Button order;
    RecyclerView rvmerchantnear;
    LinearLayout llcheckedcash, llcheckedwallet, llbtn;
    ImageButton checkedcash, checkedwallet;
    RelativeLayout rlprogress;
    Thread thread;
    boolean threadRun = true;
    TransModel transaksi;
    private DriverRequest request;
    TextView cashpayment, walletpayment;
    private double jarak;
    private long price, promocode;
    public static final String FITUR_KEY = "FiturKey";
    String alamat, biayaminimum, getbiaya, biayaakhir, biayadistance;
    double lat, lon, merlat, merlon, distance;
    private ServiceModel designedFitur;
    private final int DESTINATION_ID = 1;

    private FastItemAdapter<ItemItem> itemAdapter;
    private Realm realm;
    ImageView backbtn;
    private List<DriverModel> driverAvailable;
    private long foodCostLong = 0, maksimum;
    private long deliveryCostLong = 0;
    private String saldoWallet;
    private String checkedpaycash;
    private String idresto;
    private String alamatresto;
    private String namamerchant;
    private String back;
    int service;
    SettingPreference sp;
    RelativeLayout rlnotif;
    String time;

    EditText promokode;
    String home, layanan, description, icon;
    LatLng pickUpLatLang, destinationLatLang;
    Point pickup, destination;

    Button btnpromo;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        promocode = 0;
        time = "0 mins";
        realm = Realm.getDefaultInstance();
        rvmerchantnear = findViewById(R.id.merchantnear);
        location = findViewById(R.id.pickUpText);
        orderprice = findViewById(R.id.orderprice);
        llcheckedcash = findViewById(R.id.llcheckedcash);
        llcheckedwallet = findViewById(R.id.llcheckedwallet);
        cashpayment = findViewById(R.id.cashPayment);
        walletpayment = findViewById(R.id.walletpayment);
        deliveryfee = findViewById(R.id.cost);
        checkedcash = findViewById(R.id.checkedcash);
        checkedwallet = findViewById(R.id.checkedwallet);
        total = findViewById(R.id.price);
        diskon = findViewById(R.id.diskon);
        backbtn = findViewById(R.id.back_btn);
        diskontext = findViewById(R.id.ketsaldo);
        topuptext = findViewById(R.id.topUp);
        order = findViewById(R.id.order);
        rlprogress = findViewById(R.id.rlprogress);
        textnotif = findViewById(R.id.textnotif);
        rlnotif = findViewById(R.id.rlnotif);
        saldotext = findViewById(R.id.balance);
        promokode = findViewById(R.id.promocode);
        btnpromo = findViewById(R.id.btnpromo);
        back = "0";
        llbtn = findViewById(R.id.llbtn);

        driverAvailable = new ArrayList<>();
        service = 0;
        topuptext.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TopupSaldoActivity.class)));

        location.setOnClickListener(v -> {
            Intent intent = new Intent(DetailOrderActivity.this, PicklocationActivity.class);
            intent.putExtra(PicklocationActivity.FORM_VIEW_INDICATOR, DESTINATION_ID);
            startActivityForResult(intent, PicklocationActivity.LOCATION_PICKER_ID);
        });

        backbtn.setOnClickListener(view -> finish());

        sp = new SettingPreference(this);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0);
        lon = intent.getDoubleExtra("lon", 0);
        merlat = intent.getDoubleExtra("merlat", 0);
        merlon = intent.getDoubleExtra("merlon", 0);
        distance = intent.getDoubleExtra("distance", 0);
        alamatresto = intent.getStringExtra("alamatresto");
        idresto = intent.getStringExtra("idresto");
        alamat = intent.getStringExtra("alamat");
        namamerchant = intent.getStringExtra("namamerchant");
        service = intent.getIntExtra(FITUR_KEY, -1);
        if (service != -1)
            designedFitur = realm.where(ServiceModel.class).equalTo("idFitur", service).findFirst();
        home = Objects.requireNonNull(designedFitur).getHome();
        layanan = designedFitur.getFitur();
        description = designedFitur.getKeterangan();
        icon = designedFitur.getIcon();


        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());

        diskontext.setText("Discount " + designedFitur.getDiskon() + " with Wallet");
        total.setText("wait");
        deliveryfee.setText("wait");
        Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        pickUpLatLang = new LatLng(merlat, merlon);
        destinationLatLang = new LatLng(lat, lon);
        pickup = Point.fromLngLat(merlon, merlat);
        destination = Point.fromLngLat(lon, lat);
        location.setText(alamat);

        btnpromo.setOnClickListener(v -> {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            } catch (Exception ignored) {

            }
            if (promokode.getText().toString().isEmpty()) {
                notif("Promo code cant be empty!");
            } else {
                promokodedata();
            }
        });

        itemAdapter = new FastItemAdapter<>();
        itemAdapter.notifyDataSetChanged();
        itemAdapter.withSelectable(true);
        itemAdapter.withItemEvent(new ClickEventHook<ItemItem>() {
            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof ItemItem.ViewHolder) {
                    return ((ItemItem.ViewHolder) viewHolder).itemView;
                }
                return null;
            }

            @Override
            public void onClick(View v, int position, FastAdapter<ItemItem> fastAdapter, ItemItem item) {

            }
        });
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this));
        rvmerchantnear.setAdapter(itemAdapter);
        getRoute();
        updateEstimatedItemCost();
        loadItem();
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    private void getRoute() {
        if (pickup != null && destination != null) {
            rlprogress.setVisibility(View.VISIBLE);
            MapboxDirections client = MapboxDirections.builder()
                    .origin(pickup)
                    .destination(destination)
                    .overview(DirectionsCriteria.OVERVIEW_FULL)
                    .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
                    .accessToken(getString(R.string.mapbox_access_token))
                    .build();
            client.enqueueCall(new Callback<DirectionsResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                    if (response.body() == null) {
                        Timber.d("No routes found, make sure you set the right user and access token.");
                        return;
                    } else if (response.body().routes().size() < 1) {
                        Timber.d("No routes found");
                        return;
                    }
                    rlprogress.setVisibility(View.GONE);
                    DirectionsRoute currentroute = response.body().routes().get(0);
                    String format = String.format(Locale.US, "%.0f", currentroute.distance() / 1000f);
                    long minutes = (long) ((currentroute.duration() / 60));
                    time = minutes + " mins";
                    distance = (currentroute.distance()) / 1000f;
                    updateDistance();
                    long dist = Long.parseLong(format);
                    if (dist < maksimum) {
                        order.setOnClickListener(v -> {
                            if (readyToOrder()) {
                                sendOrder();
                                back = "1";
                            }
                        });
                    } else {
                        notif("destination too far away!");
                        order.setOnClickListener(v -> {
                            if (readyToOrder()) {
                                notif("destination too far away!");
                                back = "0";
                            }
                        });

                    }

                }

                @Override
                public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable throwable) {
                    Timber.d("Error: %s", throwable.getMessage());

                }
            });
        }
    }

    private boolean readyToOrder() {
        if (destinationLatLang == null) {
            Toast.makeText(this, "Please select your location.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (total.getText().toString().isEmpty() || total.getText().toString().equals("wait")) {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            return false;
        }

        List<PesananMerchant> existingFood = realm.copyFromRealm(realm.where(PesananMerchant.class).findAll());

        int quantity = 0;
        for (int p = 0; p < existingFood.size(); p++) {
            quantity += existingFood.get(p).getQty();
        }

        if (quantity == 0) {
            Toast.makeText(this, "Please order at least 1 item.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (jarak == -99.0) {
            Toast.makeText(this, "Please wait a moment...", Toast.LENGTH_SHORT).show();
        }

        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PicklocationActivity.LOCATION_PICKER_ID) {
            if (resultCode == RESULT_OK) {
                String addressset = data.getStringExtra(PicklocationActivity.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(PicklocationActivity.LOCATION_LATLNG);
                location.setText(addressset);
                destinationLatLang = new LatLng(Objects.requireNonNull(latLng).getLatitude(), latLng.getLongitude());
                destination = Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude());
                if (pickUpLatLang != null) {
                    getRoute();
                }

            }
        }

    }

    private void loadItem() {
        List<ItemModel> makananList = realm.copyFromRealm(realm.where(ItemModel.class).findAll());
        List<PesananMerchant> pesananFoods = realm.copyFromRealm(realm.where(PesananMerchant.class).findAll());
        itemAdapter.clear();
        for (PesananMerchant pesanan : pesananFoods) {
            ItemItem makananItem = new ItemItem(this, this);
            for (ItemModel makanan : makananList) {
                if (makanan.getId_item() == pesanan.getIdItem()) {
                    makananItem.quantity = pesanan.getQty();
                    makananItem.id = makanan.getId_item();
                    makananItem.namaMenu = makanan.getNama_item();
                    makananItem.deskripsiMenu = makanan.getDeskripsi_item();
                    makananItem.photo = makanan.getFoto_item();
                    makananItem.price = Long.parseLong(makanan.getHarga_item());
                    makananItem.promo = makanan.getStatus_promo();
                    if (makanan.getHarga_promo().isEmpty()) {
                        makananItem.hargapromo = 0;
                    } else {
                        makananItem.hargapromo = Long.parseLong(makanan.getHarga_promo());
                    }
                    makananItem.note = pesanan.getCatatan();

                    break;
                }
            }

            itemAdapter.add(makananItem);
        }

        itemAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        btnpromo.setEnabled(false);
        btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(String.valueOf(service));
        request.setCode(promokode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        final long finalBiayaTotalpay = foodCostLong + price;
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        if (response.body().getType().equals("persen")) {
                            promocode = (Long.parseLong(response.body().getNominal()) * finalBiayaTotalpay) / 100;
                        } else {
                            promocode = Long.parseLong(response.body().getNominal());
                        }
                        updateDistance();
                    } else {
                        notif("promo code not available!");
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        promocode = 0;
                        updateDistance();
                    }
                } else {
                    notif("error!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("error");
            }
        });
    }

    @Override
    public void calculatePrice() {
        updateEstimatedItemCost();
    }

    private void updateEstimatedItemCost() {
        List<PesananMerchant> existingFood = realm.copyFromRealm(realm.where(PesananMerchant.class).findAll());
        long cost = 0;
        for (int p = 0; p < existingFood.size(); p++) {
            cost += existingFood.get(p).getTotalHarga();
        }
        foodCostLong = cost;
        Utility.currencyTXT(orderprice, String.valueOf(foodCostLong), this);
        updateDistance();
    }


    double km;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (back.equals("1")) {
            Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    private void updateDistance() {

        checkedpaycash = "1";
        checkedcash.setSelected(true);
        checkedwallet.setSelected(false);
        cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
        walletpayment.setTextColor(getResources().getColor(R.color.gray));
        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        km = distance;


        this.jarak = km;

        String cost = String.valueOf(biayaminimum);
        long biayaTotal = (long) (Double.parseDouble(getbiaya) * km);
        if (biayaTotal < Double.parseDouble(biayaminimum)) {
            this.price = Long.parseLong(biayaminimum);
            biayaTotal = Long.parseLong(biayaminimum);
            biayadistance = cost;
        } else {
            biayadistance = getbiaya;
        }
        this.price = biayaTotal;

        deliveryCostLong = biayaTotal;

        Utility.currencyTXT(deliveryfee, String.valueOf(deliveryCostLong), this);
        final long finalBiayaTotalpay = foodCostLong + price;

        Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - promocode), DetailOrderActivity.this);
        Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
        long saldokini = Long.parseLong(saldoWallet);
        if (saldokini < ((foodCostLong + price) - (finalBiayaTotalpay * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(view -> {
                Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - promocode), DetailOrderActivity.this);
                Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
                checkedcash.setSelected(true);
                checkedwallet.setSelected(false);
                checkedpaycash = "1";
                cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                walletpayment.setTextColor(getResources().getColor(R.color.gray));
                checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            });
            llcheckedwallet.setEnabled(false);
        } else {
            llcheckedwallet.setEnabled(true);
            llcheckedcash.setOnClickListener(view -> {
                Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - promocode), DetailOrderActivity.this);
                Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
                checkedcash.setSelected(true);
                checkedwallet.setSelected(false);
                checkedpaycash = "1";
                cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                walletpayment.setTextColor(getResources().getColor(R.color.gray));
                checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            });

            llcheckedwallet.setOnClickListener(view -> {
                long diskonwallet = (long) ((finalBiayaTotalpay * Double.parseDouble(biayaakhir)) + promocode);
                String totalwallet = String.valueOf(diskonwallet);
                Utility.currencyTXT(diskon, totalwallet, DetailOrderActivity.this);
                Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - diskonwallet), DetailOrderActivity.this);
                checkedcash.setSelected(false);
                checkedwallet.setSelected(true);
                checkedpaycash = "0";
                walletpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                cashpayment.setTextColor(getResources().getColor(R.color.gray));
                checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            });
        }
    }

    private void sendOrder() {
        List<PesananMerchant> existingItem = realm.copyFromRealm(realm.where(PesananMerchant.class).findAll());

        for (PesananMerchant pesanan : existingItem) {
            if (pesanan.getCatatan() == null || pesanan.getCatatan().trim().equals(""))
                pesanan.setCatatan("");
        }

        ItemRequestJson param = new ItemRequestJson();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        param.setIdPelanggan(userLogin.getId());
        param.setOrderFitur(String.valueOf(service));
        param.setStartLatitude(merlat);
        param.setStartLongitude(merlon);
        param.setEndLatitude(destinationLatLang.getLatitude());
        param.setEndLongitude(destinationLatLang.getLongitude());
        param.setAlamatTujuan(location.getText().toString());
        param.setAlamatAsal(alamatresto);
        param.setJarak(jarak);
        param.setEstimasi(time);
        param.setHarga(deliveryCostLong);
        if (checkedpaycash.equals("1")) {
            param.setPakaiWallet(0);
            param.setKreditpromo(String.valueOf(promocode));
        } else {
            param.setPakaiWallet(1);
            param.setKreditpromo(diskon.getText().toString().replace(".", "").replace(sp.getSetting()[0], ""));
        }
        param.setIdResto(idresto);
        param.setTotalBiayaBelanja(foodCostLong);
        param.setCatatan("");
        param.setPesanan(existingItem);


        fetchNearDriver(param);
    }

    private void fetchNearDriver(final ItemRequestJson paramdata) {
        rlprogress.setVisibility(View.VISIBLE);
        if (destinationLatLang != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();

            BookService services = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(merlat);
            param.setLongitude(merlon);
            param.setFitur(String.valueOf(service));

            services.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                    if (response.isSuccessful()) {
                        driverAvailable = Objects.requireNonNull(response.body()).getData();
                        if (driverAvailable.isEmpty()) {
                            finish();
                            Toast.makeText(DetailOrderActivity.this, "no driver arround you!", Toast.LENGTH_SHORT).show();
                        } else {
                            sendRequestTransaksi(paramdata, driverAvailable);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

                }
            });

        }
    }

    private void buildDriverRequest(RideCarResponseJson response) {
        transaksi = response.getData().get(0);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (request == null) {
            request = new DriverRequest();
            request.setIdTransaksi(transaksi.getId());
            request.setIdPelanggan(transaksi.getIdPelanggan());
            request.setRegIdPelanggan(loginUser.getToken());
            request.setOrderFitur(home);
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setJarak(transaksi.getJarak());
            request.setHarga(transaksi.getHarga() + foodCostLong);
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(namamerchant);
            request.setLayanan(layanan);
            request.setLayanandesc(description);
            request.setIcon(icon);
            request.setBiaya(String.valueOf(foodCostLong));
            request.setTokenmerchant(transaksi.getToken_merchant());
            request.setIdtransmerchant(transaksi.getIdtransmerchant());
            request.setDistance(String.valueOf(deliveryCostLong));


            String namaLengkap = String.format("%s", loginUser.getFullnama());
            request.setNamaPelanggan(namaLengkap);
            request.setTelepon(loginUser.getNoTelepon());
            request.setType(ORDER);
        }
    }

    private void sendRequestTransaksi(ItemRequestJson param, final List<DriverModel> driverList) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksiMerchant(param).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    thread = new Thread(() -> {
                        for (int i = 0; i < driverList.size(); i++) {
                            fcmBroadcast(i, driverList);
                        }

                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (threadRun) {
                            CheckStatusTransRequest param1 = new CheckStatusTransRequest();
                            param1.setIdTransaksi(transaksi.getId());
                            service.checkStatusTransaksi(param1).enqueue(new Callback<CheckStatusTransResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<CheckStatusTransResponse> call1, @NonNull Response<CheckStatusTransResponse> response1) {
                                    if (response1.isSuccessful()) {
                                        CheckStatusTransResponse checkStatus = response1.body();
                                        if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                            notif("Driver not found!");
                                            runOnUiThread(() -> notif("Driver not found!"));

                                            new Handler().postDelayed(DetailOrderActivity.this::finish, 3000);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<CheckStatusTransResponse> call1, @NonNull Throwable t) {
                                    notif("Driver not found!");
                                    runOnUiThread(() -> notif("Driver not found!"));

                                    new Handler().postDelayed(DetailOrderActivity.this::finish, 3000);

                                }
                            });
                        }

                    });
                    thread.start();


                }
            }

            @Override
            public void onFailure(@NonNull Call<RideCarResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("Your account has a problem, please contact customer service!");
                new Handler().postDelayed(() -> finish(), 3000);
            }
        });
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList) {
        DriverModel driverToSend = driverList.get(index);
        request.setTime_accept(new Date().getTime() + "");
        final FCMMessage message = new FCMMessage();
        message.setTo(driverToSend.getRegId());
        message.setData(request);


        FCMHelper.sendMessage(Constant.FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {

            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        if (response.getResponse().equalsIgnoreCase(DriverResponse.ACCEPT) || response.getResponse().equals("3") || response.getResponse().equals("4")) {
            runOnUiThread(() -> {
                threadRun = false;
                for (DriverModel cDriver : driverAvailable) {
                    if (cDriver.getId().equals(response.getId())) {
                        Intent intent = new Intent(DetailOrderActivity.this, ProgressActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("driver_id", cDriver.getId());
                        intent.putExtra("transaction_id", request.getIdTransaksi());
                        intent.putExtra("response", "2");
                        intent.putExtra("complete", "1");
                        startActivity(intent);
                        DriverResponse response1 = new DriverResponse();
                        response1.setId("");
                        response1.setIdTransaksi("");
                        response1.setResponse("");
                        EventBus.getDefault().postSticky(response1);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        Utility.currencyTXT(saldotext, saldoWallet, this);
    }


}
