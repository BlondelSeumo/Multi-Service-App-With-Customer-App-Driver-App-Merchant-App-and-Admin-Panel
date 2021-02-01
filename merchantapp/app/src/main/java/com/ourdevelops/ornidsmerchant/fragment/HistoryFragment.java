package com.ourdevelops.ornidsmerchant.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.activity.IntroActivity;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.item.OrderItem;
import com.ourdevelops.ornidsmerchant.json.HistoryRequestJson;
import com.ourdevelops.ornidsmerchant.json.HistoryResponseJson;
import com.ourdevelops.ornidsmerchant.models.TransMerchantModel;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.models.fcm.DriverResponse;
import com.ourdevelops.ornidsmerchant.utils.Utility;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HistoryFragment extends Fragment {

    private Context context;
    private TextView pemasukan, harian, bulanan, tahunan;
    private RecyclerView historiorder;
    private OrderItem orderitem;
    private ShimmerFrameLayout historyshimmer;
    private List<TransMerchantModel> order;
    private RelativeLayout rlnodata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_history, container, false);
        context = getContext();

        pemasukan = getView.findViewById(R.id.pemasukan);
        harian = getView.findViewById(R.id.harian);
        bulanan = getView.findViewById(R.id.bulanan);
        tahunan = getView.findViewById(R.id.tahunan);
        historiorder = getView.findViewById(R.id.hisotriorder);
        historyshimmer = getView.findViewById(R.id.shimmerhistory);
        rlnodata = getView.findViewById(R.id.rlnodata);

        historiorder.setHasFixedSize(true);
        historiorder.setNestedScrollingEnabled(false);
        historiorder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return getView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    private void shimmershow() {
        historiorder.setVisibility(View.GONE);
        historyshimmer.startShimmerAnimation();
        historyshimmer.setVisibility(View.VISIBLE);

    }

    private void shimmertutup() {
        historyshimmer.stopShimmerAnimation();
        historyshimmer.setVisibility(View.GONE);
        historiorder.setVisibility(View.VISIBLE);
    }

    private void getdata() {
        shimmershow();
        if (order !=null) {
            order.clear();
        }
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        HistoryRequestJson param = new HistoryRequestJson();
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        param.setNotelepon(loginUser.getNoTelepon());
        param.setIdmerchant(loginUser.getId_merchant());
        param.setDay(formattedDate);
        merchantService.history(param).enqueue(new Callback<HistoryResponseJson>() {
            @Override
            public void onResponse(Call<HistoryResponseJson> call, Response<HistoryResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        order = response.body().getData();
                        shimmertutup();
                        Utility.currencyTXT(harian,response.body().getDaily(),context);
                        Utility.currencyTXT(bulanan,response.body().getMonthly(),context);
                        Utility.currencyTXT(tahunan,response.body().getYear(),context);
                        Utility.currencyTXT(pemasukan,response.body().getEarning(),context);
                        orderitem = new OrderItem(context, order, R.layout.item_order);
                        historiorder.setAdapter(orderitem);
                        if (response.body().getData().isEmpty()) {
                            historiorder.setVisibility(View.GONE);
                            rlnodata.setVisibility(View.VISIBLE);
                        } else {
                            historiorder.setVisibility(View.VISIBLE);
                            rlnodata.setVisibility(View.GONE);
                        }
                    } else {
                        Realm realm = BaseApp.getInstance(context).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(getContext(), IntroActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryResponseJson> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("IN PROGRESS", response.getResponse());
        if (response.getResponse().equals("2") || response.getResponse().equals("3") || response.getResponse().equals("5")) {
            getdata();
        }

    }
}
