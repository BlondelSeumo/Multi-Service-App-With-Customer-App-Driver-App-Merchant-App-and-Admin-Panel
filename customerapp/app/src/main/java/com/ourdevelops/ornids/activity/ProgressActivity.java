package com.ourdevelops.ornids.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.item.ItemOrderItem;
import com.ourdevelops.ornids.json.DetailRequestJson;
import com.ourdevelops.ornids.json.DetailTransResponseJson;
import com.ourdevelops.ornids.json.LocationDriverRequest;
import com.ourdevelops.ornids.json.LocationDriverResponse;
import com.ourdevelops.ornids.json.fcm.CancelBookRequestJson;
import com.ourdevelops.ornids.json.fcm.CancelBookResponseJson;
import com.ourdevelops.ornids.json.fcm.DriverResponse;
import com.ourdevelops.ornids.json.fcm.FCMMessage;
import com.ourdevelops.ornids.models.DriverModel;
import com.ourdevelops.ornids.models.LokasiDriverModel;
import com.ourdevelops.ornids.models.ServiceModel;
import com.ourdevelops.ornids.models.TransModel;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.NetworkManager;
import com.ourdevelops.ornids.utils.PicassoTrustAll;
import com.ourdevelops.ornids.utils.Utility;
import com.ourdevelops.ornids.utils.api.FCMHelper;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.BookService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.expressions.Expression.color;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lineProgress;
import static com.mapbox.mapboxsdk.style.expressions.Expression.linear;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconRotate;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.ourdevelops.ornids.json.fcm.FCMType.ORDER;
import static com.ourdevelops.ornids.utils.api.service.MessagingService.BROADCAST_ORDER;

/**
 * Created by Ourdevelops Team on 10/26/2019.
 */

