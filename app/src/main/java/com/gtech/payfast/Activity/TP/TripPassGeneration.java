package com.gtech.payfast.Activity.TP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.gtech.payfast.BuildConfig;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.Config.Fare;
import com.gtech.payfast.Model.Config.FareRequest;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.databinding.ActivityTripPassGenerationBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripPassGeneration extends AppCompatActivity {
    private ActivityTripPassGenerationBinding binding;
    DBHelper dbHelper;
    ArrayAdapter<String> StationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTripPassGenerationBinding.inflate(getLayoutInflater());
        View TripPassGenerationView = binding.getRoot();
        setContentView(TripPassGenerationView);

        setBasicConfig();
    }

    private void orderTP() {}

    private void setFare() {
        if (isInputValid()) {
            String  SourceId, DestinationId, pass_id;
            SourceId = dbHelper.getStationId(binding.Source.getText().toString());
            DestinationId = dbHelper.getStationId(binding.Destination.getText().toString());
            pass_id = BuildConfig.TOKEN_TYPE_TP;

            FareRequest fareRequest = new FareRequest(Integer.parseInt(SourceId), Integer.parseInt(DestinationId), Integer.parseInt(pass_id));
            Call<Fare> fare = ApiController.getInstance().apiInterface().getFare(fareRequest);
            fare.enqueue(new Callback<Fare>() {
                @Override
                public void onResponse(@NonNull Call<Fare> call, @NonNull Response<Fare> response) {

                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            String TotalFare = Integer.toString(response.body().getFare());
                            binding.TotalFare.setText("₹ " + TotalFare);
                            binding.OrderButton.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Fare> call, @NonNull Throwable t) {
                    binding.MobileQrProgressBar.setVisibility(View.GONE);
                    binding.OrderButton.setVisibility(View.VISIBLE);
                    Toast.makeText(TripPassGeneration.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isInputValid() {
        if (binding.Source.getText().toString().isEmpty()) binding.SourceLayout.setError("Please select source station!");
        else if (binding.Destination.getText().toString().isEmpty()) binding.DestinationLayout.setError("Please select destination station!");
        else if (binding.Source.getText().toString().equals(binding.Destination.getText().toString())) {
            binding.SourceLayout.setError("Source and Destination station can't be same!");
            binding.DestinationLayout.setError("Source and Destination station can't be same!");
        } else return true;
        return false;
    }

    private void setBasicConfig() {
        binding.Heading.setText(R.string.trip_pass_heading);
        binding.OrderButton.setEnabled(false);

        // SET STATIONS
        StationAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, dbHelper.getStations());
        binding.Source.setThreshold(1);
        binding.Source.setAdapter(StationAdapter);
        binding.Destination.setThreshold(1);
        binding.Destination.setAdapter(StationAdapter);

        // SET FARE AND REMOVE ERRORS ON SOURCE SELECT
        binding.Source.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.SourceLayout.setError(null);
            binding.DestinationLayout.setError(null);
            setFare();
        });

        // SET FARE AND REMOVE ERRORS ON DESTINATION SELECT
        binding.Destination.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.DestinationLayout.setError(null);
            binding.SourceLayout.setError(null);
            setFare();
        });

        // ORDER BUTTON
        binding.OrderButton.setOnClickListener(v -> orderTP());
    }
}