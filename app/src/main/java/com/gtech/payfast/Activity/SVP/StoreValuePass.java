package com.gtech.payfast.Activity.SVP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.gtech.payfast.Activity.QrActivity;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.BuildConfig;
import com.gtech.payfast.Model.IssueToken.Data;
import com.gtech.payfast.Model.IssueToken.Response.IssueResponse;
import com.gtech.payfast.Model.IssueToken.Response.ResponseData;
import com.gtech.payfast.Model.IssueToken.Response.Trips;
import com.gtech.payfast.Model.Order;
import com.gtech.payfast.Model.Pass.Trip;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.Status;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.OrderUtils;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityStoreValuePassBinding;
import com.gtech.payfast.databinding.ReloadBottomSheetLayoutBinding;

import java.text.MessageFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreValuePass extends AppCompatActivity {

    private ActivityStoreValuePassBinding binding;
    private BottomSheetDialog reloadDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreValuePassBinding.inflate(getLayoutInflater());
        View SVPView = binding.getRoot();
        setContentView(SVPView);

        // BASIC CONFIG
        setBasicConfig();

        // CHECK
        isUserHasPass();

    }

    // CHECK IF USER HAS A PASS OR NOT
    private void isUserHasPass() {

        Order order = new Order(SharedPrefUtils.getStringData(this, "NUMBER"), BuildConfig.TOKEN_TYPE_SVP);

        Call<ResponseModel> isUserHasPass = ApiController.getInstance().apiInterface().isUserHasPass(order);
        isUserHasPass.enqueue(new Callback<ResponseModel>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                Gson gson = new Gson();
                Log.e("STORE_VALUE_IS_PASS_REQUEST", gson.toJson(order));
                Log.e("STORE_VALUE_IS_PASS_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().isStatus()) {

                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.HasSVP.setVisibility(View.VISIBLE);
                        binding.HasSVPController.setVisibility(View.VISIBLE);
                        setHasSvp(response.body().getData());

                    } else {

                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.NoPass.setVisibility(View.VISIBLE);
                        binding.NoPassController.setVisibility(View.VISIBLE);
                        setDonutHaveSvp();

                    }

                } else
                    Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    // IF DON'T HAVE PASS
    private void setDonutHaveSvp() {

        binding.NoPass.setVisibility(View.VISIBLE);

        // INCREMENT COUNTER
        binding.AddButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(binding.RechargeAmount.getText().toString());
            if (ticket_count < 3000) {
                ticket_count = ticket_count + 100;
                binding.RechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // DECREMENT COUNTER
        binding.SubButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(binding.RechargeAmount.getText().toString());
            if (ticket_count > 100) {
                ticket_count = ticket_count - 100;
                binding.RechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // NEW PASS ORDER
        binding.OrderNewSVP.setOnClickListener(view -> CreateNewPassOrder());

    }

    // CREATE NEW STORE VALUE PASS ORDER
    private void CreateNewPassOrder() {

        binding.SPassProgressBar.setVisibility(View.VISIBLE);
        binding.NoPass.setVisibility(View.GONE);
        binding.OrderNewSVP.setVisibility(View.GONE);

        Order order = new Order(
                OrderUtils.getOID("SVP"),
                SharedPrefUtils.getStringData(this, "NUMBER"),
                null,
                null,
                BuildConfig.TOKEN_TYPE_SVP,
                null,
                binding.RechargeAmount.getText().toString(),
                BuildConfig.PG_ID,
                BuildConfig.OPERATOR_ID,
                Status.getInstance().getSTATUS_ORDER_CREATED(),
                Status.getInstance().getORDER_TYPE_NORMAL()
        );

        Call<ResponseModel> createSvpOrder = ApiController.getInstance().apiInterface().createNewOrder(order);
        createSvpOrder.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                Gson gson = new Gson();
                Log.e("ORDER_SVP_REQUEST", gson.toJson(order));
                Log.e("ORDER_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().isStatus()) {

                        Intent intent = new Intent(StoreValuePass.this, PaymentActivity.class);
                        intent.putExtra("PAYMENT_TYPE", "2");
                        intent.putExtra("RECHARGE_AMOUNT", binding.RechargeAmount.getText().toString());
                        intent.putExtra("ODER_NO", order.getOrder_no());
                        startActivity(intent);

                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.NoPass.setVisibility(View.VISIBLE);
                        binding.OrderNewSVP.setVisibility(View.VISIBLE);

                        finish();

                    } else {
                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.NoPass.setVisibility(View.VISIBLE);
                        binding.OrderNewSVP.setVisibility(View.VISIBLE);
                        Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // IF USER HAS PASS
    private void setHasSvp(ResponseData data) {

        binding.UserName.setText(SharedPrefUtils.getStringData(this, "NAME"));
        binding.MasterTxnId.setText(data.getMasterTxnId());
        binding.Balance.setText(MessageFormat.format("Balance: {0}", data.getBalance()));

        // SET GENERATE TRIP
        int TripsCounter = 0;

        for (Trips trip : data.getTrips())
            if (trip.getTokenStatus().equals("GENERATED") || trip.getTokenStatus().equals("IN_JOURNEY"))
                TripsCounter++;

        if (TripsCounter > 0) binding.ViewQrTicket.setVisibility(View.VISIBLE);
        else binding.GenerateTrip.setVisibility(View.VISIBLE);

        binding.GenerateTrip.setOnClickListener(view -> generateNewTrip(data));

        binding.ViewQrTicket.setOnClickListener(view -> {
            Intent intent = new Intent(StoreValuePass.this, QrActivity.class);
            intent.putExtra("ORDER_NO", data.getOrder_no());
            startActivity(intent);
            finish();
        });

        binding.ReloadPassCard.setOnClickListener(view -> setBottomSheetForReload(data));

    }

    // RELOAD PASS
    private void reloadSVP(ResponseData SvpData, String fare, ProgressBar reloadProgressBar) {

        reloadProgressBar.setVisibility(View.VISIBLE);

        Order order = new Order(
                OrderUtils.getOID("SVP"),
                SharedPrefUtils.getStringData(this, "NUMBER"),
                null,
                null,
                BuildConfig.TOKEN_TYPE_SVP,
                null,
                fare,
                BuildConfig.PG_ID,
                BuildConfig.OPERATOR_ID,
                Status.getInstance().getSTATUS_ORDER_CREATED(),
                Status.getInstance().getORDER_TYPE_RELOAD()
        );

        Call<ResponseModel> createSvpOrder = ApiController.getInstance().apiInterface().createNewOrder(order);
        createSvpOrder.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                Gson gson = new Gson();
                Log.e("RELOAD_SVP_REQUEST", gson.toJson(order));
                Log.e("RELOAD_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().isStatus()) {

                        Intent intent = new Intent(StoreValuePass.this, PaymentActivity.class);
                        intent.putExtra("PAYMENT_TYPE", "3");
                        intent.putExtra("RECHARGE_AMOUNT", fare);
                        intent.putExtra("ODER_NO", order.getOrder_no());
                        intent.putExtra("MASTER_TXN_ID", SvpData.getMasterTxnId());
                        startActivity(intent);

                        reloadProgressBar.setVisibility(View.GONE);

                        reloadDialogue.cancel();
                        finish();

                    } else {
                        reloadProgressBar.setVisibility(View.GONE);
                        Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // CREATE NEW TRIP
    private void generateNewTrip(ResponseData svpData) {

        Data dataTrip = new Data(
                Calendar.getInstance().getTime().toString(),
                "null",
                SharedPrefUtils.getStringData(this, "EMAIL"),
                SharedPrefUtils.getStringData(this, "NUMBER"),
                SharedPrefUtils.getStringData(this, "NAME"),
                BuildConfig.OPERATION_ISSUE,
                BuildConfig.OPERATOR_ID,
                svpData.getOrder_no(),
                BuildConfig.QR_TRIP_PASS,
                "null",
                BuildConfig.TOKEN_TYPE_SVP,
                svpData.getMasterTxnId()
        );

        Trip trip = new Trip(dataTrip);

        Call<IssueResponse> getTrip = ApiController.getInstance().apiInterface().issueSVPTrip(trip);
        getTrip.enqueue(new Callback<IssueResponse>() {
            @Override
            public void onResponse(@NonNull Call<IssueResponse> call, @NonNull Response<IssueResponse> response) {

                Gson gson = new Gson();
                Log.e("ISSUE_SVP_TRIP_REQUEST", gson.toJson(trip));
                Log.e("ISSUE_SVP_TRIP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().getStatus().equals("OK")) {

                        Intent intent = new Intent(StoreValuePass.this, QrActivity.class);
                        intent.putExtra("ORDER_NO", svpData.getOrder_no());
                        startActivity(intent);
                        finish();

                    } else Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();

                } else Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<IssueResponse> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "STORE VALUE PASS";
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.Heading.setText(Heading);

    }

    // BOTTOM LAYOUT FOR RELOAD
    private void setBottomSheetForReload(ResponseData SvpData) {

        ReloadBottomSheetLayoutBinding ReloadBinding;
        reloadDialogue = new BottomSheetDialog(this);
        ReloadBinding = ReloadBottomSheetLayoutBinding.inflate(getLayoutInflater());
        View ReloadView = ReloadBinding.getRoot();
        reloadDialogue.setContentView(ReloadView);

        // INCREMENT COUNTER
        ReloadBinding.ReloadAddButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(ReloadBinding.ReloadRechargeAmount.getText().toString());
            if (ticket_count < 3000) {
                ticket_count = ticket_count + 100;
                ReloadBinding.ReloadRechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // DECREMENT COUNTER
        ReloadBinding.ReloadSubButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(ReloadBinding.ReloadRechargeAmount.getText().toString());
            if (ticket_count > 100) {
                ticket_count = ticket_count - 100;
                ReloadBinding.ReloadRechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // RELOAD PASS
        ReloadBinding.ReloadReloadPass.setOnClickListener(view ->
                reloadSVP(SvpData, ReloadBinding.ReloadRechargeAmount.getText().toString(), ReloadBinding.ReloadProgressBar)
        );

        // SHOW DIALOGUE
        reloadDialogue.show();

    }

}