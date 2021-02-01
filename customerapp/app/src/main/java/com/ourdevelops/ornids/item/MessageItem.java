package com.ourdevelops.ornids.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.models.MessageModels;
import com.ourdevelops.ornids.utils.PicassoTrustAll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ourdevelops on 11/20/2020.
 */

public class MessageItem extends RecyclerView.Adapter<MessageItem.CustomViewHolder> implements Filterable {
    public Context context;
    private ArrayList<MessageModels> messageModels;
    private ArrayList<MessageModels> messageModelsFilter;
    private OnItemClickListener listener;

    private Integer today_day;

    public interface OnItemClickListener {
        void onItemClick(MessageModels item);
    }

    public MessageItem(Context context, ArrayList<MessageModels> user_dataList, OnItemClickListener listener) {
        this.context = context;
        this.messageModels = user_dataList;
        this.messageModelsFilter = user_dataList;
        this.listener = listener;

        Calendar cal = Calendar.getInstance();
        today_day = cal.get(Calendar.DAY_OF_MONTH);

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewtype) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new CustomViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return messageModelsFilter.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView username, lastMessage, dateCreated;
        ImageView userImage;

        CustomViewHolder(View view) {
            super(view);
            userImage = itemView.findViewById(R.id.userimages);
            username = itemView.findViewById(R.id.fullname);
            lastMessage = itemView.findViewById(R.id.message);
            dateCreated = itemView.findViewById(R.id.datetxt);
        }

        public void bind(final MessageModels item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });


        }

    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int i) {

        final MessageModels item = messageModelsFilter.get(i);
        holder.username.setText(item.getName());
        holder.lastMessage.setText(item.getMessage());
        holder.dateCreated.setText(ChangeDate(item.getTimestamp()));

        if (item.getPicture().equals("") && item.getPicture() != null) {
            PicassoTrustAll.getInstance(context).
                    load(R.drawable.image_placeholder)
                    .resize(100, 100)
                    .placeholder(R.drawable.image_placeholder).into(holder.userImage);
        } else {
            PicassoTrustAll.getInstance(context).
                    load(item.getPicture())
                    .resize(100, 100)
                    .placeholder(R.drawable.image_placeholder).into(holder.userImage);
        }

        String status = "" + item.getStatus();
        if (status.equals("0")) {
            holder.lastMessage.setTypeface(null, Typeface.BOLD);
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            holder.lastMessage.setTypeface(null, Typeface.NORMAL);
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.dark_gray));
        }

        holder.bind(item, listener);
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
                return sdf.format(Objects.requireNonNull(d));
            else if ((today_day - chatday) == 1)
                return "Yesterday";
        } else if (difference < 172800000) {
            int chatday = Integer.parseInt(date.substring(0, 2));
            if ((today_day - chatday) == 1)
                return "Yesterday";
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");

        if (d != null)
            return sdf.format(d);
        else
            return "";

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    messageModelsFilter = messageModels;
                } else {
                    ArrayList<MessageModels> filteredList = new ArrayList<>();
                    for (MessageModels row : messageModels) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    messageModelsFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = messageModelsFilter;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                messageModelsFilter = (ArrayList<MessageModels>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}