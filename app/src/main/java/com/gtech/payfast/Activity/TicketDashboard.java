package com.gtech.payfast.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtech.payfast.Activity.QR.MobileQr;
import com.gtech.payfast.Adapter.TicketAdapter;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.Ticket.Ticket;
import com.gtech.payfast.Model.Ticket.UpdateTicketDashboard;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityTicketDashboardBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketDashboard extends AppCompatActivity {

    private ActivityTicketDashboardBinding binding;
    private TicketAdapter ticketAdapter;
    boolean statusUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketDashboardBinding.inflate(getLayoutInflater());
        View ticketDashboard = binding.getRoot();
        setContentView(ticketDashboard);

        // SET ACTIVITY CONFIGURATION
         setBasicConfig();

         // UPDATE THE DASHBOARD
         updateTicketDashboard();

    }

    private void updateTicketDashboard() {
        binding.TProgressBar.setVisibility(View.VISIBLE);
        String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
        Call<UpdateTicketDashboard> ticketDashboardCall = ApiController.getInstance().apiInterface().updateTicketDashboard(PAX_ID);
        ticketDashboardCall.enqueue(new Callback<UpdateTicketDashboard>() {
            @Override
            public void onResponse(@NonNull Call<UpdateTicketDashboard> call, @NonNull Response<UpdateTicketDashboard> response) {
                Gson gson = new Gson();
                Log.e("TICKET_DASHBOARD_REQ", gson.toJson(PAX_ID));
                Log.e("TICKET_DASHBOARD_RESP", gson.toJson(response.body()));
                Log.e("STATUS_UPDATED", gson.toJson(statusUpdated));

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        binding.TProgressBar.setVisibility(View.GONE);
                        List<UpwardTicket> upcomingOrders = response.body().getUpcomingOrders();

                        if (upcomingOrders == null || upcomingOrders.isEmpty()) {
                            // No upcoming orders to show, go to Ticket booking
                            startActivity(new Intent(TicketDashboard.this, MobileQr.class));
                            finish();
                        } else {
                            ticketAdapter = new TicketAdapter(upcomingOrders, TicketDashboard.this);
                            binding.ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(TicketDashboard.this, LinearLayoutManager.VERTICAL, false));
                            binding.ticketsRecyclerView.setAdapter(ticketAdapter);
                            if (!statusUpdated) updateStatus();
                        }
                    } else {
                        binding.TProgressBar.setVisibility(View.GONE);
                        startActivity(new Intent(TicketDashboard.this, MobileQr.class));
                        finish();
                    }
                } else {
                    binding.TProgressBar.setVisibility(View.GONE);
                    Toast.makeText(TicketDashboard.this, "Sorry, there was a problem fetching your tickets", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateTicketDashboard> call, @NonNull Throwable t) {
                binding.TProgressBar.setVisibility(View.GONE);
                Toast.makeText(TicketDashboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStatus() {
        String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
        Call<ResponseModel> updateStatusCall = ApiController.getInstance().apiInterface().updateTicketStatus(PAX_ID);
        updateStatusCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("QR_TICKET_STATUS_REQ", gson.toJson(PAX_ID));
                Log.e("QR_TICKET_STATUS_RESP", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        statusUpdated = true;
                        updateTicketDashboard();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TicketDashboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBasicConfig() {
        binding.Heading.setText(R.string.mumbai_metro_one);
        binding.goToBookTickets.setOnClickListener(view -> startActivity(new Intent(this, MobileQr.class)));
        binding.BackButton.setOnClickListener(view -> finish());
    }
}