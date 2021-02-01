package com.ourdevelops.ornids.fragment;


import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ourdevelops.ornids.BuildConfig;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.activity.ChangepassActivity;
import com.ourdevelops.ornids.activity.EditProfileActivity;
import com.ourdevelops.ornids.activity.IntroActivity;
import com.ourdevelops.ornids.activity.PrivacyActivity;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.PicassoTrustAll;
import com.ourdevelops.ornids.utils.SettingPreference;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import io.realm.Realm;

import static android.content.Context.NOTIFICATION_SERVICE;


public class ProfileFragment extends Fragment {
    private Context context;
    private ImageView photo;
    private TextView nama, email;
    private SettingPreference sp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        photo = getView.findViewById(R.id.userphoto);
        nama = getView.findViewById(R.id.username);
        email = getView.findViewById(R.id.useremail);
        LinearLayout aboutus = getView.findViewById(R.id.llaboutus);
        LinearLayout privacy = getView.findViewById(R.id.llprivacypolicy);
        LinearLayout shareapp = getView.findViewById(R.id.llshareapp);
        LinearLayout rateapp = getView.findViewById(R.id.llrateapp);
        LinearLayout editprofile = getView.findViewById(R.id.lleditprofile);
        LinearLayout logout = getView.findViewById(R.id.lllogout);
        LinearLayout llpassword = getView.findViewById(R.id.llpassword);
        sp = new SettingPreference(context);


        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PrivacyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutus();
            }
        });

        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = "Let me recommend you this application";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));

            }
        });

        rateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + Objects.requireNonNull(getActivity()).getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                }
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        llpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChangepassActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDone();
            }
        });


        return getView;
    }

    private void clickDone() {
        new AlertDialog.Builder(context, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Realm realm = BaseApp.getInstance(context).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        removeNotif();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(getContext(), IntroActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
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

    private void aboutus() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_aboutus);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageView close = dialog.findViewById(R.id.bt_close);
        final LinearLayout email = dialog.findViewById(R.id.email);
        final LinearLayout phone = dialog.findViewById(R.id.phone);
        final LinearLayout website = dialog.findViewById(R.id.website);
        final WebView about = dialog.findViewById(R.id.aboutus);

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText;
        htmlText = sp.getSetting()[1];
        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/NeoSans_Pro_Regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        about.loadDataWithBaseURL(null, text, mimeType, encoding, null);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int REQUEST_PHONE_CALL = 1;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + (sp.getSetting()[3])));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(callIntent);
                    }
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {(sp.getSetting()[2])};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "halo");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "email" + "\n");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (sp.getSetting()[4]);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onResume() {
        super.onResume();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        nama.setText(loginUser.getFullnama());
        email.setText(loginUser.getEmail());

        PicassoTrustAll.getInstance(context)
                .load(Constant.IMAGESUSER + loginUser.getFotopelanggan())
                .placeholder(R.drawable.image_placeholder)
                .resize(250, 250)
                .into(photo);

    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }


}
