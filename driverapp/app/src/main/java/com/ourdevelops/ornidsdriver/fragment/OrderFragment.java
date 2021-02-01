package com.ourdevelops.ornidsdriver.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationCameraTransitionListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
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
import com.ourdevelops.ornidsdriver.activity.ChatActivity;
import com.ourdevelops.ornidsdriver.activity.MainActivity;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.item.ItemPesananItem;
import com.ourdevelops.ornidsdriver.json.AcceptRequestJson;
import com.ourdevelops.ornidsdriver.json.AcceptResponseJson;
import com.ourdevelops.ornidsdriver.json.ResponseJson;
import com.ourdevelops.ornidsdriver.json.VerifyRequestJson;
import com.ourdevelops.ornidsdriver.models.CustomerModel;
import com.ourdevelops.ornidsdriver.models.TransModel;
import com.ourdevelops.ornidsdriver.utils.api.FCMHelper;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.ourdevelops.ornidsdriver.json.DetailRequestJson;
import com.ourdevelops.ornidsdriver.json.DetailTransResponseJson;
import com.ourdevelops.ornidsdriver.json.fcm.FCMMessage;
import com.ourdevelops.ornidsdriver.models.OrderFCM;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.Log;
import com.ourdevelops.ornidsdriver.utils.Utility;
import com.ourdevelops.ornidsdriver.utils.PicassoTrustAll;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
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

public class OrderFragment extends Fragment implements OnMapReadyCallback, OnCameraTrackingChangedListener {

    private Context context;
    private static final int REQUEST_PERMISSION_CALL = 992;
    private String idtrans, idpelanggan, response, service, onsubmit;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;
    @BindView(R.id.layanan)
    TextView layanan;
    @BindView(R.id.layanandes)
    TextView layanandesk;
    @BindView(R.id.verifycation)
    TextView verify;
    @BindView(R.id.namamerchant)
    TextView namamerchant;
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
    @BindView(R.id.rlprogress)
    RelativeLayout rlprogress;
    @BindView(R.id.textprogress)
    TextView textprogress;
    @BindView(R.id.cost)
    TextView cost;
    @BindView(R.id.deliveryfee)
    TextView deliveryfee;
    @BindView(R.id.phonenumber)
    ImageView phone;
    @BindView(R.id.currentlocation)
    FloatingActionButton currentLocation;

