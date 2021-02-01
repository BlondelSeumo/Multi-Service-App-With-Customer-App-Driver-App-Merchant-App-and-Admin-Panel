package com.ourdevelops.ornids.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.request.DownloadRequest;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.utils.PicassoTrustAll;
import com.squareup.picasso.Callback;

import java.io.File;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FullImageFragment extends Fragment {

    private Context context;

    private String chat_id;
    private ProgressBar progressBar;

    private ProgressDialog progressDialog;
    private DownloadRequest prDownloader;

    private File fullpath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_fullimage, container, false);
        context = getContext();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        String imageUrl = Objects.requireNonNull(getArguments()).getString("image_url");
        chat_id = getArguments().getString("chat_id");
        Button savebtn2 = getView.findViewById(R.id.savebtn2);

        ImageView closeGallery = getView.findViewById(R.id.close_gallery);
        closeGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(context, R.style.DialogStyle);
        progressDialog.setMessage("Please Wait");

        PRDownloader.initialize(getActivity().getApplicationContext());

        fullpath = new File(Environment.getExternalStorageDirectory() + "/ourride/" + chat_id + ".jpg");

        Button savebtn = getView.findViewById(R.id.savebtn);
        if (fullpath.exists()) {
            savebtn.setVisibility(View.GONE);
            savebtn2.setVisibility(View.VISIBLE);
        }

        File direct = new File(Environment.getExternalStorageDirectory() + "/ourride/");

        prDownloader = PRDownloader.download(imageUrl, direct.getPath(), chat_id + ".jpg")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                    }
                });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Savepicture(false);
            }
        });
        progressBar = getView.findViewById(R.id.progress);
        ImageView singleImage = getView.findViewById(R.id.single_image);

        if (fullpath.exists()) {
            Uri uri = Uri.parse(fullpath.getAbsolutePath());
            singleImage.setImageURI(uri);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            PicassoTrustAll.getInstance(context).load(imageUrl).placeholder(R.drawable.image_placeholder)
                    .into(singleImage, new Callback() {
                        @Override
                        public void onSuccess() {

                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }

        ImageButton sharebtn = getView.findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePicture();
            }
        });


        return getView;
    }

    private void SharePicture() {
        if (Checkstoragepermision()) {
            Uri bitmapuri;
            if (fullpath.exists()) {
                bitmapuri = Uri.parse(fullpath.getAbsolutePath());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, bitmapuri);
                startActivity(Intent.createChooser(intent, ""));
            } else {
                Savepicture(true);
            }

        }
    }

    private void Savepicture(final boolean isfromshare) {
        if (Checkstoragepermision()) {

            final File direct = new File(Environment.getExternalStorageDirectory() + "/DCIM/ourride/");
            progressDialog.show();
            prDownloader.start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.parse(direct.getPath() + chat_id + ".jpg"));
                    context.sendBroadcast(intent);
                    progressDialog.dismiss();
                    if (isfromshare) {
                        SharePicture();
                    } else {
                        new AlertDialog.Builder(context, R.style.DialogStyle)
                                .setTitle("Image Saved")
                                .setMessage(fullpath.getAbsolutePath())
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onError(Error error) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                }


            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Click Again", Toast.LENGTH_LONG).show();
        }
    }

    private boolean Checkstoragepermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;

            } else {

                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {

            return true;
        }
    }


}


