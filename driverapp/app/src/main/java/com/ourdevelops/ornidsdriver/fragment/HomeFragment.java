package com.ourdevelops.ornidsdriver.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;
import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.activity.TopupSaldoActivity;
import com.ourdevelops.ornidsdriver.activity.WalletActivity;
import com.ourdevelops.ornidsdriver.activity.WithdrawActivity;
import com.ourdevelops.ornidsdriver.constants.BaseApp;
import com.ourdevelops.ornidsdriver.json.GetOnRequestJson;
import com.ourdevelops.ornidsdriver.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsdriver.utils.api.service.DriverService;
import com.ourdevelops.ornidsdriver.item.BanklistItem;
import com.ourdevelops.ornidsdriver.json.ResponseJson;
import com.ourdevelops.ornidsdriver.models.BankModels;
import com.ourdevelops.ornidsdriver.models.User;
import com.ourdevelops.ornidsdriver.utils.SettingPreference;
import com.ourdevelops.ornidsdriver.utils.Utility;
import com.ourdevelops.ornidsdriver.utils.api.service.LocService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "Home";
    private Context context;
    private TextView balance, nameuser, nighttext;
    private FrameLayout rlprogress;
    private Button onoff, uangbelanja, autobid;
    private SettingPreference sp;
    private ArrayList<BankModels> mList;
    private String statusdriver, saldodriver;
    private MapView mapView;
    private LocationComponent locationComponent;
    @CameraMode.Mode
    private int cameraMode = CameraMode.TRACKING;
    @RenderMode.Mode
    private int renderMode = RenderMode.COMPASS;
    private Location lastLocation;
    boolean gps;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Mapbox.getInstance(requireActivity(), getString(R.string.mapbox_access_token));
        View getView = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        GPSStatus();
        LinearLayout topup = getView.findViewById(R.id.topup);
        LinearLayout withdraw = getView.findViewById(R.id.withdraw);
        LinearLayout detail = getView.findViewById(R.id.detail);
        balance = getView.findViewById(R.id.balance);
        autobid = getView.findViewById(R.id.autobid);
        uangbelanja = getView.findViewById(R.id.maks);
        onoff = getView.findViewById(R.id.onoff);
        nameuser = getView.findViewById(R.id.namapengguna);
        nighttext = getView.findViewById(R.id.nighttext);
        mList = new ArrayList<>();
        sp = new SettingPreference(context);
        rlprogress = getView.findViewById(R.id.rlprogress);
        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TopupSaldoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        mapView = getView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        User user = BaseApp.getInstance(context).getLoginUser();
        nameuser.setText(user.getFullnama());
        String[] parsedpagi = "04:00".split(":");
        String[] parsedsiang = "11:00".split(":");
        String[] parsedsore = "13:00".split(":");
        String[] parsedmalam = "18:00".split(":");

        int pagi = Integer.parseInt(parsedpagi[0]), menitPagi = Integer.parseInt(parsedpagi[1]);
        int siang = Integer.parseInt(parsedsiang[0]), menitSiang = Integer.parseInt(parsedsiang[1]);
        int sore = Integer.parseInt(parsedsore[0]), menitSore = Integer.parseInt(parsedsore[1]);
        int malam = Integer.parseInt(parsedmalam[0]), menitMalam = Integer.parseInt(parsedmalam[1]);
        int totalpagi = (pagi * 60) + menitPagi;
        int totalsiang = (siang * 60) + menitSiang;
        int totalsore = (sore * 60) + menitSore;
        int totalmalam = (malam * 60) + menitMalam;

        Calendar now = Calendar.getInstance();
        int totalMenitNow = (now.get(Calendar.HOUR_OF_DAY) * 60) + now.get(Calendar.MINUTE);

        if (totalMenitNow >= totalpagi && totalMenitNow <= totalsiang && totalMenitNow <= totalsore && totalMenitNow <= totalmalam) {
            nighttext.setText("Good Morning");
        } else if (totalMenitNow >= totalpagi && totalMenitNow >= totalsiang && totalMenitNow <= totalsore && totalMenitNow <= totalmalam) {
            nighttext.setText("Good Afternoon");
        } else if (totalMenitNow >= totalpagi && totalMenitNow >= totalsiang && totalMenitNow >= totalsore && totalMenitNow <= totalmalam) {
            nighttext.setText("Good Afternoon");
        } else {
            nighttext.setText("Good Night");
        }

        sp.updateNotif("OFF");
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WithdrawActivity.class);
                i.putExtra("type", "withdraw");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WalletActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        uangbelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();

            }
        });


        if (sp.getSetting()[0].equals("OFF")) {
            autobid.setSelected(false);
        } else {
            autobid.setSelected(true);
        }
        //rlprogress.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            statusdriver = bundle.getString("status");
            saldodriver = bundle.getString("balance");
        }
        if (statusdriver.equals("1")) {
            onstoploc("start");
            rlprogress.setVisibility(View.GONE);
            sp.updateKerja("ON");
            onoff.setSelected(true);
            onoff.setText("ON");
        } else if (statusdriver.equals("4")) {
            onstoploc("stop");
            cancelForeground();
            rlprogress.setVisibility(View.GONE);
            onoff.setSelected(false);
            onoff.setText("OFF");
            sp.updateKerja("OFF");
        }

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getturnon();
            }
        });


        if (sp.getSetting()[1].isEmpty()) {
            Utility.currencyTXT(uangbelanja, "1000", context);
        } else if (sp.getSetting()[1].equals("Unlimited")) {
            uangbelanja.setText(sp.getSetting()[1]);
        } else {
            Utility.currencyTXT(uangbelanja, sp.getSetting()[1], context);
        }

        List<BankModels> items = getPeopleData(context);
        mList.addAll(items);


        return getView;
    }

    private static List<BankModels> getPeopleData(Context ctx) {
        List<BankModels> items = new ArrayList<>();
        @SuppressLint("Recycle") TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.list_maximum);
        String[] name_arr = ctx.getResources().getStringArray(R.array.list_maximum);

        for (int i = 0; i < drw_arr.length(); i++) {
            BankModels obj = new BankModels();
            obj.setText(name_arr[i]);
            items.add(obj);
        }
        return items;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        if (gps) {
            mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull final Style style) {
                    UiSettings uiSettings = mapboxMap.getUiSettings();
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
                    locationComponent.setCameraMode(cameraMode);
                    locationComponent.setRenderMode(renderMode);
                    locationComponent.forceLocationUpdate(lastLocation);
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude()))
                            .zoom(15)
                            .tilt(20)
                            .build();
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);

                }
            });
        } else {
            GPSStatus();
        }
    }

    @Override
    public void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();

        autobid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getSetting()[0].equals("OFF")) {
                    sp.updateAutoBid("ON");
                    autobid.setSelected(true);
                } else {
                    sp.updateAutoBid("OFF");
                    autobid.setSelected(false);
                }
            }
        });
        Utility.currencyTXT(balance, saldodriver, context);
    }

    private void getturnon() {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetOnRequestJson param = new GetOnRequestJson();
        param.setId(loginUser.getId());
        if (statusdriver.equals("1")) {
            param.setOn(false);
        } else {
            param.setOn(true);
        }

        userService.turnon(param).enqueue(new Callback<ResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    rlprogress.setVisibility(View.GONE);
                    statusdriver = Objects.requireNonNull(response.body()).getData();
                    if (response.body().getData().equals("1")) {
                        statusdriver = "1";
                        onstoploc("start");
                        sp.updateKerja("ON");
                        onoff.setSelected(true);
                        onoff.setText("ON");
                    } else if (response.body().getData().equals("4")) {
                        statusdriver = "4";
                        onstoploc("stop");
                        sp.updateKerja("OFF");
                        onoff.setSelected(false);
                        onoff.setText("OFF");
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void onstoploc(String status) {
        Intent intent = new Intent(context, LocService.class);
        if(status.equals("stop")) {
            intent.setAction(LocService.ACTION_STOP_FOREGROUND_SERVICE);
        } else {
            intent.setAction(LocService.ACTION_START_FOREGROUND_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }


    private void dialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_bank);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        final ImageView close = dialog.findViewById(R.id.close);
        final RecyclerView list = dialog.findViewById(R.id.recycleview);


        list.setHasFixedSize(true);
        list.setLayoutManager(new GridLayoutManager(context, 1));

        BanklistItem bankItem = new BanklistItem(context, mList, R.layout.item_petunjuk, new BanklistItem.OnItemClickListener() {
            @Override
            public void onItemClick(BankModels item) {
                if (item.getText().equals("Unlimited")) {
                    uangbelanja.setText(item.getText());
                } else {
                    Utility.currencyTXT(uangbelanja, item.getText(), context);
                }
                sp.updateMaksimalBelanja(item.getText());
                dialog.dismiss();
            }
        });

        list.setAdapter(bankItem);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void GPSStatus() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {
        }

        try {
            network_enabled = Objects.requireNonNull(lm).isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ignored) {
        }

        if (!gps_enabled && !network_enabled) {
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


    public void cancelForeground() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(321);
    }
}
