package com.gtech.payfast.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gtech.payfast.Activity.QrActivity;
import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.R;

import java.util.Date;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    // list of Upcoming Orders
    List<UpwardTicket> upcomingOrders;
    Context context;

    public TicketAdapter(List<UpwardTicket> upcomingOrders, Context context) {
        this.upcomingOrders = upcomingOrders;
        this.context = context;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tickets_recycler_view_layout, parent, false);
        return new TicketAdapter.TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        holder.Source.setText(upcomingOrders.get(position).getSource());
        holder.Destination.setText(upcomingOrders.get(position).getDestination());

        String journeyType;

        if (upcomingOrders.get(position).getPass_id() == 10) {
            journeyType = "Single Journey";
            holder.ArrowSingleJourney.setVisibility(View.VISIBLE);
            holder.ArrowReturnJourney.setVisibility(View.GONE);
            holder.JourneyType.setText(journeyType);
        }
        if (upcomingOrders.get(position).getPass_id() == 90) {
            journeyType = "Return Journey";
            holder.ArrowSingleJourney.setVisibility(View.GONE);
            holder.ArrowReturnJourney.setVisibility(View.VISIBLE);
            holder.JourneyType.setText(journeyType);
        }

        holder.PassengerCount.setText(Integer.toString(upcomingOrders.get(position).getUnit()));
        holder.Fare.setText("₹" + upcomingOrders.get(position).getTotal_price());
        String validTill = "Valid till last train on " + upcomingOrders.get(position).getMs_qr_exp().split(" ")[0];
        holder.Expiry.setText(validTill);

        holder.TPCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, QrActivity.class);
            intent.putExtra("ORDER_ID", upcomingOrders.get(position).getSale_or_no());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return upcomingOrders.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView Source, Destination, PassengerCount, JourneyType, Fare, Expiry;
        CardView TPCard;
        ImageView ArrowSingleJourney, ArrowReturnJourney;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            Source = itemView.findViewById(R.id.SourceStation);
            Destination = itemView.findViewById(R.id.DestinationStation);
            TPCard = itemView.findViewById(R.id.TicketCard);
            PassengerCount = itemView.findViewById(R.id.PassengerCount);
            JourneyType = itemView.findViewById(R.id.JourneyType);
            Fare = itemView.findViewById(R.id.TicketFare);
            Expiry = itemView.findViewById(R.id.TicketExpiryDate);
            ArrowSingleJourney = itemView.findViewById(R.id.ArrowSingleJourney);
            ArrowReturnJourney = itemView.findViewById(R.id.ArrowReturnJourney);

        }
    }
}
