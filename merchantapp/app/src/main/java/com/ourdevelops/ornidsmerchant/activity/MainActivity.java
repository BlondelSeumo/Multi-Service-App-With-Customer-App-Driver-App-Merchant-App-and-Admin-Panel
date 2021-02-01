package com.ourdevelops.ornidsmerchant.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.constants.VersionChecker;
import com.ourdevelops.ornidsmerchant.fragment.HistoryFragment;
import com.ourdevelops.ornidsmerchant.fragment.HomeFragment;
import com.ourdevelops.ornidsmerchant.fragment.MenuFragment;
import com.ourdevelops.ornidsmerchant.fragment.MessageFragment;
import com.ourdevelops.ornidsmerchant.fragment.SettingsFragment;
import com.ourdevelops.ornidsmerchant.models.User;


import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {
    long mBackPressed;


    public static String apikey;

    LinearLayout mAdViewLayout;

    @SuppressLint("StaticFieldLeak")
    public static MainActivity mainActivity;
    private FragmentManager fragmentManager;
    BottomNavigationView navigation;
    int previousSelect = 0;


    public static MainActivity getInstance() {
        return mainActivity;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Menu menu = navigation.getMenu();
            menu.findItem(R.id.home).setIcon(R.drawable.ic_store_s);
            menu.findItem(R.id.history).setIcon(R.drawable.ic_transaksi);
            menu.findItem(R.id.chat).setIcon(R.drawable.ic_pesan);
            menu.findItem(R.id.menu).setIcon(R.drawable.ic_menu_s);
            menu.findItem(R.id.settings).setIcon(R.drawable.ic_profil);
            switch (item.getItemId()) {
                case R.id.home:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = getWindow().getDecorView().getSystemUiVisibility();
                        flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        getWindow().getDecorView().setSystemUiVisibility(flags);
                        getWindow().setStatusBarColor(getResources().getColor(R.color.colorgradient));
                    }
                    HomeFragment homeFragment = new HomeFragment();
                    navigationItemSelected(0);
                    item.setIcon(R.drawable.ic_store);
                    loadFrag(homeFragment, getString(R.string.menu_store), fragmentManager);
                    return true;

                case R.id.history:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    HistoryFragment historyFragment = new HistoryFragment();
                    navigationItemSelected(1);
                    item.setIcon(R.drawable.ic_transaksi_s);
                    loadFrag(historyFragment, getString(R.string.menu_history), fragmentManager);
                    return true;

                case R.id.chat:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    MessageFragment messageFragment = new MessageFragment();
                    navigationItemSelected(2);
                    item.setIcon(R.drawable.ic_pesan_s);
                    loadFrag(messageFragment, getString(R.string.menu_chat), fragmentManager);
                    return true;

                case R.id.menu:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    MenuFragment menuFragment = new MenuFragment();
                    navigationItemSelected(3);
                    item.setIcon(R.drawable.ic_menu);
                    loadFrag(menuFragment, getString(R.string.menu_menu), fragmentManager);
                    return true;

                case R.id.settings:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int flags = MainActivity.this.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                        MainActivity.this.getWindow().getDecorView().setSystemUiVisibility(flags);
                        MainActivity.this.getWindow().setStatusBarColor(Color.WHITE); // optional
                    }
                    SettingsFragment settingsFragment = new SettingsFragment();
                    navigationItemSelected(4);
                    item.setIcon(R.drawable.ic_profil_s);
                    loadFrag(settingsFragment, getString(R.string.menu_settings), fragmentManager);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdViewLayout = findViewById(R.id.adView);
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.home).setIcon(R.drawable.ic_store);
        HomeFragment homeFragment = new HomeFragment();
        loadFrag(homeFragment, getString(R.string.menu_store), fragmentManager);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        Constant.USERID = loginUser.getId();

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Constant.versionname = Objects.requireNonNull(packageInfo).versionName;


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Check_version();
    }

    public void Check_version(){
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }

    @Override
    public void onBackPressed() {
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                clickDone();

            }
        } else {
            super.onBackPressed();
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Container, f1, name);
        ft.commit();
    }

    public void navigationItemSelected(int position) {
        previousSelect = position;
    }




}
