package com.gtech.payfast.Retrofit;


import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.Config.Fare;
import com.gtech.payfast.Model.Config.Station;
import com.gtech.payfast.Model.Fetch.Qr;
import com.gtech.payfast.Model.IssueToken.Data;
import com.gtech.payfast.Model.IssueToken.Issue;
import com.gtech.payfast.Model.IssueToken.Response.IssueResponse;
import com.gtech.payfast.Model.Order;
import com.gtech.payfast.Model.Pass.Trip;
import com.gtech.payfast.Model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    /********************************** USER **************************************/
    @Headers("Accept:application/json")
    @POST("api/user")
    Call<ResponseModel> verifyUser(@Body User user);

    @Headers("Accept:application/json")
    @POST("api/user/add")
    Call<ResponseModel> registerUser(@Body User user);

    /********************************** CONFIG DATA **************************************/
    // GET CONFIG DATA
    @Headers("Accept:application/json")
    @GET("api/stations")
    Call<List<Station>> getStations();

    @Headers("Accept:application/json")
    @GET("api/fares")
    Call<List<Fare>> getFare();

    /********************************** ODER **************************************/
    //CREATE ORDER
    @Headers("Accept:application/json")
    @POST("api/order/add")
    Call<ResponseModel> createNewOrder(@Body Order order);

    /********************************** MOBILE QR **************************************/
    @Headers("Accept:application/json")
    @POST("api/qr/new")
    Call<IssueResponse> issueMobileQrToken(@Body Issue issue);

    @Headers("Accept:application/json")
    @POST("api/qr")
    Call<ResponseModel> getQrs(@Body Order order);

    /********************************** STORE VALUE PASS **************************************/
    @Headers("Accept:application/json")
    @POST("api/pass")
    Call<ResponseModel> isUserHasPass(@Body Order order);

    @Headers("Accept:application/json")
    @POST("api/pass/new")
    Call<IssueResponse> issueSVP(@Body Issue issue);

    @Headers("Accept:application/json")
    @POST("api/pass/trip/new")
    Call<IssueResponse> issueSVPTrip(@Body Trip data);

    @Headers("Accept:application/json")
    @POST("api/pass/reload")
    Call<IssueResponse> reloadSVP(@Body Issue reloadSVP);

}
