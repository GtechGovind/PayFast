package com.gtech.payfast.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.GRA.Data;
import com.gtech.payfast.Model.GRA.GRAStatus;
import com.gtech.payfast.Model.GRA.GRATicket;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityGraBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GRA extends AppCompatActivity {
    private ActivityGraBinding binding;
    private String slQrNo, stationId, ticketType;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGraBinding.inflate(getLayoutInflater());
        View GRAView = binding.getRoot();
        setContentView(GRAView);

        // INITIATE DATABASE
        dbHelper = new DBHelper(this);
        // GET SALE QR NO
        slQrNo = getIntent().getStringExtra("SL_QR_NO");
        // GET TICKET TYPE
        ticketType = getIntent().getStringExtra("TICKET_TYPE");

        setConfig();

    }

    private void generateTicket(Data data) {
        // CONFIG REQUEST BODY
        GRATicket graTicket = new GRATicket();
        graTicket.setData(data);
        String paxMobile = SharedPrefUtils.getStringData(GRA.this, "NUMBER");
        graTicket.setPax_mobile(paxMobile);
        graTicket.setStation_id(stationId);

        // API CALL
        Call<ResponseModel> graTicketCall = ApiController.getInstance().apiInterface().getGraTicket(graTicket);
        graTicketCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("GRA_TICKET_REQ", gson.toJson(graTicket));
                Log.e("GRA_TICKET_RES", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // CALL PAYMENT ACTIVITY
                        String orderId = response.body().getOrder_id();
                        Intent intent = new Intent(GRA.this, PaymentActivity.class);
                        intent.putExtra("ORDER_ID", orderId);
                        String paymentType;
                        if (ticketType == "SVP") paymentType = "2";
                        else if (ticketType == "TP") paymentType = "4";
                        else paymentType = "1";
                        intent.putExtra("PAYMENT_TYPE", paymentType);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    private void setConfig() {
        binding.Heading.setText(R.string.mumbai_metro_one);
        binding.BackButton.setOnClickListener(v -> finish());
        binding.Profile.setOnClickListener(v -> startActivity(new Intent(GRA.this, ProfileActivity.class)));
        // DISABLE GENERATE TICKET BUTTON
        binding.GenerateTicket.setVisibility(View.GONE);
        // HIDE PENALTY INFO CONTAINER
        binding.PenaltyInfoContainer.setVisibility(View.GONE);
        // SET ON CLICK LISTENER TO GRA (CHECK) BUTTON
        binding.grabutton.setOnClickListener(view -> {
            binding.grabutton.setEnabled(false);
            binding.GRAProgressBar.setVisibility(View.VISIBLE);
            binding.MessageText.setText("");
            stationId = dbHelper.getStationId(binding.StationsGra.getSelectedItem().toString());
            Call<GRAStatus> graStatusCall = ApiController.getInstance().apiInterface().graStatus(slQrNo, stationId);
            graStatusCall.enqueue(new Callback<GRAStatus>() {
                @Override
                public void onResponse(Call<GRAStatus> call, Response<GRAStatus> response) {
                    binding.GRAProgressBar.setVisibility(View.GONE);
                    binding.grabutton.setEnabled(true);
                    Gson gson = new Gson();
                    Log.e("GRA_STATUS_REQ", gson.toJson(stationId));
                    Log.e("GRA_STATUS_REQ", gson.toJson(slQrNo));
                    Log.e("GRA_STATUS_RESP", gson.toJson(response.body()));

                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            binding.PenaltyInfoContainer.setVisibility(View.VISIBLE);
                            Data resPenaltyData = response.body().getData();
                            int penaltyAmt = resPenaltyData.getAmount();
                            String penalty = "₹" + penaltyAmt;
                            binding.PenaltyAmount.setText(penalty);
                            binding.grabutton.setVisibility(View.GONE);
                            binding.GenerateTicket.setVisibility(View.VISIBLE);
                            binding.GenerateTicket.setOnClickListener(view1 -> generateTicket(resPenaltyData));
                        } else {
                            // SHOW MESSAGE TEXT
                            binding.MessageText.setText(response.body().getError());
                        }
                    }
                }

                @Override
                public void onFailure(Call<GRAStatus> call, Throwable t) {

                }
            });
        });
        binding.StationsGra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.GenerateTicket.setVisibility(View.GONE);
                binding.grabutton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}