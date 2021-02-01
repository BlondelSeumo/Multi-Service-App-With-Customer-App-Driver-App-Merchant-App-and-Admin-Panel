package com.ourdevelops.ornids.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornids.R;
import com.ourdevelops.ornids.constants.BaseApp;
import com.ourdevelops.ornids.item.HistoryItem;
import com.ourdevelops.ornids.json.AllTransResponseJson;
import com.ourdevelops.ornids.json.DetailRequestJson;
import com.ourdevelops.ornids.models.User;
import com.ourdevelops.ornids.utils.api.ServiceGenerator;
import com.ourdevelops.ornids.utils.api.service.UserService;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {


    private Context context;
    private ShimmerFrameLayout shimmer;
    private RecyclerView recycle;
    private HistoryItem historyItem;
    private RelativeLayout rlnodata;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_recycle, container, false);
        context = getContext();
        shimmer = getView.findViewById(R.id.shimmerwallet);
        recycle = getView.findViewById(R.id.inboxlist);
        rlnodata = getView.findViewById(R.id.rlnodata);
        Toolbar toolbar = getView.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new GridLayoutManager(context, 1));

        return getView;
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

    private void getdatatrans() {
        shimmershow();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(loginUser.getId());
        userService.history(param).enqueue(new Callback<AllTransResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AllTransResponseJson> call, @NonNull Response<AllTransResponseJson> response) {
                if (response.isSuccessful()) {
                    shimmertutup();
                    historyItem = new HistoryItem(context, Objects.requireNonNull(response.body()).getData(), R.layout.item_order);
                    recycle.setAdapter(historyItem);
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
            public void onFailure(@NonNull Call<AllTransResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getdatatrans();
    }
}
