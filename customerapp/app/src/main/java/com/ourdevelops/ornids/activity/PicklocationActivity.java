package com.ourdevelops.ornids.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ourdevelops.ornids.R;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by ourdevelops Team on 12/3/2020.
 */

public class PicklocationActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnCameraIdleListener,
        OnLocationClickListener, OnCameraTrackingChangedListener {

    public static final int LOCATION_PICKER_ID = 78;
    public static final String FORM_VIEW_INDICATOR = "FormToFill";

    public static final String LOCATION_NAME = "LocationName";
    public static final String LOCATION_LATLNG = "LocationLatLng";
    private Location lastLocation;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    TextView autoCompleteTextView;
    private LocationComponent locationComponent;
    TextView currentAddress;
    MapView mapView;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    Button selectLocation;
    FloatingActionButton backbutton, currentLocation;
    @CameraMode.Mode
    private int cameraMode = CameraMode.TRACKING;
    @RenderMode.Mode
    private int renderMode = RenderMode.GPS;

    private int formToFill;
    boolean gps;

    public PicklocationActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_picklocation);
        autoCompleteTextView = findViewById(R.id.locationPicker_autoCompleteText);
        currentAddress = findViewById(R.id.locationPicker_currentAddress);
        selectLocation = findViewById(R.id.locationPicker_destinationButton);
        backbutton = findViewById(R.id.back_btn);
        currentLocation = findViewById(R.id.currentlocation);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        Intent intent = getIntent();
        formToFill = intent.getIntExtra(FORM_VIEW_INDICATOR, -1);

        selectLocation.setOnClickListener(view -> selectLocation());

        backbutton.setOnClickListener(view -> finish());

        autoCompleteTextView.setOnClickListener(v -> setupAutocompleteTextView());


    }

    private void selectLocation() {
        LatLng selectedLocation = mapboxMap.getCameraPosition().target;
        String selectedAddress = currentAddress.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
        intent.putExtra(LOCATION_NAME, selectedAddress);
        intent.putExtra(LOCATION_LATLNG, selectedLocation);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setupAutocompleteTextView() {
        Intent intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.mapbox_access_token))
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(15)
                        .build(PlaceOptions.MODE_CARDS))
                .build(PicklocationActivity.this);
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
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
            PicklocationActivity.this.mapboxMap = mapboxMap;
            mapboxMap.setStyle(Style.LIGHT, style -> {
                UiSettings uiSettings = mapboxMap.getUiSettings();
                uiSettings.setAttributionEnabled(false);
                uiSettings.setLogoEnabled(false);
                uiSettings.setCompassEnabled(false);
                locationComponent = mapboxMap.getLocationComponent();
                locationComponent.activateLocationComponent(
                        LocationComponentActivationOptions
                                .builder(PicklocationActivity.this, style)
                                .useDefaultLocationEngine(true)
                                .locationEngineRequest(new LocationEngineRequest.Builder(750)
                                        .setFastestInterval(750)
                                        .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                                        .build())
                                .build());

                locationComponent.setLocationComponentEnabled(true);
                locationComponent.addOnLocationClickListener(PicklocationActivity.this);
                locationComponent.addOnCameraTrackingChangedListener(PicklocationActivity.this);
                locationComponent.setCameraMode(cameraMode);
                setRendererMode(renderMode);
                locationComponent.forceLocationUpdate(lastLocation);
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(Objects.requireNonNull(locationComponent.getLastKnownLocation()).getLatitude(), locationComponent.getLastKnownLocation().getLongitude()))
                        .zoom(15)
                        .tilt(20)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));

                mapboxMap.addOnCameraIdleListener(PicklocationActivity.this);
            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void setRendererMode(@RenderMode.Mode int mode) {
        renderMode = mode;
    }

    @Override
    public void onCameraIdle() {
        if (mapboxMap != null) {
            MapboxGeocoding reverseGeocode = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(mapboxMap.getCameraPosition().target.getLongitude(), mapboxMap.getCameraPosition().target.getLatitude()))
                    .build();
            reverseGeocode.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(@NonNull Call<GeocodingResponse> call, @NonNull Response<GeocodingResponse> response) {
                    List<CarmenFeature> results = Objects.requireNonNull(response.body()).features();
                    if (results.size() > 0) {
                        CarmenFeature feature = results.get(0);
                        currentAddress.setText(feature.placeName());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GeocodingResponse> call, @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

        }
    }

    @Override
    public void onCameraTrackingDismissed() {

    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
        this.cameraMode = currentMode;
    }

    @Override
    public void onLocationComponentClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            if (mapboxMap != null) {
                currentAddress.setText(selectedCarmenFeature.placeName());
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    String geojsonSourceLayerId = "geojsonSourceLayerId";
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) Objects.requireNonNull(selectedCarmenFeature.geometry())).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(15)
                                    .build()), 4000);
                }
            }
        }
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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



}
