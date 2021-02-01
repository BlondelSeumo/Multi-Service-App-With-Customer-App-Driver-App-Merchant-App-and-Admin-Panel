package com.ourdevelops.ornids.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.utils.AppIntroPagerAdapter;
import com.ourdevelops.ornids.utils.ProjectUtils;
import com.ourdevelops.ornids.utils.SharedPrefrence;

import java.util.Objects;


public class IntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    private AppIntroPagerAdapter mAdapter;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    public SharedPrefrence preference;
    private Context mContext;
    Button buttonSign;
    int[] mResources = {R.drawable.slide_one, R.drawable.slide_two, R.drawable.slide_three};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.Fullscreen(IntroActivity.this);
        setContentView(R.layout.activity_intro);
        mContext = IntroActivity.this;
        preference = SharedPrefrence.getInstance(mContext);

        buttonSign = findViewById(R.id.tombolstar);
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        removeNotif();
        mViewPager = findViewById(R.id.viewpager);
        viewPagerCountDots = findViewById(R.id.viewPagerCountDots);


        mAdapter = new AppIntroPagerAdapter(IntroActivity.this, mContext, mResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        setPageViewIndicator();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    25,
                    25
            );

            params.setMargins(4, 20, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }


        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void scrollPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        clickDone();
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
    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }

}
