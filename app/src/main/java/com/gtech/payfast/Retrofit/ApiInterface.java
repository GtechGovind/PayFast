package com.gtech.payfast.Retrofit;


import com.gtech.payfast.Activity.GRA;
import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.Config.FareRequest;
import com.gtech.payfast.Model.Config.Fare;
import com.gtech.payfast.Model.Config.StationsResponse;
import com.gtech.payfast.Model.GRA.GRAStatus;
import com.gtech.payfast.Model.GRA.GRATicket;
import com.gtech.payfast.Model.RefundDetail;
import com.gtech.payfast.Model.SVP.CreateSVPass;
import com.gtech.payfast.Model.SVP.ReloadSVPass;
import com.gtech.payfast.Model.SVP.SVDashboard;
import com.gtech.payfast.Model.SVP.SVStatus;
import com.gtech.payfast.Model.TP.CreateTP;
import com.gtech.payfast.Model.TP.ReloadTP;
import com.gtech.payfast.Model.TP.TPDashboard;
import com.gtech.payfast.Model.TP.TPStatus;
import com.gtech.payfast.Model.Ticket.ProcessedTicket;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.Ticket.Order;
import com.gtech.payfast.Model.Ticket.Ticket;
import com.gtech.payfast.Model.Ticket.UpdateTicketDashboard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    /********************************** USER **************************************/
    @Headers("Accept:application/json")
    @GET("api/user/check/{phone-number}")
    Call<ResponseModel> checkUser(@Path("phone-number") String mobile);

    @Headers("Accept:application/json")
    @POST("api/user/create")
    Call<ResponseModel> registerUser(@Body User user);

    /********************************** CONFIG DATA **************************************/
    // GET CONFIG DATA
    @Headers("Accept:application/json")
    @GET("api/get/station")
    Call<StationsResponse> getStations();

    @Headers("Accept:application/json")
    @POST("api/get/fare")
    Call<Fare> getFare(@Body FareRequest fare);

    /********************************** STORE VALUE PASS **************************************/

    @Headers("Accept:application/json")
    @GET("api/sv/canIssuePass/{pax-mobile}")
    Call<ResponseModel> canIssuePass(@Path("pax-mobile") String mobile);

    @Headers("Accept:application/json")
    @GET("api/sv/dashboard/{pax-id}")
    Call<SVDashboard> updateSVDashboard(@Path("pax-id") String paxId);

    @Headers("Accept:application/json")
    @POST("api/sv/create")
    Call<ResponseModel> createSVPass(@Body CreateSVPass createSVPass);

    @Headers("Accept:application/json")
    @GET("api/processing/init/{order-id}")
    Call<ResponseModel> processSVP(@Path("order-id") String orderId);


    @Headers("Accept:application/json")
    @GET("api/sv/trip/{order-id}")
    Call<ResponseModel> generateSVTrip(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @GET("api/sv/status/{ms-qr-no}")
    Call<SVStatus> getSVStatus(@Path("ms-qr-no") String msQrNo);

    @Headers("Accept:application/json")
    @GET("api/sv/reload/status/{order-id}")
    Call<ResponseModel> canReloadSVP(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @POST("api/sv/reload")
    Call<ResponseModel> reloadSVP(@Body ReloadSVPass svPass);

    /* *********************************** TICKET ********************************************/
    @Headers("Accept:application/json")
    @POST("api/ticket/create")
    Call<ResponseModel> createTicket(@Body Order order);

    @Headers("Accept:application/json")
    @GET("api/processing/init/{order-id}")
    Call<ProcessedTicket> processTicket(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @GET("api/ticket/view/{order-id}")
    Call<Ticket> viewTicket(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @GET("api/ticket/dashboard/{pax-id}")
    Call<UpdateTicketDashboard> updateTicketDashboard(@Path("pax-id") String paxId);

    @Headers("Accept:application/json")
    @GET("api/ticket/status/{pax-id}")
    Call<ResponseModel> updateTicketStatus(@Path("pax-id") String paxId);

    /* ******************************** TRIP PASS *******************************************/
    @Headers("Accept:application/json")
    @GET("/api/tp/status/{ms-qr-no}")
    Call<TPStatus> getTPStatus(@Path("ms-qr-no") String msQrNo);

    @Headers("Accept:application/json")
    @GET("/api/tp/dashboard/{pax-id}")
    Call<TPDashboard> updateTPDashboard(@Path("pax-id") String paxId);

    @Headers("Accept:application/json")
    @GET("api/tp/canIssuePass/{pax-mobile}")
    Call<ResponseModel> canIssueTP(@Path("pax-mobile") String mobile);

    @Headers("Accept:application/json")
    @GET("api/tp/trip/{order-id}")
    Call<ResponseModel> generateTripTP(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @GET("api/tp/reload/status/{order-id}")
    Call<ResponseModel> canReloadTP(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @POST("api/tp/reload")
    Call<ResponseModel> reloadTP(@Body ReloadTP reloadTP);

    @Headers("Accept:application/json")
    @GET("api/refund/ticket/{order-id}")
    Call<ResponseModel> refundTP(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @GET("api/refund/{order-id}")
    Call<RefundDetail> getRefundDetails(@Path("order-id") String orderId);

    @Headers("Accept:application/json")
    @POST("api/tp/create")
    Call<ResponseModel> createTP(@Body CreateTP createTP);

    /* ******************************* GRA *********************************** */
    @Headers("Accept:application/json")
    @GET("api/gra/{sl-qr-no}/{station-id}")
    Call<GRAStatus> graStatus(@Path("sl-qr-no") String slQrNo, @Path("station-id") String stationId);

    @Headers("Accept:application/json")
    @POST("api/gra")
    Call<ResponseModel> getGraTicket(@Body GRATicket graTicket);
}
