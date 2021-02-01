package com.ourdevelops.ornids.utils.api.service;

import com.ourdevelops.ornids.json.AllMerchantByNearResponseJson;
import com.ourdevelops.ornids.json.AllMerchantbyCatRequestJson;
import com.ourdevelops.ornids.json.AllTransResponseJson;
import com.ourdevelops.ornids.json.BankResponseJson;
import com.ourdevelops.ornids.json.NewsDetailRequestJson;
import com.ourdevelops.ornids.json.NewsDetailResponseJson;
import com.ourdevelops.ornids.json.ChangePassRequestJson;
import com.ourdevelops.ornids.json.DetailRequestJson;
import com.ourdevelops.ornids.json.EditprofileRequestJson;
import com.ourdevelops.ornids.json.GetAllMerchantbyCatRequestJson;
import com.ourdevelops.ornids.json.GetServiceResponseJson;
import com.ourdevelops.ornids.json.GetHomeRequestJson;
import com.ourdevelops.ornids.json.GetHomeResponseJson;
import com.ourdevelops.ornids.json.GetMerchantbyCatRequestJson;
import com.ourdevelops.ornids.json.LoginRequestJson;
import com.ourdevelops.ornids.json.LoginResponseJson;
import com.ourdevelops.ornids.json.MerchantByCatResponseJson;
import com.ourdevelops.ornids.json.MerchantByIdResponseJson;
import com.ourdevelops.ornids.json.MerchantByNearResponseJson;
import com.ourdevelops.ornids.json.MerchantbyIdRequestJson;
import com.ourdevelops.ornids.json.PrivacyRequestJson;
import com.ourdevelops.ornids.json.PrivacyResponseJson;
import com.ourdevelops.ornids.json.PromoRequestJson;
import com.ourdevelops.ornids.json.PromoResponseJson;
import com.ourdevelops.ornids.json.RateRequestJson;
import com.ourdevelops.ornids.json.RateResponseJson;
import com.ourdevelops.ornids.json.RegisterRequestJson;
import com.ourdevelops.ornids.json.RegisterResponseJson;
import com.ourdevelops.ornids.json.ResponseJson;
import com.ourdevelops.ornids.json.SearchMerchantbyCatRequestJson;
import com.ourdevelops.ornids.json.StripeRequestJson;
import com.ourdevelops.ornids.json.TopupRequestJson;
import com.ourdevelops.ornids.json.TopupResponseJson;
import com.ourdevelops.ornids.json.WalletRequestJson;
import com.ourdevelops.ornids.json.WalletResponseJson;
import com.ourdevelops.ornids.json.WithdrawRequestJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface UserService {

    @POST("customerapi/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("customerapi/kodepromo")
    Call<PromoResponseJson> promocode(@Body PromoRequestJson param);

    @POST("customerapi/listkodepromo")
    Call<PromoResponseJson> listpromocode(@Body PromoRequestJson param);

    @POST("customerapi/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("customerapi/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("customerapi/register_user")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @GET("customerapi/detail_fitur")
    Call<GetServiceResponseJson> getFitur();

    @POST("customerapi/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("customerapi/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("customerapi/home")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("customerapi/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("customerapi/stripeaction")
    Call<ResponseJson> actionstripe(@Body StripeRequestJson param);

    @POST("customerapi/intentstripe")
    Call<ResponseJson> intentstripe(@Body StripeRequestJson param);

    @POST("customerapi/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("customerapi/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("customerapi/rate_driver")
    Call<RateResponseJson> rateDriver(@Body RateRequestJson param);

    @POST("customerapi/edit_profile")
    Call<RegisterResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("customerapi/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("customerapi/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("customerapi/detail_berita")
    Call<NewsDetailResponseJson> beritadetail(@Body NewsDetailRequestJson param);

    @POST("customerapi/all_berita")
    Call<NewsDetailResponseJson> allberita(@Body NewsDetailRequestJson param);

    @POST("customerapi/merchantbykategoripromo")
    Call<MerchantByCatResponseJson> getmerchanbycat(@Body GetMerchantbyCatRequestJson param);

    @POST("customerapi/merchantbykategori")
    Call<MerchantByNearResponseJson> getmerchanbynear(@Body GetMerchantbyCatRequestJson param);

    @POST("customerapi/allmerchantbykategori")
    Call<AllMerchantByNearResponseJson> getallmerchanbynear(@Body GetAllMerchantbyCatRequestJson param);

    @POST("customerapi/itembykategori")
    Call<MerchantByIdResponseJson> getitembycat(@Body GetAllMerchantbyCatRequestJson param);

    @POST("customerapi/searchmerchant")
    Call<AllMerchantByNearResponseJson> searchmerchant(@Body SearchMerchantbyCatRequestJson param);

    @POST("customerapi/allmerchant")
    Call<AllMerchantByNearResponseJson> allmerchant(@Body AllMerchantbyCatRequestJson param);

    @POST("customerapi/merchantbyid")
    Call<MerchantByIdResponseJson> merchantbyid(@Body MerchantbyIdRequestJson param);


}
