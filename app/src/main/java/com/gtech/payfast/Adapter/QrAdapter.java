package com.gtech.payfast.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gtech.payfast.Database.DBHelper;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.R;

import java.util.List;

public class QrAdapter extends RecyclerView.Adapter<QrAdapter.QrViewHolder> {

    List<UpwardTicket> ticketQrs;
    ProgressBar qrProgressBar;
    Context context;
    DBHelper dbHelper;

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


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull QrViewHolder holder, int position) {

        switch (ticketQrs.get(position).getPass_id()) {
            case 10:
                holder.TicketType.setText("Single Journey Ticket");
                break;
            case 90:
                holder.TicketType.setText("Return Journey Ticket");
                holder.ArrowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.return_arrow));
                break;
            case 81:
                holder.TicketType.setText("Store Value Ticket");
                break;
        }

        if (ticketQrs.get(position).getSource() != null) {

            holder.Source.setText(ticketQrs.get(position).getSource());
            holder.Destination.setText(ticketQrs.get(position).getDestination());

        }

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

        // holder.Status.setText(ticketQrs.get(position).getQr_status());
        holder.MasterTxnId.setText("MID: " + ticketQrs.get(position).getMs_qr_no());
        int count = position + 1;
        holder.Passenger.setText("Passenger " + count + "  ||  " + ticketQrs.get(position).getQr_dir());

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

        TextView TicketType, Source, Destination, Passenger;
        ImageView ArrowImage, QrCode;
        Chip SID, Status, NeedHelp, ShareQr, MasterTxnId;

        public QrViewHolder(@NonNull View itemView) {
            super(itemView);

            TicketType = itemView.findViewById(R.id.TicketType);
            Source = itemView.findViewById(R.id.Source);
            Destination = itemView.findViewById(R.id.Destination);
            ArrowImage = itemView.findViewById(R.id.ArrowImage);
            QrCode = itemView.findViewById(R.id.QrCode);
            SID = itemView.findViewById(R.id.SID);
            Status = itemView.findViewById(R.id.Status);
            NeedHelp = itemView.findViewById(R.id.NeedHelp);
            ShareQr = itemView.findViewById(R.id.ShareQr);
            MasterTxnId = itemView.findViewById(R.id.MasterTxnId);
            Passenger = itemView.findViewById(R.id.Passenger);

        }
    }

}
