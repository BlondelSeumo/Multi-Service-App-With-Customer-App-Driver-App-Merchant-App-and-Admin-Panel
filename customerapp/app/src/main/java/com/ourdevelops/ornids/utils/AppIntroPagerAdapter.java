package com.ourdevelops.ornids.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.activity.IntroActivity;

import androidx.viewpager.widget.PagerAdapter;


public class AppIntroPagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] mResources;
    private IntroActivity activity;


    public AppIntroPagerAdapter(IntroActivity appIntroActivity, Context mContext, int[] mResources) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = mResources;
        this.activity = appIntroActivity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_slider_layout, container, false);
        ImageView ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

        TextView ctvText = (TextView) itemView.findViewById(R.id.ctvText);
        TextView ctvTextdecrib = (TextView) itemView.findViewById(R.id.ctvTextdecrib);
        TextView iv_icon = (TextView) itemView.findViewById(R.id.iv_icon);
        ivImage.setImageResource(mResources[position]);
        setDescText(position, ctvText, ctvTextdecrib);


        container.addView(itemView);
        ctvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int pos = position + 1;
                activity.scrollPage(pos);


            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setDescText(int pos, TextView ctvText, TextView ctvTextdecrib) {
        switch (pos) {
            case 0:
                ctvTextdecrib.setText(mContext.getString(R.string.introtext1));
                ctvText.setText(mContext.getString(R.string.introdesc1));
                break;
            case 1:
                ctvTextdecrib.setText(mContext.getString(R.string.introtext2));
                ctvText.setText(mContext.getString(R.string.introdesc2));
                break;
            case 2:
                ctvTextdecrib.setText(mContext.getString(R.string.introtext3));
                ctvText.setText(mContext.getString(R.string.introdesc3));
                break;
        }
    }
}