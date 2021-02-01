package com.ourdevelops.ornids.constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.utils.Log;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Objects;


/**
 * Created by ourdevelops on 12/4/2018.
 */


public class VersionChecker extends AsyncTask<String, String, String> {

    private String newVersion;

    @SuppressLint("StaticFieldLeak")
    private Activity context;

    public VersionChecker(Activity context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {

        try {
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select(".IQ1z0d .htlgb")
                    .get(7)
                    .ownText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVersion;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (newVersion != null) {
            int latestVersion = Integer.parseInt(newVersion.replace(".", ""));
            int versionCode = Integer.parseInt(Objects.requireNonNull(pInfo).versionName.replace(".", ""));
            Log.e("", newVersion);
            if (versionCode < latestVersion) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.DialogStyle);
                alert.setTitle(R.string.app_name)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Please update" + " " + context.getString(R.string.app_name) + " " + "app. you have an old version.")
                        .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                                context.finish();
                            }
                        });

                alert.setCancelable(false);
                alert.show();
            }
        }
    }
}