package com.ourdevelops.ornids.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ourdevelops.ornids.R;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import nl.changer.audiowife.AudioWife;


public class PlayAudioFragment extends Fragment {

    private AudioWife audioWife;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View getView = inflater.inflate(R.layout.fragment_playaudio, container, false);

        Context context = getContext();

        ImageView close_btn = getView.findViewById(R.id.close_btn);
        ImageButton playBtn = getView.findViewById(R.id.playbtn);
        ImageButton pauseBtn = getView.findViewById(R.id.pause_btn);
        SeekBar seekBar = getView.findViewById(R.id.seekbar);
        TextView durationTime = getView.findViewById(R.id.duration_time);
        TextView totalTime = getView.findViewById(R.id.totaltime);

        String filepath = Objects.requireNonNull(getArguments()).getString("path");
        Uri uri = Uri.parse(filepath);

        audioWife = AudioWife.getInstance();
        audioWife.init(context, uri)
                .setPlayView(playBtn)
                .setPauseView(pauseBtn)
                .setSeekBar(seekBar)
                .setRuntimeView(durationTime)
                .setTotalTimeView(totalTime);

        audioWife.play();

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        return getView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        audioWife.pause();
        audioWife.release();
    }
}
