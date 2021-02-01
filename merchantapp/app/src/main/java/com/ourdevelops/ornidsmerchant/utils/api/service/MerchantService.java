package com.ourdevelops.ornidsmerchant.utils.api.service;

import com.ourdevelops.ornidsmerchant.json.ActiveCatRequestJson;
import com.ourdevelops.ornidsmerchant.json.AddEditItemRequestJson;
import com.ourdevelops.ornidsmerchant.json.AddEditKategoriRequestJson;
import com.ourdevelops.ornidsmerchant.json.BankResponseJson;
import com.ourdevelops.ornidsmerchant.json.ChangePassRequestJson;
import com.ourdevelops.ornidsmerchant.json.DetailRequestJson;
import com.ourdevelops.ornidsmerchant.json.DetailTransResponseJson;
import com.ourdevelops.ornidsmerchant.json.EditMerchantRequestJson;
import com.ourdevelops.ornidsmerchant.json.EditProfileRequestJson;
import com.ourdevelops.ornidsmerchant.json.GetServiceResponseJson;
import com.ourdevelops.ornidsmerchant.json.GetOnRequestJson;
import com.ourdevelops.ornidsmerchant.json.HistoryRequestJson;
import com.ourdevelops.ornidsmerchant.json.HistoryResponseJson;
import com.ourdevelops.ornidsmerchant.json.HomeRequestJson;
import com.ourdevelops.ornidsmerchant.json.HomeResponseJson;
import com.ourdevelops.ornidsmerchant.json.ItemRequestJson;
import com.ourdevelops.ornidsmerchant.json.ItemResponseJson;
import com.ourdevelops.ornidsmerchant.json.CategoryRequestJson;
import com.ourdevelops.ornidsmerchant.json.CategoryResponseJson;
import com.ourdevelops.ornidsmerchant.json.LoginRequestJson;
import com.ourdevelops.ornidsmerchant.json.LoginResponseJson;
import com.ourdevelops.ornidsmerchant.json.PrivacyRequestJson;
import com.ourdevelops.ornidsmerchant.json.PrivacyResponseJson;
import com.ourdevelops.ornidsmerchant.json.RegisterRequestJson;
import com.ourdevelops.ornidsmerchant.json.RegisterResponseJson;
import com.ourdevelops.ornidsmerchant.json.ResponseJson;
import com.ourdevelops.ornidsmerchant.json.StripeRequestJson;
import com.ourdevelops.ornidsmerchant.json.TopupRequestJson;
import com.ourdevelops.ornidsmerchant.json.TopupResponseJson;
import com.ourdevelops.ornidsmerchant.json.WalletRequestJson;
import com.ourdevelops.ornidsmerchant.json.WalletResponseJson;
import com.ourdevelops.ornidsmerchant.json.WithdrawRequestJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface MerchantService {

    @GET("partnerapi/kategorimerchant")
    Call<GetServiceResponseJson> getFitur();

    @POST("customerapi/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("partnerapi/kategorimerchantbyfitur")
    Call<GetServiceResponseJson> getKategori(@Body HistoryRequestJson param);

    @POST("partnerapi/onoff")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("partnerapi/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("partnerapi/register_merchant")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("partnerapi/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("customerapi/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("partnerapi/edit_profile")
    Call<LoginResponseJson> editprofile(@Body EditProfileRequestJson param);

    @POST("partnerapi/edit_merchant")
    Call<LoginResponseJson> editmerchant(@Body EditMerchantRequestJson param);

    @POST("partnerapi/home")
    Call<HomeResponseJson> home(@Body HomeRequestJson param);

    @POST("partnerapi/history")
    Call<HistoryResponseJson> history(@Body HistoryRequestJson param);

    @POST("partnerapi/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("partnerapi/category")
    Call<CategoryResponseJson> category(@Body CategoryRequestJson param);

    @POST("partnerapi/item")
    Call<ItemResponseJson> itemlist(@Body ItemRequestJson param);

    @POST("partnerapi/active_kategori")
    Call<ResponseJson> activekategori(@Body ActiveCatRequestJson param);

    @POST("partnerapi/active_item")
    Call<ResponseJson> activeitem(@Body ActiveCatRequestJson param);

    @POST("partnerapi/add_kategori")
    Call<ResponseJson> addkategori(@Body AddEditKategoriRequestJson param);

    @POST("partnerapi/edit_kategori")
    Call<ResponseJson> editkategori(@Body AddEditKategoriRequestJson param);

    @POST("partnerapi/delete_kategori")
    Call<ResponseJson> deletekategori(@Body AddEditKategoriRequestJson param);

    @POST("partnerapi/add_item")
    Call<ResponseJson> additem(@Body AddEditItemRequestJson param);

    @POST("partnerapi/edit_item")
    Call<ResponseJson> edititem(@Body AddEditItemRequestJson param);

    @POST("partnerapi/delete_item")
    Call<ResponseJson> deleteitem(@Body AddEditItemRequestJson param);

    @POST("customerapi/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("partnerapi/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("customerapi/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("partnerapi/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("partnerapi/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("partnerapi/stripeaction")
    Call<ResponseJson> actionstripe(@Body StripeRequestJson param);

    @POST("partnerapi/intentstripe")
    Call<ResponseJson> intentstripe(@Body StripeRequestJson param);

}
