package com.gtech.payfast.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.gtech.payfast.Activity.MainDashboard;
import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityLoginBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private Boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // SET CONFIG
        setBasicConfig();

        // LOGIN USER
        binding.LoginButton.setOnClickListener(view12 -> {

            if (isLoginValid()) {

                isLoading = true;
                binding.LoginProgressBar.setVisibility(View.VISIBLE);
                binding.LoginButton.setVisibility(View.GONE);
                // Disable register button while try login
                binding.RegisterButton.setEnabled(false);

                checkUser();

            }

        });

    }

    // CHECK IF USER EXIST
    public void checkUser() {

        String NUMBER = Objects.requireNonNull(binding.Number.getText()).toString().trim();
        User user = new User(NUMBER);

        Call<ResponseModel> checkUser = ApiController.getInstance().apiInterface().checkUser(NUMBER);
        checkUser.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                if (isLoading) {
                    isLoading = false;
                    binding.LoginProgressBar.setVisibility(View.GONE);
                    binding.LoginButton.setVisibility(View.VISIBLE);
                    binding.RegisterButton.setEnabled(true);
                }

                Gson gson = new Gson();
                Log.e("CHECK_USER_REQUEST", gson.toJson(NUMBER));
                Log.e("CHECK_USER_RESPONSE", gson.toJson(response.body()));

                ResponseModel checkUserResponse = response.body();

                if (checkUserResponse != null) {

                    if (checkUserResponse.isStatus()) {

                        User oldUser = checkUserResponse.getUser();

                        Intent intent = new Intent(LoginActivity.this, MainDashboard.class);
                        intent.putExtra("NUMBER", oldUser.getPax_mobile());
                        intent.putExtra("EMAIL", oldUser.getPax_email());
                        intent.putExtra("NAME", oldUser.getPax_name());
                        SharedPrefUtils.saveData(LoginActivity.this, "NUMBER", oldUser.getPax_mobile());
                        SharedPrefUtils.saveData(LoginActivity.this, "PAX_ID", oldUser.getPax_id().toString());
                        Log.e("SAVING PAX ID", oldUser.getPax_id().toString());
                        startActivity(intent);
                        finish();

                    } else {

                        Toast.makeText(LoginActivity.this, "User doesn't exist please Register.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("NUMBER", user.getPax_mobile());
                        startActivity(intent);

                        finish();

                    }

                } else Toast.makeText(LoginActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                if (isLoading) {
                    isLoading = false;
                    binding.LoginProgressBar.setVisibility(View.GONE);
                    binding.LoginButton.setVisibility(View.VISIBLE);
                    binding.RegisterButton.setEnabled(true);
                }
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // VALIDATE USER INPUT
    private boolean isLoginValid() {

        String PhoneNumber = Objects.requireNonNull(binding.Number.getText()).toString();

        if (PhoneNumber.isEmpty() || PhoneNumber.length() < 10 ) {
            binding.NumberLayout.setError("Please provide valid phone number!");
        } else return true;

        return false;
    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "LOGIN";
        binding.OptionButton.setOnClickListener(view2 -> Toast.makeText(this, "Your details are safe with us \uD83D\uDE0A", Toast.LENGTH_SHORT).show());
        binding.BackButton.setOnClickListener(view1 -> finish());
        binding.Heading.setText(Heading);

        // GO TO REGISTER
        binding.RegisterButton.setOnClickListener(view1 -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // REMOVE ERRORS
        binding.Number.setOnClickListener(view13 -> binding.NumberLayout.setError(null));

        // PRE SET NUMBER
        if (getIntent().getStringExtra("NUMBER") != null) binding.Number.setText(getIntent().getStringExtra("NUMBER"));

    }

}