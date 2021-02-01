package com.ourdevelops.ornidsmerchant.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.models.ItemOrderModel;
import com.ourdevelops.ornidsmerchant.utils.Utility;

import java.util.List;

/**
 * Created by Ourdevelops Team on 3/24/2019.
 */

public class MenuItem extends RecyclerView.Adapter<MenuItem.ItemRowHolder> {

    private List<ItemOrderModel> dataList;
    private Context mContext;
    private int rowLayout;

    public MenuItem(Context context, List<ItemOrderModel> dataList, int rowLayout) {
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

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final ItemOrderModel singleItem = dataList.get(position);
        holder.namamenu.setText(singleItem.getNama_item());
        holder.jumlahmenu.setText(singleItem.getJumlah_item());
        if (singleItem.getCatatan().isEmpty()) {
            holder.note.setVisibility(View.GONE);
        } else {
            holder.note.setText(singleItem.getCatatan());
        }
        Utility.currencyTXT(holder.jumlahhargamenu, singleItem.getTotalharga(), mContext);

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView namamenu, jumlahmenu, note, jumlahhargamenu;

        ItemRowHolder(View itemView) {
            super(itemView);

            namamenu = itemView.findViewById(R.id.namamenu);
            jumlahmenu = itemView.findViewById(R.id.jumlahmenu);
            note = itemView.findViewById(R.id.note);
            jumlahhargamenu = itemView.findViewById(R.id.jumlahhargamenu);
        }
    }
}
