package com.gtech.payfast.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gtech.payfast.Activity.SVP.StoreValuePass;
import com.gtech.payfast.Activity.TP.TripPass;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.R;
import com.gtech.payfast.Utils.SharedPrefUtils;
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
        binding.QrCard.setOnClickListener(view -> startActivity(new Intent(this, TicketDashboard.class)));
        binding.SVPCard.setOnClickListener(view -> startActivity(new Intent(this, StoreValuePass.class)));
        binding.TPCard.setOnClickListener(view -> startActivity(new Intent(this, TripPass.class)));

    }

    // SET CONFIG
    private void setBasicConfig() {

        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.Heading.setText(R.string.mumbai_metro_one);

        String username = SharedPrefUtils.getStringData(MainDashboard.this, "NAME");
        String welcomeText;
        if (username != null) welcomeText = "Welcome, " + username;
        else welcomeText = "Welcome";
        binding.WelcomeText.setText(welcomeText);

    }

}