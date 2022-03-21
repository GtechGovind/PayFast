package com.gtech.payfast.Auth;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.gtech.payfast.Activity.MainDashboard;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityOtpBinding;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    private ActivityOtpBinding binding;
    private String BackendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // SET CONFIG
        setBasicConfig();

        // SEND OTP
        sendOTP();

        // REGISTER USER
        binding.VerifyOtp.setOnClickListener(view1 -> {

            if (isOTPValid()) {

                binding.VerifyOtpProgressBar.setVisibility(View.VISIBLE);
                binding.VerifyOtp.setVisibility(View.GONE);

                verifyOTP();

            }

        });

    }

    // COUNTER
    private void setCounter() {

        CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                binding.OtpCounter.setText("Please Wait " + (millisUntilFinished / 1000) + " seconds.");
            }

            @Override
            public void onFinish() {
                binding.OtpCounter.setVisibility(View.GONE);
                binding.ResendOtp.setVisibility(View.VISIBLE);
            }
        };

        countDownTimer.start();

    }

    // VERIFY OTP
    private void verifyOTP() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(BackendOtp, Objects.requireNonNull(binding.OTP.getText()).toString());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(OtpActivity.this, "Welcome " + getIntent().getStringExtra("NAME"), Toast.LENGTH_SHORT).show();

                        SharedPrefUtils.saveData(OtpActivity.this, "NAME", getIntent().getStringExtra("NAME"));
                        SharedPrefUtils.saveData(OtpActivity.this, "EMAIL", getIntent().getStringExtra("EMAIL"));
                        SharedPrefUtils.saveData(OtpActivity.this, "NUMBER", getIntent().getStringExtra("NUMBER"));

                        Intent intent = new Intent(OtpActivity.this, MainDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        binding.VerifyOtpProgressBar.setVisibility(View.GONE);

                        finish();

                    } else {

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            binding.VerifyOtpProgressBar.setVisibility(View.GONE);
                            binding.VerifyOtp.setVisibility(View.VISIBLE);
                            binding.OTPLayout.setError("Wrong OIP please resend otp!");

                        }
                    }
                });

    }

    // SEND OTP
    private void sendOTP() {

        binding.VerifyOtpProgressBar.setVisibility(View.VISIBLE);
        binding.VerifyOtp.setVisibility(View.GONE);

        Toast.makeText(this, "Sending otp ...", Toast.LENGTH_SHORT).show();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91" + getIntent().getStringExtra("NUMBER"))
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(otp, forceResendingToken);

                        Toast.makeText(OtpActivity.this, "OTP is sent to +91-" + getIntent().getStringExtra("NUMBER"), Toast.LENGTH_SHORT).show();
                        BackendOtp = otp;
                        binding.VerifyOtpProgressBar.setVisibility(View.GONE);
                        binding.VerifyOtp.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.VerifyOtpProgressBar.setVisibility(View.GONE);
                        binding.VerifyOtp.setVisibility(View.GONE);
                    }
                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    // VALIDATE USER INPUT
    private boolean isOTPValid() {

        String OTP = Objects.requireNonNull(binding.OTP.getText()).toString();

        if (OTP.isEmpty()) binding.OTPLayout.setError("Please provide valid phone number!");
        else return true;

        return false;
    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "VERIFY OTP";
        binding.OptionButton.setOnClickListener(view2 -> Toast.makeText(this, "Your details are safe with us \uD83D\uDE0A", Toast.LENGTH_SHORT).show());
        binding.BackButton.setOnClickListener(view1 -> finish());
        binding.Heading.setText(Heading);

        // GO TO REGISTER
        binding.WrongNumber.setOnClickListener(view1 -> finish());

        // REMOVE ERRORS
        binding.OTP.setOnClickListener(view13 -> binding.OTPLayout.setError(null));

        // RESEND OTP
        binding.ResendOtp.setOnClickListener(view -> {
            sendOTP();
            setCounter();
        });

        // SET COUNTER
        setCounter();

    }

}
