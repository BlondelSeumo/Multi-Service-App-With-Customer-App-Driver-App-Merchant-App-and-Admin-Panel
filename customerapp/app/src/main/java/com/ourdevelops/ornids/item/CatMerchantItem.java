package com.ourdevelops.ornids.item;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.models.CatMerchantModel;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by otacodes on 3/24/2019.
 */

public class CatMerchantItem extends RecyclerView.Adapter<CatMerchantItem.ItemRowHolder> {

    private List<CatMerchantModel> dataList;
    private Context mContext;
    private int rowLayout;
    private int selectedPosition=0;
    private CatMerchantItem.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CatMerchantModel item);
    }

    public CatMerchantItem(Context context, List<CatMerchantModel> dataList, int rowLayout, CatMerchantItem.OnItemClickListener listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final CatMerchantModel singleItem = dataList.get(position);

        holder.text.setText(singleItem.getNama_kategori());

        if(position==selectedPosition)
        {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_rect));
        }
        else
        {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.btn_bordered));
            Random rand = new Random();
            int i = rand.nextInt(4);


            switch (i) {
                case 1:
                    holder.background.getBackground().setColorFilter(mContext.getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
                    break;
                default:
                    break;
            }
        }
        holder.bind(singleItem,listener);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout background;

        ItemRowHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            background = itemView.findViewById(R.id.background);
        }
        public void bind(final CatMerchantModel item, final CatMerchantItem.OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                    selectedPosition=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });


        }
    }


}
