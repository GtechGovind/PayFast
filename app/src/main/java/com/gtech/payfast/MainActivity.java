package com.gtech.payfast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtech.payfast.Activity.MainDashboard;
import com.gtech.payfast.Auth.LoginActivity;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.Config.StationsResponse;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DATABASE
        dbHelper = new DBHelper(this);

        // GET STATIONS
        getStations();

        // GET FARE
        // getFare();

        // SPLASH SCREEN
        new Handler().postDelayed(() -> {

            if (SharedPrefUtils.getStringData(this, "NUMBER") != null) {
                startActivity(new Intent(this, MainDashboard.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();
        }, 2000);

    }

    /*
    private void getFare() {

        Call<List<Fare>> getFares = ApiController.getInstance().apiInterface().getFare();
        getFares.enqueue(new Callback<List<Fare>>() {
            @Override
            public void onResponse(@NonNull Call<List<Fare>> call, @NonNull Response<List<Fare>> response) {

                Gson gson = new Gson();
                Log.e("STATIONS_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) dbHelper.insertIntoFares(response.body());
                else Toast.makeText(MainActivity.this, "Internal Server Error try after sometime!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<List<Fare>> call, @NonNull Throwable t) {

            }
        });

    }
    */

    private void getStations() {

        Call<StationsResponse> getStations = ApiController.getInstance().apiInterface().getStations();
        getStations.enqueue(new Callback<StationsResponse>() {
            @Override
            public void onResponse(@NonNull Call<StationsResponse> call, @NonNull Response<StationsResponse> response) {

                Gson gson = new Gson();
                Log.e("STATIONS_RESPONSE", gson.toJson(response.body()));
                if (response.body() != null) dbHelper.insertIntoStations(response.body().getStations());
                else Toast.makeText(MainActivity.this, "Internal Server Error try after sometime!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<StationsResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}