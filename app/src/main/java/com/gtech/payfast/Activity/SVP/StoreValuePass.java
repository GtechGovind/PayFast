package com.gtech.payfast.Activity.SVP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gtech.payfast.Activity.QrActivity;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.BuildConfig;
import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.IssueToken.Data;
import com.gtech.payfast.Model.IssueToken.Response.IssueResponse;
import com.gtech.payfast.Model.IssueToken.Response.ResponseData;
import com.gtech.payfast.Model.IssueToken.Response.Trips;
import com.gtech.payfast.Model.Pass.Trip;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.SVP.CreateSVPass;
import com.gtech.payfast.Model.SVP.ReloadSVPass;
import com.gtech.payfast.Model.SVP.SVDashboard;
import com.gtech.payfast.Model.SVP.SVStatus;
import com.gtech.payfast.Model.Status;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.OrderUtils;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityStoreValuePassBinding;
import com.gtech.payfast.databinding.ReloadBottomSheetLayoutBinding;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreValuePass extends AppCompatActivity {

    private ActivityStoreValuePassBinding binding;
    private BottomSheetDialog reloadDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreValuePassBinding.inflate(getLayoutInflater());
        View SVPView = binding.getRoot();
        setContentView(SVPView);

        // BASIC CONFIG
        setBasicConfig();

        // UPDATE DASHBOARD
        updateDashboard();
    }

    private void canIssuePass() {
        String mobile = SharedPrefUtils.getStringData(this, "NUMBER");
        Call<ResponseModel> canIssuePassCall = ApiController.getInstance().apiInterface().canIssuePass(mobile);
        canIssuePassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("SV_CAN_ISSUE_PASS_REQ", gson.toJson(mobile));
                Log.e("SV_CAN_ISSUE_PASS_RESP", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // Can issue pass
                        // Create SV pass
                        Toast.makeText(StoreValuePass.this, "USER CAN BE ISSUED PASS!", Toast.LENGTH_SHORT).show();
                        setDonutHaveSvp();
                    } else {
                        // Cannot issue pass
                        Toast.makeText(StoreValuePass.this, "USER CANNOT BE ISSUED PASS!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Request was unsuccessful
                    Toast.makeText(StoreValuePass.this, "REQUEST FAILED", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                binding.SPassProgressBar.setVisibility(View.GONE);
                binding.HasSVP.setVisibility(View.VISIBLE);
                binding.HasSVPController.setVisibility(View.VISIBLE);
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // UPDATE DASHBOARD
    private void updateDashboard() {
        String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
        Call<SVDashboard> svDashboardCall= ApiController.getInstance().apiInterface().updateSVDashboard(PAX_ID);
        svDashboardCall.enqueue(new Callback<SVDashboard>() {
            @Override
            public void onResponse(@NonNull Call<SVDashboard> call, @NonNull Response<SVDashboard> response) {
                Gson gson = new Gson();
                Log.e("UPDATE_DASHBOARD_REQ", gson.toJson(PAX_ID));
                Log.e("UPDATE_DASHBOARD_RESP", gson.toJson(response.body()));

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.HasSVP.setVisibility(View.VISIBLE);
                        binding.HasSVPController.setVisibility(View.VISIBLE);

                        Log.e("UPDATE_DASHBOARD_REQ", gson.toJson(PAX_ID));
                        Log.e("UPDATE_DASHBOARD_USER", gson.toJson(response.body().getUser()));
                        Log.e("UPDATE_DASHBOARD_PASS", gson.toJson(response.body().getPass()));
                        Log.e("UPDATE_DASHBOARD_TRIP", gson.toJson(response.body().getTrip()));
                        User user = response.body().getUser();
                        UpwardTicket pass = response.body().getPass();
                        UpwardTicket trip = response.body().getTrip();
                        if (user != null) {
                            String name = "Name: " + user.getPax_name();
                            binding.UserName.setText(name);
                        }
                        if (pass != null) {
                            binding.MasterTxnId.setText(pass.getMs_qr_no());
                            binding.Balance.setText(String.valueOf(pass.getTotal_price()));
                        }
                        if (trip != null) {
                            String balanceAmt = "Balance: " + trip.getBalance_amt();
                            binding.Balance.setText(balanceAmt);

                            // SHOW QR CODE IF TRIP HAS BEEN GENERATED,
                            // OTHERWISE SHOW GENERATE TRIP BUTTON
                            binding.GenerateTrip.setVisibility(View.GONE);
                            binding.QrCode.setVisibility(View.VISIBLE);
                            writeQr(trip.getQr_data());
                        } else {
                            // ONCLICK GenerateTrip, call generate trip API
                            if (pass != null) {
                                binding.GenerateTrip.setVisibility(View.VISIBLE);
                                binding.GenerateTrip.setOnClickListener(view -> generateSVTrip(pass.getSale_or_no()));
                            }
                        }
                        // GET SV STATUS
                        if (pass != null) {
                            getSVStatus(pass.getMs_qr_no());
                            canReloadPass(pass.getSale_or_no());
                        }

                        // CHECK IF PASS CAN BE RELOADED OR NOT
                        // SHOW THE RELOAD BUTTON

                    } else {
                        // Call can issue pass
                        binding.SPassProgressBar.setVisibility(View.GONE);
                        canIssuePass();
                    }
                } else {
                    Toast.makeText(StoreValuePass.this, "There was a problem fetching your request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SVDashboard> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    // IF DON'T HAVE PASS
    private void setDonutHaveSvp() {

        binding.NoPass.setVisibility(View.VISIBLE);
        binding.NoPassController.setVisibility(View.VISIBLE);
        // INCREMENT COUNTER
        binding.AddButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(binding.RechargeAmount.getText().toString());
            if (ticket_count < 3000) {
                ticket_count = ticket_count + 100;
                binding.RechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // DECREMENT COUNTER
        binding.SubButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(binding.RechargeAmount.getText().toString());
            if (ticket_count > 100) {
                ticket_count = ticket_count - 100;
                binding.RechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // NEW PASS ORDER
        binding.OrderNewSVP.setOnClickListener(view -> CreateNewPassOrder());

    }

    // CREATE NEW STORE VALUE PASS ORDER
    private void CreateNewPassOrder() {

        binding.SPassProgressBar.setVisibility(View.VISIBLE);
        binding.NoPass.setVisibility(View.GONE);
        // PROCEED TO PAY button visibility disabled
        binding.OrderNewSVP.setVisibility(View.GONE);

        // Recharge amount and mobile number to create SV pass
        Integer price = Integer.parseInt(binding.RechargeAmount.getText().toString());
        String mobile = SharedPrefUtils.getStringData(this, "NUMBER");

        CreateSVPass createSVPass = new CreateSVPass(price, mobile);
        Call<ResponseModel> createSVPassCall = ApiController.getInstance().apiInterface().createSVPass(createSVPass);
        createSVPassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {

                        Intent intent = new Intent(StoreValuePass.this, PaymentActivity.class);
                        // TODO: Intent put extra
                        intent.putExtra("PAYMENT_TYPE", "2");
                        intent.putExtra("ORDER_ID", response.body().getOrder_id());
                        intent.putExtra("PRICE", price);
                        startActivity(intent);

                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.NoPass.setVisibility(View.VISIBLE);
                        binding.OrderNewSVP.setVisibility(View.VISIBLE);

                        finish();
                    } else {
                        // response status is false
                        // TODO: show error -> could not create SV Pass
                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.NoPass.setVisibility(View.VISIBLE);
                        binding.OrderNewSVP.setVisibility(View.VISIBLE);
                        Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // request failed
                    // TODO: show error
                    Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                // TODO: handle network failure
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // IF USER HAS PASS
    private void setHasSvp(ResponseData data) {

        binding.UserName.setText(SharedPrefUtils.getStringData(this, "NAME"));
        binding.MasterTxnId.setText(data.getMasterTxnId());
        binding.Balance.setText(MessageFormat.format("Balance: {0}", data.getBalance()));

        // SET GENERATE TRIP
        int TripsCounter = 0;

        for (Trips trip : data.getTrips())
            if (trip.getTokenStatus().equals("GENERATED") || trip.getTokenStatus().equals("IN_JOURNEY"))
                TripsCounter++;

        if (TripsCounter > 0) binding.ViewQrTicket.setVisibility(View.VISIBLE);
        else binding.GenerateTrip.setVisibility(View.VISIBLE);

        binding.GenerateTrip.setOnClickListener(view -> generateNewTrip(data));

        binding.ViewQrTicket.setOnClickListener(view -> {
            Intent intent = new Intent(StoreValuePass.this, QrActivity.class);
            intent.putExtra("ORDER_NO", data.getOrder_no());
            startActivity(intent);
            finish();
        });

        // binding.ReloadPassCard.setOnClickListener(view -> setBottomSheetForReload(data));

    }

    // GENERATE SV TRIP
    private void generateSVTrip(String orderId) {
        binding.SPassProgressBar.setVisibility(View.VISIBLE);
        Call<ResponseModel> generateSVTripCall =  ApiController.getInstance().apiInterface().generateSVTrip(orderId);
        generateSVTripCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("GENERATE_SV_TRIP_REQ", gson.toJson(orderId));
                Log.e("GENERATE_SV_TRIP_RESP", gson.toJson(response.body()));

                binding.SPassProgressBar.setVisibility(View.GONE);

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // TRIP GENERATED SUCCESSFULLY
                        updateDashboard();

                    } else {
                        // TRIP WAS NOT GENERATED
                        Toast.makeText(StoreValuePass.this, "Failed to generate trip", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // RESPONSE BODY IS NULL
                    Toast.makeText(StoreValuePass.this, "There was a problem making your request", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // RELOAD PASS
    private void reloadSVP(ResponseData SvpData, String fare, ProgressBar reloadProgressBar) {

        reloadProgressBar.setVisibility(View.VISIBLE);

        Order order = new Order(
                OrderUtils.getOID("SVP"),
                SharedPrefUtils.getStringData(this, "NUMBER"),
                null,
                null,
                BuildConfig.TOKEN_TYPE_SVP,
                null,
                fare,
                BuildConfig.PG_ID,
                BuildConfig.OPERATOR_ID,
                Status.getInstance().getSTATUS_ORDER_CREATED(),
                Status.getInstance().getORDER_TYPE_RELOAD()
        );

        Call<ResponseModel> createSvpOrder = ApiController.getInstance().apiInterface().createNewOrder(order);
        createSvpOrder.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                Gson gson = new Gson();
                Log.e("RELOAD_SVP_REQUEST", gson.toJson(order));
                Log.e("RELOAD_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().isStatus()) {

                        Intent intent = new Intent(StoreValuePass.this, PaymentActivity.class);
                        intent.putExtra("PAYMENT_TYPE", "3");
                        intent.putExtra("RECHARGE_AMOUNT", fare);
                        intent.putExtra("ODER_NO", order.getOrder_no());
                        intent.putExtra("MASTER_TXN_ID", SvpData.getMasterTxnId());
                        startActivity(intent);

                        reloadProgressBar.setVisibility(View.GONE);

                        reloadDialogue.cancel();
                        finish();

                    } else {
                        reloadProgressBar.setVisibility(View.GONE);
                        Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // CREATE NEW TRIP
    private void generateNewTrip(ResponseData svpData) {

        Data dataTrip = new Data(
                Calendar.getInstance().getTime().toString(),
                "null",
                SharedPrefUtils.getStringData(this, "EMAIL"),
                SharedPrefUtils.getStringData(this, "NUMBER"),
                SharedPrefUtils.getStringData(this, "NAME"),
                BuildConfig.OPERATION_ISSUE,
                BuildConfig.OPERATOR_ID,
                svpData.getOrder_no(),
                BuildConfig.QR_TRIP_PASS,
                "null",
                BuildConfig.TOKEN_TYPE_SVP,
                svpData.getMasterTxnId()
        );

        Trip trip = new Trip(dataTrip);

        Call<IssueResponse> getTrip = ApiController.getInstance().apiInterface().issueSVPTrip(trip);
        getTrip.enqueue(new Callback<IssueResponse>() {
            @Override
            public void onResponse(@NonNull Call<IssueResponse> call, @NonNull Response<IssueResponse> response) {

                Gson gson = new Gson();
                Log.e("ISSUE_SVP_TRIP_REQUEST", gson.toJson(trip));
                Log.e("ISSUE_SVP_TRIP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().getStatus().equals("OK")) {

                        Intent intent = new Intent(StoreValuePass.this, QrActivity.class);
                        intent.putExtra("ORDER_NO", svpData.getOrder_no());
                        startActivity(intent);
                        finish();

                    } else Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();

                } else Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NonNull Call<IssueResponse> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // CAN RELOAD PASS
    private void canReloadPass(String orderId) {

        Call<ResponseModel> canReloadSVPCall = ApiController.getInstance().apiInterface().canReloadSVP(orderId);
        canReloadSVPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("CAN_RELOAD_PASS_REQ", gson.toJson(orderId));
                Log.e("CAN_RELOAD_PASS_RESP", gson.toJson(response.body()));
                if (response.body() != null) {
                     if (response.body().isStatus()) {
                         // PASS CAN BE RELOADED
                         // SHOW RELOAD PASS BUTTON
                         binding.ReloadPassCard.setVisibility(View.VISIBLE);
                         binding.ReloadPassCard.setOnClickListener(view -> setBottomSheetForReload(orderId));

                     }
                 }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void reloadSVPPass(String orderId, String fare, ProgressBar reloadProgressBar) {
        reloadProgressBar.setVisibility(View.VISIBLE);

        ReloadSVPass svPassReload = new ReloadSVPass(orderId, Integer.parseInt(fare));
        Call<ResponseModel> reloadSVPassCall = ApiController.getInstance().apiInterface().reloadSVP(svPassReload);

        reloadSVPassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("RELOAD_SVP_REQUEST", gson.toJson(orderId));
                Log.e("RELOAD_SVP_RESPONSE", gson.toJson(response.body()));

                if (response.body() != null) {

                    if (response.body().isStatus()) {

                        Intent intent = new Intent(StoreValuePass.this, PaymentActivity.class);
                        intent.putExtra("PAYMENT_TYPE", "2");
                        intent.putExtra("ORDER_ID", response.body().getOrder_id());
                        startActivity(intent);

                        reloadProgressBar.setVisibility(View.GONE);

                        reloadDialogue.cancel();
                        finish();

                    } else {
                        reloadProgressBar.setVisibility(View.GONE);
                        Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(StoreValuePass.this, "Some internal server error try after some time \uD83D\uDE14", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // WRITE QR
    private void writeQr(String qrData) {
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
            binding.QrCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    // UPDATE SV STATUS
    private void getSVStatus(String msTxnId) {
        //
        Call<SVStatus> getSVStatusCall = ApiController.getInstance().apiInterface().getSVStatus(msTxnId);
        getSVStatusCall.enqueue(new Callback<SVStatus>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SVStatus> call, @NonNull Response<SVStatus> response) {

                Gson gson = new Gson();
                Log.e("SV_STATUS_REQ", gson.toJson(msTxnId));
                Log.e("SV_STATUS_RESP", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // SV status updated
                        // Update dashboard
                        if (response.body().getData() != null) {
                            binding.Balance.setText(response.body().getData().getBalance().toString());
                            if (!response.body().getData().getTrips().isEmpty())
                                writeQr(response.body().getData().getTrips().get(0).getQrCodeData());
                        }
                    }
                } else {
                    // REQUEST FAILED
                    Toast.makeText(StoreValuePass.this, "Failed to get SV status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SVStatus> call, @NonNull Throwable t) {
                Toast.makeText(StoreValuePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // SET CONFIG
    private void setBasicConfig() {

        String Heading = "STORE VALUE PASS";
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.Heading.setText(Heading);

        binding.GenerateTrip.setVisibility(View.GONE);
        binding.QrCode.setVisibility(View.GONE);
        binding.ReloadPassCard.setVisibility(View.GONE);
    }

    // BOTTOM LAYOUT FOR RELOAD
    private void setBottomSheetForReload(String orderId) {

        ReloadBottomSheetLayoutBinding ReloadBinding;
        reloadDialogue = new BottomSheetDialog(this);
        ReloadBinding = ReloadBottomSheetLayoutBinding.inflate(getLayoutInflater());
        View ReloadView = ReloadBinding.getRoot();
        reloadDialogue.setContentView(ReloadView);

        // INCREMENT COUNTER
        ReloadBinding.ReloadAddButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(ReloadBinding.ReloadRechargeAmount.getText().toString());
            if (ticket_count < 3000) {
                ticket_count = ticket_count + 100;
                ReloadBinding.ReloadRechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // DECREMENT COUNTER
        ReloadBinding.ReloadSubButton.setOnClickListener(view -> {
            int ticket_count = Integer.parseInt(ReloadBinding.ReloadRechargeAmount.getText().toString());
            if (ticket_count > 100) {
                ticket_count = ticket_count - 100;
                ReloadBinding.ReloadRechargeAmount.setText(String.valueOf(ticket_count));
            }
        });

        // RELOAD PASS
        ReloadBinding.ReloadReloadPass.setOnClickListener(view ->
                reloadSVPPass(orderId, ReloadBinding.ReloadRechargeAmount.getText().toString(), ReloadBinding.ReloadProgressBar)
        );

        // SHOW DIALOGUE
        reloadDialogue.show();

    }

}