public class ProgressActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_PERMISSION_CALL = 992;
    private boolean isCancelable = true;
    String idtrans, iddriver, response;
    String regdriver, service, imagedriver, tokenmerchant;
    private int markerCount;
    String latdriver = "";
    String londriver = "";
    String complete, icondriver, gethome;
    private Handler handler;
    FloatingActionButton currentLocation;
    Timer timer = new Timer();
    private Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(() -> {
                try {
                    LocationDriverRequest param = new LocationDriverRequest();
                    final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                    param.setId(iddriver);
                    service.liatLokasiDriver(param).enqueue(new Callback<LocationDriverResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LocationDriverResponse> call, @NonNull Response<LocationDriverResponse> responses) {
                            if (responses.isSuccessful()) {
                                final LokasiDriverModel latlang = Objects.requireNonNull(responses.body()).getData().get(0);
                                final LatLng location = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                                if (response.equals("3")) {
                                    CameraPosition position = new CameraPosition.Builder()
                                            .target(location)
                                            .zoom(18)
                                            .tilt(20)
                                            .build();
                                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 4000);
                                    currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));
                                }
                                updateDriverMarker(location);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LocationDriverResponse> call, @NonNull Throwable t) {


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (NetworkManager.isConnectToInternet(ProgressActivity.this)) {
                            try {
                                LocationDriverRequest param = new LocationDriverRequest();
                                final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                                param.setId(iddriver);
                                service.liatLokasiDriver(param).enqueue(new Callback<LocationDriverResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<LocationDriverResponse> call, @NonNull Response<LocationDriverResponse> responses) {
                                        if (responses.isSuccessful()) {
                                            final LokasiDriverModel latlang = Objects.requireNonNull(responses.body()).getData().get(0);
                                            final LatLng location = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                                            if (response.equals("3")) {
                                                CameraPosition position = new CameraPosition.Builder()
                                                        .target(location)
                                                        .zoom(18)
                                                        .tilt(20)
                                                        .build();
                                                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 4000);
                                                currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));
                                            }
                                            updateDriverMarker(location);
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<LocationDriverResponse> call, @NonNull Throwable t) {


                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 0, 10000);
            }).start();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDriverLocationUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDriverLocationUpdate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopDriverLocationUpdate();
        super.onBackPressed();
        if (complete.equals("1")) {
            Intent intent = new Intent(ProgressActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    private void stopDriverLocationUpdate() {
        timer.cancel();
        handler.removeCallbacks(updateDriverRunnable);

    }

    TextView textprogress, status, time, costtext;
    View viewStatus;
    CardView cardStatus;
    ImageView phone, chat;
    TextView produk, sendername, receivername;
    Button senderphone, receiverphone;
    TextView textnotif, priceText;
    TextView diskon, cost, distanceText, fiturtext, destinationText, pickUpText;
    ImageView photo, image;
    FloatingActionButton backbtn;
    TextView layanandesk, layanan, totaltext;
    LinearLayout llpayment, bottomsheet, setDestinationContainer, setPickUpContainer, llchat, lldestination, lldistance, lldetailsend, llmerchantdetail;
    RelativeLayout rlnotif, rlprogress;
    Button orderButton;
    RecyclerView rvmerchantnear;
    ItemOrderItem itemOrderItem;
    Realm realm;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_ride);
        realm = Realm.getDefaultInstance();
        handler = new Handler();
        service = "0";
        icondriver = "0";
        gethome = "0";
        Intent intent = getIntent();
        iddriver = intent.getStringExtra("driver_id");
        idtrans = intent.getStringExtra("transaction_id");
        response = intent.getStringExtra("response");
        if (intent.getStringExtra("complete") == null) {
            complete = "false";
        } else {
            complete = intent.getStringExtra("complete");
        }
        receiverphone = findViewById(R.id.receiverphone);
        senderphone = findViewById(R.id.senderphone);
        receivername = findViewById(R.id.receivername);
        sendername = findViewById(R.id.sendername);
        produk = findViewById(R.id.produk);
        lldetailsend = findViewById(R.id.senddetail);
        lldistance = findViewById(R.id.lldistance);
        lldestination = findViewById(R.id.lldestination);
        status = findViewById(R.id.status);
        viewStatus = findViewById(R.id.viewstatus);
        cardStatus = findViewById(R.id.cardstatus);
        chat = findViewById(R.id.chat);
        phone = findViewById(R.id.phonenumber);
        setPickUpContainer = findViewById(R.id.pickUpContainer);
        setDestinationContainer = findViewById(R.id.destinationContainer);
        bottomsheet = findViewById(R.id.bottom_sheet);
        llpayment = findViewById(R.id.llpayment);
        layanan = findViewById(R.id.layanan);
        layanandesk = findViewById(R.id.layanandes);
        backbtn = findViewById(R.id.back_btn);
        currentLocation = findViewById(R.id.currentlocation);
        llchat = findViewById(R.id.llchat);
        image = findViewById(R.id.image);
        photo = findViewById(R.id.background);
        pickUpText = findViewById(R.id.pickUpText);
        destinationText = findViewById(R.id.destinationText);
        fiturtext = findViewById(R.id.service);
        distanceText = findViewById(R.id.distance);
        cost = findViewById(R.id.cost);
        diskon = findViewById(R.id.diskon);
        priceText = findViewById(R.id.price);
        orderButton = findViewById(R.id.order);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        textprogress = findViewById(R.id.textprogress);
        llmerchantdetail = findViewById(R.id.merchantdetail);
        time = findViewById(R.id.time);
        costtext = findViewById(R.id.cost_text);
        rvmerchantnear = findViewById(R.id.merchantnear);
        totaltext = findViewById(R.id.totaltext);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        cardStatus.setVisibility(View.VISIBLE);
        viewStatus.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        setPickUpContainer.setVisibility(View.GONE);
        setDestinationContainer.setVisibility(View.GONE);
        llpayment.setVisibility(View.GONE);

        backbtn.setOnClickListener(view -> finish());

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        orderButton.setText(getString(R.string.text_cancel));
        orderButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_red));

        orderButton.setOnClickListener(view -> {
            if (isCancelable) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                alertDialogBuilder.setTitle("Cancel order");
                alertDialogBuilder.setMessage("Do you want to cancel this order?");
                alertDialogBuilder.setPositiveButton("yes",
                        (arg0, arg1) -> cancelOrder());

                alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                notif("You cannot cancel the order, the trip has already begun!");
            }
        });


        rlprogress.setVisibility(View.VISIBLE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));

    }


    LatLng currenLocation;
    MapboxMap mapboxMap;

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapboxMap.setStyle(new Style.Builder().fromUri(Style.LIGHT).withImage("ORIGIN_ICON_ID", Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(
                    getResources().getDrawable(R.drawable.ic_pikup_map))))
                    .withImage("DESTINATION_ICON_ID", Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(
                            getResources().getDrawable(R.drawable.ic_destination_map)))), style -> {
                styles = style;
                UiSettings uiSettings = mapboxMap.getUiSettings();
                uiSettings.setAttributionEnabled(false);
                uiSettings.setLogoEnabled(false);
                uiSettings.setCompassEnabled(false);
                uiSettings.setRotateGesturesEnabled(false);
                initSources(style);
                initLayers(style);
                getData(mapboxMap);
                LocationDriverRequest param = new LocationDriverRequest();
                final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                param.setId(iddriver);
                service.liatLokasiDriver(param).enqueue(new Callback<LocationDriverResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LocationDriverResponse> call, @NonNull Response<LocationDriverResponse> responses) {
                        if (responses.isSuccessful()) {
                            final LokasiDriverModel latlang = Objects.requireNonNull(responses.body()).getData().get(0);
                            currenLocation = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                            if (response.equals("2") || response.equals("3")) {
                                startDriverLocationUpdate();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LocationDriverResponse> call, @NonNull Throwable t) {


                    }
                });
            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    Point pickup, destination;

    private void getData(MapboxMap mapboxMap) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService services = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdDriver(iddriver);
        services.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {

                    final TransModel transaksi = Objects.requireNonNull(responsedata.body()).getData().get(0);
                    ServiceModel designedFitur = realm.where(ServiceModel.class).equalTo("idFitur", Integer.valueOf(transaksi.getOrderFitur())).findFirst();
                    icondriver = Objects.requireNonNull(designedFitur).getIcon_driver();
                    gethome = designedFitur.getHome();
                    DriverModel driver = responsedata.body().getDriver().get(0);
                    response = String.valueOf(transaksi.status);
                    if (transaksi.status == 4 && transaksi.getRate().isEmpty()) {
                        Intent intent = new Intent(ProgressActivity.this, RateActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("driver_id", iddriver);
                        intent.putExtra("transaction_id", idtrans);
                        intent.putExtra("response", response);
                        startActivity(intent);
                        finish();
                    } else {
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(transaksi.getStartLatitude(), transaksi.getStartLongitude()))
                                .zoom(15)
                                .tilt(20)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                        currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));
                    }
                    parsedata(transaksi, driver, designedFitur);
                    regdriver = driver.getRegId();
                    tokenmerchant = transaksi.getToken_merchant();
                    imagedriver = Constant.IMAGESDRIVER + driver.getFoto();

                    if (transaksi.isPakaiWallet()) {
                        totaltext.setText("Total (Wallet)");
                    } else {
                        totaltext.setText("Total (Cash)");
                    }

                    service = transaksi.getOrderFitur();

                    if (!designedFitur.getHome().equals("3")) {

                        fiturtext.setText(transaksi.getEstimasi());
                        if (designedFitur.getHome().equals("4")) {
                            llmerchantdetail.setVisibility(View.VISIBLE);
                            Utility.currencyTXT(fiturtext, String.valueOf(transaksi.getHarga()), ProgressActivity.this);
                            lldistance.setVisibility(View.GONE);
                            time.setText("Delivery Fee");
                            costtext.setText("Order Cost");

                            itemOrderItem = new ItemOrderItem(responsedata.body().getItem(), R.layout.item_pesanan);
                            rvmerchantnear.setAdapter(itemOrderItem);

                        } else if (designedFitur.getHome().equals("2")) {
                            fiturtext.setText(transaksi.getEstimasi());
                            lldetailsend.setVisibility(View.VISIBLE);
                            produk.setText(transaksi.getNamaBarang());
                            sendername.setText(transaksi.namaPengirim);
                            receivername.setText(transaksi.namaPenerima);

                            senderphone.setOnClickListener(v -> {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Driver");
                                alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPengirim() + "(" + transaksi.teleponPengirim + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        (arg0, arg1) -> {
                                            if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                return;
                                            }

                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            callIntent.setData(Uri.parse("tel:" + transaksi.teleponPengirim));
                                            startActivity(callIntent);
                                        });

                                alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            });

                            receiverphone.setOnClickListener(v -> {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Driver");
                                alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima() + "(" + transaksi.teleponPenerima + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        (arg0, arg1) -> {
                                            if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                return;
                                            }

                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                            startActivity(callIntent);
                                        });

                                alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


                            });

                        }
                    } else {
                        fiturtext.setText(transaksi.getEstimasi());
                        lldestination.setVisibility(View.GONE);
                        lldistance.setVisibility(View.GONE);
                    }

                    String formatdistance = String.format(Locale.US, "%.1f", transaksi.getJarak());
                    distanceText.setText(formatdistance);



                    destination = Point.fromLngLat(transaksi.getEndLongitude(), transaksi.getEndLatitude());
                    pickup = Point.fromLngLat(transaksi.getStartLongitude(), transaksi.getStartLatitude());
                    if (designedFitur.getHome().equals("3")) {
                        GeoJsonSource originDestinationPointGeoJsonSource = styles.getSourceAs("DEST_SOURCE_ID");

                        if (originDestinationPointGeoJsonSource != null) {
                            originDestinationPointGeoJsonSource.setGeoJson(getOriginAndDestinationFeatureCollectionrent());
                        }
                    } else {
                        getRoute(mapboxMap, pickup, destination);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void parsedata(TransModel request, final DriverModel driver, ServiceModel fiturmodel) {
        final User loginUser = BaseApp.getInstance(ProgressActivity.this).getLoginUser();
        rlprogress.setVisibility(View.GONE);


        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESDRIVER + driver.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(photo);

        layanandesk.setText(driver.getNomor_kendaraan() + " " + getString(R.string.text_with_bullet) + " " + driver.getTipe());

        if (!response.equals("2")) {


            if (!response.equals("3")) {
                if (response.equals("4")) {
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText(getString(R.string.notification_finish));
                } else if (response.equals("5")) {
                    isCancelable = false;
                    llchat.setVisibility(View.GONE);
                    orderButton.setVisibility(View.GONE);
                    status.setText(getString(R.string.notification_cancel));
                }
            } else {
                llchat.setVisibility(View.VISIBLE);
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                if (fiturmodel.getHome().equals("4")) {
                    status.setText("driver delivers the order");
                } else {
                    status.setText(getString(R.string.notification_start));
                }
            }
        } else {
            llchat.setVisibility(View.VISIBLE);
            if (fiturmodel.getHome().equals("4")) {
                status.setText("the driver is buying an order");
            } else {
                status.setText(getString(R.string.notification_accept));
            }
        }
        layanan.setText(driver.getNamaDriver());
        if (fiturmodel.getHome().equals("4")) {
            pickUpText.setText(request.getNama_merchant());
            Utility.currencyTXT(cost, String.valueOf(request.getTotal_biaya()), this);
        } else {
            pickUpText.setText(request.getAlamatAsal());
            Utility.currencyTXT(cost, String.valueOf(request.getHarga()), this);
        }
        destinationText.setText(request.getAlamatTujuan());

        Utility.currencyTXT(diskon, request.getKreditPromo(), this);
        Utility.currencyTXT(priceText, String.valueOf(request.getBiaya_akhir()), this);

        phone.setOnClickListener(v -> {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProgressActivity.this, R.style.DialogStyle);
            alertDialogBuilder.setTitle("Call Driver");
            alertDialogBuilder.setMessage("You want to call driver (" + driver.getNoTelepon() + ")?");
            alertDialogBuilder.setPositiveButton("yes",
                    (arg0, arg1) -> {
                        if (ActivityCompat.checkSelfPermission(ProgressActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ProgressActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                            return;
                        }

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + driver.getNoTelepon()));
                        startActivity(callIntent);
                    });

            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        });

        chat.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, ChatActivity.class);
            intent.putExtra("senderid", loginUser.getId());
            intent.putExtra("receiverid", driver.getId());
            intent.putExtra("tokendriver", loginUser.getToken());
            intent.putExtra("tokenku", driver.getRegId());
            intent.putExtra("name", driver.getNamaDriver());
            intent.putExtra("pic", Constant.IMAGESDRIVER + driver.getFoto());
            startActivity(intent);
        });
    }

    private void initSources(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource("DEST_LINE_SOURCE_ID", new GeoJsonOptions().withLineMetrics(true)));
        loadedMapStyle.addSource(new GeoJsonSource("DEST_SOURCE_ID"));

    }

    private void initLayers(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new LineLayer("DROUTE_LAYER_ID", "DEST_LINE_SOURCE_ID").withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(6f),
                lineGradient(interpolate(
                        linear(), lineProgress(),
                        stop(0f, color(getResources().getColor(R.color.colorPrimary))),
                        stop(1f, color(getResources().getColor(R.color.colorgradient))
                        )))));

        loadedMapStyle.addLayer(new SymbolLayer("DICON_LAYER_ID", "DEST_SOURCE_ID").withProperties(
                iconImage(match(get("originDestination"), literal("origin"),
                        stop("origin", "ORIGIN_ICON_ID"),
                        stop("destination", "DESTINATION_ICON_ID"))),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconSize(2.0f),
                iconOffset(new Float[]{0f, -4f})));
    }

    MapboxDirections client;
    private DirectionsRoute currentRoute;

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    return;
                } else if (response.body().routes().size() < 1) {
                    return;
                }

                currentRoute = response.body().routes().get(0);

                if (currentRoute != null) {
                    if (mapboxMap != null) {
                        mapboxMap.getStyle(style -> {
                            GeoJsonSource originDestinationPointGeoJsonSource = style.getSourceAs("DEST_SOURCE_ID");

                            if (originDestinationPointGeoJsonSource != null) {
                                originDestinationPointGeoJsonSource.setGeoJson(getOriginAndDestinationFeatureCollection());
                            }
                            GeoJsonSource lineLayerRouteGeoJsonSource = style.getSourceAs("DEST_LINE_SOURCE_ID");

                            if (lineLayerRouteGeoJsonSource != null) {
                                LineString lineString = LineString.fromPolyline(Objects.requireNonNull(currentRoute.geometry()), PRECISION_6);
                                lineLayerRouteGeoJsonSource.setGeoJson(Feature.fromGeometry(lineString));
                            }
                        });
                    }
                } else {
                    Timber.d("Directions route is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable throwable) {
            }
        });
    }

    private FeatureCollection getOriginAndDestinationFeatureCollection() {
        Feature origin = Feature.fromGeometry(pickup);
        origin.addStringProperty("originDestination", "origin");
        Feature destinationFeature = Feature.fromGeometry(destination);
        destinationFeature.addStringProperty("originDestination", "destination");
        return FeatureCollection.fromFeatures(new Feature[]{origin, destinationFeature});
    }

    private FeatureCollection getOriginAndDestinationFeatureCollectionrent() {
        Feature origin = Feature.fromGeometry(pickup);
        origin.addStringProperty("originDestination", "origin");
        return FeatureCollection.fromFeatures(new Feature[]{origin});
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }

    private void cancelOrder() {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(ProgressActivity.this).getLoginUser();
        CancelBookRequestJson requestcancel = new CancelBookRequestJson();
        requestcancel.id = loginUser.getId();
        requestcancel.transaction_id = idtrans;

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        service.cancelOrder(requestcancel).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<CancelBookResponseJson> call, @NonNull Response<CancelBookResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("canceled")) {
                        rlprogress.setVisibility(View.GONE);
                        fcmcancel();
                        fcmcancelmerchant();
                        notif("Order Canceled!");
                        finish();
                    } else {
                        notif("Failed!");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelBookResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });


    }

    private void fcmcancel() {
        DriverResponse response = new DriverResponse();
        response.type = ORDER;
        response.setIdTransaksi(idtrans);
        response.setResponse(DriverResponse.REJECT);

        FCMMessage message = new FCMMessage();
        message.setTo(regdriver);
        message.setData(response);


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

    private void fcmcancelmerchant() {
        DriverResponse response = new DriverResponse();
        response.type = ORDER;
        response.setIdTransaksi(idtrans);
        response.setResponse(String.valueOf(Constant.CANCEL));

        FCMMessage message = new FCMMessage();
        message.setTo(tokenmerchant);
        message.setData(response);


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

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    private void startDriverLocationUpdate() {
        handler = new Handler();
        handler.postDelayed(updateDriverRunnable, 10000);
    }

    private void markerRide(@NonNull Style loadedMapStyle, int drawable) {
        loadedMapStyle.addImage("f", BitmapFactory.decodeResource(
                getResources(), drawable));

        loadedMapStyle.addSource(new GeoJsonSource("id", Point.fromLngLat(currenLocation.getLongitude(), currenLocation.getLatitude())));
        loadedMapStyle.addLayer(new SymbolLayer("l",
                "id").withProperties(
                iconImage("f"),
                iconAllowOverlap(true),
                iconSize(1.0f),
                iconRotate(0f),
                iconIgnorePlacement(true)
        ));


    }

    private void updateDriverMarker(LatLng latLng) {
        latdriver = String.valueOf(latLng.getLatitude());
        londriver = String.valueOf(latLng.getLongitude());
        Location mCurrentLocation = new Location(LocationManager.NETWORK_PROVIDER);
        mCurrentLocation.setLatitude(Double.parseDouble(latdriver));
        mCurrentLocation.setLongitude(Double.parseDouble(londriver));

        if (markerCount == 1) {
            animateMarker(mCurrentLocation);

        } else if (markerCount == 0) {
            switch (icondriver) {
                case "1":
                    markerRide(styles, R.drawable.drivermap);
                    break;
                case "2":
                    markerRide(styles, R.drawable.carmap);

                    break;
                case "3":
                    markerRide(styles, R.drawable.truck);

                    break;
                case "4":
                    markerRide(styles, R.drawable.delivery);

                    break;
                case "5":
                    markerRide(styles, R.drawable.hatchback);

                    break;
                case "6":
                    markerRide(styles, R.drawable.suv);

                    break;
                case "7":
                    markerRide(styles, R.drawable.van);

                    break;
                case "8":
                    markerRide(styles, R.drawable.bicycle);

                    break;
                case "9":
                    markerRide(styles, R.drawable.bajaj);

                    break;
            }
            markerCount = 1;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            }
        }

    }

    private Layer markermap;
    Style styles;

    public void animateMarker(final Location destination) {
        final LatLng startPosition = currenLocation;
        final LatLng endPosition = new LatLng(destination.getLatitude(), destination.getLongitude());
        if (!startPosition.equals(endPosition)) {
            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                try {
                    float v = animation.getAnimatedFraction();
                    LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);

                    markermap = styles.getLayer("l");
                    if (markermap != null) {
                        markermap.setProperties(iconRotate(getBearing(startPosition, newPosition)));
                        GeoJsonSource source = styles.getSourceAs("id");
                        if (source != null) {
                            source.setGeoJson(Point.fromLngLat(newPosition.getLongitude(), newPosition.getLatitude()));

                        }

                    }
                } catch (Exception ex) {
                    // I don't care atm..
                }
            });
            valueAnimator.start();
            currenLocation = endPosition;
        }
    }

    public static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.getLatitude() - end.getLatitude());
        double lng = Math.abs(begin.getLongitude() - end.getLongitude());

        final double v = Math.toDegrees(Math.atan(lng / lat));
        if (begin.getLatitude() < end.getLatitude() && begin.getLatitude() < end.getLatitude())
            return (float) v;
        else if (begin.getLatitude() >= end.getLatitude() && begin.getLatitude() < end.getLatitude())
            return (float) ((90 - v) + 90);
        else if (begin.getLatitude() >= end.getLatitude() && begin.getLatitude() >= end.getLatitude())
            return (float) (v + 180);
        else if (begin.getLatitude() < end.getLatitude() && begin.getLatitude() >= end.getLatitude())
            return (float) ((90 - v) + 270);
        return -1;
    }

    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.getLatitude() - a.getLatitude()) * fraction + a.getLatitude();
                double lngDelta = b.getLongitude() - a.getLongitude();
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.getLongitude();
                return new LatLng(lat, lng);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void orderHandler(int code) {
        switch (code) {
            case Constant.REJECT:
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                break;
            case Constant.CANCEL:
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                llchat.setVisibility(View.GONE);
                status.setText(getString(R.string.notification_cancel));
                break;
            case Constant.ACCEPT:
                response = "2";
                llchat.setVisibility(View.VISIBLE);
                if (gethome.equals("4")) {
                    status.setText("the driver is buying an order");
                } else {
                    status.setText(getString(R.string.notification_accept));
                }
                break;
            case Constant.START:
                llchat.setVisibility(View.VISIBLE);
                isCancelable = false;
                orderButton.setVisibility(View.GONE);
                response = "3";
                if (gethome.equals("4")) {
                    status.setText("driver delivers the order");
                } else {
                    status.setText(getString(R.string.notification_start));
                }
                break;
            case Constant.FINISH:
                isCancelable = false;
                llchat.setVisibility(View.GONE);
                orderButton.setVisibility(View.GONE);
                response = "4";
                status.setText(getString(R.string.notification_finish));
                getData(mapboxMap);
                stopDriverLocationUpdate();
                break;
        }
    }

    Bundle orderBundle;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            orderBundle = intent.getExtras();
            orderHandler(Objects.requireNonNull(orderBundle).getInt("code"));
        }
    };

    //
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
        removeNotif();
    }

    //
    @Override
    protected void onStart() {
        mapView.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        EventBus.getDefault().unregister(this);
        super.onStop();

        unregisterReceiver(broadcastReceiver);
        stopDriverLocationUpdate();
    }


    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        if (!complete.equals("true")) {
            orderHandler(Integer.parseInt(response.getResponse()));
            DriverResponse responses = new DriverResponse();
            responses.setId("");
            responses.setIdTransaksi("");
            responses.setResponse("");
            EventBus.getDefault().postSticky(responses);
        }

    }


}
