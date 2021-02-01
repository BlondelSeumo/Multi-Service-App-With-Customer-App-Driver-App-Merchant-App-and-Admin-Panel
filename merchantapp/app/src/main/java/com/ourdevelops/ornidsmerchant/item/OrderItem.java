package com.ourdevelops.ornidsmerchant.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.activity.OrdervalidasiActivity;
import com.ourdevelops.ornidsmerchant.models.TransMerchantModel;
import com.ourdevelops.ornidsmerchant.utils.SettingPreference;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by otacodes on 3/24/2019.
 */

public class OrderItem extends RecyclerView.Adapter<OrderItem.ItemRowHolder> {

    private List<TransMerchantModel> dataList;
    private Context mContext;
    private int rowLayout;


    public OrderItem(Context context, List<TransMerchantModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;

    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final TransMerchantModel singleItem = dataList.get(position);

        switch (singleItem.getStatus()) {
            case "2":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.colorgradient), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.colorgradient));
                holder.status.setText("New Order");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_progress));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.colorgradient));
                break;
            case "3":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.yellow));
                holder.status.setText("Delivery");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_delivery));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                break;
            case "4":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.status.setText("Finish");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_checklist));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.green));
                break;
            case "5":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.status.setText("Cancel");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_close));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.red));
                break;
        }
        holder.text.setText(singleItem.customer_fullname);
        holder.textinv.setText("INV-"+singleItem.getId_transaksi()+singleItem.getIdtransmerchant());

        SettingPreference sp = new SettingPreference(mContext);
        if (singleItem.total_price.length() == 1) {
            holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+sp.getSetting()[0]+"0.0" + singleItem.total_price);
        } else if (singleItem.total_price.length() == 2) {
            holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+sp.getSetting()[0]+"0." + singleItem.total_price);
        } else {
            Double getprice = Double.valueOf(singleItem.total_price);
            DecimalFormat formatter = new DecimalFormat("#,###,##");
            String formattedString = formatter.format(getprice);
            holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+sp.getSetting()[0] + formattedString.replace(",","."));

        }
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = inputFormat.parse(singleItem.getCreated());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final String finalDate = timeFormat.format(Objects.requireNonNull(date));
        holder.tanggal.setText(finalDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OrdervalidasiActivity.class);
                i.putExtra("invoice","INV-"+singleItem.getId_transaksi()+singleItem.getIdtransmerchant());
                i.putExtra("id",singleItem.transaction_id);
                i.putExtra("idpelanggan",singleItem.idpelanggan);
                i.putExtra("iddriver",singleItem.iddriver);
                i.putExtra("ordertime",finalDate);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text, tanggal, nominal, textinv,status;
        ImageView images;

        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            textinv = itemView.findViewById(R.id.textinvoice);
            tanggal = itemView.findViewById(R.id.tanggal);
            nominal = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.status);
        }
    }


}
