package com.gtech.payfast.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gtech.payfast.Model.Ticket.UpwardTicket;
import com.gtech.payfast.R;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    // list of Upcoming Orders
    // list of Recent Orders
    List<UpwardTicket> upcomingOrders;
    List<UpwardTicket> recentOrders;

    public TicketAdapter(List<UpwardTicket> upcomingOrders, List<UpwardTicket> recentOrders) {
        this.upcomingOrders = upcomingOrders;
        this.recentOrders = recentOrders;
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
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView Source, Destination, Passenger;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            Source = itemView.findViewById(R.id.Source);
            Destination = itemView.findViewById(R.id.Destination);
            Passenger = itemView.findViewById(R.id.Passenger);

        }
    }
}
