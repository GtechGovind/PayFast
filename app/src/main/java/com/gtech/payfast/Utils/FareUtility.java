package com.gtech.payfast.Utils;

import android.content.Context;
import android.util.Log;

import com.gtech.payfast.Database.DBHelper;

public class FareUtility {

    public static String getFareForSingleJourney(Context context, String source, String destination, String fare_for, String ticket_count) {

        DBHelper dbHelper = new DBHelper(context);

        int TicketCount = Integer.parseInt(ticket_count);
        Log.e("FARE", dbHelper.getFare(source, destination, fare_for));
        double fare = Double.parseDouble(dbHelper.getFare(source, destination, fare_for));

        return String.valueOf(fare*TicketCount);

    }

    public static String getFareForReturnJourney(Context context, String source, String destination, String fare_for, String ticket_count) {

        DBHelper dbHelper = new DBHelper(context);

        int TicketCount = Integer.parseInt(ticket_count);
        double fare = Double.parseDouble(dbHelper.getFare(source, destination, fare_for));

        return String.valueOf(2*fare*TicketCount);

    }

}
