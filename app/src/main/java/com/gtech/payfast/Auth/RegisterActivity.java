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
import com.gtech.payfast.Utils.EmailValidatorUtility;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityRegisterBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private Boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // SET CONFIG
        setBasicConfig();

        // REGISTER USER
        binding.RegisterButton.setOnClickListener(view1 -> {

            if (isRegisterInputValid()) {

                isLoading = true;
                binding.RegisterProgressBar.setVisibility(View.VISIBLE);
                binding.RegisterButton.setVisibility(View.GONE);
                // disable login while user tries to register
                binding.LoginButton.setEnabled(false);
                registerUser();

            }

        });

    }

    // REGISTER USER CALL
    private void registerUser() {

        User user = new User(
                Objects.requireNonNull(binding.FullName.getText()).toString(),
                Objects.requireNonNull(binding.Email.getText()).toString(),
                Objects.requireNonNull(binding.Number.getText()).toString()
        );

        Call<ResponseModel> registerUser = ApiController.getInstance().apiInterface().registerUser(user);
        registerUser.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                if (isLoading) {
                    isLoading = false;
                    binding.RegisterProgressBar.setVisibility(View.GONE);
                    binding.RegisterButton.setVisibility(View.VISIBLE);
                    // disable login while user tries to register
                    binding.LoginButton.setEnabled(true);
                }
                Gson gson = new Gson();
                Log.e("CHECK_USER_REQUEST", gson.toJson(user));
                Log.e("CHECK_USER_RESPONSE", gson.toJson(response.body()));

                ResponseModel registerUserResponse = response.body();

                if (registerUserResponse != null) {

                    if (registerUserResponse.isStatus()) {

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("NUMBER", Objects.requireNonNull(binding.Number.getText()).toString());
                        intent.putExtra("EMAIL", Objects.requireNonNull(binding.Email.getText()).toString());
                        intent.putExtra("NAME", Objects.requireNonNull(binding.FullName.getText()).toString());

                        SharedPrefUtils.saveData(RegisterActivity.this, "NAME", response.body().getUser().getPax_name());
                        SharedPrefUtils.saveData(RegisterActivity.this, "NUMBER", response.body().getUser().getPax_mobile());
                        SharedPrefUtils.saveData(RegisterActivity.this, "PAX_ID", response.body().getUser().getPax_id());
                        startActivity(intent);

                        finish();

                    } else {

                        Toast.makeText(RegisterActivity.this, "User already exist please login.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("NUMBER", user.getPax_mobile());
                        startActivity(intent);

                        binding.RegisterProgressBar.setVisibility(View.GONE);

                        finish();

                    }

                } else Toast.makeText(RegisterActivity.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                if (isLoading) {
                    isLoading = false;
                    binding.RegisterProgressBar.setVisibility(View.GONE);
                    binding.RegisterButton.setVisibility(View.VISIBLE);
                    // disable login while user tries to register
                    binding.LoginButton.setEnabled(true);
                }
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // VALIDATE USER INPUT
    private boolean isRegisterInputValid() {

        String PhoneNumber = Objects.requireNonNull(binding.Number.getText()).toString();
        String UserName = Objects.requireNonNull(binding.FullName.getText()).toString();
        String UserEmail = Objects.requireNonNull(binding.Email.getText()).toString();

        if (PhoneNumber.isEmpty() || PhoneNumber.length() < 10 ) binding.NumberLayout.setError("Please provide valid phone number!");
        else if (UserName.isEmpty()) binding.FullNameLayout.setError("Please enter valid name!");
        else if (!EmailValidatorUtility.isValidEmail(UserEmail)) binding.EmailLayout.setError("Please enter valid email!");
        else return true;

        return false;
    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "LOGIN";
        binding.OptionButton.setOnClickListener(view2 -> Toast.makeText(this, "Your details are safe with us \uD83D\uDE0A", Toast.LENGTH_SHORT).show());
        binding.BackButton.setOnClickListener(view1 -> finish());
        binding.Heading.setText(Heading);

        // GO TO REGISTER
        binding.LoginButton.setOnClickListener(view1 -> startActivity(new Intent(this, LoginActivity.class)));

        // REMOVE ERRORS
        binding.Number.setOnClickListener(view13 -> binding.NumberLayout.setError(null));
        binding.FullName.setOnClickListener(view -> binding.FullNameLayout.setError(null));
        binding.Email.setOnClickListener(view -> binding.EmailLayout.setError(null));

        // PRE SET NUMBER
        if (getIntent().getStringExtra("NUMBER") != null) binding.Number.setText(getIntent().getStringExtra("NUMBER"));

    }

}