package com.ourdevelops.ornids.utils.api.service;

import com.ourdevelops.ornids.json.CheckStatusTransRequest;
import com.ourdevelops.ornids.json.CheckStatusTransResponse;
import com.ourdevelops.ornids.json.DetailRequestJson;
import com.ourdevelops.ornids.json.DetailTransResponseJson;
import com.ourdevelops.ornids.json.GetNearRideCarRequestJson;
import com.ourdevelops.ornids.json.GetNearRideCarResponseJson;
import com.ourdevelops.ornids.json.ItemRequestJson;
import com.ourdevelops.ornids.json.LocationDriverRequest;
import com.ourdevelops.ornids.json.LocationDriverResponse;
import com.ourdevelops.ornids.json.RideCarRequestJson;
import com.ourdevelops.ornids.json.RideCarResponseJson;
import com.ourdevelops.ornids.json.SendRequestJson;
import com.ourdevelops.ornids.json.SendResponseJson;
import com.ourdevelops.ornids.json.fcm.CancelBookRequestJson;
import com.ourdevelops.ornids.json.fcm.CancelBookResponseJson;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public interface BookService {

    @POST("customerapi/list_ride")
    Call<GetNearRideCarResponseJson> getNearRide(@Body GetNearRideCarRequestJson param);

    @POST("customerapi/list_car")
    Call<GetNearRideCarResponseJson> getNearCar(@Body GetNearRideCarRequestJson param);

    @POST("customerapi/request_transaksi")
    Call<RideCarResponseJson> requestTransaksi(@Body RideCarRequestJson param);

    @POST("customerapi/inserttransaksimerchant")
    Call<RideCarResponseJson> requestTransaksiMerchant(@Body ItemRequestJson param);

    @POST("customerapi/request_transaksi_send")
    Call<SendResponseJson> requestTransaksisend(@Body SendRequestJson param);

    @POST("customerapi/check_status_transaksi")
    Call<CheckStatusTransResponse> checkStatusTransaksi(@Body CheckStatusTransRequest param);

    @POST("customerapi/user_cancel")
    Call<CancelBookResponseJson> cancelOrder(@Body CancelBookRequestJson param);

    @POST("customerapi/liat_lokasi_driver")
    Call<LocationDriverResponse> liatLokasiDriver(@Body LocationDriverRequest param);

    @POST("customerapi/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);


}
