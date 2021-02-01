package com.ourdevelops.ornids.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.models.ItemOrderModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ourdevelops team on 3/24/2019.
 */

public class ItemOrderItem extends RecyclerView.Adapter<ItemOrderItem.ItemRowHolder> {

    private List<ItemOrderModel> dataList;
    private int rowLayout;

    public ItemOrderItem(List<ItemOrderModel> dataList, int rowLayout) {
        this.dataList = dataList;
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
        holder.name.setText(singleItem.getNama_item());
        holder.qty.setText(singleItem.getJumlah_item());
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView name,qty;

        ItemRowHolder(View itemView) {
            super(itemView);
            qty = itemView.findViewById(R.id.qty);
            name = itemView.findViewById(R.id.namaitem);

        }
    }
}
