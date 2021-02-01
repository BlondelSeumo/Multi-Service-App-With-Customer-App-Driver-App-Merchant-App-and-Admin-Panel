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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
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
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.json.GetNearRideCarRequestJson;
import com.ourdevelops.ornids.json.GetNearRideCarResponseJson;
import com.ourdevelops.ornids.models.DriverModel;
import com.ourdevelops.ornids.models.ServiceModel;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.PicassoTrustAll;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconRotate;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

/**
 * Created by Ourdevelops Team on 10/26/2019.
 */

public class SendActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String FITUR_KEY = "FiturKey";
    String ICONFITUR;

    @BindView(R.id.pickUpContainer)
    LinearLayout setPickUpContainer;
    @BindView(R.id.destinationContainer)
    LinearLayout setDestinationContainer;
    @BindView(R.id.pickUpButton)
    Button setPickUpButton;
    @BindView(R.id.destinationButton)
    Button setDestinationButton;
    @BindView(R.id.pickUpText)
    TextView pickUpText;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomsheet;
    @BindView(R.id.destinationText)
    TextView destinationText;
    @BindView(R.id.order)
    Button orderButton;
    @BindView(R.id.image)
    ImageView icon;
    @BindView(R.id.layanan)
    TextView layanan;
    @BindView(R.id.layanandes)
    TextView layanandesk;
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

    private ArrayList<DriverModel> driverAvailable;
    private Realm realm;
    private ServiceModel designedFitur;
    private double distance;
    String service, getbiaya, biayaminimum, biayaakhir;
    private String timeDistance,icondrver;
    MapView mapView;
    int fiturId;
    long maksimum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);
        driverAvailable = new ArrayList<>();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        setPickUpContainer.setVisibility(View.VISIBLE);
        setDestinationContainer.setVisibility(View.GONE);
        backbtn.setOnClickListener(view -> finish());

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        pickUpText.setOnClickListener(v -> {
            setPickUpContainer.setVisibility(View.VISIBLE);
            setDestinationContainer.setVisibility(View.GONE);
            openAutocompleteActivity(1);
        });

        destinationText.setOnClickListener(v -> {
            setDestinationContainer.setVisibility(View.VISIBLE);
            setPickUpContainer.setVisibility(View.GONE);
            openAutocompleteActivity(2);
        });



        driverAvailable = new ArrayList<>();



        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        fiturId = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");
        if (fiturId != -1)
            designedFitur = realm.where(ServiceModel.class).equalTo("idFitur", fiturId).findFirst();

        service = String.valueOf(Objects.requireNonNull(designedFitur).getIdFitur());
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        icondrver = designedFitur.getIcon_driver();
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());


        PicassoTrustAll.getInstance(this)
                .load(Constant.IMAGESFITUR + ICONFITUR)
                .placeholder(R.drawable.logo)
                .resize(100, 100)
                .into(icon);

        layanan.setText(designedFitur.getFitur());
        layanandesk.setText(designedFitur.getKeterangan());
        orderButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button));
    }

    private void openAutocompleteActivity(int request_code) {
        Intent intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.mapbox_access_token))
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(15)
                        .build(PlaceOptions.MODE_CARDS))
                .build(SendActivity.this);
        startActivityForResult(intent, request_code);
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
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
                if (mapboxMap != null) {
                    destinationText.setText(selectedCarmenFeature.placeName());
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
                        onDestination(style, mapboxMap, centerPos);
                    }
                }
            }
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    LocationComponent locationComponent;
    MapboxMap mapboxMap;

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
                                    .builder(SendActivity.this, style)
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
                    initDroppedMarkerdestination(style);
                    initDottedLineSourceAndLayer(style);
                    currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));
                    setPickUpButton.setOnClickListener(view -> {
                        LatLng centerPos = mapboxMap.getCameraPosition().target;
                        onPickUp(style, mapboxMap, centerPos);
                    });

                    setDestinationButton.setOnClickListener(view -> {
                        LatLng centerPos = mapboxMap.getCameraPosition().target;
                        onDestination(style, mapboxMap, centerPos);
                    });

                });
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }

    Point pickup,destination;
    LatLng pickUpLatLang, destinationLatLang;

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
        setDestinationContainer.setVisibility(View.VISIBLE);
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

    private void onDestination(Style style, MapboxMap mapboxMap, LatLng centerPos) {

        if (style.getLayer("DROPPED_MARKER_LAYER_ID_DEST") != null) {
            GeoJsonSource source = style.getSourceAs("dropped-marker-source-id-dest");
            if (source != null) {
                source.setGeoJson(Point.fromLngLat(centerPos.getLongitude(), centerPos.getLatitude()));
            }
            Layer destMarker = style.getLayer("DROPPED_MARKER_LAYER_ID_DEST");
            if (destMarker != null) {
                destMarker.setProperties(visibility(Property.VISIBLE));
            }
        }
        destinationLatLang = new LatLng(centerPos.getLatitude(), centerPos.getLongitude());
        setDestinationContainer.setVisibility(View.GONE);
        if (pickUpText.getText().toString().isEmpty()) {
            setPickUpContainer.setVisibility(View.VISIBLE);
        } else {
            setPickUpContainer.setVisibility(View.GONE);
        }
        destination = Point.fromLngLat(centerPos.getLongitude(), centerPos.getLatitude());
        getaddress(destination, mapboxMap, destinationText);
    }

    private void initDottedLineSourceAndLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource("SOURCE_ID"));
        loadedMapStyle.addLayerBelow(
                new LineLayer(
                        "DIRECTIONS_LAYER_ID", "SOURCE_ID").withProperties(
                        lineCap(Property.LINE_CAP_ROUND),
                        lineJoin(Property.LINE_JOIN_ROUND),
                        lineWidth(6f),
                        lineColor(getResources().getColor(R.color.colorPrimary))
                ), "LAYER_BELOW_ID");
    }

    private void initDroppedMarkerdestination(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("dropped-icon-image-dest", BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_destination_map));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id-dest"));
        loadedMapStyle.addLayer(new SymbolLayer("DROPPED_MARKER_LAYER_ID_DEST",
                "dropped-marker-source-id-dest").withProperties(
                iconImage("dropped-icon-image-dest"),
                iconAllowOverlap(true),
                visibility(Property.NONE),
                iconSize(2.0f),
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
                            mapboxMap.getStyle(style -> {
                                textView.setText(feature.placeName());
                                getRoute(mapboxMap);
                            });

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

    private void getRoute(MapboxMap mapboxMap) {
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
                    DirectionsRoute currentroute = response.body().routes().get(0);
                    drawNavigationPolylineRoute(currentroute, mapboxMap);
                    rlprogress.setVisibility(View.GONE);
                    setDestinationContainer.setVisibility(View.GONE);
                    setPickUpContainer.setVisibility(View.GONE);
                    String format = String.format(Locale.US, "%.0f", currentroute.distance() / 1000f);
                    long dist = Long.parseLong(format);
                    if (dist < maksimum) {
                        rlprogress.setVisibility(View.GONE);
                        updateDistance(currentroute.distance());
                        long minutes = (long) ((currentroute.duration() / 60));
                        timeDistance = minutes+" mins";
                    } else {
                        orderButton.setEnabled(false);
                        orderButton.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button));
                        setDestinationContainer.setVisibility(View.VISIBLE);
                        rlprogress.setVisibility(View.GONE);
                        notif("destination too far away!");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<DirectionsResponse> call, @NonNull Throwable throwable) {
                    Timber.d("Error: %s", throwable.getMessage());

                }
            });
        }
    }

    private void drawNavigationPolylineRoute(final DirectionsRoute route, MapboxMap mapboxMap) {
        if (mapboxMap != null) {
            mapboxMap.getStyle(style -> {
                List<Feature> directionsRouteFeatureList = new ArrayList<>();
                LineString lineString = LineString.fromPolyline(Objects.requireNonNull(route.geometry()), PRECISION_6);
                List<Point> coordinates = lineString.coordinates();
                for (int i = 0; i < coordinates.size(); i++) {
                    directionsRouteFeatureList.add(Feature.fromGeometry(LineString.fromLngLats(coordinates)));
                }
                FeatureCollection dashedLineDirectionsFeatureCollection = FeatureCollection.fromFeatures(directionsRouteFeatureList);
                GeoJsonSource source = style.getSourceAs("SOURCE_ID");
                if (source != null) {
                    source.setGeoJson(dashedLineDirectionsFeatureCollection);
                }

            });
        }
    }


    private void updateDistance(Double distance) {
        orderButton.setEnabled(true);
        orderButton.setBackground(getResources().getDrawable(R.drawable.button_round_1));
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        setDestinationContainer.setVisibility(View.GONE);
        setPickUpContainer.setVisibility(View.GONE);
        orderButton.setVisibility(View.VISIBLE);
        String format = String.format(Locale.US, "%.0f", distance / 1000f);
        this.distance = Double.parseDouble(format);

        orderButton.setOnClickListener(v -> {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                onNextButtonClick();
            }
        });


    }

    private void onNextButtonClick() {
        Intent intent = new Intent(this, SendDetailActivity.class);
        intent.putExtra("distance", distance);//double
        intent.putExtra("price", getbiaya);//long
        intent.putExtra("pickup_latlng", pickUpLatLang);
        intent.putExtra("destination_latlng", destinationLatLang);
        intent.putExtra("pickup", pickUpText.getText().toString());
        intent.putExtra("destination", destinationText.getText().toString());
        intent.putExtra("driver", driverAvailable);
        intent.putExtra("minimum_cost", biayaminimum);
        intent.putExtra("time_distance", timeDistance);
        intent.putExtra("driver", driverAvailable);
        intent.putExtra("icon", ICONFITUR);
        intent.putExtra("layanan", layanan.getText().toString());
        intent.putExtra("layanandesk", layanandesk.getText().toString());
        intent.putExtra(FITUR_KEY, fiturId);
        startActivity(intent);
        finish();
    }

    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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