package com.gtech.payfast.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gtech.payfast.Activity.GRA;
import com.gtech.payfast.Activity.QrActivity;
import com.gtech.payfast.Activity.TicketDashboard;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.RefundDetail;
import com.gtech.payfast.Model.ResponseModel;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.R;
import com.gtech.payfast.Retrofit.ApiController;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrAdapter extends RecyclerView.Adapter<QrAdapter.QrViewHolder> {

    List<UpwardTicket> ticketQrs;
    ProgressBar qrProgressBar;
    Context context;
    DBHelper dbHelper;
    String slQrNo, orderId;

    public QrAdapter(List<UpwardTicket> qrs, ProgressBar qrProgressBar, Context context) {
        this.ticketQrs = qrs;
        this.qrProgressBar = qrProgressBar;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public QrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_activity_recycler_view_layout, parent, false);
        return new QrViewHolder(view);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull QrViewHolder holder, int position) {

        holder.SID.setText(ticketQrs.get(position).getSl_qr_no());

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(ticketQrs.get(position).getQr_data(), BarcodeFormat.QR_CODE, 800, 800);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            holder.QrCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        // SET TICKET STATUS
        String status;
        switch (ticketQrs.get(position).getQr_status()) {
            case 1:
                status = "GENERATED";
                break;
            case 2:
                status = "IN JOURNEY";
                break;
            case 3:
                status = "COMPLETED";
                break;
            case 4:
                status = "EXPIRED";
                break;
            default:
                status = "";
                break;
        }
        // SET CURRENT PASSENGER INDEX
        String passengerCount = "Passenger " + (position + 1);
        holder.Passenger.setText(passengerCount);
        slQrNo = ticketQrs.get(position).getSl_qr_no();
        orderId = ticketQrs.get(position).getSale_or_no();
        holder.NeedHelp.setOnClickListener(view -> {
            openNeedHelpModal();
        });
    }

    private void openNeedHelpModal() {
        final int[] choice = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Help");
        String[] items = {"Unable to Exit", "Refund"};
        int checkedItem = 0;
        builder.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                case 1:
                    choice[0] = which;
                    break;
            }
        });
        builder.setCancelable(false)
                .setPositiveButton("Next", (dialog, id) -> {
                    switch (choice[0]) {
                        case 0:
                            // OPEN GRA ACTIVITY
                            Intent intent = new Intent(context, GRA.class);
                            intent.putExtra("SL_QR_NO", slQrNo);
                            context.startActivity(intent);
                            break;
                        case 1:
                            // Refund dialog
                            getRefundDetails(orderId);
                            break;
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

    private void getRefundDetails(String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Fetching refund details...");
        AlertDialog alert = builder.create();
        alert.setTitle("Refund Ticket");
        alert.show();
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Call<RefundDetail> refundDetailCall = ApiController.getInstance().apiInterface().getRefundDetails(orderId);
        refundDetailCall.enqueue(new Callback<RefundDetail>() {
            @Override
            public void onResponse(Call<RefundDetail> call, Response<RefundDetail> response) {
                alert.dismiss();
                Gson gson = new Gson();
                Log.e("REFUND_DETAILS_REQ", gson.toJson(orderId));
                Log.e("REFUND_DETAILS_RESP", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // GET PROCESSING FEE AMOUNT FROM REQUEST, REFUND AMOUNT AND PASS PRICE FROM REQUEST
                        String processingFeeAmt = Integer.toString(response.body().getRefund().getProcessing_fee_amount());
                        String refundAmt = Integer.toString(response.body().getRefund().getRefund_amount());
                        String passPrice = Integer.toString(response.body().getRefund().getPass_price());
                        // DISPLAY POPUP MODAL WITH THE REFUND DETAILS
                        final View refundDetailsLayout
                                = li
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
                        builder.setMessage("Are you sure you want to refund your ticket")
                                .setCancelable(false)  // ON CONFIRMATION REFUND PASS
                                .setPositiveButton("Yes, Refund", (dialog, id) -> refundTicket(orderId))
                                .setNegativeButton("No", (dialog, id) -> {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Refund Ticket");
                        alert.show();
                    } else {
                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundDetail> call, Throwable t) { ;
            }
        });
    }

    private void refundTicket(String orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Loading...");
        AlertDialog alert = builder.create();
        alert.show();
        Call<ResponseModel> refundTPCall = ApiController.getInstance().apiInterface().refundTP(orderId);

        refundTPCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                alert.dismiss();
                Gson gson = new Gson();
                Log.e("REFUND_TICKET_REQ", gson.toJson(orderId));
                Log.e("REFUND_TICKET_RESP", gson.toJson(response.body()));


                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        Intent intent = new Intent(context, TicketDashboard.class);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        qrProgressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return ticketQrs.size();
    }

    static class QrViewHolder extends RecyclerView.ViewHolder {

        TextView Passenger;
        ImageView QrCode;
        Chip SID, NeedHelp, ShareQr;

        public QrViewHolder(@NonNull View itemView) {
            super(itemView);

            QrCode = itemView.findViewById(R.id.QrCode);
            SID = itemView.findViewById(R.id.SID);
            NeedHelp = itemView.findViewById(R.id.NeedHelp);
            ShareQr = itemView.findViewById(R.id.ShareQr);
            Passenger = itemView.findViewById(R.id.Passenger);
        }
    }

}
