package com.gtech.payfast.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gtech.payfast.Activity.QR.MobileQr;
import com.gtech.payfast.Activity.SVP.StoreValuePass;
import com.gtech.payfast.Activity.TP.TripPass;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.databinding.ActivityMainDashboardBinding;

public class MainDashboard extends AppCompatActivity {

    private ActivityMainDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainDashboardBinding.inflate(getLayoutInflater());
        View MainDashboardView = binding.getRoot();
        setContentView(MainDashboardView);

        // SET CONFIG
        setBasicConfig();

        // SET LISTENERS FOR PRODUCTS
        binding.QrCard.setOnClickListener(view -> startActivity(new Intent(this, MobileQr.class)));
        binding.SVPCard.setOnClickListener(view -> startActivity(new Intent(this, StoreValuePass.class)));
        binding.TPCard.setOnClickListener(view -> startActivity(new Intent(this, TripPass.class)));

    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "DASHBOARD";
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.Heading.setText(Heading);

    }

}