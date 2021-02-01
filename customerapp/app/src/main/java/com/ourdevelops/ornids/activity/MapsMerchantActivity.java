package com.ourdevelops.ornids.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsManager;
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
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.ourdevelops.ornids.R;


import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;

public class MapsMerchantActivity extends AppCompatActivity implements OnMapReadyCallback {

    FloatingActionButton backbtn, currentLocation;
    String lat,lon,name;
    MapView mapView;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_maps_merchant);

        Intent intent = getIntent();
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");
        name = intent.getStringExtra("name");

        backbtn = findViewById(R.id.back_btn);
        currentLocation = findViewById(R.id.currentlocation);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        backbtn.setOnClickListener(v -> finish());


    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapboxMap.setStyle(Style.LIGHT, style -> {
                initDroppedMarker(style);
                UiSettings uiSettings = mapboxMap.getUiSettings();
                uiSettings.setAttributionEnabled(false);
                uiSettings.setLogoEnabled(false);
                uiSettings.setCompassEnabled(false);
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)))
                        .zoom(18)
                        .tilt(20)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                currentLocation.setOnClickListener(view -> mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000));

                if (style.getLayer("DROPPED_MARKER_LAYER_ID") != null) {
                    GeoJsonSource source = style.getSourceAs("dropped-marker-source-id");
                    if (source != null) {
                        source.setGeoJson(Point.fromLngLat(Double.parseDouble(lon), Double.parseDouble(lat)));
                    }
                }

            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("dropped-icon-image", BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_pikup_map));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id"));
        loadedMapStyle.addLayer(new SymbolLayer("DROPPED_MARKER_LAYER_ID",
                "dropped-marker-source-id").withProperties(
                iconImage("dropped-icon-image"),
                iconAllowOverlap(true),
                iconSize(2.0f),
                iconIgnorePlacement(true)
        ));

    }
}
