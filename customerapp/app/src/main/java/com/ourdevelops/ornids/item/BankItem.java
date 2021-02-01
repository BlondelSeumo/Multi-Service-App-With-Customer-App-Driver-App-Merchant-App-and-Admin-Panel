package com.ourdevelops.ornids.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.models.BankModel;
import com.ourdevelops.ornids.utils.PicassoTrustAll;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by otacodes on 3/24/2019.
 */

public class BankItem extends RecyclerView.Adapter<BankItem.ItemRowHolder> {

    private List<BankModel> dataList;
    private Context mContext;
    private int rowLayout;

    public BankItem(Context context, List<BankModel> dataList, int rowLayout) {
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
        final BankModel singleItem = dataList.get(position);
        PicassoTrustAll.getInstance(mContext)
                .load(Constant.IMAGESBANK + singleItem.getImage_bank())
                .resize(250, 250)
                .into(holder.images);

        holder.namabank.setText(singleItem.getNama_bank());
        holder.wallet_account.setText(singleItem.getRekening_bank());


        if (position % 2 == 1) {
            holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.backgroundgray));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView namabank, wallet_account;
        ImageView images;
        RelativeLayout background;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.rootLayout);
            namabank = itemView.findViewById(R.id.namabank);
            wallet_account = itemView.findViewById(R.id.norekening);
            images = itemView.findViewById(R.id.images);
        }
    }
}
