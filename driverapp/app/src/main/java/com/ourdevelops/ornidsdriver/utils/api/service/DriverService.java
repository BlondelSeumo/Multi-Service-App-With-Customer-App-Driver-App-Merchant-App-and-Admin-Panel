package com.ourdevelops.ornidsdriver.utils.api.service;

import com.ourdevelops.ornidsdriver.json.AcceptRequestJson;
import com.ourdevelops.ornidsdriver.json.AcceptResponseJson;
import com.ourdevelops.ornidsdriver.json.BankResponseJson;
import com.ourdevelops.ornidsdriver.json.ChangePassRequestJson;
import com.ourdevelops.ornidsdriver.json.GetOnRequestJson;
import com.ourdevelops.ornidsdriver.json.JobResponseJson;
import com.ourdevelops.ornidsdriver.json.StripeRequestJson;
import com.ourdevelops.ornidsdriver.json.UpdateLocationRequestJson;
import com.ourdevelops.ornidsdriver.json.AllTransResponseJson;
import com.ourdevelops.ornidsdriver.json.DetailRequestJson;
import com.ourdevelops.ornidsdriver.json.DetailTransResponseJson;
import com.ourdevelops.ornidsdriver.json.EditVehicleRequestJson;
import com.ourdevelops.ornidsdriver.json.EditprofileRequestJson;
import com.ourdevelops.ornidsdriver.json.GetHomeRequestJson;
import com.ourdevelops.ornidsdriver.json.GetHomeResponseJson;
import com.ourdevelops.ornidsdriver.json.LoginRequestJson;
import com.ourdevelops.ornidsdriver.json.LoginResponseJson;
import com.ourdevelops.ornidsdriver.json.PrivacyRequestJson;
import com.ourdevelops.ornidsdriver.json.PrivacyResponseJson;
import com.ourdevelops.ornidsdriver.json.RegisterRequestJson;
import com.ourdevelops.ornidsdriver.json.RegisterResponseJson;
import com.ourdevelops.ornidsdriver.json.ResponseJson;
import com.ourdevelops.ornidsdriver.json.TopupRequestJson;
import com.ourdevelops.ornidsdriver.json.TopupResponseJson;
import com.ourdevelops.ornidsdriver.json.VerifyRequestJson;
import com.ourdevelops.ornidsdriver.json.WalletRequestJson;
import com.ourdevelops.ornidsdriver.json.WalletResponseJson;
import com.ourdevelops.ornidsdriver.json.WithdrawRequestJson;
import com.ourdevelops.ornidsdriver.json.WithdrawResponseJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface DriverService {

    @POST("driver/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("driver/update_location")
    Call<ResponseJson> updatelocation(@Body UpdateLocationRequestJson param);

    @POST("driver/syncronizing_account")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("driver/logout")
    Call<GetHomeResponseJson> logout(@Body GetHomeRequestJson param);

    @POST("driver/turning_on")
    Call<ResponseJson> turnon(@Body GetOnRequestJson param);

    @POST("driver/accept")
    Call<AcceptResponseJson> accept(@Body AcceptRequestJson param);

    @POST("driver/start")
    Call<AcceptResponseJson> startrequest(@Body AcceptRequestJson param);

    @POST("driver/finish")
    Call<AcceptResponseJson> finishrequest(@Body AcceptRequestJson param);

    @POST("driver/edit_profile")
    Call<LoginResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("driver/edit_kendaraan")
    Call<LoginResponseJson> editKendaraan(@Body EditVehicleRequestJson param);

    @POST("driver/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("driver/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("driver/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("driver/register_driver")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @POST("customerapi/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("driver/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);

    @POST("driver/job")
    Call<JobResponseJson> job();


    @POST("customerapi/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("customerapi/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("driver/withdraw")
    Call<WithdrawResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("customerapi/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("driver/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("driver/verifycode")
    Call<ResponseJson> verifycode(@Body VerifyRequestJson param);

    @POST("driver/stripeaction")
    Call<ResponseJson> actionstripe(@Body StripeRequestJson param);

    @POST("driver/intentstripe")
    Call<ResponseJson> intentstripe(@Body StripeRequestJson param);


}
