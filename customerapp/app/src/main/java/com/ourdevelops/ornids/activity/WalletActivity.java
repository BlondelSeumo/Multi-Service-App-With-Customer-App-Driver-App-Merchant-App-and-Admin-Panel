package com.ourdevelops.ornids.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.item.WalletItem;
import com.ourdevelops.ornids.json.WalletRequestJson;
import com.ourdevelops.ornids.json.WalletResponseJson;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.UserService;

import java.util.Objects;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {
    ImageView backbtn;
    ShimmerFrameLayout shimmer;
    RecyclerView recycle;
    WalletItem walletItem;
    RelativeLayout rlnodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        shimmer = findViewById(R.id.shimmerwallet);
        recycle = findViewById(R.id.recycle);
        rlnodata = findViewById(R.id.rlnodata);
        backbtn = findViewById(R.id.back_btn);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new GridLayoutManager(this, 1));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        getdatawallet();

    }

    private void shimmershow() {
        recycle.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmerAnimation();
    }

    private void shimmertutup() {

        recycle.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmerAnimation();
    }

    private void getdatawallet() {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        WalletRequestJson param = new WalletRequestJson();
        param.setId(loginUser.getId());
        userService.wallet(param).enqueue(new Callback<WalletResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<WalletResponseJson> call, @NonNull Response<WalletResponseJson> response) {
                if (response.isSuccessful()) {
                    shimmertutup();
                    walletItem = new WalletItem(WalletActivity.this, Objects.requireNonNull(response.body()).getData(), R.layout.item_wallet);
                    recycle.setAdapter(walletItem);
                    if (response.body().getData().isEmpty()) {
                        recycle.setVisibility(View.GONE);
                        rlnodata.setVisibility(View.VISIBLE);
                    } else {
                        recycle.setVisibility(View.VISIBLE);
                        rlnodata.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WalletResponseJson> call, @NonNull Throwable t) {

            }
        });
    }
}
