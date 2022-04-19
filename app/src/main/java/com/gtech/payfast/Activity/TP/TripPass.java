package com.gtech.payfast.Activity.TP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gtech.payfast.Activity.GRA;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.Model.RefundDetail;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.TP.ReloadTP;
import com.gtech.payfast.Model.TP.TPDashboard;

import com.gtech.payfast.Model.TP.TPStatus;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityTripPassBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripPass extends AppCompatActivity {
    private ActivityTripPassBinding binding;
    AlertDialog.Builder builder;
    private String slQrNo;
    private TPDashboard.Pass pass;
    private TPDashboard.User user;
    private TPDashboard.Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTripPassBinding.inflate(getLayoutInflater());
        View TripPassView = binding.getRoot();
        setContentView(TripPassView);
        // INIT ALERT DIALOG
        builder = new AlertDialog.Builder(this);
        // SET BASIC CONFIG
        setBasicConfig();
        // UPDATE DASHBOARD
        getDashboardData();
    }

    private void getDashboardData() {
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
                    pass = response.body().getPass();
                    user = response.body().getUser();
                    trip = response.body().getTrip();
                    updateDashboard();
                } else {
                    binding.HasTP.setVisibility(View.GONE);
                    binding.NoPass.setVisibility(View.VISIBLE);
                    canIssuePass();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TPDashboard> call, @NonNull Throwable t) {
                binding.HasTP.setVisibility(View.GONE);
                binding.NoPass.setVisibility(View.VISIBLE);
                canIssuePass();
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
                        Date date = new Date(response.body().getData().getMasterExpiry() * 1000L);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        binding.ExpiryDate.setText( format.format(date) );
                        // SET BALANCE TRIPS
                        String balanceTrips = response.body().getData().getBalanceTrip().toString();
                        binding.BalanceTrips.setText(balanceTrips);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TPStatus> call, @NonNull Throwable t) {
            }
        });
    }

    private void updateDashboard() {
        binding.QrCodeCard.setVisibility(View.GONE);
        binding.HasTP.setVisibility(View.VISIBLE);
        binding.RefundPassCard.setEnabled(true);
        binding.RefundPassCard.setOnClickListener(v -> getRefundDetails(pass.getSale_or_no()));

        binding.UserName.setText(user.getPax_name());
        binding.MasterTxnId.setText(pass.getMs_qr_no());
        binding.SourceTP.setText(pass.getSource());
        binding.DestinationTP.setText(pass.getDestination());

        if (trip != null) {
            binding.QrCodeCard.setVisibility(View.VISIBLE);
            slQrNo = trip.getSl_qr_no();
            binding.QrNoTP.setText(slQrNo);

            writeQr(trip.getQr_data());
        } else {
            binding.HasTPController.setVisibility(View.VISIBLE);
            binding.GenerateTrip.setVisibility(View.VISIBLE);
            binding.GenerateTrip.setOnClickListener(v -> generateTrip(pass.getSale_or_no()));
        }

        if (pass != null) {
            getTPStatus(pass.getMs_qr_no());
            canReloadPass(pass.getSale_or_no(), pass.getUnit_price());
        }
    }

    // CAN ISSUE TRIP PASS
    private void canIssuePass() {
        binding.TPProgressBar.setVisibility(View.VISIBLE);
        String mobile = SharedPrefUtils.getStringData(this, "NUMBER");
        Call<ResponseModel> canIssuePassCall = ApiController.getInstance().apiInterface().canIssueTP(mobile);
        canIssuePassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                binding.TPProgressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // Pass can be issued
                        // Start pass generation activity
                        startActivity(new Intent(TripPass.this, TripPassGeneration.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                binding.TPProgressBar.setVisibility(View.GONE);
            }
        });
    }

    // GENERATE TRIP
    private void generateTrip(String orderId) {
        binding.GenerateTripArrow.setVisibility(View.GONE);
        binding.TripProgressBar.setVisibility(View.VISIBLE);
        binding.GenerateTrip.setEnabled(false);
        Call<ResponseModel> generateTripCall = ApiController.getInstance().apiInterface().generateTripTP(orderId);
        generateTripCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                binding.GenerateTrip.setEnabled(true);
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        binding.TripProgressBar.setVisibility(View.GONE);
                        binding.GenerateTripArrow.setVisibility(View.VISIBLE);
                        binding.GenerateTrip.setVisibility(View.GONE);
                        getDashboardData();
                    } else {
                        // Could not generate trip
                    }
                } else {
                    // Problem with generate trip request
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                binding.TPProgressBar.setVisibility(View.VISIBLE);
                binding.GenerateTrip.setEnabled(true);
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
                        Toast.makeText(TripPass.this, response.body().getError(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    // GET REFUND DETAILS
    private void getRefundDetails(String orderId) {
        showRefundLoader();
        Call<RefundDetail> refundDetailCall = ApiController.getInstance().apiInterface().getRefundDetails(orderId);
        refundDetailCall.enqueue(new Callback<RefundDetail>() {
            @Override
            public void onResponse(@NonNull Call<RefundDetail> call, @NonNull Response<RefundDetail> response) {
                Gson gson = new Gson();
                Log.e("REFUND_DETAILS_REQ", gson.toJson(orderId));
                Log.e("REFUND_DETAILS_RESP", gson.toJson(response.body()));
                hideRefundLoader();
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // GET PROCESSING FEE AMOUNT FROM REQUEST, REFUND AMOUNT AND PASS PRICE FROM REQUEST
                        String processingFeeAmt = Float.toString(response.body().getRefund().getProcessing_fee_amount());
                        String refundAmt = Float.toString(response.body().getRefund().getRefund_amount());
                        String passPrice = Integer.toString(response.body().getRefund().getPass_price());
                        // DISPLAY POPUP MODAL WITH THE REFUND DETAILS
                        final View refundDetailsLayout
                                = getLayoutInflater()
                                .inflate(
                                        R.layout.modal_refund_details,
                                        null);
                        builder.setView(refundDetailsLayout);
                        TextView rfPrice = refundDetailsLayout.findViewById(R.id.RFPrice);
                        rfPrice.setText(passPrice);
                        TextView rfProcessingFee = refundDetailsLayout.findViewById(R.id.RFProcessingFee);
                        rfProcessingFee.setText(processingFeeAmt);
                        TextView rfRefundAmount = refundDetailsLayout.findViewById(R.id.RFRefundAmount);
                        rfRefundAmount.setText(refundAmt);
                        // OPEN
                        builder.setMessage("Are you sure you want to refund your pass card?")
                                .setCancelable(false)  // ON CONFIRMATION REFUND PASS
                                .setPositiveButton("Yes, Refund", (dialog, id) -> refundPass(orderId))
                                .setNegativeButton("No", (dialog, id) -> {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Refund Pass Card");
                        alert.show();
                    } else {
                        // NOPE. NOT HAPPENING.
                        Toast.makeText(TripPass.this, response.body().getError(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RefundDetail> call, @NonNull Throwable t) {
                binding.RefundProgressBar.setVisibility(View.GONE);
                binding.RefundCardArrow.setVisibility(View.VISIBLE);
                binding.RefundPassCard.setEnabled(true);
            }
        });
    }

    // REFUND PASS
    private void refundPass(String orderId) {
        showRefundLoader();
        Call<ResponseModel> refundTPCall = ApiController.getInstance().apiInterface().refundTP(orderId);
        refundTPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("REFUND_PASS_REQ", gson.toJson(orderId));
                Log.e("REFUND_PASS_RESP", gson.toJson(response.body()));
                hideRefundLoader();
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        getDashboardData();
                    } else {
                        Toast.makeText(TripPass.this, response.body().getError(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                hideRefundLoader();
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


    // SHOW ACTIVITY INDICATOR (PROGRESS BAR) FOR REFUND REQUEST
    private void showRefundLoader() {
        binding.RefundPassCard.setEnabled(false);
        binding.RefundCardArrow.setVisibility(View.GONE);
        binding.RefundProgressBar.setVisibility(View.VISIBLE);
    }
    // DISABLE ACTIVITY INDICATOR
    private void hideRefundLoader() {
        binding.RefundProgressBar.setVisibility(View.GONE);
        binding.RefundCardArrow.setVisibility(View.VISIBLE);
        binding.RefundPassCard.setEnabled(true);
    }

    private void openNeedHelpModal() {
        final int[] choice = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(TripPass.this);
        builder.setTitle("Need Help");
        String[] items = {"Unable to Exit"};
        int checkedItem = 0;
        builder.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            if (which == 0) {
                choice[0] = which;
            }
        });
        builder.setCancelable(false)
                .setPositiveButton("Next", (dialog, id) -> {
                    if (choice[0] == 0) {// OPEN GRA ACTIVITY
                        Intent intent = new Intent(TripPass.this, GRA.class);
                        intent.putExtra("SL_QR_NO", slQrNo);
                        intent.putExtra("TICKET_TYPE", "TP");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    //  Action for 'NO' Button
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void setBasicConfig() {
        binding.BackButton.setOnClickListener(view -> finish());
        binding.NeedHelp.setOnClickListener(v -> openNeedHelpModal());

        binding.Heading.setText(R.string.mumbai_metro_one);
        binding.TPassProgressBar.setVisibility(View.GONE);
        binding.ReloadTPButton.setVisibility(View.GONE);
        binding.RefundPassCard.setEnabled(false);
        binding.TPProgressBar.setVisibility(View.GONE);
        binding.QrCodeCard.setVisibility(View.GONE);

    }
}