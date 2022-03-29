package com.gtech.payfast.Activity.TP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApi;
import com.google.gson.Gson;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.TP.TPDashboard;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityTripPassBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripPass extends AppCompatActivity {
    private ActivityTripPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTripPassBinding.inflate(getLayoutInflater());
        View TripPassView = binding.getRoot();
        setContentView(TripPassView);

        setBasicConfig();
        updateDashboard();
        // UPDATE TRIP PASS DASHBOARD
        // CHECK FOR TRIP PASS' STATUS
        // IF NO TRIP PASS
        //          - CHECK IF TRIP PASS CAN BE ISSUED
        //          - CREATE TRIP PASS
        //          - PROCESS PAYMENT
        //          - GENERATE TRIP
        // ELSE
        //          - GENERATE TRIP
        //          - RELOAD PASS
    }

    private void updateDashboard() {
        binding.TPassProgressBar.setVisibility(View.VISIBLE);
        String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
        Call<TPDashboard> updateTPDashboard = ApiController.getInstance().apiInterface().updateTPDashboard(PAX_ID);

        updateTPDashboard.enqueue(new Callback<TPDashboard>() {
            @Override
            public void onResponse(@NonNull Call<TPDashboard> call, @NonNull Response<TPDashboard> response) {
                Gson gson = new Gson();
                Log.e("UPDATE_TP_DASHBOARD_REQ", gson.toJson(PAX_ID));
                Log.e("UPDATE_TP_DASHBOARD_REP", gson.toJson(response.body()));

                binding.TPassProgressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // Pass exists
                        // Update pass
                    } else {
                        // No Trip Pass
                        // Check if trip pass can be issued
                        canIssuePass();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<TPDashboard> call, @NonNull Throwable t) {

            }
        });
    }

    private void canIssuePass() {
        String mobile = SharedPrefUtils.getStringData(this, "NUMBER");
        Call<ResponseModel> canIssuePassCall = ApiController.getInstance().apiInterface().canIssueTP(mobile);
        canIssuePassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {

                        // Pass can be issued
                        // Start pass generation activity
                        startActivity(new Intent(TripPass.this, TripPassGeneration.class));
                    } else {
                        // Pass cannot be issued
                        Toast.makeText(TripPass.this, "Pass cannot be issued", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Request failed
                    Toast.makeText(TripPass.this, "There was a problem fetching issue Pass request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TripPass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBasicConfig() {
        binding.Heading.setText(R.string.trip_pass_heading);
        binding.TPassProgressBar.setVisibility(View.GONE);
    }
}