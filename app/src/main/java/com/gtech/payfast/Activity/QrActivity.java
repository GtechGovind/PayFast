package com.gtech.payfast.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.gtech.payfast.Adapter.QrAdapter;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.Ticket.Ticket;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.databinding.ActivityQrBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrActivity extends AppCompatActivity {

    private ActivityQrBinding binding;
    private QrAdapter qrAdapter;
    DBHelper dbHelper;
    String paxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrBinding.inflate(getLayoutInflater());
        View QrView = binding.getRoot();
        setContentView(QrView);

        // SET BASIC
        setBasicConfig();

        // GET QR CODE
        getQrCodes();

        dbHelper = new DBHelper(this);

    }

    private void getQrCodes() {
        String ORDER_ID = getIntent().getStringExtra("ORDER_ID");
        Call<Ticket> getQrs = ApiController.getInstance().apiInterface().viewTicket(ORDER_ID);
        getQrs.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(@NonNull Call<Ticket> call, @NonNull Response<Ticket> response) {
                Gson gson = new Gson();
                Log.e("GET_QR_REQUEST", gson.toJson(ORDER_ID));
                Log.e("GET_QR_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Log.e("Upward Ticket", gson.toJson(response.body().getUpwardTicket()));
                        List<UpwardTicket> ticketQrs = response.body().getUpwardTicket();
                        qrAdapter = new QrAdapter(ticketQrs, binding.QrProgressBar, QrActivity.this);
                        binding.QrRecyclerView.setAdapter(qrAdapter);
                    } else {
                        // TODO: error
                        Toast.makeText(QrActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // TODO: error
                    Toast.makeText(QrActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Ticket> call, @NonNull Throwable t) {
                Toast.makeText(QrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "TRIP QR";
        binding.QrRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, TicketDashboard.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.Heading.setText(Heading);

    }

}