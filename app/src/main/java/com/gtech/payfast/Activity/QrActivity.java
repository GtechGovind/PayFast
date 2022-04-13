package com.gtech.payfast.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.gtech.payfast.Adapter.QrAdapter;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.Ticket.Ticket;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.databinding.ActivityQrBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrActivity extends AppCompatActivity {

    private ActivityQrBinding binding;
    DBHelper dbHelper;
    List<UpwardTicket> ticketQrs;
    List<UpwardTicket> returnTicketQrs;

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
                        ticketQrs = response.body().getUpwardTicket();
                        // SET TICKET DATA ON CARD
                        setTicketCard(ticketQrs.get(0));
                        returnTicketQrs = response.body().getReturnTicket();
                        // SHOW OUTWARD TICKETS
                        setAdapter(false);
                        binding.outwardTicket.setBackgroundColor(getResources().getColor(R.color.primary));
                        // IF RJT, SHOW TOGGLE BUTTON
                        if (returnTicketQrs != null) {
                            if (!returnTicketQrs.isEmpty()) configToggle();
                        }
                        if (ticketQrs.size() > 1) {
                            Toast.makeText(QrActivity.this, "Swipe to see other tickets!", Toast.LENGTH_LONG).show();
                        }
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

    // SET TICKET CARD
    private void setTicketCard(UpwardTicket ticketData) {
        // SET SOURCE AND DESTINATION ON THE CARD
        binding.Source.setText(ticketData.getSource());
        binding.Destination.setText(ticketData.getDestination());
        // SET ARROW IMAGE BASED ON JOURNEY TYPE
        if (ticketData.getPass_id() == 90) {
            binding.ArrowImage.setImageDrawable(
                    ResourcesCompat.getDrawable(this.getResources(), R.drawable.return_arrow, null));
        }
        // SET BOOKING AND EXPIRY DATE
        binding.BookingDate.setText(ticketData.getTxn_date());
        binding.ExpiryDate.setText(ticketData.getMs_qr_exp());
        // SET UNIT PRICE
        String fare = "₹" + ticketData.getUnit_price();
        binding.Fare.setText(fare);
    }

    // CONFIGURE TOGGLE BUTTON
    private void configToggle() {
        binding.toggleButton.setVisibility(View.VISIBLE);
        binding.toggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            // SHOW OUTWARD TICKETS
            if(group.getCheckedButtonId()==R.id.outwardTicket)
            {
                setAdapter(false);
                binding.outwardTicket.setBackgroundColor(getResources().getColor(R.color.primary));
                binding.returnTicket.setBackgroundColor(getResources().getColor(R.color.grey_light));
            }else if(group.getCheckedButtonId()==R.id.returnTicket) {
                // SHOW RETURN TICKETS
                setAdapter(true);
                binding.outwardTicket.setBackgroundColor(getResources().getColor(R.color.grey_light));
                binding.returnTicket.setBackgroundColor(getResources().getColor(R.color.primary));
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

    private void setAdapter(Boolean isReturn)
    {
        QrAdapter qrAdapter;
        if (isReturn) {
            qrAdapter = new QrAdapter(returnTicketQrs, binding.QrProgressBar, QrActivity.this);
        } else {
            qrAdapter = new QrAdapter(ticketQrs, binding.QrProgressBar, QrActivity.this);
        }

        binding.QrRecyclerView.setAdapter(qrAdapter);
        binding.MetroLogo.setVisibility(View.VISIBLE);
    }

}