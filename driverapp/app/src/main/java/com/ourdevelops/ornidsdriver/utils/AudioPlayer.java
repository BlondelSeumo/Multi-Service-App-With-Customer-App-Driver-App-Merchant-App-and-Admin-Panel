package com.ourdevelops.ornidsdriver.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;


import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {

    private Context mContext;
    Ringtone defaultRingtone;

    public AudioPlayer(Context context) {
        this.mContext = context.getApplicationContext();
        Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context.getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        defaultRingtone = RingtoneManager.getRingtone(context, defaultRingtoneUri);
    }



    public void playRingtone() {
        if (defaultRingtone != null)
            defaultRingtone.play();
    }

    public void stopRingtone() {
        if (defaultRingtone != null)
            defaultRingtone.stop();
    }



}
