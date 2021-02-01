package com.ourdevelops.ornids.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.activity.ChatActivity;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.models.ChatModels;
import com.ourdevelops.ornids.utils.PicassoTrustAll;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by otacodes on 12/12/2018.
 */

public class ItemChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatModels> mDataSet;
    private String myID;
    private static final int mychat = 1;
    private static final int friendchat = 2;
    private static final int mychatimage = 3;
    private static final int otherchatimage = 4;
    private static final int alert_message = 7;

    private static final int my_audio_message = 8;
    private static final int other_audio_message = 9;


    private Context context;
    private Integer today_day;

    private OnItemClickListener listener;
    private OnLongClickListener long_listener;

    public interface OnItemClickListener {
        void onItemClick(ChatModels item, View view);
    }

    public interface OnLongClickListener {
        void onLongclick(ChatModels item, View view);
    }

    public ItemChat(List<ChatModels> dataSet, String id, Context context, OnItemClickListener listener, OnLongClickListener long_listener) {
        mDataSet = dataSet;
        this.myID = id;
        this.context = context;
        this.listener = listener;
        this.long_listener = long_listener;
        Calendar cal = Calendar.getInstance();
        today_day = cal.get(Calendar.DAY_OF_MONTH);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        View v;
        switch (viewtype) {
            case mychat:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_my, viewGroup, false);
                return new Chatviewholder(v);
            case friendchat:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_other, viewGroup, false);
                return new Chatviewholder(v);
            case mychatimage:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_my, viewGroup, false);
                return new Chatimageviewholder(v);
            case otherchatimage:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_image_other, viewGroup, false);
                return new Chatimageviewholder(v);

            case my_audio_message:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chataudio, viewGroup, false);
                return new Chataudioviewholder(v);

            case other_audio_message:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_audio_other, viewGroup, false);
                return new Chataudioviewholder(v);
            case alert_message:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_alert, viewGroup, false);
                return new Alertviewholder(v);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModels chat = mDataSet.get(position);

        switch (chat.getType()) {
            case "text":
                Chatviewholder chatviewholder = (Chatviewholder) holder;
                if (chat.getSender_id().equals(myID)) {
                    if (chat.getStatus().equals("1"))
                        chatviewholder.message_seen.setText("Seen at " + chat.getTime());
                    else
                        chatviewholder.message_seen.setText("Sent");

                } else {
                    chatviewholder.message_seen.setText("");
                }

                if (position != 0) {
                    ChatModels chat2 = mDataSet.get(position - 1);
                    if (chat2.getTimestamp().substring(14, 16).equals(chat.getTimestamp().substring(14, 16))) {
                        chatviewholder.datetxt.setVisibility(View.GONE);
                    } else {
                        chatviewholder.datetxt.setVisibility(View.VISIBLE);
                        chatviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    }
                    chatviewholder.message.setText(chat.getText());
                } else {
                    chatviewholder.datetxt.setVisibility(View.VISIBLE);
                    chatviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    chatviewholder.message.setText(chat.getText());
                }

                chatviewholder.bind(chat, long_listener);

                break;
            case "image": {
                final Chatimageviewholder chatimageholder = (Chatimageviewholder) holder;
                if (chat.getSender_id().equals(myID)) {
                    if (chat.getStatus().equals("1"))
                        chatimageholder.messageSeen.setText("Seen at " + chat.getTime());
                    else
                        chatimageholder.messageSeen.setText("Sent");

                } else {
                    chatimageholder.messageSeen.setText("");
                }
                if (chat.getPic_url().equals("none")) {
                    if (ChatActivity.uploadingImageId.equals(chat.getChat_id())) {
                        chatimageholder.progressBar.setVisibility(View.VISIBLE);
                        chatimageholder.messageSeen.setText("");
                    } else {
                        chatimageholder.progressBar.setVisibility(View.GONE);
                        chatimageholder.notSendMessageIcon.setVisibility(View.VISIBLE);
                        chatimageholder.messageSeen.setText("Not delivered. ");
                    }
                } else {
                    chatimageholder.notSendMessageIcon.setVisibility(View.GONE);
                    chatimageholder.progressBar.setVisibility(View.GONE);
                }

                if (position != 0) {
                    ChatModels chat2 = mDataSet.get(position - 1);
                    if (chat2.getTimestamp().substring(14, 16).equals(chat.getTimestamp().substring(14, 16))) {
                        chatimageholder.datetxt.setVisibility(View.GONE);
                    } else {
                        chatimageholder.datetxt.setVisibility(View.VISIBLE);
                        chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    }
                    PicassoTrustAll.getInstance(context).load(chat.getPic_url()).placeholder(R.drawable.image_placeholder).resize(400, 400).centerCrop().into(chatimageholder.chatimage);
                } else {
                    chatimageholder.datetxt.setVisibility(View.VISIBLE);
                    chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    PicassoTrustAll.getInstance(context).load(chat.getPic_url()).placeholder(R.drawable.image_placeholder).resize(400, 400).centerCrop().into(chatimageholder.chatimage);


                }

                chatimageholder.bind(mDataSet.get(position), listener, long_listener);
                break;
            }
            case "audio":
                final Chataudioviewholder chataudioviewholder = (Chataudioviewholder) holder;
                // check if the message is from sender or receiver
                if (chat.getSender_id().equals(myID)) {
                    if (chat.getStatus().equals("1"))
                        chataudioviewholder.messageSeen.setText("Seen at " + chat.getTime());
                    else
                        chataudioviewholder.messageSeen.setText("Sent");

                } else {
                    chataudioviewholder.messageSeen.setText("");
                }
                if (chat.getPic_url().equals("none")) {
                    if (ChatActivity.uploadingAudioId.equals(chat.getChat_id())) {
                        chataudioviewholder.progressBar.setVisibility(View.VISIBLE);
                        chataudioviewholder.messageSeen.setText("");
                    } else {
                        chataudioviewholder.progressBar.setVisibility(View.GONE);
                        chataudioviewholder.notSendMessageIcon.setVisibility(View.VISIBLE);
                        chataudioviewholder.messageSeen.setText("Not delivered. ");
                    }
                } else {
                    chataudioviewholder.notSendMessageIcon.setVisibility(View.GONE);
                    chataudioviewholder.progressBar.setVisibility(View.GONE);
                }

                // make the group of message by date set the gap of 1 min
                // means message send with in 1 min will show as a group
                if (position != 0) {
                    ChatModels chat2 = mDataSet.get(position - 1);
                    if (chat2.getTimestamp().substring(14, 16).equals(chat.getTimestamp().substring(14, 16))) {
                        chataudioviewholder.datetxt.setVisibility(View.GONE);
                    } else {
                        chataudioviewholder.datetxt.setVisibility(View.VISIBLE);
                        chataudioviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    }
                } else {
                    chataudioviewholder.datetxt.setVisibility(View.VISIBLE);
                    chataudioviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));

                }

                chataudioviewholder.seekBar.setEnabled(false);

                File fullpath = new File(Environment.getExternalStorageDirectory() + "/BaseKencan/" + chat.getChat_id() + ".mp3");
                if (fullpath.exists()) {
                    chataudioviewholder.totalTime.setText(getfileduration(Uri.parse(fullpath.getAbsolutePath())));

                } else {
                    chataudioviewholder.totalTime.setText(null);
                }


                chataudioviewholder.bind(mDataSet.get(position), listener, long_listener);

                break;
            case "gif": {
                final Chatimageviewholder chatimageholder = (Chatimageviewholder) holder;
                if (chat.getSender_id().equals(myID)) {
                    if (chat.getStatus().equals("1"))
                        chatimageholder.messageSeen.setText("Seen at " + chat.getTime());
                    else
                        chatimageholder.messageSeen.setText("Sent");

                } else {
                    chatimageholder.messageSeen.setText("");
                }
                // make the group of message by date set the gap of 1 min
                // means message send with in 1 min will show as a group
                if (position != 0) {
                    ChatModels chat2 = mDataSet.get(position - 1);
                    if (chat2.getTimestamp().substring(14, 16).equals(chat.getTimestamp().substring(14, 16))) {
                        chatimageholder.datetxt.setVisibility(View.GONE);
                    } else {
                        chatimageholder.datetxt.setVisibility(View.VISIBLE);
                        chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    }
                } else {
                    chatimageholder.datetxt.setVisibility(View.VISIBLE);
                    chatimageholder.datetxt.setText(ChangeDate(chat.getTimestamp()));


                }

                chatimageholder.bind(mDataSet.get(position), listener, long_listener);
                break;
            }
            case "delete":
                Alertviewholder alertviewholder = (Alertviewholder) holder;
                alertviewholder.message.setTextColor(context.getResources().getColor(R.color.gray));
                alertviewholder.message.setBackground(context.getResources().getDrawable(R.drawable.round_edittext_background));

                alertviewholder.message.setText("This message is deleted by " + chat.getSender_name());

                if (position != 0) {
                    ChatModels chat2 = mDataSet.get(position - 1);
                    if (chat2.getTimestamp().substring(11, 13).equals(chat.getTimestamp().substring(11, 13))) {
                        alertviewholder.datetxt.setVisibility(View.GONE);
                    } else {
                        alertviewholder.datetxt.setVisibility(View.VISIBLE);
                        alertviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));
                    }

                } else {
                    alertviewholder.datetxt.setVisibility(View.VISIBLE);
                    alertviewholder.datetxt.setText(ChangeDate(chat.getTimestamp()));

                }

                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        switch (mDataSet.get(position).getType()) {
            case "text":
                if (mDataSet.get(position).getSender_id().equals(myID)) {
                    return mychat;
                }
                return friendchat;
            case "image":
                if (mDataSet.get(position).getSender_id().equals(myID)) {
                    return mychatimage;
                }

                return otherchatimage;

            case "audio":
                if (mDataSet.get(position).getSender_id().equals(myID)) {
                    return my_audio_message;
                }
                return other_audio_message;
            default:
                return alert_message;
        }
    }

    static class Chatviewholder extends RecyclerView.ViewHolder {
        TextView message, datetxt, message_seen;
        View view;

        Chatviewholder(View itemView) {
            super(itemView);
            view = itemView;
            this.message = view.findViewById(R.id.msgtxt);
            this.datetxt = view.findViewById(R.id.datetxt);
            message_seen = view.findViewById(R.id.messageseen);
        }

        public void bind(final ChatModels item, final OnLongClickListener long_listener) {
            message.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    long_listener.onLongclick(item, v);
                    return false;
                }
            });
        }
    }

    static class Chatimageviewholder extends RecyclerView.ViewHolder {
        ImageView chatimage;
        TextView datetxt, messageSeen;
        ProgressBar progressBar;
        ImageView notSendMessageIcon;
        View getView;

        Chatimageviewholder(View itemView) {
            super(itemView);
            getView = itemView;
            this.chatimage = getView.findViewById(R.id.chatimage);
            this.datetxt = getView.findViewById(R.id.datetxt);
            messageSeen = getView.findViewById(R.id.messageseen);
            notSendMessageIcon = getView.findViewById(R.id.notsend);
            progressBar = getView.findViewById(R.id.progress);
        }

        public void bind(final ChatModels item, final OnItemClickListener listener, final OnLongClickListener long_listener) {

            chatimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, v);
                }
            });

            chatimage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    long_listener.onLongclick(item, v);
                    return false;
                }
            });
        }

    }

    static class Chataudioviewholder extends RecyclerView.ViewHolder {
        TextView datetxt, messageSeen;
        ProgressBar progressBar;
        ImageView notSendMessageIcon;
        ImageView playBtn;
        SeekBar seekBar;
        TextView totalTime;
        LinearLayout audioBubble;
        View getView;

        Chataudioviewholder(View itemView) {
            super(itemView);
            getView = itemView;
            audioBubble = getView.findViewById(R.id.audiobubble);
            datetxt = getView.findViewById(R.id.datetxt);
            messageSeen = getView.findViewById(R.id.messageseen);
            notSendMessageIcon = getView.findViewById(R.id.notsend);
            progressBar = getView.findViewById(R.id.progress);
            this.playBtn = getView.findViewById(R.id.playbtn);
            this.seekBar = getView.findViewById(R.id.seekbar);
            this.totalTime = getView.findViewById(R.id.totaltime);

        }

        public void bind(final ChatModels item, final OnItemClickListener listener, final OnLongClickListener long_listener) {


            audioBubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, v);
                }
            });

            audioBubble.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    long_listener.onLongclick(item, v);
                    return false;
                }
            });


        }


    }

    static class Alertviewholder extends RecyclerView.ViewHolder {
        TextView message, datetxt;
        View getView;

        Alertviewholder(View itemView) {
            super(itemView);
            getView = itemView;
            this.message = getView.findViewById(R.id.message);
            this.datetxt = getView.findViewById(R.id.datetxt);
        }

    }

    private String ChangeDate(String date) {
        long currenttime = System.currentTimeMillis();

        long databasedate = 0;
        Date d = null;
        try {
            d = Constant.df.parse(date);
            databasedate = Objects.requireNonNull(d).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = currenttime - databasedate;
        if (difference < 86400000) {
            int chatday = Integer.parseInt(date.substring(0, 2));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            if (today_day == chatday)
                return "Today " + sdf.format(Objects.requireNonNull(d));
            else if ((today_day - chatday) == 1)
                return "Yesterday " + sdf.format(Objects.requireNonNull(d));
        } else if (difference < 172800000) {
            int chatday = Integer.parseInt(date.substring(0, 2));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            if ((today_day - chatday) == 1)
                return "Yesterday " + sdf.format(Objects.requireNonNull(d));
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy hh:mm a");

        if (d != null)
            return sdf.format(d);
        else
            return "";
    }

    @SuppressLint("DefaultLocale")
    private String getfileduration(Uri uri) {
        try {

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(context, uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            final int file_duration = Integer.parseInt(durationStr);

            long second = (file_duration / 1000) % 60;
            long minute = (file_duration / (1000 * 60)) % 60;

            return String.format("%02d:%02d", minute, second);
        } catch (Exception ignored) {

        }
        return null;
    }


}
