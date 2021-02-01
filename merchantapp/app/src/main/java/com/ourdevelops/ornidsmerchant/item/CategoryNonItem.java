package com.ourdevelops.ornidsmerchant.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.activity.MenuActivity;
import com.ourdevelops.ornidsmerchant.models.CategoryItemNonModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by otacodes on 3/24/2019.
 */

public class CategoryNonItem extends RecyclerView.Adapter<CategoryNonItem.ItemRowHolder> {

    private List<CategoryItemNonModel> dataList;
    private Context mContext;
    private int rowLayout;

    public CategoryNonItem(Context context, List<CategoryItemNonModel> dataList, int rowLayout) {
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
        final CategoryItemNonModel singleItem = dataList.get(position);

        holder.namacategory.setText(singleItem.getNama_kategori_item());
        holder.jumlahmenucat.setText(singleItem.getTotal_item());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.putExtra("idkategori", singleItem.getId_kategori_item());
                intent.putExtra("active", "0");
                intent.putExtra("nama", singleItem.getNama_kategori_item());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView namacategory, jumlahmenucat;

        ItemRowHolder(View itemView) {
            super(itemView);

            namacategory = itemView.findViewById(R.id.namacategory);
            jumlahmenucat = itemView.findViewById(R.id.jumlahmenucat);
        }
    }
}
