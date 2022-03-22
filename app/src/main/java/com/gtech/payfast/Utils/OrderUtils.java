package com.gtech.payfast.Utils;

import com.gtech.payfast.BuildConfig;

import java.util.UUID;

public class OrderUtils {

    public static String getOID(String _sufix) {
        String uuid = UUID.randomUUID().toString();
        return "ATEK-" + _sufix + "-" + uuid.toUpperCase();
    }

    public static String truncate(String str, int len) {
        if (len <= 3) {
            throw new IllegalArgumentException();
        }

        if (str.length() > len) {
            return str.substring(0, (len - 3)) + "...";
        } else {
            return str;
        }
    }

    public static String getOrderTypeCode(String TicketType){
        if (TicketType.equals("Single")) return BuildConfig.TOKEN_TYPE_SJT;
        else return BuildConfig.TOKEN_TYPE_RJT;
    }

}
