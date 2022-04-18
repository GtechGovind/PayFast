package com.gtech.payfast.Activity.SVP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gtech.payfast.Activity.GRA;
import com.gtech.payfast.Auth.ProfileActivity;
import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.RefundDetail;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.SVP.CreateSVPass;
import com.gtech.payfast.Model.SVP.ReloadSVPass;
import com.gtech.payfast.Model.SVP.SVDashboard;
import com.gtech.payfast.Model.SVP.SVStatus;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.Payment.PaymentActivity;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;
import com.gtech.payfast.Utils.SharedPrefUtils;
import com.gtech.payfast.databinding.ActivityStoreValuePassBinding;
import com.gtech.payfast.databinding.ReloadBottomSheetLayoutBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreValuePass extends AppCompatActivity {

    private ActivityStoreValuePassBinding binding;
    private BottomSheetDialog reloadDialogue;
    private boolean statusUpdated = false;
    AlertDialog.Builder builder;
    private String slQrNo;
    private User user = null;
    private UpwardTicket pass, trip = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreValuePassBinding.inflate(getLayoutInflater());
        View SVPView = binding.getRoot();
        setContentView(SVPView);
        // INIT ALERT DIALOG
        builder = new AlertDialog.Builder(this);
        // BASIC CONFIG
        setBasicConfig();

        // UPDATE DASHBOARD
        getDashboardData();
    }

    // GET DASHBOARD DATA
    private void getDashboardData() {
        String PAX_ID = SharedPrefUtils.getStringData(this, "PAX_ID");
        Call<SVDashboard> svDashboardCall = ApiController.getInstance().apiInterface().updateSVDashboard(PAX_ID);
        svDashboardCall.enqueue(new Callback<SVDashboard>() {
            @Override
            public void onResponse(@NonNull Call<SVDashboard> call, @NonNull Response<SVDashboard> response) {
                Gson gson = new Gson();
                Log.e("UPDATE_DASHBOARD_REQ", gson.toJson(PAX_ID));
                Log.e("UPDATE_DASHBOARD_RESP", gson.toJson(response.body()));

                binding.SPassProgressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().isStatus()) {

                        user = response.body().getUser();
                        pass = response.body().getPass();
                        trip = response.body().getTrip();
                        updateDashboard();
                    } else {
                        binding.HasSVP.setVisibility(View.GONE);
                        binding.HasSVPController.setVisibility(View.GONE);
                        canIssuePass();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SVDashboard> call, @NonNull Throwable t) {
                binding.HasSVP.setVisibility(View.GONE);
                binding.HasSVPController.setVisibility(View.GONE);
                canIssuePass();
            }
        });
    }

    private void canIssuePass() {
        binding.SPassProgressBar.setVisibility(View.VISIBLE);
        String mobile = SharedPrefUtils.getStringData(this, "NUMBER");
        Call<ResponseModel> canIssuePassCall = ApiController.getInstance().apiInterface().canIssuePass(mobile);
        canIssuePassCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("SV_CAN_ISSUE_PASS_REQ", gson.toJson(mobile));
                Log.e("SV_CAN_ISSUE_PASS_RESP", gson.toJson(response.body()));

                binding.SPassProgressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // Can issue pass
                        // Create SV pass
                        setDonutHaveSvp();
                    } else {
                        Log.e("CAN_ISSUE_SV_RESP", gson.toJson(response.body().getError()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                binding.NoPass.setText("CANNOT ISSUE PASS CARD");
                binding.NoPass.setVisibility(View.VISIBLE);
            }
        });
    }

    // UPDATE DASHBOARD
    private void updateDashboard() {
        binding.QrCodeCard.setVisibility(View.GONE);
        binding.HasSVP.setVisibility(View.VISIBLE);
        binding.HasSVPController.setVisibility(View.VISIBLE);
        if (user != null) {
            String name = user.getPax_name();
            binding.UserName.setText(name);
        }
        if (pass != null) {
            binding.MasterTxnId.setText(pass.getMs_qr_no());
            binding.ExpiryDate.setText(pass.getMs_qr_exp().split(" ")[0]);
            if (trip != null) {
                binding.GenerateTrip.setVisibility(View.GONE);
                binding.QrCodeCard.setVisibility(View.VISIBLE);
                slQrNo = trip.getSl_qr_no();
                binding.QrNo.setText(slQrNo);
                writeQr(trip.getQr_data());
            } else {
                binding.GenerateTrip.setVisibility(View.VISIBLE);
                binding.GenerateTrip.setOnClickListener(view -> generateSVTrip(pass.getSale_or_no()));
            }
            // GET SV STATUS
            if (!statusUpdated) getSVStatus(pass.getMs_qr_no());
            // SETUP PASS RELOAD
            canReloadPass(pass.getSale_or_no());
            // SETUP REFUND BUTTON
            binding.RefundSVPass.setVisibility(View.VISIBLE);
            binding.RefundSVPass.setOnClickListener(v -> getRefundDetails(pass.getSale_or_no()));
        }
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
        binding.OrderNewSVP.setEnabled(false);

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
                        intent.putExtra("PAYMENT_TYPE", "2");
                        intent.putExtra("ORDER_ID", response.body().getOrder_id());
                        intent.putExtra("PRICE", price);
                        startActivity(intent);

                        binding.SPassProgressBar.setVisibility(View.GONE);
                        binding.NoPass.setVisibility(View.VISIBLE);
                        binding.OrderNewSVP.setEnabled(true);
                        finish();
                    } else {
                        // response status is false
                        Log.e("CREATE_NEW_SV_RESP", response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                binding.SPassProgressBar.setVisibility(View.GONE);
                binding.NoPass.setVisibility(View.VISIBLE);
                binding.OrderNewSVP.setEnabled(true);
            }
        });

    }

    // GENERATE SV TRIP
    private void generateSVTrip(String orderId) {
        binding.GenerateTripArrow.setVisibility(View.GONE);
        binding.TripProgressBar.setVisibility(View.VISIBLE);
        Call<ResponseModel> generateSVTripCall =  ApiController.getInstance().apiInterface().generateSVTrip(orderId);
        generateSVTripCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Gson gson = new Gson();
                Log.e("GENERATE_SV_TRIP_REQ", gson.toJson(orderId));
                Log.e("GENERATE_SV_TRIP_RESP", gson.toJson(response.body()));

                binding.TripProgressBar.setVisibility(View.GONE);
                binding.GenerateTripArrow.setVisibility(View.VISIBLE);

                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        // TRIP GENERATED SUCCESSFULLY
                        getDashboardData();

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
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

                         binding.ReloadSVButton.setEnabled(false);
                         binding.ReloadSVButton.setOnClickListener(view -> setBottomSheetForReload(orderId));
                     }
                 }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
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
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
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
                        statusUpdated = true;
                        // SET BALANCE AMOUNT
                        String balanceAmt = "₹" + response.body().getData().getBalance().toString();
                        binding.Balance.setText(balanceAmt);
                        getDashboardData();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SVStatus> call, @NonNull Throwable t) {
            }
        });
    }

    // REFUND SV
    private void refundPass(String orderId) {
        showRefundLoader();
        Call<ResponseModel> refundTPCall = ApiController.getInstance().apiInterface().refundTP(orderId);

        refundTPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                hideRefundLoader();
                Gson gson = new Gson();
                Log.e("REFUND_PASS_REQ", gson.toJson(orderId));
                Log.e("REFUND_PASS_RESP", gson.toJson(response.body()));


                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        getDashboardData();
                    } else {

                    }
                } else {
                    // NETWORK ERROR: COULD NOT REFUND PASS
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
                hideRefundLoader();
                Gson gson = new Gson();
                Log.e("REFUND_DETAILS_REQ", gson.toJson(orderId));
                Log.e("REFUND_DETAILS_RESP", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // DISPLAY POPUP MODAL WITH THE REFUND DETAILS
                        // ON CONFIRMATION REFUND PASS
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
                        Toast.makeText(StoreValuePass.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // ERROR REQUEST FAILED
                }
            }

            @Override
            public void onFailure(@NonNull Call<RefundDetail> call, @NonNull Throwable t) {
                hideRefundLoader();
            }
        });
    }

    // SHOW ACTIVITY INDICATOR (PROGRESS BAR) FOR REFUND REQUEST
    private void showRefundLoader() {
        binding.RefundSVPass.setEnabled(false);
        binding.RefundCardArrow.setVisibility(View.GONE);
        binding.RefundProgressBar.setVisibility(View.VISIBLE);
    }
    // DISABLE ACTIVITY INDICATOR
    private void hideRefundLoader() {
        binding.RefundProgressBar.setVisibility(View.GONE);
        binding.RefundCardArrow.setVisibility(View.VISIBLE);
        binding.RefundSVPass.setEnabled(true);
    }

    // SET CONFIG
    private void setBasicConfig() {
        binding.Profile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        binding.BackButton.setOnClickListener(view -> finish());
        binding.Heading.setText(R.string.mumbai_metro_one);

        binding.HasSVPController.setVisibility(View.GONE);
        binding.ReloadSVButton.setEnabled(false);
        binding.NeedHelp.setOnClickListener(v -> openNeedHelpModal());
    }

    private void openNeedHelpModal() {
        final int[] choice = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(StoreValuePass.this);
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
                        Intent intent = new Intent(StoreValuePass.this, GRA.class);
                        intent.putExtra("SL_QR_NO", slQrNo);
                        intent.putExtra("TICKET_TYPE", "SVP") ;
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

        // RELOAD PASS56
        ReloadBinding.ReloadReloadPass.setOnClickListener(view ->
                reloadSVPPass(orderId, ReloadBinding.ReloadRechargeAmount.getText().toString(), ReloadBinding.ReloadProgressBar)
        );

        // SHOW DIALOGUE
        reloadDialogue.show();

    }

}