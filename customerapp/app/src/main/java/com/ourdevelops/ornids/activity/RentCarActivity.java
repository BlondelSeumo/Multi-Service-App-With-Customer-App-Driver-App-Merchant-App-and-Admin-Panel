package com.ourdevelops.ornids.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.json.CheckStatusTransRequest;
import com.ourdevelops.ornids.json.CheckStatusTransResponse;
import com.ourdevelops.ornids.json.GetNearRideCarRequestJson;
import com.ourdevelops.ornids.json.GetNearRideCarResponseJson;
import com.ourdevelops.ornids.json.PromoRequestJson;
import com.ourdevelops.ornids.json.PromoResponseJson;
import com.ourdevelops.ornids.json.RideCarRequestJson;
import com.ourdevelops.ornids.json.RideCarResponseJson;
import com.ourdevelops.ornids.json.fcm.DriverRequest;
import com.ourdevelops.ornids.json.fcm.DriverResponse;
import com.ourdevelops.ornids.json.fcm.FCMMessage;
import com.ourdevelops.ornids.models.DriverModel;
import com.ourdevelops.ornids.models.ServiceModel;
import com.ourdevelops.ornids.models.TransModel;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.PicassoTrustAll;
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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconRotate;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;
import static com.ourdevelops.ornids.json.fcm.FCMType.ORDER;

/**
 * Created by Ourdevelops Team on 10/26/2019.
 */

public class RentCarActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String FITUR_KEY = "FiturKey";
    String ICONFITUR;
    TransModel transaksi;
    Thread thread;
    boolean threadRun = true;
    private DriverRequest request;
    Context context = RentCarActivity.this;

    @BindView(R.id.pickUpContainer)
    LinearLayout setPickUpContainer;
    @BindView(R.id.pickUpButton)
    Button setPickUpButton;
    @BindView(R.id.pickUpText)
    TextView pickUpText;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;
    @BindView(R.id.detail)
    LinearLayout detail;
    @BindView(R.id.price)
    TextView priceText;
    @BindView(R.id.topUp)
    TextView topUp;
    @BindView(R.id.order)
    Button orderButton;
    @BindView(R.id.image)
    ImageView icon;
    @BindView(R.id.layanan)
    TextView layanan;
    @BindView(R.id.layanandes)
    TextView layanandesk;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.ketsaldo)
    TextView diskontext;
    @BindView(R.id.diskon)
    TextView diskon;
    @BindView(R.id.balance)
    TextView saldotext;
    @BindView(R.id.checkedcash)
    ImageButton checkedcash;
    @BindView(R.id.checkedwallet)
    ImageButton checkedwallet;
    @BindView(R.id.cashPayment)
    TextView cashpayment;
    @BindView(R.id.walletpayment)
    TextView walletpayment;
    @BindView(R.id.llcheckedwallet)
    LinearLayout llcheckedwallet;
    @BindView(R.id.llcheckedcash)
    LinearLayout llcheckedcash;
    @BindView(R.id.back_btn)
    FloatingActionButton backbtn;
    @BindView(R.id.currentlocation)
    FloatingActionButton currentLocation;
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @BindView(R.id.rlnotif)
    RelativeLayout rlnotif;
    @BindView(R.id.textnotif)
    TextView textnotif;
    @BindView(R.id.textprogress)
    TextView textprogress;
    @BindView(R.id.service)
    TextView fiturtext;
    @BindView(R.id.enamjam)
    Button enamjam;
    @BindView(R.id.duabelasjam)
    Button duabelasjam;
    @BindView(R.id.satuhari)
    Button satuhari;

    @BindView(R.id.promocode)
    EditText promokode;

    @BindView(R.id.btnpromo)
    Button btnpromo;

    @BindView(R.id.promonotif)
    TextView promonotif;

    private List<DriverModel> driverAvailable;
    private Realm realm;
    private ServiceModel designedFitur;
    private double distance;
    private long price, promocode;
    private String saldoWallet;
    private String checkedpaywallet;
    String service, getbiaya, biayaminimum, biayaakhir,icondrver;
    MapView mapView;
    LatLng pickUpLatLang;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        ButterKnife.bind(this);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        promocode = 0;
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        setPickUpContainer.setVisibility(View.VISIBLE);

        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());


        backbtn.setOnClickListener(view -> finish());

        topUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TopupSaldoActivity.class)));

        orderButton.setOnClickListener(v -> {
            if (pickUpText.getText().toString().isEmpty()) {
                notif("Location cant be empty!");
            } else {
                onOrderButton();
            }
        });
