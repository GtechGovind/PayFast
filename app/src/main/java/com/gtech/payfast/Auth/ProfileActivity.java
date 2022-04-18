package com.gtech.payfast.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gtech.payfast.R;
import com.gtech.payfast.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setBasicConfig();
    }

    private void setBasicConfig() {
        binding.Heading.setText(R.string.mumbai_metro_one);
        binding.BackButton.setOnClickListener(view -> finish());
    }
}