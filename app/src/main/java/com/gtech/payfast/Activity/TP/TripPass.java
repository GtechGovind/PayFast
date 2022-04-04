package com.gtech.payfast.Activity.TP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gtech.payfast.Activity.SVP.StoreValuePass;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.TP.ReloadTP;
import com.gtech.payfast.Model.TP.TPDashboard;
import com.gtech.payfast.Model.TP.TPStatus;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityTripPassBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripPass extends AppCompatActivity {
    private ActivityTripPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTripPassBinding.inflate(getLayoutInflater());
        View TripPassView = binding.getRoot();
        setContentView(TripPassView);

        setBasicConfig();

        updateDashboard();
    }

    private void updateDashboard() {
        binding.TPassProgressBar.setVisibility(View.VISIBLE);
        String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
        Call<TPDashboard> updateTPDashboard = ApiController.getInstance().apiInterface().updateTPDashboard(PAX_ID);

        updateTPDashboard.enqueue(new Callback<TPDashboard>() {
            @Override
            public void onResponse(@NonNull Call<TPDashboard> call, @NonNull Response<TPDashboard> response) {
                Gson gson = new Gson();
                Log.e("UPDATE_TP_DASHBOARD_REQ", gson.toJson(PAX_ID));
                Log.e("UPDATE_TP_DASHBOARD_REP", gson.toJson(response.body()));

                binding.TPassProgressBar.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus()) {
                        // Pass exists
                        // Update pass
                        binding.HasTP.setVisibility(View.VISIBLE);
                        binding.RefundPassCard.setVisibility(View.VISIBLE);
                        binding.RefundPassCard.setOnClickListener(v -> refundPass(response.body().getPass().getSale_or_no()));

                        binding.UserName.setText(response.body().getUser().getPax_name());
                        binding.MasterTxnId.setText(response.body().getPass().getMs_qr_no());
                        binding.SourceTP.setText(response.body().getPass().getSource());
                        binding.DestinationTP.setText(response.body().getPass().getDestination());

                        if (response.body().getTrip() != null) {
                            binding.QrCodeCard.setVisibility(View.VISIBLE);
                            binding.QrNoTP.setText(response.body().getTrip().getSl_qr_no());
                            writeQr(response.body().getTrip().getQr_data());
                        } else {
                            binding.HasTPController.setVisibility(View.VISIBLE);
                            binding.GenerateTrip.setVisibility(View.VISIBLE);
                            binding.GenerateTrip.setOnClickListener(v -> generateTrip(response.body().getPass().getSale_or_no()));
                        }

                        if (response.body().getPass() != null) {
                            getTPStatus(response.body().getPass().getMs_qr_no());
                            canReloadPass(response.body().getPass().getSale_or_no(), response.body().getPass().getUnit_price());
                        }
                } else {
                    binding.HasTP.setVisibility(View.GONE);
                    binding.NoPass.setVisibility(View.VISIBLE);
                    canIssuePass();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TPDashboard> call, @NonNull Throwable t) {
                Toast.makeText(TripPass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // GET TP PASS STATUS
    private void getTPStatus(String msQrNo) {
        Call<TPStatus> TPStatusCall = ApiController.getInstance().apiInterface().getTPStatus(msQrNo);
        TPStatusCall.enqueue(new Callback<TPStatus>() {
            @Override
            public void onResponse(@NonNull Call<TPStatus> call, @NonNull Response<TPStatus> response) {
                Gson gson = new Gson();
                Log.e("TP_STATUS_REQ", gson.toJson(msQrNo));
                Log.e("TP_STATUS_RESP", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // SET EXPIRY DATE
                        // CONVERT EPOCH TIME TO DATE STRING
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
                        format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                        Date date = new Date(response.body().getData().getMasterExpiry());
                        String formatted = format.format(date);
                        binding.ExpiryDate.setText(formatted);
                        // SET BALANCE TRIPS
                        binding.BalanceTrips.setText(response.body().getData().getBalanceTrip().toString());
                    }
                } else {
                    // REQUEST FAILED
                    Toast.makeText(TripPass.this, "Failed to get TP Status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TPStatus> call, @NonNull Throwable t) {
                Toast.makeText(TripPass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // CAN ISSUE TRIP PASS
    private void canIssuePass() {
        binding.TPProgressBar.setVisibility(View.VISIBLE);
        String mobile = SharedPrefUtils.getStringData(this, "NUMBER");
        Call<ResponseModel> canIssuePassCall = ApiController.getInstance().apiInterface().canIssueTP(mobile);
        canIssuePassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                binding.TPProgressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // Pass can be issued
                        // Start pass generation activity
                        startActivity(new Intent(TripPass.this, TripPassGeneration.class));
                        finish();
                    } else {
                        // Pass cannot be issued
                        Toast.makeText(TripPass.this, "Pass cannot be issued", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Request failed
                    Toast.makeText(TripPass.this, "There was a problem fetching issue Pass request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                binding.TPProgressBar.setVisibility(View.GONE);
                Toast.makeText(TripPass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // GENERATE TRIP
    private void generateTrip(String orderId) {
        binding.TPProgressBar.setVisibility(View.VISIBLE);
        Call<ResponseModel> generateTripCall = ApiController.getInstance().apiInterface().generateTripTP(orderId);
        generateTripCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        binding.TPProgressBar.setVisibility(View.GONE);
                        binding.GenerateTrip.setVisibility(View.GONE);
                        updateDashboard();
                    } else {
                        // Could not generate trip
                        Toast.makeText(TripPass.this, "Could not generate trip pass. Try Again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Problem with generate trip request
                    Toast.makeText(TripPass.this, "Problem with generate trip request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TripPass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // CAN USER RELOAD PASS
    private void canReloadPass(String orderId, int amount) {
        Call<ResponseModel> canReloadPassCall = ApiController.getInstance().apiInterface().canReloadTP(orderId);
        canReloadPassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("RELOAD_PASS_REQ", orderId);
                Log.e("RELOAD_PASS_RESP", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        binding.ReloadTPButton.setVisibility(View.VISIBLE);
                        binding.ReloadTPButton.setOnClickListener(view -> reloadPass(orderId, amount));
                    }
                } else {
                    // Problem with generate trip request
                    Toast.makeText(TripPass.this, "Network error, try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    // RELOAD PASS
    private void reloadPass(String orderId, int amount) {
        binding.TPProgressBar.setVisibility(View.VISIBLE);
        ReloadTP reloadTP = new ReloadTP(orderId, amount);
        Call<ResponseModel> reloadPassCall = ApiController.getInstance().apiInterface().reloadTP(reloadTP);

        reloadPassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {

                        Intent intent = new Intent(TripPass.this, PaymentActivity.class);
                        intent.putExtra("PAYMENT_TYPE", "4");
                        intent.putExtra("ORDER_ID", response.body().getOrder_id());
                        startActivity(intent);

                        finish();
                    } else {
                        // response status is false
                        Toast.makeText(TripPass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // request failed
                    Toast.makeText(TripPass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    // REFUND PASS
    private void refundPass(String orderId) {
        binding.TPProgressBar.setVisibility(View.VISIBLE);
        Call<ResponseModel> refundTPCall = ApiController.getInstance().apiInterface().refundTP(orderId);

        refundTPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("REFUND_PASS_REQ", gson.toJson(orderId));
                Log.e("REFUND_PASS_RESP", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        binding.TPProgressBar.setVisibility(View.GONE);
                        updateDashboard();
                    } else {
                        Toast.makeText(TripPass.this, "There was a problem refunding your pass", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // NETWORK ERROR: COULD NOT REFUND PASS
                    Toast.makeText(TripPass.this, "There was a problem refunding your pass", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(TripPass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // WRITE QR
    private void writeQr(String qrData) {
        binding.GenerateTrip.setVisibility(View.GONE);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(qrData, BarcodeFormat.QR_CODE, 800, 800);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            binding.HasTPController.setVisibility(View.VISIBLE);
            binding.QrCodeTP.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void setBasicConfig() {
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.BackButton.setOnClickListener(view -> finish());

        binding.Heading.setText(R.string.mumbai_metro_one);
        binding.TPassProgressBar.setVisibility(View.GONE);
        binding.ReloadTPButton.setVisibility(View.GONE);
        binding.RefundPassCard.setVisibility(View.GONE);
        binding.TPProgressBar.setVisibility(View.GONE);
        binding.QrCodeCard.setVisibility(View.GONE);

    }
}