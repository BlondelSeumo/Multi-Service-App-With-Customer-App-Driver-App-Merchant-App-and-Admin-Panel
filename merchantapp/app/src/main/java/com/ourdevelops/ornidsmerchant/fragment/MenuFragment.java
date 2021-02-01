package com.ourdevelops.ornidsmerchant.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ourdevelops.ornidsmerchant.R;
import com.ourdevelops.ornidsmerchant.activity.IntroActivity;
import com.ourdevelops.ornidsmerchant.constants.BaseApp;
import com.ourdevelops.ornidsmerchant.item.CategoryItem;
import com.ourdevelops.ornidsmerchant.item.CategoryNonItem;
import com.ourdevelops.ornidsmerchant.json.AddEditKategoriRequestJson;
import com.ourdevelops.ornidsmerchant.json.CategoryRequestJson;
import com.ourdevelops.ornidsmerchant.json.CategoryResponseJson;
import com.ourdevelops.ornidsmerchant.json.ResponseJson;
import com.ourdevelops.ornidsmerchant.models.CategoryItemModel;
import com.ourdevelops.ornidsmerchant.models.CategoryItemNonModel;
import com.ourdevelops.ornidsmerchant.models.User;
import com.ourdevelops.ornidsmerchant.utils.Log;
import com.ourdevelops.ornidsmerchant.utils.api.ServiceGenerator;
import com.ourdevelops.ornidsmerchant.utils.api.service.MerchantService;

import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    private Context context;
    private RecyclerView activecategory, nonactivecategory;
    private TextView itemada, itempromo, itemhabis;
    private ShimmerFrameLayout shimmercaton, shimmercatoff;
    private List<CategoryItemModel> order;
    private List<CategoryItemNonModel> ordernon;
    private CategoryItem categoryItem;
    private CategoryNonItem categorynonItem;
    private LinearLayout llactive, llnonactive;
    private RelativeLayout rlnodata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_menu, container, false);
        context = getContext();
        Button dialogaddcategory = getView.findViewById(R.id.buttonaddcategory);
        activecategory = getView.findViewById(R.id.activecategory);
        nonactivecategory = getView.findViewById(R.id.nonactivecategory);
        itemada = getView.findViewById(R.id.itemada);
        itempromo = getView.findViewById(R.id.itempromo);
        itemhabis = getView.findViewById(R.id.itemhabis);
        shimmercaton = getView.findViewById(R.id.shimmercaton);
        shimmercatoff = getView.findViewById(R.id.shimmercatoff);
        rlnodata = getView.findViewById(R.id.rlnodata);
        llactive = getView.findViewById(R.id.llactive);
        llnonactive = getView.findViewById(R.id.llnonactive);


        dialogaddcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcategory();
            }
        });

        activecategory.setHasFixedSize(true);
        activecategory.setNestedScrollingEnabled(false);
        activecategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        nonactivecategory.setHasFixedSize(true);
        nonactivecategory.setNestedScrollingEnabled(false);
        nonactivecategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        return getView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    private void shimmershow() {
        activecategory.setVisibility(View.GONE);
        shimmercaton.startShimmerAnimation();
        shimmercaton.setVisibility(View.VISIBLE);

        nonactivecategory.setVisibility(View.GONE);
        shimmercatoff.startShimmerAnimation();
        shimmercatoff.setVisibility(View.VISIBLE);

    }

    private void shimmertutup() {
        shimmercaton.stopShimmerAnimation();
        activecategory.setVisibility(View.VISIBLE);
        shimmercaton.setVisibility(View.GONE);

        shimmercatoff.stopShimmerAnimation();
        nonactivecategory.setVisibility(View.VISIBLE);
        shimmercatoff.setVisibility(View.GONE);
    }

    private void getdata() {
        shimmershow();
        if (order != null && ordernon != null) {
            order.clear();
            ordernon.clear();
        }
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        CategoryRequestJson param = new CategoryRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setIdmerchant(loginUser.getId_merchant());
        merchantService.category(param).enqueue(new Callback<CategoryResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<CategoryResponseJson> call, Response<CategoryResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        order = response.body().getData();
                        ordernon = response.body().getDatanon();
                        shimmertutup();

                        itemada.setText(response.body().getTotalitemactive() + " Item Available");
                        itemhabis.setText(response.body().getTotalitemnonactive() + " Item Out of Stock");
                        itempromo.setText(response.body().getTotalitempromo() + " Item Promo");
                        if (response.body().getData().isEmpty() && response.body().getDatanon().isEmpty()) {
                            rlnodata.setVisibility(View.VISIBLE);
                        } else {
                            rlnodata.setVisibility(View.GONE);
                        }
                        categoryItem = new CategoryItem(context, order, R.layout.item_category);
                        activecategory.setAdapter(categoryItem);
                        if (response.body().getData().isEmpty()) {
                            llactive.setVisibility(View.GONE);
                        } else {
                            llactive.setVisibility(View.VISIBLE);
                        }

                        categorynonItem = new CategoryNonItem(context, ordernon, R.layout.item_category);
                        nonactivecategory.setAdapter(categorynonItem);
                        if (response.body().getDatanon().isEmpty()) {
                            llnonactive.setVisibility(View.GONE);
                        } else {
                            llnonactive.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Realm realm = BaseApp.getInstance(context).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(context, IntroActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponseJson> call, Throwable t) {

            }
        });
    }


    private String Switchstring;
    private void addcategory() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_addcategory);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView close = dialog.findViewById(R.id.close);
        final EditText text = dialog.findViewById(R.id.textadd);
        SwitchCompat switchadd = dialog.findViewById(R.id.switchactive);
        final Button submit = dialog.findViewById(R.id.submit);


        switchadd.setChecked(true);
        Switchstring = "1";

        switchadd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Switchstring = "1";
                    } else {
                        Switchstring = "0";
                    }

                Log.e("switch",Switchstring);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please input Category Name!", Toast.LENGTH_SHORT).show();
                } else {
                    submit.setEnabled(false);
                    submit.setText("Please Wait...");
                    submit.setBackground(context.getResources().getDrawable(R.drawable.button_round_3));
                    User loginUser = BaseApp.getInstance(context).getLoginUser();
                    MerchantService merchantService = ServiceGenerator.createService(
                            MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
                    AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
                    param.setNotelepon(loginUser.getNoTelepon());
                    param.setId(loginUser.getId_merchant());
                    param.setNamakategori(text.getText().toString());
                    param.setStatus(Switchstring);
                    merchantService.addkategori(param).enqueue(new Callback<ResponseJson>() {
                        @Override
                        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                            if (response.isSuccessful()) {
                                if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                                    getdata();
                                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                } else {
                                    submit.setEnabled(true);
                                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                                    submit.setText("Add Category");
                                    submit.setBackground(context.getResources().getDrawable(R.drawable.button_round_1));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseJson> call, Throwable t) {
                            submit.setEnabled(true);
                            Toast.makeText(context, "Error Connection!", Toast.LENGTH_SHORT).show();
                            submit.setText("Add Category");
                            submit.setBackground(context.getResources().getDrawable(R.drawable.button_round_1));

                        }
                    });

                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
