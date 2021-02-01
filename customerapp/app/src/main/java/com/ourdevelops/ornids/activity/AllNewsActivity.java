package com.ourdevelops.ornids.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.item.NewsItem;
import com.ourdevelops.ornids.json.NewsDetailRequestJson;
import com.ourdevelops.ornids.json.NewsDetailResponseJson;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.UserService;

import java.util.Objects;

public class AllNewsActivity extends AppCompatActivity {
    ShimmerFrameLayout shimmer;
    RecyclerView recycle;
    NewsItem newsItem;
    RelativeLayout rlnodata;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_berita);
        shimmer = findViewById(R.id.shimmer);
        recycle = findViewById(R.id.inboxlist);
        rlnodata = findViewById(R.id.rlnodata);
        backButton = findViewById(R.id.back_btn);
        recycle.setHasFixedSize(true);
        recycle.setNestedScrollingEnabled(false);
        recycle.setLayoutManager(new GridLayoutManager(this, 1));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
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

    private void getData() {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        NewsDetailRequestJson param = new NewsDetailRequestJson();

        service.allberita(param).enqueue(new Callback<NewsDetailResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<NewsDetailResponseJson> call, @NonNull Response<NewsDetailResponseJson> response) {
                if (response.isSuccessful()) {
                    shimmertutup();
                    if (Objects.requireNonNull(response.body()).getData().isEmpty()) {
                        rlnodata.setVisibility(View.VISIBLE);
                    } else {
                        newsItem = new NewsItem(AllNewsActivity.this, response.body().getData(), R.layout.item_grid_full);
                        recycle.setAdapter(newsItem);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<NewsDetailResponseJson> call, @NonNull Throwable t) {

            }
        });

    }
}
