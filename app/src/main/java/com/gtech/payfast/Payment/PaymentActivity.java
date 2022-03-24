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
import com.gtech.payfast.BuildConfig;
import com.gtech.payfast.Model.IssueToken.Data;
import com.gtech.payfast.Model.IssueToken.Issue;
import com.gtech.payfast.Model.IssueToken.Payment;
import com.gtech.payfast.Model.IssueToken.Response.IssueResponse;
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
                    issueSVP();
                    break;
                case "3":
                    reloadSVP();
                    break;
            }

        });

    }

    // RELOAD STORE VALUE PASS
    private void reloadSVP() {

        String ORDER_NO, RECHARGE_AMOUNT, MASTER_TXN_ID;

        ORDER_NO = getIntent().getStringExtra("ODER_NO");
        RECHARGE_AMOUNT = getIntent().getStringExtra("RECHARGE_AMOUNT");
        MASTER_TXN_ID = getIntent().getStringExtra("MASTER_TXN_ID");

        Data data = new Data(
                Calendar.getInstance().getTime().toString(),
                SharedPrefUtils.getStringData(this, "EMAIL"),
                RECHARGE_AMOUNT,
                SharedPrefUtils.getStringData(this, "NUMBER"),
                SharedPrefUtils.getStringData(this, "NAME"),
                BuildConfig.OPERATION_RELOAD,
                BuildConfig.OPERATOR_ID,
                ORDER_NO,
                BuildConfig.TOKEN_TYPE_SVP,
                "0",
                MASTER_TXN_ID
        );

        Payment payment = new Payment(RECHARGE_AMOUNT, BuildConfig.PG_ID);

        Issue reloadSVP = new Issue(data, payment);

        Call<IssueResponse> reloadPass = ApiController.getInstance().apiInterface().reloadSVP(reloadSVP);
        reloadPass.enqueue(new Callback<IssueResponse>() {
            @Override
            public void onResponse(@NonNull Call<IssueResponse> call, @NonNull Response<IssueResponse> response) {

                Gson gson = new Gson();
                Log.e("RELOAD_SVP_REQUEST", gson.toJson(reloadPass));
                Log.e("RELOAD_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().getStatus().equals("OK")) startActivity(new Intent(PaymentActivity.this, StoreValuePass.class));
                    else Toast.makeText(PaymentActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    finish();

                } else Toast.makeText(PaymentActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<IssueResponse> call, @NonNull Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // STORE VALUE PASS
    private void issueSVP() {

        String ORDER_NO, RECHARGE_AMOUNT;

        ORDER_NO = getIntent().getStringExtra("ODER_NO");
        RECHARGE_AMOUNT = getIntent().getStringExtra("RECHARGE_AMOUNT");

        Data data = new Data(
                Calendar.getInstance().getTime().toString(),
                "null",
                SharedPrefUtils.getStringData(this, "EMAIL"),
                RECHARGE_AMOUNT,
                SharedPrefUtils.getStringData(this, "NUMBER"),
                SharedPrefUtils.getStringData(this, "NAME"),
                BuildConfig.OPERATION_ISSUE,
                BuildConfig.OPERATOR_ID,
                ORDER_NO,
                BuildConfig.QR_STORE_VALUE_PASS,
                "null",
                BuildConfig.SUPPORT_MOBILE_QR,
                BuildConfig.TOKEN_TYPE_SVP,
                "0"
        );

        Payment payment = new Payment(RECHARGE_AMOUNT, BuildConfig.PG_ID);

        Issue issue = new Issue(data, payment);

        Call<IssueResponse> issueSVP = ApiController.getInstance().apiInterface().issueSVP(issue);
        issueSVP.enqueue(new Callback<IssueResponse>() {
            @Override
            public void onResponse(@NonNull Call<IssueResponse> call, @NonNull Response<IssueResponse> response) {

                Gson gson = new Gson();
                Log.e("ISSUE_QR_REQUEST", gson.toJson(issue));
                Log.e("ISSUE_QR_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().getStatus().equals("OK")) {

                        startActivity(new Intent(PaymentActivity.this, StoreValuePass.class));
                        finish();

                    } else Toast.makeText(PaymentActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();

                } else Toast.makeText(PaymentActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<IssueResponse> call, @NonNull Throwable t) {
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

    // MOBILE QR TICKET
    private void issueMobileQrTicket() {

        String ORDER_NO, SOURCE_ID, TICKET_TYPE, TICKET_COUNT, DESTINATION_ID, TOTAL_FARE;

        ORDER_NO = getIntent().getStringExtra("ORDER_ID");
        SOURCE_ID = getIntent().getStringExtra("SOURCE_ID");
        TICKET_TYPE = getIntent().getStringExtra("TICKET_TYPE");
        TICKET_COUNT = getIntent().getStringExtra("TICKET_COUNT");
        DESTINATION_ID = getIntent().getStringExtra("DESTINATION_ID");
        TOTAL_FARE = getIntent().getStringExtra("TOTAL_FARE");

        Data data = new Data(
                Calendar.getInstance().getTime().toString(),
                DESTINATION_ID,
                SharedPrefUtils.getStringData(this, "EMAIL"),
                TOTAL_FARE,
                SharedPrefUtils.getStringData(this, "NUMBER"),
                SharedPrefUtils.getStringData(this, "NAME"),
                BuildConfig.OPERATION_ISSUE,
                BuildConfig.OPERATOR_ID,
                ORDER_NO,
                (TICKET_TYPE.equals("10")) ? BuildConfig.QR_SINGLE_JOURNEY : BuildConfig.QR_RETURN_JOURNEY,
                SOURCE_ID,
                BuildConfig.SUPPORT_MOBILE_QR,
                TICKET_TYPE,
                TICKET_COUNT
        );

        Payment payment = new Payment(TOTAL_FARE, BuildConfig.PG_ID);

        Issue issue = new Issue(data, payment);

        Call<IssueResponse> issueQrToken = ApiController.getInstance().apiInterface().issueMobileQrToken(issue);
        issueQrToken.enqueue(new Callback<IssueResponse>() {
            @Override
            public void onResponse(@NonNull Call<IssueResponse> call, @NonNull Response<IssueResponse> response) {

                Gson gson = new Gson();
                Log.e("ISSUE_QR_REQUEST", gson.toJson(issue));
                Log.e("ISSUE_QR_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().getStatus().equals("OK")) {

                        Intent intent = new Intent(PaymentActivity.this, QrActivity.class);
                        intent.putExtra("ORDER_NO", response.body().getOrder_no());
                        startActivity(intent);
                        finish();

                    } else Toast.makeText(PaymentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else Toast.makeText(PaymentActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<IssueResponse> call, @NonNull Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", t.getMessage());
            }
        });

    }

}