    @BindView(R.id.chat)
    ImageView chat;
    @BindView(R.id.phonemerchant)
    ImageView phonemerchant;
    @BindView(R.id.chatmerchant)
    ImageView chatmerchant;
    @BindView(R.id.lldestination)
    LinearLayout lldestination;
    @BindView(R.id.orderdetail)
    LinearLayout llorderdetail;
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
    @BindView(R.id.order)
    Button submit;
    @BindView(R.id.merchantdetail)
    LinearLayout llmerchantdetail;
    @BindView(R.id.merchantinfo)
    LinearLayout llmerchantinfo;
    @BindView(R.id.llbutton)
    LinearLayout llbutton;
    @BindView(R.id.merchantnear)
    RecyclerView rvmerchantnear;
    @BindView(R.id.shimmerincash)
    ShimmerFrameLayout shimmerincash;
    @BindView(R.id.shimmerinwallet)
    ShimmerFrameLayout shimmerinwallet;
    @BindView(R.id.incash)
    TextView incash;
    @BindView(R.id.inwallet)
    TextView inwallet;
    private ItemPesananItem itemPesananItem;
    private TextView totaltext;
    private String type;
    MapView mapView;
    boolean gps;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Mapbox.getInstance(requireActivity(), getString(R.string.mapbox_access_token));
        View getView = Objects.requireNonNull(inflater).inflate(R.layout.activity_detail_order, container, false);
        context = getContext();
        GPSStatus();
        ButterKnife.bind(this, getView);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        totaltext = getView.findViewById(R.id.totaltext);
        service = "0";
        type = "0";
        Bundle bundle = getArguments();
        if (bundle != null) {
            idpelanggan = bundle.getString("customer_id");
            idtrans = bundle.getString("transaction_id");
            response = bundle.getString("response");
        }
        shimmerload();
        switch (response) {
            case "2":
                onsubmit = "2";
                llchat.setVisibility(View.VISIBLE);
                break;
            case "3":
                onsubmit = "3";
                llchat.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                verify.setVisibility(View.GONE);
                submit.setText("finish");
                break;
            case "4":
                llchat.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                layanandesk.setText(getString(R.string.notification_finish));
                break;
            case "5":
                llchat.setVisibility(View.GONE);
                layanandesk.setText(getString(R.string.notification_cancel));
                break;
        }
        mapView = getView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        rlprogress.setVisibility(View.GONE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));
        return getView;
    }

    private Point pickup, destination;

    private void getData(final String idtrans, final String idpelanggan, Style style, MapboxMap mapboxMap) {
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService services = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);
        services.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {
                    shimmertutup();
                    Log.e("", String.valueOf(Objects.requireNonNull(responsedata.body()).getData().get(0)));
                    final TransModel transaksi = responsedata.body().getData().get(0);
                    final CustomerModel pelanggan = responsedata.body().getPelanggan().get(0);
                    type = transaksi.getHome();

                    if (transaksi.isPakaiWallet()) {
                        Utility.currencyTXT(incash, "0", requireActivity());
                        Utility.currencyTXT(inwallet, String.valueOf(transaksi.getHarga()), requireActivity());
                    } else {
                        Utility.currencyTXT(inwallet, transaksi.getKreditPromo(), requireActivity());
                        Utility.currencyTXT(incash, transaksi.getBiaya_akhir(), requireActivity());
                    }
                    String formatdistance = String.format(Locale.US, "%.1f", transaksi.getJarak());
                    distanceText.setText(formatdistance);
                    fiturtext.setText(transaksi.getEstimasi());

                    if (onsubmit.equals("2")) {
                        destination = Point.fromLngLat(transaksi.getStartLongitude(), transaksi.getStartLatitude());
                        pickup = Point.fromLngLat(Objects.requireNonNull(locationComponent.getLastKnownLocation()).getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
                        initSources(style, "origin", "ORI_LINE_SOURCE_ID", "ORI_SOURCE_ID");
                        initLayers(style, "ORI_LINE_SOURCE_ID", "ORI_SOURCE_ID", "O_ROUTE_LAYER_ID", "OICON_LAYER_ID");
                        getRoute(mapboxMap, pickup, destination, "origin", "ORI_LINE_SOURCE_ID", "ORI_SOURCE_ID");
                        if (transaksi.getHome().equals("4")) {
                            layanandesk.setText("Go buy orders");
                            submit.setText("deliver orders");
                            verify.setVisibility(View.VISIBLE);
                        } else {
                            layanandesk.setText(getString(R.string.notification_accept));
                        }
                        submit.setVisibility(View.VISIBLE);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (transaksi.getHome().equals("4")) {

                                    if (verify.getText().toString().isEmpty()) {
                                        Toast.makeText(context, "Please enter verify code!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                                        String finalDate = timeFormat.format(transaksi.getWaktuOrder());
                                        rlprogress.setVisibility(View.VISIBLE);
                                        verify(verify.getText().toString(), pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, finalDate, style, mapboxMap);
                                    }
                                } else {
                                    start(pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, String.valueOf(transaksi.getWaktuOrder()), style, mapboxMap);
                                }

                            }
                        });
                    } else if (onsubmit.equals("3")) {
                        style.removeLayer("O_ROUTE_LAYER_ID");
                        style.removeSource("ORI_LINE_SOURCE_ID");
                        style.removeImage("ORIGIN_ICON_ID");
                        destination = Point.fromLngLat(transaksi.getEndLongitude(), transaksi.getEndLatitude());
                        pickup = Point.fromLngLat(Objects.requireNonNull(locationComponent.getLastKnownLocation()).getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
                        initSources(style, "destination", "DEST_LINE_SOURCE_ID", "DEST_SOURCE_ID");
                        initLayers(style, "DEST_LINE_SOURCE_ID", "DEST_SOURCE_ID", "DROUTE_LAYER_ID", "DICON_LAYER_ID");
                        getRoute(mapboxMap, pickup, destination, "destination", "DEST_LINE_SOURCE_ID", "DEST_SOURCE_ID");
                        if (transaksi.getHome().equals("4")) {
                            layanandesk.setText("deliver orders");
                        } else {
                            layanandesk.setText(getString(R.string.notification_start));
                        }

                        verify.setVisibility(View.GONE);
                        submit.setText("Finish");
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish(pelanggan, transaksi.merchant_token);
                            }
                        });
                    }

                    service = transaksi.getOrderFitur();

                    if (transaksi.getHome().equals("3")) {
                        lldestination.setVisibility(View.GONE);
                        lldistance.setVisibility(View.GONE);
                        fiturtext.setText(transaksi.getEstimasi());
                    } else if (transaksi.getHome().equals("4")) {
                        llorderdetail.setVisibility(View.VISIBLE);
                        llmerchantdetail.setVisibility(View.VISIBLE);
                        llmerchantinfo.setVisibility(View.VISIBLE);
                        Utility.currencyTXT(deliveryfee, String.valueOf(transaksi.getHarga()), context);
                        Utility.currencyTXT(cost, String.valueOf(transaksi.getTotal_biaya()), context);
                        namamerchant.setText(transaksi.getNama_merchant());
                        itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan);
                        rvmerchantnear.setAdapter(itemPesananItem);

                        phonemerchant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Customer");
                                alertDialogBuilder.setMessage("You want to call Merchant (+" + transaksi.getTeleponmerchant() + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:+" + transaksi.getTeleponmerchant()));
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

                        chatmerchant.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, ChatActivity.class);
                                intent.putExtra("senderid", loginUser.getId());
                                intent.putExtra("receiverid", transaksi.getId_merchant());
                                intent.putExtra("tokendriver", loginUser.getToken());
                                intent.putExtra("tokenku", transaksi.getToken_merchant());
                                intent.putExtra("name", transaksi.getNama_merchant());
                                intent.putExtra("pic", Constant.IMAGESMERCHANT + transaksi.getFoto_merchant());
                                startActivity(intent);
                            }
                        });

                    } else if (transaksi.getHome().equals("2")) {
                        lldetailsend.setVisibility(View.VISIBLE);
                        produk.setText(transaksi.getNamaBarang());
                        sendername.setText(transaksi.namaPengirim);
                        receivername.setText(transaksi.namaPenerima);

                        senderphone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Driver");
                                alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPengirim() + "(" + transaksi.teleponPengirim + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Driver");
                                alertDialogBuilder.setMessage("You want to call " + transaksi.getNamaPenerima() + "(" + transaksi.teleponPenerima + ")?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
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

                    }

                    parsedata(transaksi, pelanggan);


                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });


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

    private void parsedata(TransModel request, final CustomerModel pelanggan) {
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        rlprogress.setVisibility(View.GONE);

        PicassoTrustAll.getInstance(context)
                .load(Constant.IMAGESUSER + pelanggan.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(photo);


        layanan.setText(pelanggan.getFullnama());
        pickUpText.setText(request.getAlamatAsal());
        destinationText.setText(request.getAlamatTujuan());
        if (type.equals("4")) {
            double totalbiaya = Double.parseDouble(request.getTotal_biaya());
            Utility.currencyTXT(priceText, String.valueOf(request.getHarga() + totalbiaya), context);
        } else {
            Utility.currencyTXT(priceText, String.valueOf(request.getHarga()), context);
        }

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogStyle);
                alertDialogBuilder.setTitle("Call Customer");
                alertDialogBuilder.setMessage("You want to call Costumer (+" + pelanggan.getNoTelepon() + ")?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }

                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:+" + pelanggan.getNoTelepon()));
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

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("senderid", loginUser.getId());
                intent.putExtra("receiverid", pelanggan.getId());
                intent.putExtra("tokendriver", loginUser.getToken());
                intent.putExtra("tokenku", pelanggan.getToken());
                intent.putExtra("name", pelanggan.getFullnama());
                intent.putExtra("pic", Constant.IMAGESUSER + pelanggan.getFoto());
                startActivity(intent);
            }
        });
    }


    private Location location;
    @CameraMode.Mode
    private int cameraMode = CameraMode.TRACKING_COMPASS;

    @RenderMode.Mode
    private int renderMode = RenderMode.NORMAL;

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        if(gps) {
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

                    locationComponent = mapboxMap.getLocationComponent();
                    locationComponent.activateLocationComponent(
                            LocationComponentActivationOptions
                                    .builder(context, style)
                                    .useDefaultLocationEngine(true)
                                    .locationEngineRequest(new LocationEngineRequest.Builder(750)
                                            .setFastestInterval(750)
                                            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                                            .build())
                                    .build());
                    locationComponent.setLocationComponentEnabled(true);
                    setCameraTrackingMode(CameraMode.TRACKING_COMPASS, mapboxMap);
                    locationComponent.setCameraMode(cameraMode);
                    locationComponent.setRenderMode(renderMode);
                    locationComponent.forceLocationUpdate(location);
                    currentLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setCameraTrackingMode(CameraMode.TRACKING_COMPASS, mapboxMap);
                        }
                    });
                    getData(idtrans, idpelanggan, style, mapboxMap);

                }
            });
        } else {
            GPSStatus();
        }
    }


    private void initSources(@NonNull Style loadedMapStyle, String iconid, String lineid, String iconsourceid) {

        loadedMapStyle.addSource(new GeoJsonSource(lineid, new GeoJsonOptions().withLineMetrics(true)));
        loadedMapStyle.addSource(new GeoJsonSource(iconsourceid, getOriginAndDestinationFeatureCollection(iconid)));

    }

    private void initLayers(@NonNull Style loadedMapStyle, String lineid, String iconsourceid, String routeid, String iconlayerid) {
        loadedMapStyle.addLayer(new LineLayer(routeid, lineid).withProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(6f),
                lineGradient(interpolate(
                        linear(), lineProgress(),
                        stop(0f, color(context.getResources().getColor(R.color.colorPrimary))),
                        stop(1f, color(context.getResources().getColor(R.color.colorgradient))
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

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination, String iconid, String lineid, String iconsourceid) {
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
                                    originDestinationPointGeoJsonSource.setGeoJson(getOriginAndDestinationFeatureCollection(iconid));
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

    private FeatureCollection getOriginAndDestinationFeatureCollection(String iconid) {
        Feature destinationFeature = Feature.fromGeometry(destination);
        destinationFeature.addStringProperty("originDestination", iconid);
        return FeatureCollection.fromFeatures(new Feature[]{destinationFeature});
    }


    private void start(final CustomerModel pelanggan, final String tokenmerchant, final String idtransmerchant, final String waktuorder, Style style, MapboxMap mapboxMap) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.startrequest(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("berhasil")) {
                        rlprogress.setVisibility(View.GONE);
                        onsubmit = "3";
                        getData(idtrans, idpelanggan, style, mapboxMap);
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.driver_id = loginUser.getId();
                        orderfcm.transaction_id = idtrans;
                        orderfcm.response = "3";
                        if (type.equals("4")) {
                            orderfcm.customer_id = idpelanggan;
                            orderfcm.invoice = "INV-" + idtrans + idtransmerchant;
                            orderfcm.ordertime = waktuorder;
                            orderfcm.desc = "driver delivers the order";
                            sendMessageToDriver(tokenmerchant, orderfcm);
                        } else {
                            orderfcm.desc = getString(R.string.notification_start);
                        }
                        sendMessageToDriver(pelanggan.getToken(), orderfcm);
                    } else {
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(context, "Order is no longer available!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error Connection!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(View.GONE);
            }
        });
    }

    private void verify(String verificode, final CustomerModel pelanggan, final String tokenmerchant, final String idtransmerchant, final String waktuorder, Style style, MapboxMap mapboxMap) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        VerifyRequestJson param = new VerifyRequestJson();
        param.setId(loginUser.getNoTelepon());
        param.setIdtrans(idtrans);
        param.setVerifycode(verificode);
        userService.verifycode(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull final Response<ResponseJson> response) {
                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {

                        start(pelanggan, tokenmerchant, idtransmerchant, waktuorder, style, mapboxMap);
                    } else {
                        rlprogress.setVisibility(View.GONE);
                        Toast.makeText(context, "verifycode not correct!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error Connection!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(View.GONE);
            }
        });
    }

    private void finish(final CustomerModel pelanggan, final String tokenmerchant) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.finishrequest(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("berhasil")) {
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.driver_id = loginUser.getId();
                        orderfcm.transaction_id = idtrans;
                        orderfcm.response = "4";
                        orderfcm.desc = getString(R.string.notification_finish);
                        if (type.equals("4")) {
                            sendMessageToDriver(tokenmerchant, orderfcm);
                            sendMessageToDriver(pelanggan.getToken(), orderfcm);
                        } else {
                            sendMessageToDriver(pelanggan.getToken(), orderfcm);
                        }

                    } else {
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(context, "Order is no longer available!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error Connection!", Toast.LENGTH_SHORT).show();
                rlprogress.setVisibility(View.GONE);
            }
        });
    }

    private void sendMessageToDriver(final String regIDTujuan, final OrderFCM response) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(response);

        FCMHelper.sendMessage(Constant.FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                android.util.Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    private LocationComponent locationComponent;

    private void setCameraTrackingMode(@CameraMode.Mode int mode, MapboxMap mapboxMap) {
        locationComponent.setCameraMode(mode, new OnLocationCameraTransitionListener() {
            @Override
            public void onLocationCameraTransitionFinished(@CameraMode.Mode int cameraMode) {
                locationComponent.zoomWhileTracking(20, 750, new MapboxMap.CancelableCallback() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onFinish() {
                        locationComponent.tiltWhileTracking(60);
                    }
                });

            }

            @Override
            public void onLocationCameraTransitionCanceled(@CameraMode.Mode int cameraMode) {
            }
        });
    }


    @Override
    public void onCameraTrackingDismissed() {
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
        this.cameraMode = currentMode;

    }

    public void GPSStatus() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ignored) {}

        try {
            network_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ignored) {}

        if(!gps_enabled && !network_enabled) {
            gps = false;
            Toast.makeText(context, "On Location in High Accuracy", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2);
        } else {
            gps = true;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            GPSStatus();
        }
    }
}
