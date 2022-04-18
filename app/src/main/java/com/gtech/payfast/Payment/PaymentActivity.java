package com.gtech.payfast.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.gtech.payfast.Activity.QrActivity;
import com.gtech.payfast.Activity.SVP.StoreValuePass;
import com.gtech.payfast.Activity.TP.TripPass;
import com.gtech.payfast.BuildConfig;
import com.gtech.payfast.Model.IssueToken.Data;
import com.gtech.payfast.Model.IssueToken.Issue;
import com.gtech.payfast.Model.IssueToken.Payment;
import com.gtech.payfast.Model.IssueToken.Response.IssueResponse;
import com.gtech.payfast.Model.Pass.Trip;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.SVP.SVDashboard;
import com.gtech.payfast.Model.Ticket.ProcessedTicket;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityPaymentBinding;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View PaymentView = binding.getRoot();
        setContentView(PaymentView);

        // ON PAYMENT SUCCESS
        binding.PaymentSuccess.setOnClickListener(view -> {

            binding.PaymentProgressBar.setVisibility(View.VISIBLE);
            binding.PaymentSuccess.setVisibility(View.GONE);
            binding.PaymentFailed.setVisibility(View.GONE);

            switch (getIntent().getStringExtra("PAYMENT_TYPE")) {
                case "1":
                    processTicket();
                    break;
                case "2":
                    processSVP();
                    break;
                case "4":
                    processTP();
                    break;
            }

        });

    }

    // Init processing SVP
    private void processSVP() {
        String ORDER_ID;

        ORDER_ID = getIntent().getStringExtra("ORDER_ID");

        Call<ResponseModel> processSVPCall = ApiController.getInstance().apiInterface().processSVP(ORDER_ID);
        processSVPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                Gson gson = new Gson();
                Log.e("PROCESS_SVP_REQUEST", gson.toJson(ORDER_ID));
                Log.e("PROCESS_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // SVP pass has been processed
                        // Redirect to SVP Dashboard
                        startActivity(new Intent(PaymentActivity.this, StoreValuePass.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processTP() {
        String ORDER_ID;

        ORDER_ID = getIntent().getStringExtra("ORDER_ID");

        Call<ResponseModel> processSVPCall = ApiController.getInstance().apiInterface().processSVP(ORDER_ID);
        processSVPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                Gson gson = new Gson();
                Log.e("PROCESS_SVP_REQUEST", gson.toJson(ORDER_ID));
                Log.e("PROCESS_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // TP pass has been processed
                        // Redirect to TP Dashboard
                        startActivity(new Intent(PaymentActivity.this, TripPass.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Process QR Mobile Ticket
    private void processTicket() {
        String ORDER_ID;
        ORDER_ID = getIntent().getStringExtra("ORDER_ID");

        Call<ProcessedTicket> processedTicket = ApiController.getInstance().apiInterface().processTicket(ORDER_ID);
        processedTicket.enqueue(new Callback<ProcessedTicket>() {
            @Override
            public void onResponse(@NonNull Call<ProcessedTicket> call, @NonNull Response<ProcessedTicket> response) {
                Gson gson = new Gson();
                Log.e("ISSUE_QR_REQUEST", gson.toJson(ORDER_ID));
                Log.e("ISSUE_QR_RESPONSE", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().getStatus() && (response.body().getProduct_id() == 1 ||  response.body().getProduct_id() == 2)) {
                        // Payment processed
                        Intent intent = new Intent(PaymentActivity.this, QrActivity.class);
                        intent.putExtra("ORDER_ID", response.body().getOrder_id());
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ProcessedTicket> call, @NonNull Throwable t) {

            }
        });
    }

}