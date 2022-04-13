package com.gtech.payfast.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.gtech.payfast.Activity.QR.MobileQr;
import com.gtech.payfast.Adapter.TicketAdapter;
import com.gtech.payfast.Model.ResponseModel;
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
                        List<UpwardTicket> recentOrders = response.body().getRecentOrders();
                        boolean isRecentOrders = recentOrders != null;
                        boolean isUpcomingOrders = upcomingOrders != null;
                        // HIDE RECENT ORDERS VIEW IF NO RECENT ORDERS
                        if (isRecentOrders) {
                            setRecentOrder(recentOrders.get(0));
                        } else {
                            binding.RecentOrdersText.setVisibility(View.GONE);
                        }
                        // HIDE UPCOMING ORDERS VIEW IF NOT UPCOMING ORDERS
                        if (!isUpcomingOrders) {
                            binding.UpcomingOrdersText.setVisibility(View.GONE);
                        } else { // DISPLAY OTHERWISE
                            binding.UpcomingOrdersText.setVisibility(View.VISIBLE);
                            ticketAdapter = new TicketAdapter(upcomingOrders, TicketDashboard.this);
                            binding.ticketsRecyclerView.setLayoutManager(new LinearLayoutManager(TicketDashboard.this, LinearLayoutManager.VERTICAL, false));
                            binding.ticketsRecyclerView.setAdapter(ticketAdapter);
                        }
                        if (!isRecentOrders && !isUpcomingOrders) {
                            // No upcoming orders to show, go to Ticket booking
                            startActivity(new Intent(TicketDashboard.this, MobileQr.class));
                            finish();
                        } else {
                            if (!statusUpdated) updateStatus();
                        }
                    } else {
                        binding.TProgressBar.setVisibility(View.GONE);
                        startActivity(new Intent(TicketDashboard.this, MobileQr.class));
                        finish();
                    }
                } else {
                    binding.TProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateTicketDashboard> call, @NonNull Throwable t) {
                binding.TProgressBar.setVisibility(View.GONE);
            }
        });
    }

    // SET UP RECENT ORDER CARD
    private void setRecentOrder(UpwardTicket recentOrder) {
        binding.RecentOrdersText.setVisibility(View.VISIBLE);
        binding.RecentTicketCard.setVisibility(View.VISIBLE);
        // SET ARROW IMAGE
        if (recentOrder.getPass_id() == 10) {
            binding.ArrowReturnJourneyRec.setVisibility(View.GONE);
        } else if (recentOrder.getPass_id() == 90) {
            binding.ArrowSingleJourneyRec.setVisibility(View.GONE);
        }
        // SET SOURCE AND DESTINATION
        String source = recentOrder.getSource();
        String destination = recentOrder.getDestination();
        binding.SourceRecent.setText(source);
        binding.DestinationRecent.setText(destination);
        // SET FARE TEXT
        String fare = "Click to buy again, fare ₹" + recentOrder.getUnit_price() / recentOrder.getUnit() + " per ticket";
        binding.FareRecent.setText(fare);
        // INITIAL ON CLICK LISTENER FOR THE CARD
        // ON CLICK START MOBILE QR ACTIVITY
        Intent intent = new Intent(TicketDashboard.this, MobileQr.class);
        intent.putExtra("SOURCE_RECENT", source);
        intent.putExtra("DESTINATION_RECENT", destination);
        binding.RecentTicketCard.setOnClickListener(v -> {
            startActivity(intent);
            finish();
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
            }
        });
    }

    private void setBasicConfig() {
        binding.Heading.setText(R.string.mumbai_metro_one);
        binding.goToBookTickets.setOnClickListener(view -> startActivity(new Intent(this, MobileQr.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.RecentTicketCard.setVisibility(View.GONE);
    }
}