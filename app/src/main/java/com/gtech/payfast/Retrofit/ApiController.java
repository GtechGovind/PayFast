package com.gtech.payfast.Retrofit;



import com.gtech.payfast.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiController {

    private static final String URL = BuildConfig.HOST_URL;
    private static ApiController apiController;
    private static Retrofit retrofit;

    ApiController() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static synchronized ApiController getInstance() {

        if (apiController == null) apiController = new ApiController();
        return apiController;

    }

    public ApiInterface apiInterface() {
        return retrofit.create(ApiInterface.class);
    }
}
