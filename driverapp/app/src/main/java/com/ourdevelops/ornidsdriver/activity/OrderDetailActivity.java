package com.ourdevelops.ornidsdriver.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ornolfr.ratingview.RatingView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.item.ItemPesananItem;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.ourdevelops.ornidsdriver.json.DetailRequestJson;
import com.ourdevelops.ornidsdriver.json.DetailTransResponseJson;
import com.ourdevelops.ornidsdriver.models.CustomerModel;
import com.ourdevelops.ornidsdriver.models.TransModel;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.Log;
import com.ourdevelops.ornidsdriver.utils.Utility;
import com.ourdevelops.ornidsdriver.utils.PicassoTrustAll;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
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
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineGradient;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class OrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;
    @BindView(R.id.layanan)
    TextView layanan;
    @BindView(R.id.layanandes)
    TextView layanandesk;
    @BindView(R.id.llchat)
    LinearLayout llchat;
    @BindView(R.id.background)
    CircleImageView photo;
    @BindView(R.id.pickUpText)
    TextView pickUpText;
    @BindView(R.id.destinationText)
    TextView destinationText;
    @BindView(R.id.service)
    TextView fiturtext;
    @BindView(R.id.distance)
    TextView distanceText;
    @BindView(R.id.price)
    TextView priceText;
    @BindView(R.id.namamerchant)
    TextView namamerchant;
    @BindView(R.id.order)
    Button orderButton;
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @BindView(R.id.textprogress)
    TextView textprogress;
    @BindView(R.id.phonenumber)
    ImageView phone;
    @BindView(R.id.chat)
    ImageView chat;
    @BindView(R.id.llchatmerchant)
    LinearLayout llchatmerchant;
    @BindView(R.id.lldestination)
    LinearLayout lldestination;
    @BindView(R.id.lldistance)
    LinearLayout lldistance;
    @BindView(R.id.senddetail)
    LinearLayout lldetailsend;
    @BindView(R.id.produk)
    TextView produk;
    @BindView(R.id.sendername)
    TextView sendername;
    @BindView(R.id.receivername)
    TextView receivername;
    @BindView(R.id.senderphone)
    Button senderphone;
    @BindView(R.id.receiverphone)
    Button receiverphone;

    @BindView(R.id.llincash)
    LinearLayout llincash;
    @BindView(R.id.llinwallet)
    LinearLayout llinwallet;
    @BindView(R.id.incash)
    TextView incash;
    @BindView(R.id.inwallet)
    TextView inwallet;

    @BindView(R.id.scroller)
    NestedScrollView scrollView;

    @BindView(R.id.back_btn)
    ImageView backbutton;
    @BindView(R.id.status)
    LinearLayout llrating;
    @BindView(R.id.ratingView)
    RatingView ratingView;
    @BindView(R.id.llbutton)
    LinearLayout llbutton;

    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.deliveryfee)
    TextView deliveryfee;
    @BindView(R.id.orderdetail)
    LinearLayout llorderdetail;
    @BindView(R.id.merchantdetail)
    LinearLayout llmerchantdetail;
    @BindView(R.id.merchantinfo)
    LinearLayout llmerchantinfo;
    @BindView(R.id.shimmerlayanan)
    ShimmerFrameLayout shimmerlayanan;
    @BindView(R.id.shimmerpickup)
    ShimmerFrameLayout shimmerpickup;
    @BindView(R.id.shimmerdestination)
    ShimmerFrameLayout shimmerdestination;
    @BindView(R.id.shimmerfitur)
    ShimmerFrameLayout shimmerfitur;
    @BindView(R.id.shimmerdistance)
    ShimmerFrameLayout shimmerdistance;
    @BindView(R.id.shimmerprice)
    ShimmerFrameLayout shimmerprice;

    @BindView(R.id.shimmerincash)
    ShimmerFrameLayout shimmerincash;
    @BindView(R.id.shimmerinwallet)
    ShimmerFrameLayout shimmerinwallet;
    @BindView(R.id.currentlocation)
    FloatingActionButton currentLocation;

    @BindView(R.id.merchantnear)
    RecyclerView rvmerchantnear;

    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private static final int REQUEST_PERMISSION_CALL = 992;
    String idtrans, idpelanggan, response, service;
    ItemPesananItem itemPesananItem;
    TextView totaltext;
    MapView mapView;

    Point destination, pickup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setPeekHeight(300);
        llrating.setVisibility(View.VISIBLE);
        backbutton.setVisibility(View.VISIBLE);
        llbutton.setVisibility(View.GONE);
        llchat.setVisibility(View.GONE);
        llchatmerchant.setVisibility(View.GONE);
        totaltext = findViewById(R.id.totaltext);
        currentLocation.setVisibility(View.GONE);
        shimmerload();


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        idpelanggan = intent.getStringExtra("customer_id");
        idtrans = intent.getStringExtra("transaction_id");
        response = intent.getStringExtra("response");

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (Objects.equals(response, "2")) {
            llchat.setVisibility(View.VISIBLE);
            layanandesk.setText(getString(R.string.notification_accept));
        } else if (Objects.equals(response, "3")) {
            llchat.setVisibility(View.VISIBLE);
            orderButton.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_start));
        } else if (Objects.equals(response, "4")) {
            scrollView.setPadding(0, 0, 0, 10);
            llchat.setVisibility(View.GONE);
            orderButton.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_finish));
        } else if (Objects.equals(response, "5")) {
            scrollView.setPadding(0, 0, 0, 10);
            llchat.setVisibility(View.GONE);
            orderButton.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_cancel));
        }
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapboxMap.setStyle(new Style.Builder().fromUri(Style.LIGHT)
                .withImage("ORIGIN_ICON_ID", Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(
                        getResources().getDrawable(R.drawable.ic_pickup_map))))
                .withImage("DESTINATION_ICON_ID", Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(
                        getResources().getDrawable(R.drawable.ic_destination_map)))), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {

                UiSettings uiSettings = mapboxMap.getUiSettings();
                uiSettings.setCompassEnabled(false);
                uiSettings.setAttributionEnabled(false);
                uiSettings.setLogoEnabled(false);

                getData(idtrans, idpelanggan, style, mapboxMap);

            }
        });
    }

    private void getData(final String idtrans, final String idpelanggan, Style style, MapboxMap mapboxMap) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService services = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);
        services.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {
                    shimmertutup();
                    final TransModel transaksi = responsedata.body().getData().get(0);
                    CustomerModel pelanggan = responsedata.body().getPelanggan().get(0);
                    String formatdistance = String.format(Locale.US, "%.1f", transaksi.getJarak());
                    distanceText.setText(formatdistance);
                    fiturtext.setText(transaksi.getEstimasi());
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(transaksi.getEndLatitude(), transaksi.getEndLongitude()))
                            .zoom(10)
                            .tilt(20)
                            .build();
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                    service = transaksi.getOrderFitur();
                    Log.e("home", transaksi.getHome());

                    switch (transaksi.getHome()) {
                        case "3":
                            lldestination.setVisibility(View.GONE);
                            lldistance.setVisibility(View.GONE);
                            fiturtext.setText(transaksi.getEstimasi());
                            break;
                        case "4":
                            llorderdetail.setVisibility(View.VISIBLE);
                            llmerchantdetail.setVisibility(View.VISIBLE);
                            llmerchantinfo.setVisibility(View.VISIBLE);
                            Utility.currencyTXT(deliveryfee, String.valueOf(transaksi.getHarga()), OrderDetailActivity.this);
                            Utility.currencyTXT(cost, String.valueOf(transaksi.getTotal_biaya()), OrderDetailActivity.this);
                            namamerchant.setText(transaksi.getNama_merchant());

                            itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan);
                            rvmerchantnear.setAdapter(itemPesananItem);
                            break;
                        case "2":
                            lldetailsend.setVisibility(View.VISIBLE);
                            produk.setText(transaksi.getNamaBarang());
                            sendername.setText(transaksi.namaPengirim);
                            receivername.setText(transaksi.namaPenerima);

                            senderphone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Call Driver");
                                    alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPengirim() + "(" + transaksi.teleponPengirim + ")?");
                                    alertDialogBuilder.setPositiveButton("yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPengirim));
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

                            receiverphone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                    alertDialogBuilder.setTitle("Call Driver");
                                    alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima() + "(" + transaksi.teleponPenerima + ")?");
                                    alertDialogBuilder.setPositiveButton("yes",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                        return;
                                                    }

                                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
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

                            break;
                    }


                    destination = Point.fromLngLat(transaksi.getEndLongitude(), transaksi.getEndLatitude());
                    pickup = Point.fromLngLat(transaksi.getStartLongitude(), transaksi.getStartLatitude());
                    initSources(style, "DEST_LINE_SOURCE_ID", "DEST_SOURCE_ID");
                    initLayers(style, "DEST_LINE_SOURCE_ID", "DEST_SOURCE_ID", "DROUTE_LAYER_ID", "DICON_LAYER_ID");
                    getRoute(mapboxMap, pickup, destination, "DEST_LINE_SOURCE_ID", "DEST_SOURCE_ID");


                    parsedata(transaksi, pelanggan);

                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });


    }

    private void initSources(@NonNull Style loadedMapStyle, String lineid, String iconsourceid) {
        loadedMapStyle.addSource(new GeoJsonSource(lineid, new GeoJsonOptions().withLineMetrics(true)));
        loadedMapStyle.addSource(new GeoJsonSource(iconsourceid, getOriginAndDestinationFeatureCollection()));

    }

    private void initLayers(@NonNull Style loadedMapStyle, String lineid, String iconsourceid, String routeid, String iconlayerid) {
        loadedMapStyle.addLayer(new LineLayer(routeid, lineid).withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(6f),
                lineGradient(interpolate(
                        linear(), lineProgress(),
                        stop(0f, color(getResources().getColor(R.color.colorPrimary))),
                        stop(1f, color(getResources().getColor(R.color.colorgradient))
                        )))));

        loadedMapStyle.addLayer(new SymbolLayer(iconlayerid, iconsourceid).withProperties(
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

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination, String lineid, String iconsourceid) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    return;
                } else if (response.body().routes().size() < 1) {
                    return;
                }

                currentRoute = response.body().routes().get(0);

                if (currentRoute != null) {
                    if (mapboxMap != null) {
                        mapboxMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                GeoJsonSource originDestinationPointGeoJsonSource = style.getSourceAs(iconsourceid);

                                if (originDestinationPointGeoJsonSource != null) {
                                    originDestinationPointGeoJsonSource.setGeoJson(getOriginAndDestinationFeatureCollection());
                                }
                                GeoJsonSource lineLayerRouteGeoJsonSource = style.getSourceAs(lineid);

                                if (lineLayerRouteGeoJsonSource != null) {
                                    LineString lineString = LineString.fromPolyline(Objects.requireNonNull(currentRoute.geometry()), PRECISION_6);
                                    lineLayerRouteGeoJsonSource.setGeoJson(Feature.fromGeometry(lineString));
                                }
                            }
                        });
                    }
                } else {
                    Timber.d("Directions route is null");
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
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

    private void shimmerload() {
        shimmerlayanan.startShimmerAnimation();
        shimmerpickup.startShimmerAnimation();
        shimmerdestination.startShimmerAnimation();
        shimmerfitur.startShimmerAnimation();
        shimmerdistance.startShimmerAnimation();
        shimmerprice.startShimmerAnimation();
        shimmerincash.startShimmerAnimation();
        shimmerinwallet.startShimmerAnimation();

        layanan.setVisibility(View.GONE);
        layanandesk.setVisibility(View.GONE);
        pickUpText.setVisibility(View.GONE);
        destinationText.setVisibility(View.GONE);
        fiturtext.setVisibility(View.GONE);
        priceText.setVisibility(View.GONE);
        incash.setVisibility(View.GONE);
        inwallet.setVisibility(View.GONE);
    }

    private void shimmertutup() {
        shimmerlayanan.stopShimmerAnimation();
        shimmerpickup.stopShimmerAnimation();
        shimmerdestination.stopShimmerAnimation();
        shimmerfitur.stopShimmerAnimation();
        shimmerdistance.stopShimmerAnimation();
        shimmerprice.stopShimmerAnimation();
        shimmerincash.stopShimmerAnimation();
        shimmerinwallet.stopShimmerAnimation();

        shimmerlayanan.setVisibility(View.GONE);
        shimmerpickup.setVisibility(View.GONE);
        shimmerdestination.setVisibility(View.GONE);
        shimmerfitur.setVisibility(View.GONE);
        shimmerdistance.setVisibility(View.GONE);
        shimmerprice.setVisibility(View.GONE);
        shimmerincash.setVisibility(View.GONE);
        shimmerinwallet.setVisibility(View.GONE);

        layanan.setVisibility(View.VISIBLE);
        layanandesk.setVisibility(View.VISIBLE);
        pickUpText.setVisibility(View.VISIBLE);
        destinationText.setVisibility(View.VISIBLE);
        distanceText.setVisibility(View.VISIBLE);
        fiturtext.setVisibility(View.VISIBLE);
        priceText.setVisibility(View.VISIBLE);
        incash.setVisibility(View.VISIBLE);
        inwallet.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void parsedata(TransModel request, final CustomerModel pelanggan) {
        rlprogress.setVisibility(View.GONE);

        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESUSER + pelanggan.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(photo);

        if (request.isPakaiWallet()) {
            Utility.currencyTXT(incash, "0", this);
            Utility.currencyTXT(inwallet, String.valueOf(request.getHarga()), this);
        } else {
            Utility.currencyTXT(inwallet, request.getKreditPromo(), this);
            Utility.currencyTXT(incash, request.getBiaya_akhir(), this);
        }

        if (!request.getRate().isEmpty()) {
            ratingView.setRating(Float.parseFloat(request.getRate()));
        }

        layanan.setText(pelanggan.getFullnama());
        pickUpText.setText(request.getAlamatAsal());
        destinationText.setText(request.getAlamatTujuan());
        if (request.getHome().equals("4")) {
            double totalbiaya = Double.parseDouble(request.getTotal_biaya());
            Utility.currencyTXT(priceText, String.valueOf(request.getHarga() + totalbiaya), this);
        } else {
            Utility.currencyTXT(priceText, String.valueOf(request.getHarga()), this);
        }

    }
}