//
        pickUpText.setOnClickListener(v -> {
            setPickUpContainer.setVisibility(View.VISIBLE);
            openAutocompleteActivity();
        });



        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        int fiturId = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");
        if (fiturId != -1)
            designedFitur = realm.where(ServiceModel.class).equalTo("idFitur", fiturId).findFirst();


        service = String.valueOf(Objects.requireNonNull(designedFitur).getIdFitur());
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        icondrver = designedFitur.getIcon_driver();
        layanan.setText(designedFitur.getFitur());
        layanandesk.setText(designedFitur.getKeterangan());
        enamjam.setSelected(true);
        duabelasjam.setSelected(false);
        satuhari.setSelected(false);
        updateDistance(6);

        enamjam.setOnClickListener(view -> {
            enamjam.setSelected(true);
            duabelasjam.setSelected(false);
            satuhari.setSelected(false);
            updateDistance(6);
        });

        duabelasjam.setOnClickListener(view -> {
            enamjam.setSelected(false);
            duabelasjam.setSelected(true);
            satuhari.setSelected(false);
            updateDistance(12);
        });

        satuhari.setOnClickListener(view -> {
            enamjam.setSelected(false);
            duabelasjam.setSelected(false);
            satuhari.setSelected(true);
            updateDistance(24);
        });

        diskontext.setText("Discount " + designedFitur.getDiskon() + " with Wallet");
        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESFITUR + ICONFITUR)
                .placeholder(R.drawable.logo)
                .resize(100, 100)
                .into(icon);


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

    }

    private void openAutocompleteActivity() {
        Intent intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.mapbox_access_token))
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(15)
                        .build(PlaceOptions.MODE_CARDS))
                .build(RentCarActivity.this);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
                if (mapboxMap != null) {
                    pickUpText.setText(selectedCarmenFeature.placeName());
                    Style style = mapboxMap.getStyle();
                    if (style != null) {
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(((Point) Objects.requireNonNull(selectedCarmenFeature.geometry())).latitude(),
                                                ((Point) selectedCarmenFeature.geometry()).longitude()))
                                        .zoom(15)
                                        .build()), 4);
                        LatLng centerPos = new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                ((Point) selectedCarmenFeature.geometry()).longitude());
                        onPickUp(style, mapboxMap, centerPos);
                    }
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        btnpromo.setEnabled(false);
        btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(service);
        request.setCode(promokode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        if (response.body().getType().equals("persen")) {
                            promocode = (Long.parseLong(response.body().getNominal()) * price) / 100;
                        } else {
                            promocode = Long.parseLong(response.body().getNominal());
                        }
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * price);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(price - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(price - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        }
                    } else {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        notif("promo code not available!");
                        promocode = 0;
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * price);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(price - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(price - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        }
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

    @SuppressLint("SetTextI18n")
    private void updateDistance(long jam) {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        detail.setVisibility(View.VISIBLE);
        orderButton.setVisibility(View.VISIBLE);
        fiturtext.setText(jam + " hr");
        Utility.currencyTXT(diskon, String.valueOf(promocode), RentCarActivity.this);
        checkedpaywallet = "0";
        distance = jam;
        checkedcash.setSelected(true);
        checkedwallet.setSelected(false);
        cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
        walletpayment.setTextColor(getResources().getColor(R.color.gray));
        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        String costs = String.valueOf(biayaminimum);
        long biayaTotal = (long) (Double.parseDouble(getbiaya) * jam);
        if (biayaTotal < Double.parseDouble(biayaminimum)) {
            this.price = Long.parseLong(biayaminimum);
            biayaTotal = Long.parseLong(biayaminimum);
            Utility.currencyTXT(cost, costs, this);
        } else {
            Utility.currencyTXT(cost, getbiaya, this);
        }
        this.price = biayaTotal;

        final long finalBiayaTotal = biayaTotal;
        String totalbiaya = String.valueOf(finalBiayaTotal - promocode);
        Utility.currencyTXT(priceText, totalbiaya, this);

        long saldokini = Long.parseLong(saldoWallet);
        if (saldokini < (biayaTotal - (price * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(view -> {
                String totalbiaya13 = String.valueOf(finalBiayaTotal - promocode);
                Utility.currencyTXT(priceText, totalbiaya13, getBaseContext());
                Utility.currencyTXT(diskon, String.valueOf(promocode), RentCarActivity.this);
                checkedcash.setSelected(true);
                checkedwallet.setSelected(false);
                checkedpaywallet = "0";
                cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                walletpayment.setTextColor(getResources().getColor(R.color.gray));
                checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            });
        } else {
            llcheckedcash.setOnClickListener(view -> {
                String totalbiaya1 = String.valueOf(finalBiayaTotal - promocode);
                Utility.currencyTXT(priceText, totalbiaya1, context);
                Utility.currencyTXT(diskon, String.valueOf(promocode), RentCarActivity.this);
                checkedcash.setSelected(true);
                checkedwallet.setSelected(false);
                checkedpaywallet = "0";
                cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                walletpayment.setTextColor(getResources().getColor(R.color.gray));
                checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            });

            final long finalBiayaTotal1 = biayaTotal;
            llcheckedwallet.setOnClickListener(view -> {
                long diskonwallet = (long) (Double.parseDouble(biayaakhir) * price);
                String totalwallet = String.valueOf(diskonwallet + promocode);
                Utility.currencyTXT(diskon, totalwallet, context);
                String totalbiaya12 = String.valueOf(finalBiayaTotal1 - (diskonwallet + promocode));
                Utility.currencyTXT(priceText, totalbiaya12, context);
                checkedcash.setSelected(false);
                checkedwallet.setSelected(true);
                checkedpaywallet = "1";
                walletpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                cashpayment.setTextColor(getResources().getColor(R.color.gray));
                checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            });
        }

    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    MapboxMap mapboxMap;
    LocationComponent locationComponent;
    Point pickup;
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ignored) {}

        try {
            network_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ignored) {}


            if (PermissionsManager.areLocationPermissionsGranted(this) | gps_enabled && network_enabled) {
                this.mapboxMap = mapboxMap;
                mapboxMap.setStyle(Style.LIGHT, style -> {

                    markerRide(style);
                    UiSettings uiSettings = mapboxMap.getUiSettings();
                    uiSettings.setAttributionEnabled(false);
                    uiSettings.setLogoEnabled(false);
                    uiSettings.setCompassEnabled(false);
                    uiSettings.setRotateGesturesEnabled(false);
                    locationComponent = mapboxMap.getLocationComponent();
                    locationComponent.activateLocationComponent(
                            LocationComponentActivationOptions
                                    .builder(RentCarActivity.this, style)
                                    .useDefaultLocationEngine(true)
                                    .locationEngineRequest(new LocationEngineRequest.Builder(750)
                                            .setFastestInterval(750)
                                            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                                            .build())
                                    .build());

                    locationComponent.setLocationComponentEnabled(true);
                    locationComponent.setRenderMode(RenderMode.COMPASS);
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(Objects.requireNonNull(locationComponent.getLastKnownLocation()).getLatitude(), locationComponent.getLastKnownLocation().getLongitude()))
                            .zoom(18)
                            .tilt(20)
                            .build();
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                    fetchNearDriver(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude(), service, style);
                    initDroppedMarkerpickup(style);
                    currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));
                    setPickUpButton.setOnClickListener(view -> {
                        LatLng centerPos = mapboxMap.getCameraPosition().target;
                        onPickUp(style, mapboxMap, centerPos);
                    });

                });
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
    }

    private void markerRide(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource("driverid"));
        loadedMapStyle.addLayer(new SymbolLayer("layerid",
                "driverid").withProperties(
                iconImage("imageid"),
                iconAllowOverlap(true),
                visibility(Property.NONE),
                iconSize(1.0f),
                iconIgnorePlacement(true)
        ));


    }

    private void initDroppedMarkerpickup(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("dropped-icon-image-pickup", BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_pikup_map));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id-pickup"));
        loadedMapStyle.addLayer(new SymbolLayer("DROPPED_MARKER_LAYER_ID_PICKUP",
                "dropped-marker-source-id-pickup").withProperties(
                iconImage("dropped-icon-image-pickup"),
                iconAllowOverlap(true),
                visibility(Property.NONE),
                iconSize(2.0f),
                iconIgnorePlacement(true)
        ));

    }

    private void onPickUp(Style style, MapboxMap mapboxMap, LatLng centerPos) {

        style.removeLayer("layerid");
        style.removeSource("driverid");
        markerRide(style);

        fetchNearDriver(centerPos.getLatitude(), centerPos.getLongitude(), service, style);
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(centerPos.getLatitude(), centerPos.getLongitude()))
                .zoom(15)
                .tilt(20)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
        setPickUpContainer.setVisibility(View.GONE);
        pickUpLatLang = new LatLng(centerPos.getLatitude(), centerPos.getLongitude());
        if (style.getLayer("DROPPED_MARKER_LAYER_ID_PICKUP") != null) {
            GeoJsonSource source = style.getSourceAs("dropped-marker-source-id-pickup");
            if (source != null) {
                source.setGeoJson(Point.fromLngLat(centerPos.getLongitude(), centerPos.getLatitude()));
            }
            Layer pickUpMarker = style.getLayer("DROPPED_MARKER_LAYER_ID_PICKUP");
            if (pickUpMarker != null) {
                pickUpMarker.setProperties(visibility(Property.VISIBLE));
            }
        }
        textprogress.setVisibility(View.VISIBLE);

        pickup = Point.fromLngLat(centerPos.getLongitude(), centerPos.getLatitude());
        getaddress(pickup, mapboxMap, pickUpText);
    }

    private void getaddress(final Point point, MapboxMap mapboxMap, TextView textView) {
        try {
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(point.longitude(), point.latitude()))
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(@NonNull Call<GeocodingResponse> call, @NonNull Response<GeocodingResponse> response) {

                    if (response.body() != null) {
                        List<CarmenFeature> results = response.body().features();
                        if (results.size() > 0) {
                            CarmenFeature feature = results.get(0);
                            mapboxMap.getStyle(style -> textView.setText(feature.placeName()));

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GeocodingResponse> call, @NonNull Throwable throwable) {
                }
            });
        } catch (ServicesException servicesException) {
            Timber.e("Error geocoding: %s", servicesException.toString());
            servicesException.printStackTrace();
        }
    }

    private void fetchNearDriver(double latitude, double longitude, String service, Style style) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }

        if (mapboxMap != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();

            BookService services = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(latitude);
            param.setLongitude(longitude);
            param.setFitur(service);

            services.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                    if (response.isSuccessful()) {
                        driverAvailable = Objects.requireNonNull(response.body()).getData();
                        createMarker(style);
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

                }
            });
        }
    }

    List<Feature> symbolLayerIconFeatureList;
    private void createMarker(Style style) {
        Layer markermap;
        switch (icondrver) {
            case "1":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.drivermap));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "2":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.carmap));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "3":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.truck));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "4":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.delivery));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "5":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.hatchback));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "6":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.suv));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "7":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.van));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "8":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.bicycle));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
            case "9":
                symbolLayerIconFeatureList = new ArrayList<>();
                for (DriverModel driver : driverAvailable) {
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(driver.getLongitude(), driver.getLatitude())));
                    style.addImage("imageid", BitmapFactory.decodeResource(
                            getResources(), R.drawable.bajaj));
                    markermap = style.getLayer("layerid");
                    if (markermap != null) {
                        GeoJsonSource source = style.getSourceAs("driverid");

                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(driver.getLongitude(), driver.getLatitude()));
                        }
                        markermap.setProperties(visibility(Property.VISIBLE));
                        markermap.setProperties(iconRotate(Float.valueOf(driver.getBearing())));
                    }
                }
                break;
        }
    }


    private void onOrderButton() {
        if (checkedpaywallet.equals("1")) {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                RideCarRequestJson param = new RideCarRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(service);
                param.setStartLatitude(pickUpLatLang.getLatitude());
                param.setStartLongitude(pickUpLatLang.getLongitude());
                param.setEndLatitude(0);
                param.setEndLongitude(0);
                param.setJarak(distance);
                param.setHarga(this.price);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(String.valueOf((Double.parseDouble(biayaakhir) * this.price) + promocode));
                param.setAlamatAsal(pickUpText.getText().toString());
                param.setAlamatTujuan("Rent Car");
                param.setPakaiWallet(1);
                sendRequestTransaksi(param, driverAvailable);
            }
        } else {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                RideCarRequestJson param = new RideCarRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(service);
                param.setStartLatitude(pickUpLatLang.getLatitude());
                param.setStartLongitude(pickUpLatLang.getLongitude());
                param.setEndLatitude(0);
                param.setEndLongitude(0);
                param.setJarak(distance);
                param.setHarga(this.price);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(String.valueOf(promocode));
                param.setAlamatAsal(pickUpText.getText().toString());
                param.setAlamatTujuan("Rent Car");
                param.setPakaiWallet(0);

                sendRequestTransaksi(param, driverAvailable);
            }
        }
    }

    private void sendRequestTransaksi(RideCarRequestJson param, final List<DriverModel> driverList) {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksi(param).enqueue(new Callback<RideCarResponseJson>() {
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

                                            new Handler().postDelayed(RentCarActivity.this::finish, 3000);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<CheckStatusTransResponse> call1, @NonNull Throwable t) {
                                    notif("Driver not found!");
                                    runOnUiThread(() -> notif("Driver not found!"));

                                    new Handler().postDelayed(RentCarActivity.this::finish, 3000);

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

    private void buildDriverRequest(RideCarResponseJson response) {
        transaksi = response.getData().get(0);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (request == null) {
            request = new DriverRequest();
            request.setIdTransaksi(transaksi.getId());
            request.setIdPelanggan(transaksi.getIdPelanggan());
            request.setRegIdPelanggan(loginUser.getToken());
            request.setOrderFitur(designedFitur.getHome());
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setJarak(transaksi.getJarak());
            request.setHarga(transaksi.getHarga());
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(transaksi.getEstimasi());
            request.setLayanan(layanan.getText().toString());
            request.setLayanandesc(layanandesk.getText().toString());
            request.setIcon(ICONFITUR);
            request.setBiaya(cost.getText().toString());


            String namaLengkap = String.format("%s", loginUser.getFullnama());
            request.setNamaPelanggan(namaLengkap);
            request.setTelepon(loginUser.getNoTelepon());
            request.setType(ORDER);
        }
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


                        Intent intent = new Intent(RentCarActivity.this, ProgressActivity.class);
                        intent.putExtra("driver_id", cDriver.getId());
                        intent.putExtra("transaction_id", request.getIdTransaksi());
                        intent.putExtra("response", "2");
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

protected void onStart() {
    super.onStart();
    mapView.onStart();
    EventBus.getDefault().register(this);
}

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        User userLogin = BaseApp.getInstance(this).getLoginUser();
        saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        Utility.currencyTXT(saldotext, saldoWallet, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        realm.close();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
