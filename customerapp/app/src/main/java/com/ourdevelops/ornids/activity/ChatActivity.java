package com.ourdevelops.ornids.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.EditText;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Progress;
import com.downloader.request.DownloadRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.constants.Functions;
import com.ourdevelops.ornids.fragment.FullImageFragment;
import com.ourdevelops.ornids.fragment.PlayAudioFragment;
import com.ourdevelops.ornids.item.ItemChat;
import com.ourdevelops.ornids.json.fcm.FCMMessage;
import com.ourdevelops.ornids.models.Chat;
import com.ourdevelops.ornids.models.ChatModels;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.PicassoTrustAll;
import com.ourdevelops.ornids.utils.SendAudio;
import com.ourdevelops.ornids.utils.api.FCMHelper;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class ChatActivity extends AppCompatActivity {

    DatabaseReference reference;
    String senderid = "";
    String Receiverid = "";
    String Receiver_name = "";
    String Receiver_pic = "";
    public static String token = "null";
    String tokendriver;
    String tokenku;
    TextView typingIndicator;
    private DatabaseReference adduserToInbox;
    private DatabaseReference sendTypingIndication;

    RecyclerView chatrecyclerview;
    TextView userName;
    private List<ChatModels> mChats = new ArrayList<>();
    ItemChat mAdapter;
    ProgressBar progressBar;

    Query queryGetchat;
    Query myBlockStatusQuery;
    Query otherBlockStatusQuery;
    boolean isUserAlreadyBlock = false;

    public static String uploadingImageId = "none";
    LinearLayout llcamera;
    ImageView camerabtn, profileimage;
    CardView takephoto, opengallery;
    ImageButton sendbtn, micBtn;
    public static String uploadingAudioId = "none";
    SendAudio sendAudio;
    EditText message;
    File direct;
    DownloadRequest prDownloader;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        direct = new File(Environment.getExternalStorageDirectory() + "/ouride/");
        Intent i = getIntent();
        tokendriver = i.getStringExtra("tokendriver");
        tokenku = i.getStringExtra("tokenku");
        senderid = i.getStringExtra("senderid");
        Receiverid = i.getStringExtra("receiverid");
        Receiver_name = i.getStringExtra("name");
        Receiver_pic = i.getStringExtra("pic");

        sendbtn = findViewById(R.id.sendbtn);
        takephoto = findViewById(R.id.takephoto);
        opengallery = findViewById(R.id.opengallery);
        profileimage = findViewById(R.id.profileimage);
        llcamera = findViewById(R.id.llcamera);
        camerabtn = findViewById(R.id.uploadimagebtn);
        micBtn = findViewById(R.id.mic_btn);
        reference = FirebaseDatabase.getInstance().getReference();
        adduserToInbox = FirebaseDatabase.getInstance().getReference();
        reference.child("viewnotif").child(Receiverid).removeValue();
        userName = findViewById(R.id.fullname);
        progressBar = findViewById(R.id.progress_bar);
        chatrecyclerview = findViewById(R.id.chatlist);
        sendbtn = findViewById(R.id.sendbtn);
        userName.setText(Receiver_name);
        PRDownloader.initialize(getApplicationContext());


        if (Receiver_pic.isEmpty()) {
            PicassoTrustAll.getInstance(this).load(R.drawable.image_placeholder)
                    .resize(100, 100)
                    .into(profileimage);
        } else {
            PicassoTrustAll.getInstance(this).load(Receiver_pic)
                    .resize(100, 100)
                    .into(profileimage);
        }
        message = findViewById(R.id.msgedittext);
        message.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.toString().trim().length() == 0) {
                    sendbtn.setVisibility(View.GONE);
                    micBtn.setVisibility(View.VISIBLE);
                } else {
                    sendbtn.setVisibility(View.VISIBLE);
                    micBtn.setVisibility(View.GONE);
                }
            }
        });

        sendAudio = new SendAudio(this, message, reference, adduserToInbox,
                senderid, Receiverid, tokenku, tokendriver, Receiver_name, Receiver_pic);

        reference.child("Users").child(Receiverid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    token = Objects.requireNonNull(dataSnapshot.child("token").getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(message.getText().toString())) {
                    SendMessage(message.getText().toString());
                    message.setText(null);


                }
            }
        });

        final LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(false);
        chatrecyclerview.setLayoutManager(layout);
        chatrecyclerview.setHasFixedSize(false);
        OverScrollDecoratorHelper.setUpOverScroll(chatrecyclerview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        mAdapter = new ItemChat(mChats, senderid, this, new ItemChat.OnItemClickListener() {
            @Override
            public void onItemClick(ChatModels item, View v) {

                if (item.getType().equals("image"))
                    OpenfullsizeImage(item);

                if (v.getId() == R.id.audiobubble) {
                    RelativeLayout mainlayout = (RelativeLayout) v.getParent();

                    File fullpath = new File(Environment.getExternalStorageDirectory() + "/ouride/" + item.getChat_id() + ".mp3");
                    if (fullpath.exists()) {
                        OpenAudio(fullpath.getAbsolutePath());

                    } else {
                        downloadaudio((ProgressBar) mainlayout.findViewById(R.id.progress), item);
                    }

                }


            }


        }, new ItemChat.OnLongClickListener() {
            @Override
            public void onLongclick(ChatModels item, View view) {
                if (view.getId() == R.id.msgtxt) {
                    if (senderid.equals(item.getSender_id()) && istodaymessage(item.getTimestamp()))
                        delete_Message(item);
                } else if (view.getId() == R.id.chatimage) {
                    if (senderid.equals(item.getSender_id()) && istodaymessage(item.getTimestamp()))
                        delete_Message(item);
                } else if (view.getId() == R.id.audiobubble) {
                    if (senderid.equals(item.getSender_id()) && istodaymessage(item.getTimestamp()))
                        delete_Message(item);
                }
            }
        });

        chatrecyclerview.setAdapter(mAdapter);
        chatrecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean userScrolled;
            int scrollOutitems;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollOutitems = layout.findFirstCompletelyVisibleItemPosition();

                if (userScrolled && (scrollOutitems == 0 && mChats.size() > 9)) {
                    userScrolled = false;
                    reference.child("chat").child(senderid + "-" + Receiverid).orderByChild("chat_id")
                            .endAt(mChats.get(0).getChat_id()).limitToLast(20)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ArrayList<ChatModels> arrayList = new ArrayList<>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        ChatModels item = snapshot.getValue(ChatModels.class);
                                        arrayList.add(item);
                                    }
                                    for (int i = arrayList.size() - 2; i >= 0; i--) {
                                        mChats.add(0, arrayList.get(i));
                                    }

                                    mAdapter.notifyDataSetChanged();

                                    if (arrayList.size() > 8) {
                                        chatrecyclerview.scrollToPosition(arrayList.size());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(message.getText().toString())) {
                    SendMessage(message.getText().toString());
                    message.setText(null);
                }
            }
        });
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_camrapermission())
                    openCameraIntent();
            }
        });

        opengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_ReadStoragepermission()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            }
        });


        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llcamera.getVisibility() == View.VISIBLE) {
                    closecameralayout();
                } else {
                    opencameralayout();
                }
            }
        });

        findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideSoftKeyboard(ChatActivity.this);
                finish();
            }
        });

        message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    SendTypingIndicator(false);
                }
            }
        });


        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    SendTypingIndicator(false);
                } else {
                    SendTypingIndicator(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final long[] touchtime = {System.currentTimeMillis()};
        micBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    if (check_Recordpermission() && check_writeStoragepermission()) {
                        touchtime[0] = System.currentTimeMillis();
                        sendAudio.Runbeep("start");
                        Toast.makeText(ChatActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (touchtime[0] + 1000 > System.currentTimeMillis()) {
                        sendAudio.stop_timer();
                        Toast.makeText(ChatActivity.this, "Hold Mic Button to Record", Toast.LENGTH_SHORT).show();
                    } else {
                        sendAudio.stopRecording();
                        Toast.makeText(ChatActivity.this, "Stop Recording...", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }

        });
        ReceivetypeIndication();

        findViewById(R.id.Goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideSoftKeyboard(ChatActivity.this);
                finish();
            }
        });


        ReceivetypeIndication();
        getChat();
    }

    ValueEventListener valueEventListener;
    ChildEventListener eventListener;
    ValueEventListener my_inbox_listener;
    ValueEventListener other_inbox_listener;

    public void getChat() {
        mChats.clear();
        DatabaseReference mchatRefReteriving = FirebaseDatabase.getInstance().getReference();
        queryGetchat = mchatRefReteriving.child("chat").child(senderid + "-" + Receiverid);

        myBlockStatusQuery = mchatRefReteriving.child("Inbox")
                .child(senderid)
                .child(Receiverid);

        otherBlockStatusQuery = mchatRefReteriving.child("Inbox")
                .child(Receiverid)
                .child(senderid);

        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                try {
                    ChatModels model = dataSnapshot.getValue(ChatModels.class);
                    mChats.add(model);
                    mAdapter.notifyDataSetChanged();
                    chatrecyclerview.scrollToPosition(mChats.size() - 1);
                } catch (Exception ex) {
                    Log.e("", Objects.requireNonNull(ex.getMessage()));
                }
                ChangeStatus();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot.getValue() != null) {

                    try {
                        ChatModels model = dataSnapshot.getValue(ChatModels.class);

                        for (int i = mChats.size() - 1; i >= 0; i--) {
                            if (mChats.get(i).getTimestamp().equals(dataSnapshot.child("timestamp").getValue())) {
                                mChats.remove(i);
                                mChats.add(i, model);
                                break;
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception ex) {
                        Log.e("", Objects.requireNonNull(ex.getMessage()));
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("", databaseError.getMessage());
            }
        };

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(senderid + "-" + Receiverid)) {
                    progressBar.setVisibility(View.GONE);
                    queryGetchat.removeEventListener(valueEventListener);
                } else {
                    progressBar.setVisibility(View.GONE);
                    queryGetchat.removeEventListener(valueEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        my_inbox_listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.child("block").getValue() != null) {
                    String block = Objects.requireNonNull(dataSnapshot.child("block").getValue()).toString();
                    if (block.equals("1")) {
                        findViewById(R.id.writechatlayout).setVisibility(View.INVISIBLE);
                    } else {
                        findViewById(R.id.writechatlayout).setVisibility(View.VISIBLE);
                    }
                } else {
                    findViewById(R.id.writechatlayout).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        other_inbox_listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.child("block").getValue() != null) {
                    String block = Objects.requireNonNull(dataSnapshot.child("block").getValue()).toString();
                    isUserAlreadyBlock = block.equals("1");
                } else {
                    isUserAlreadyBlock = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        queryGetchat.limitToLast(20).addChildEventListener(eventListener);
        mchatRefReteriving.child("chat").addValueEventListener(valueEventListener);

        myBlockStatusQuery.addValueEventListener(my_inbox_listener);
        otherBlockStatusQuery.addValueEventListener(other_inbox_listener);
    }

    public void SendMessage(final String message) {
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        Date c = Calendar.getInstance().getTime();
        final String formattedDate = Constant.df.format(c);

        final String current_user_ref = "chat" + "/" + senderid + "-" + Receiverid;
        final String chat_user_ref = "chat" + "/" + Receiverid + "-" + senderid;

        DatabaseReference reference = this.reference.child("chat").child(senderid + "-" + Receiverid).push();
        final String pushid = reference.getKey();
        final HashMap<String, String> message_user_map = new HashMap<>();
        message_user_map.put("receiver_id", Receiverid);
        message_user_map.put("sender_id", senderid);
        message_user_map.put("tokendriver", tokendriver);
        message_user_map.put("tokenuser", tokenku);
        message_user_map.put("chat_id", pushid);
        message_user_map.put("text", message);
        message_user_map.put("type", "text");
        message_user_map.put("pic_url", "");
        message_user_map.put("status", "0");
        message_user_map.put("time", "");
        message_user_map.put("sender_name", loginUser.getFullnama());
        message_user_map.put("timestamp", formattedDate);

        final HashMap<String, Object> user_map = new HashMap<>();
        user_map.put(current_user_ref + "/" + pushid, message_user_map);
        user_map.put(chat_user_ref + "/" + pushid, message_user_map);

        this.reference.updateChildren(user_map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                String inbox_sender_ref = "Inbox" + "/" + senderid + "/" + Receiverid;
                String inbox_receiver_ref = "Inbox" + "/" + Receiverid + "/" + senderid;

                HashMap<String, java.io.Serializable> sendermap = new HashMap<>();
                sendermap.put("rid", senderid);
                sendermap.put("name", loginUser.getFullnama());
                sendermap.put("pic", Constant.IMAGESUSER + loginUser.getFotopelanggan());
                sendermap.put("tokendriver", tokenku);
                sendermap.put("tokenuser", tokendriver);
                sendermap.put("msg", message);
                sendermap.put("status", "0");
                sendermap.put("timestamp", -1 * System.currentTimeMillis());
                sendermap.put("date", formattedDate);

                HashMap<String, java.io.Serializable> receivermap = new HashMap<>();
                receivermap.put("rid", Receiverid);
                receivermap.put("name", Receiver_name);
                receivermap.put("pic", Receiver_pic);
                receivermap.put("tokendriver", tokendriver);
                receivermap.put("tokenuser", tokenku);
                receivermap.put("msg", message);
                receivermap.put("status", "1");
                receivermap.put("timestamp", -1 * System.currentTimeMillis());
                receivermap.put("date", formattedDate);

                HashMap<String, Object> both_user_map = new HashMap<>();
                both_user_map.put(inbox_sender_ref, receivermap);
                both_user_map.put(inbox_receiver_ref, sendermap);

                adduserToInbox.updateChildren(both_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Chat chat = new Chat();
                        chat.senderid = senderid;
                        chat.receiverid = Receiverid;
                        chat.name = loginUser.getFullnama();
                        chat.pic = Constant.IMAGESUSER + loginUser.getFotopelanggan();
                        chat.tokendriver = tokendriver;
                        chat.tokenuser = tokenku;
                        chat.message = message;
                        sendMessageToDriver(tokenku, chat);
                    }
                });
            }
        });
    }

    public void OpenfullsizeImage(ChatModels item) {
        FullImageFragment see_image_f = new FullImageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("image_url", item.getPic_url());
        args.putSerializable("chat_id", item.getChat_id());
        see_image_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.chatlayout, see_image_f).commit();

    }

    public void UploadImage(ByteArrayOutputStream byteArrayOutputStream) {
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        byte[] data = byteArrayOutputStream.toByteArray();
        Date c = Calendar.getInstance().getTime();
        final String formattedDate = Constant.df.format(c);

        StorageReference reference = FirebaseStorage.getInstance().getReference();
        DatabaseReference dref = this.reference.child("chat").child(senderid + "-" + Receiverid).push();
        final String key = dref.getKey();
        uploadingImageId = key;
        final String current_user_ref = "chat" + "/" + senderid + "-" + Receiverid;
        final String chat_user_ref = "chat" + "/" + Receiverid + "-" + senderid;

        HashMap<String, String> my_dummi_pic_map = new HashMap<>();
        my_dummi_pic_map.put("receiver_id", Receiverid);
        my_dummi_pic_map.put("sender_id", senderid);
        my_dummi_pic_map.put("chat_id", key);
        my_dummi_pic_map.put("tokendriver", tokendriver);
        my_dummi_pic_map.put("tokenuser", tokenku);
        my_dummi_pic_map.put("text", "");
        my_dummi_pic_map.put("type", "image");
        my_dummi_pic_map.put("pic_url", "none");
        my_dummi_pic_map.put("status", "0");
        my_dummi_pic_map.put("time", "");
        my_dummi_pic_map.put("sender_name", loginUser.getFullnama());
        my_dummi_pic_map.put("timestamp", formattedDate);

        HashMap<String, Object> dummy_push = new HashMap<>();
        dummy_push.put(current_user_ref + "/" + key, my_dummi_pic_map);
        this.reference.updateChildren(dummy_push);

        reference.child("images").child(key + ".jpg").putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadingImageId = "none";

                        HashMap<String, String> message_user_map = new HashMap<>();
                        message_user_map.put("receiver_id", Receiverid);
                        message_user_map.put("sender_id", senderid);
                        message_user_map.put("chat_id", key);
                        message_user_map.put("tokendriver", tokendriver);
                        message_user_map.put("tokenuser", tokenku);
                        message_user_map.put("text", "");
                        message_user_map.put("type", "image");
                        message_user_map.put("pic_url", uri.toString());
                        message_user_map.put("status", "0");
                        message_user_map.put("time", "");
                        message_user_map.put("sender_name", loginUser.getFullnama());
                        message_user_map.put("timestamp", formattedDate);

                        HashMap<String, Object> user_map = new HashMap<>();

                        user_map.put(current_user_ref + "/" + key, message_user_map);
                        user_map.put(chat_user_ref + "/" + key, message_user_map);

                        ChatActivity.this.reference.updateChildren(user_map, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                String inbox_sender_ref = "Inbox" + "/" + senderid + "/" + Receiverid;
                                String inbox_receiver_ref = "Inbox" + "/" + Receiverid + "/" + senderid;

                                HashMap<String, java.io.Serializable> sendermap = new HashMap<>();
                                sendermap.put("rid", senderid);
                                sendermap.put("name", loginUser.getFullnama());
                                sendermap.put("pic", Constant.IMAGESUSER + loginUser.getFotopelanggan());
                                sendermap.put("tokendriver", tokendriver);
                                sendermap.put("tokenuser", tokenku);
                                sendermap.put("msg", "Send an image...");
                                sendermap.put("status", "0");
                                sendermap.put("timestamp", -1 * System.currentTimeMillis());
                                sendermap.put("date", formattedDate);

                                HashMap<String, java.io.Serializable> receivermap = new HashMap<>();
                                receivermap.put("rid", Receiverid);
                                receivermap.put("name", Receiver_name);
                                receivermap.put("pic", Receiver_pic);
                                receivermap.put("tokendriver", tokendriver);
                                receivermap.put("tokenuser", tokenku);
                                receivermap.put("msg", "Send an image...");
                                receivermap.put("status", "1");
                                receivermap.put("timestamp", -1 * System.currentTimeMillis());
                                receivermap.put("date", formattedDate);

                                HashMap<String, Object> both_user_map = new HashMap<>();
                                both_user_map.put(inbox_sender_ref, receivermap);
                                both_user_map.put(inbox_receiver_ref, sendermap);

                                adduserToInbox.updateChildren(both_user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Chat chat = new Chat();
                                        chat.senderid = senderid;
                                        chat.receiverid = Receiverid;
                                        chat.name = loginUser.getFullnama();
                                        chat.pic = Constant.IMAGESUSER + loginUser.getFotopelanggan();
                                        chat.tokendriver = tokendriver;
                                        chat.tokenuser = tokenku;
                                        chat.message = "Send Image.......";
                                        sendMessageToDriver(tokenku, chat);


                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
    }


    public void ChangeStatus() {
        final Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Query query1 = reference.child("chat").child(Receiverid + "-" + senderid).orderByChild("status").equalTo("0");
        final Query query2 = reference.child("chat").child(senderid + "-" + Receiverid).orderByChild("status").equalTo("0");

        final DatabaseReference inbox_change_status_1 = reference.child("Inbox").child(senderid + "/" + Receiverid);
        final DatabaseReference inbox_change_status_2 = reference.child("Inbox").child(Receiverid + "/" + senderid);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot nodeDataSnapshot : dataSnapshot.getChildren()) {
                    if (!Objects.equals(nodeDataSnapshot.child("sender_id").getValue(), senderid)) {
                        String key = nodeDataSnapshot.getKey();
                        String path = "chat" + "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        result.put("time", sdf.format(c));
                        reference.child(path).updateChildren(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot nodeDataSnapshot : dataSnapshot.getChildren()) {
                    if (!Objects.equals(nodeDataSnapshot.child("sender_id").getValue(), senderid)) {
                        String key = nodeDataSnapshot.getKey();
                        String path = "chat" + "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        result.put("time", sdf.format(c));
                        reference.child(path).updateChildren(result);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        inbox_change_status_1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (Objects.equals(dataSnapshot.child("rid").getValue(), Receiverid)) {
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        inbox_change_status_1.updateChildren(result);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        inbox_change_status_2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (Objects.equals(dataSnapshot.child("rid").getValue(), Receiverid)) {
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("status", "1");
                        inbox_change_status_2.updateChildren(result);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public boolean Checkstoragepermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

    public void downloadaudio(final ProgressBar p_bar, final ChatModels item) {

        if (Checkstoragepermision()) {
            p_bar.setVisibility(View.VISIBLE);
            prDownloader = PRDownloader.download(item.getPic_url(), direct.getPath(), item.getChat_id() + ".mp3")
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
            prDownloader.start(new OnDownloadListener() {
                @Override
                public void onDownloadComplete() {
                    p_bar.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                    File fullpath = new File(Environment.getExternalStorageDirectory() + "/ouride/" + item.getChat_id() + ".mp3");
                    OpenAudio(fullpath.getAbsolutePath());

                }

                @Override
                public void onError(Error error) {
                    p_bar.setVisibility(View.GONE);
                    Toast.makeText(ChatActivity.this, "download error", Toast.LENGTH_SHORT).show();

                }


            });
        }
    }


    public void OpenAudio(String path) {
        PlayAudioFragment play_audio_fragment = new PlayAudioFragment();
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("path", path);
        play_audio_fragment.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.chatting, play_audio_fragment).commit();

    }

    private void delete_Message(final ChatModels chat_models) {

        final CharSequence[] options = {"Delete this message", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);

        builder.setTitle(null);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Delete this message")) {
                    update_message(chat_models);

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    public void update_message(ChatModels item) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final String current_user_ref = "chat" + "/" + senderid + "-" + Receiverid;
        final String chat_user_ref = "chat" + "/" + Receiverid + "-" + senderid;


        final HashMap<String, String> message_user_map = new HashMap<>();
        message_user_map.put("receiver_id", item.getReceiver_id());
        message_user_map.put("sender_id", item.getSender_id());
        message_user_map.put("chat_id", item.getChat_id());
        message_user_map.put("text", "Delete this message");
        message_user_map.put("type", "delete");
        message_user_map.put("pic_url", "");
        message_user_map.put("status", "0");
        message_user_map.put("time", "");
        message_user_map.put("sender_name", loginUser.getFullnama());
        message_user_map.put("timestamp", item.getTimestamp());

        final HashMap<String, Object> user_map = new HashMap<>();
        user_map.put(current_user_ref + "/" + item.getChat_id(), message_user_map);
        user_map.put(chat_user_ref + "/" + item.getChat_id(), message_user_map);

        reference.updateChildren(user_map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

            }
        });

    }

    public boolean istodaymessage(String date) {
        Calendar cal = Calendar.getInstance();
        int today_day = cal.get(Calendar.DAY_OF_MONTH);
        long currenttime = System.currentTimeMillis();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        long databasedate = 0;
        Date d;
        try {
            d = f.parse(date);
            databasedate = Objects.requireNonNull(d).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = currenttime - databasedate;
        if (difference < 86400000) {
            int chatday = Integer.parseInt(date.substring(0, 2));
            return today_day == chatday;
        }

        return false;
    }

    public boolean check_Recordpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        Constant.permission_Recording_audio);
            }
        }
        return false;
    }

    private boolean check_camrapermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA}, Constant.permission_camera_code);
            }
        }
        return false;
    }

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constant.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    private boolean check_writeStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Constant.permission_write_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.permission_camera_code) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Tap again", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }
        }

        if (requestCode == Constant.permission_Read_data) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Tap again", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == Constant.permission_write_data) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Tap Again", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == Constant.permission_Recording_audio) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Tap Again", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this.getApplicationContext(), this.getPackageName() + ".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, 1);
            }
        }
    }

    String imageFilePath;

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                Matrix matrix = new Matrix();
                try {
                    ExifInterface exif = new ExifInterface(imageFilePath);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri selectedImage = (Uri.fromFile(new File(imageFilePath)));

                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(imagebitmap, (int) (imagebitmap.getWidth() * 0.1), (int) (imagebitmap.getHeight()*0.1) ,  true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaleBitmap, 0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                UploadImage(baos);

            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(imagebitmap, (int) (imagebitmap.getWidth() * 0.1), (int) (imagebitmap.getHeight()*0.1) ,  true);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Bitmap rotatedBitmap = Bitmap.createBitmap(scaleBitmap, 0, 0, scaleBitmap.getWidth(), scaleBitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                UploadImage(baos);

            }

        }

    }

    public void SendTypingIndicator(boolean indicate) {
        if (indicate) {
            final HashMap<String, String> message_user_map = new HashMap<>();
            message_user_map.put("receiver_id", Receiverid);
            message_user_map.put("sender_id", senderid);

            sendTypingIndication = FirebaseDatabase.getInstance().getReference().child("typing_indicator");
            sendTypingIndication.child(senderid + "-" + Receiverid).setValue(message_user_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    sendTypingIndication.child(Receiverid + "-" + senderid).setValue(message_user_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            });
        } else {
            sendTypingIndication = FirebaseDatabase.getInstance().getReference().child("typing_indicator");

            sendTypingIndication.child(senderid + "-" + Receiverid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    sendTypingIndication.child(Receiverid + "-" + senderid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }
            });

        }

    }


    public void ReceivetypeIndication() {
        typingIndicator = findViewById(R.id.typeindicator);

        DatabaseReference receiveTypingIndication = FirebaseDatabase.getInstance().getReference().child("typing_indicator");
        receiveTypingIndication.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Receiverid + "-" + senderid).exists()) {
                    String receiver = String.valueOf(dataSnapshot.child(Receiverid + "-" + senderid).child("sender_id").getValue());
                    if (receiver.equals(Receiverid)) {
                        typingIndicator.setVisibility(View.VISIBLE);
                    }
                } else {
                    typingIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uploadingImageId = "none";
        SendTypingIndicator(false);
        queryGetchat.removeEventListener(eventListener);
        myBlockStatusQuery.removeEventListener(my_inbox_listener);
        otherBlockStatusQuery.removeEventListener(other_inbox_listener);
    }


    public void closecameralayout() {
        camerabtn.setColorFilter(this.getResources().getColor(R.color.gray));
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                1000);
        animate.setDuration(200);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llcamera.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        llcamera.startAnimation(animate);
    }


    public void opencameralayout() {
        llcamera.setVisibility(View.VISIBLE);
        camerabtn.setColorFilter(this.getResources().getColor(R.color.colorPrimary));
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                llcamera.getHeight(),
                0);
        animate.setDuration(200);
        animate.setFillAfter(true);
        llcamera.startAnimation(animate);
    }

    private void sendMessageToDriver(final String regIDTujuan, final Chat chat) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(chat);

        FCMHelper.sendMessage(Constant.FCM_KEY, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                Log.e("REQUEST TO DRIVER", message.getData().toString());
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }


}
