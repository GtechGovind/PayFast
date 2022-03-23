package com.gtech.payfast.Activity.QR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.BuildConfig;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.Config.FareRequest;
import com.gtech.payfast.Model.Config.Fare;
import com.gtech.payfast.Model.Order;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.Status;
import com.gtech.payfast.Model.Ticket;
import com.gtech.payfast.Model.TicketResponse;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.OrderUtils;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityMobileQrBinding;

import java.text.MessageFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileQr extends AppCompatActivity {

    private ActivityMobileQrBinding binding;
    ArrayAdapter<String> StationAdapter;
    String TicketType = "Single", TotalFare = "0";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobileQrBinding.inflate(getLayoutInflater());
        View MobileQrView = binding.getRoot();
        setContentView(MobileQrView);

        // INITIATE DATABASE
        dbHelper = new DBHelper(this);

        // SET CONFIG
        setBasicConfig();

        binding.OrderButton.setOnClickListener(view -> {

            binding.MobileQrProgressBar.setVisibility(View.VISIBLE);
            binding.OrderButton.setVisibility(View.GONE);

            if (isMobileQrInputValid()) createTicket();

        });

    }

    // CREATE TICKET
    private void createTicket() {

        Ticket newTicket = new Ticket(
                dbHelper.getStationId(binding.Source.getText().toString()),
                dbHelper.getStationId(binding.Destination.getText().toString()),
                OrderUtils.getOrderTypeCode(TicketType),
                binding.TicketCount.getText().toString(),
                TotalFare,
                SharedPrefUtils.getStringData(this, "NUMBER")
        );

        Call<TicketResponse> createTicket = ApiController.getInstance().apiInterface().createTicket(newTicket);
        createTicket.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(@NonNull Call<TicketResponse> call, @NonNull Response<TicketResponse> response) {
                Gson gson = new Gson();
                Log.e("CHECK_USER_REQUEST", gson.toJson(newTicket));
                Log.e("CHECK_USER_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        String order_id = response.body().getOrder_id();
                        Log.e("ORDER ID", order_id);

                        Intent intent = new Intent(MobileQr.this, PaymentActivity.class);
                        intent.putExtra("ORDER_ID", response.body().getOrder_id());
                        intent.putExtra("SOURCE_ID", newTicket.getSource_id());
                        intent.putExtra("TICKET_COUNT", newTicket.getQuantity());
                        intent.putExtra("DESTINATION_ID", newTicket.getDestination_id());
                        intent.putExtra("TOTAL_FARE", newTicket.getFare());
                        intent.putExtra("PAYMENT_TYPE", "1");

                        binding.MobileQrProgressBar.setVisibility(View.GONE);
                        binding.OrderButton.setVisibility(View.VISIBLE);

                        startActivity(intent);
                    } else {
                        binding.MobileQrProgressBar.setVisibility(View.GONE);
                        binding.OrderButton.setVisibility(View.VISIBLE);
                        Toast.makeText(MobileQr.this, "Some error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.MobileQrProgressBar.setVisibility(View.GONE);
                    binding.OrderButton.setVisibility(View.VISIBLE);
                    Toast.makeText(MobileQr.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TicketResponse> call, @NonNull Throwable t) {
                Log.e("ORDER ID", t.getMessage());
            }
        });
    }

    // SET FARE
    @SuppressLint("SetTextI18n")
    public void setFare() {

        if (isMobileQrInputValid()) {

            String  SourceId, DestinationId, pass_id;
            SourceId = dbHelper.getStationId(binding.Source.getText().toString());
            DestinationId = dbHelper.getStationId(binding.Destination.getText().toString());
            pass_id = TicketType.equals("Single") ? BuildConfig.TOKEN_TYPE_SJT : BuildConfig.TOKEN_TYPE_RJT;

            FareRequest fareRequest = new FareRequest(Integer.parseInt(SourceId), Integer.parseInt(DestinationId), Integer.parseInt(pass_id));
            Call<Fare> fare = ApiController.getInstance().apiInterface().getFare(fareRequest);
            fare.enqueue(new Callback<Fare>() {
                @Override
                public void onResponse(@NonNull Call<Fare> call, @NonNull Response<Fare> response) {
                    Integer count = Integer.parseInt(binding.TicketCount.getText().toString());
                    int total;

                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            total = response.body().getFare() * count;
                            TotalFare = Integer.toString(total);
                            String TicketCount = Integer.toString(count);
                            binding.TotalFare.setText("₹ " + TotalFare);

                            double totalFareInDouble = Double.parseDouble(TotalFare);
                            double ticketCountInDouble = Double.parseDouble(TicketCount);
                            String FareForSingleTicket = String.valueOf(totalFareInDouble/ticketCountInDouble);

                            binding.FarePerTicket.setText(MessageFormat.format("₹ {0} / TICKET", FareForSingleTicket));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Fare> call, @NonNull Throwable t) {
                    binding.MobileQrProgressBar.setVisibility(View.GONE);
                    binding.OrderButton.setVisibility(View.VISIBLE);
                    Toast.makeText(MobileQr.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    // CHECK IF THE INPUT IS VALID
    private boolean isMobileQrInputValid() {


        if (binding.Source.getText().toString().isEmpty()) binding.SourceLayout.setError("Please select source station!");
        else if (binding.Destination.getText().toString().isEmpty()) binding.DestinationLayout.setError("Please select destination station!");
        else if (binding.Source.getText().toString().equals(binding.Destination.getText().toString())) {
            binding.SourceLayout.setError("Source and Destination station can't be same!");
            binding.DestinationLayout.setError("Source and Destination station can't be same!");
        } else return true;
        return false;
    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "MOBILE QR ORDER";
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.Heading.setText(Heading);

        // INCREMENT COUNTER
        binding.AddButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(binding.TicketCount.getText().toString());
            if (ticket_count < 6) {
                ticket_count = ticket_count + 1;
                binding.TicketCount.setText(String.valueOf(ticket_count));
                setFare();
            }
        });

        // DECREMENT COUNTER
        binding.SubButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(binding.TicketCount.getText().toString());
            if (ticket_count > 1) {
                ticket_count = ticket_count - 1;
                binding.TicketCount.setText(String.valueOf(ticket_count));
                setFare();
            }
        });

        // TICKET TYPE
        binding.JourneyGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(i);
            TicketType = radioButton.getText().toString().trim();
            setFare();
        });

        // SET FARE AND REMOVE ERRORS
        binding.Source.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.SourceLayout.setError(null);
            binding.DestinationLayout.setError(null);
            setFare();
        });

        // SET FARE AND REMOVE ERRORS
        binding.Destination.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.DestinationLayout.setError(null);
            binding.SourceLayout.setError(null);
            setFare();
        });

        //SET STATION ADAPTER
        StationAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, dbHelper.getStations());
        binding.Source.setThreshold(1);
        binding.Source.setAdapter(StationAdapter);
        binding.Destination.setThreshold(1);
        binding.Destination.setAdapter(StationAdapter);

    }

}