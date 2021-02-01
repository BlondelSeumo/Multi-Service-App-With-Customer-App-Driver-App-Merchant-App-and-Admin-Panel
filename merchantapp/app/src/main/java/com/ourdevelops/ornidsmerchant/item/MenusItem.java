package com.ourdevelops.ornidsmerchant.item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.activity.EditmenuActivity;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.constants.Constant;
import com.ourdevelops.ornidsmerchant.json.ActiveCatRequestJson;
import com.ourdevelops.ornidsmerchant.json.ResponseJson;
import com.ourdevelops.ornidsmerchant.models.ItemModel;
import com.ourdevelops.ornidsmerchant.models.User;

import com.ourdevelops.ornidsmerchant.utils.Utility;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;
import com.ourdevelops.ornidsmerchant.utils.PicassoTrustAll;

import java.util.List;
import java.util.Objects;

/**
 * Created by otacodes on 3/24/2019.
 */

public class MenusItem extends RecyclerView.Adapter<MenusItem.ItemRowHolder> {

    private List<ItemModel> dataList;
    private Context mContext;
    private int rowLayout;
    private MenusItem.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ItemModel item, View view);
    }

    public MenusItem(Context context, List<ItemModel> dataList, int rowLayout, MenusItem.OnItemClickListener listener) {
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
        final ItemModel singleItem = dataList.get(position);
        holder.namamenu.setText(singleItem.getNama_item());
        holder.descmenu.setText(singleItem.getDeskripsi_item());


            PicassoTrustAll.getInstance(mContext)
                    .load(Constant.IMAGESITEM+singleItem.getFoto_item())
                    .resize(100, 100)
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.imagemenu);



        if (singleItem.getStatus_promo().equals("1")) {
            holder.promobadge.setVisibility(View.VISIBLE);
            holder.shimmerview.startShimmerAnimation();
            holder.hargapromo.setVisibility(View.VISIBLE);
            Utility.currencyTXT(holder.hargapromo, singleItem.getHarga_item(), mContext);
            holder.hargapromo.setPaintFlags(holder.hargapromo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            Utility.currencyTXT(holder.hargamenu, singleItem.getHarga_promo(), mContext);
        } else {
            holder.promobadge.setVisibility(View.GONE);
            holder.shimmerview.stopShimmerAnimation();
            holder.hargapromo.setVisibility(View.GONE);
            Utility.currencyTXT(holder.hargamenu, singleItem.getHarga_item(), mContext);
        }

        if (singleItem.getStatus_item().equals("1")) {
            holder.activemenu.setChecked(true);
        } else {
            holder.activemenu.setChecked(false);
        }

        holder.clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, EditmenuActivity.class);
                i.putExtra("idkategori",singleItem.getKategori_item());
                i.putExtra("nama",singleItem.getNama_item());
                i.putExtra("deskripsi",singleItem.getDeskripsi_item());
                i.putExtra("price",singleItem.getHarga_item());
                i.putExtra("hargapromo",singleItem.getHarga_promo());
                i.putExtra("active",singleItem.getStatus_promo());
                i.putExtra("id",singleItem.getId_item());
                i.putExtra("fotolama",singleItem.getFoto_item());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);

            }
        });

        holder.activemenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Switchstring("1", String.valueOf(singleItem.getId_item()), singleItem.getNama_item() + " Activate");
                } else {
                    Switchstring("0", String.valueOf(singleItem.getId_item()), singleItem.getNama_item() + " NonActivate");
                }
            }
        });
        holder.bind(singleItem, listener);

    }

    private void Switchstring(String switchactive, String idkat, final String text) {
        User loginUser = BaseApp.getInstance(mContext).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        ActiveCatRequestJson param = new ActiveCatRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setIdkategori(idkat);
        param.setStatus(switchactive);
        merchantService.activeitem(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView namamenu, descmenu, hargamenu, deletemenu, hargapromo;
        ShimmerFrameLayout shimmerview;
        ImageView imagemenu;
        SwitchCompat activemenu;
        FrameLayout promobadge;
        LinearLayout clicked;

        ItemRowHolder(View itemView) {
            super(itemView);

            namamenu = itemView.findViewById(R.id.namamenu);
            descmenu = itemView.findViewById(R.id.descmenu);
            hargamenu = itemView.findViewById(R.id.hargamenu);
            hargapromo = itemView.findViewById(R.id.hargapromo);
            deletemenu = itemView.findViewById(R.id.deletemenu);
            shimmerview = itemView.findViewById(R.id.shimmerview);
            activemenu = itemView.findViewById(R.id.activemenu);
            imagemenu = itemView.findViewById(R.id.imagemenu);
            promobadge = itemView.findViewById(R.id.promobadge);
            clicked = itemView.findViewById(R.id.clicked);
        }

        void bind(final ItemModel item, final MenusItem.OnItemClickListener listener) {
            deletemenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, v);
                }
            });
        }
    }
}
