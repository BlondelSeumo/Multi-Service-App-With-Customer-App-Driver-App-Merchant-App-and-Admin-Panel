package com.ourdevelops.ornidsdriver.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ourdevelops.ornidsdriver.R;
import com.ourdevelops.ornidsdriver.activity.OrderDetailActivity;
import com.ourdevelops.ornidsdriver.constants.Constant;
import com.ourdevelops.ornidsdriver.models.AllTransModel;
import com.ourdevelops.ornidsdriver.utils.Utility;
import com.ourdevelops.ornidsdriver.utils.PicassoTrustAll;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by otacodes on 3/24/2019.
 */

public class HistoryItem extends RecyclerView.Adapter<HistoryItem.ItemRowHolder> {

    private List<AllTransModel> dataList;
    private Context mContext;
    private int rowLayout;


    public HistoryItem(Context context, List<AllTransModel> dataList, int rowLayout) {
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
        final AllTransModel singleItem = dataList.get(position);
        holder.text.setText("Order " + singleItem.getFitur());
        if (singleItem.getHome().equals("4")) {
            double totalbiaya = Double.parseDouble(singleItem.getTotalbiaya());
            Utility.currencyTXT(holder.nominal, String.valueOf(singleItem.getHarga()+ totalbiaya), mContext);
        } else {
            Utility.currencyTXT(holder.nominal, String.valueOf(singleItem.getHarga()), mContext);
        }
        holder.description.setText(singleItem.getStatustransaksi());


        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String finalDate = timeFormat.format(singleItem.getWaktuOrder());
        holder.tanggal.setText(finalDate);

        PicassoTrustAll.getInstance(mContext)
                .load(Constant.IMAGESFITUR + singleItem.getIcon())
                .into(holder.images);

        if (singleItem.status == 4 && singleItem.getRate().isEmpty()) {
            holder.description.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.colorgradient));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_rect));

        } else if (singleItem.status == 5) {
            holder.description.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_rect_red));

        } else {
            holder.description.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.colorgradient));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_rect));

        }
        holder.itemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OrderDetailActivity.class);
                i.putExtra("customer_id", singleItem.getIdPelanggan());
                i.putExtra("transaction_id", singleItem.getIdTransaksi());
                i.putExtra("response", String.valueOf(singleItem.status));
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
        TextView text, tanggal, nominal, description;
        ImageView background, images;
        RelativeLayout itemlayout;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            images = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            tanggal = itemView.findViewById(R.id.texttanggal);
            nominal = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.textket);
            itemlayout = itemView.findViewById(R.id.mainlayout);
        }
    }


}
