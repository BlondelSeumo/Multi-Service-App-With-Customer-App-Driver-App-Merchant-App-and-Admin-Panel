package com.ourdevelops.ornids.item;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.activity.NewsDetailActivity;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.constants.Constant;
import com.ourdevelops.ornids.models.NewsModel;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.DatabaseHelper;
import com.ourdevelops.ornids.utils.PicassoTrustAll;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by otacodes on 3/24/2019.
 */

public class NewsItem extends RecyclerView.Adapter<NewsItem.ItemRowHolder> {

    private List<NewsModel> dataList;
    private Context mContext;
    private int rowLayout;
    private DatabaseHelper databaseHelper;

    public NewsItem(Context context, List<NewsModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
        databaseHelper = new DatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final NewsModel singleItem = dataList.get(position);
        holder.name.setText(singleItem.getTitle());
        if (!singleItem.getFotoberita().isEmpty()) {
            PicassoTrustAll.getInstance(mContext)
                    .load(Constant.IMAGESBERITA + singleItem.getFotoberita())
                    .resize(250, 250)
                    .into(holder.images);
        }

        holder.category.setText(singleItem.getKategori());
        String htmlText = singleItem.getContent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.content.setText(Html.fromHtml(htmlText));
        }

        final User loginUser = BaseApp.getInstance(mContext).getLoginUser();
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues fav = new ContentValues();
                if (databaseHelper.getFavouriteById(String.valueOf(singleItem.getIdberita()))) {
                    databaseHelper.removeFavouriteById(String.valueOf(singleItem.getIdberita()));
                    holder.favourite.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    Toast.makeText(mContext, "Remove To Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    fav.put(DatabaseHelper.KEY_ID, singleItem.getIdberita());
                    fav.put(DatabaseHelper.KEY_USERID, loginUser.getId());
                    fav.put(DatabaseHelper.KEY_TITLE, singleItem.getTitle());
                    fav.put(DatabaseHelper.KEY_CONTENT, singleItem.getContent());
                    fav.put(DatabaseHelper.KEY_KATEGORI, singleItem.getKategori());
                    fav.put(DatabaseHelper.KEY_IMAGE, singleItem.getFotoberita());
                    databaseHelper.addFavourite(DatabaseHelper.TABLE_FAVOURITE_NAME, fav, null);
                    holder.favourite.setColorFilter(mContext.getResources().getColor(R.color.red));
                    Toast.makeText(mContext, "Add To Favourite", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (databaseHelper.getFavouriteById(String.valueOf(singleItem.getIdberita()))) {
            holder.favourite.setColorFilter(mContext.getResources().getColor(R.color.red));
        } else {
            holder.favourite.setColorFilter(mContext.getResources().getColor(R.color.gray));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, NewsDetailActivity.class);
                i.putExtra("id", singleItem.getIdberita());
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
        TextView name, category, content;
        ImageView images, favourite;

        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.namakategori);
            category = itemView.findViewById(R.id.category);
            content = itemView.findViewById(R.id.content);
            favourite = itemView.findViewById(R.id.favourite);
        }
    }
}
