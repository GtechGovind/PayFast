package com.gtech.payfast.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gtech.payfast.Adapter.TicketAdapter;
import com.gtech.payfast.Model.Ticket.UpdateTicketDashboard;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketDashboardBinding.inflate(getLayoutInflater());
        View ticketDashboard = binding.getRoot();
        setContentView(ticketDashboard);

        // setBasicConfig();
         updateTicketDashboard();
    }

    private void updateTicketDashboard() {
         String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
//        String PAX_ID = "SJSDKS";
        Call<UpdateTicketDashboard> ticketDashboardCall = ApiController.getInstance().apiInterface().updateTicketDashboard(PAX_ID);
        ticketDashboardCall.enqueue(new Callback<UpdateTicketDashboard>() {
            @Override
            public void onResponse(@NonNull Call<UpdateTicketDashboard> call, @NonNull Response<UpdateTicketDashboard> response) {
                Gson gson = new Gson();
                Log.e("TICKET_DASHBOARD_REQ", gson.toJson(PAX_ID));
                Log.e("TICKET_DASHBOARD_RESP", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        List<UpwardTicket> upcomingOrders = response.body().getUpcomingOrders();
                        List<UpwardTicket> recentOrders = response.body().getRecentOrders();
                        ticketAdapter = new TicketAdapter(upcomingOrders, recentOrders);
                        binding.ticketsRecyclerView.setAdapter(ticketAdapter);
                        Toast.makeText(TicketDashboard.this, "Hurray!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TicketDashboard.this, "There was a problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateTicketDashboard> call, @NonNull Throwable t) {
                Toast.makeText(TicketDashboard.this, "There was a problem fetching your ticket", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBasicConfig() {
    }
}