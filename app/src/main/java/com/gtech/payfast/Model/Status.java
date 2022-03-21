package com.gtech.payfast.Model;

public class Status {

    private static Status status;

    String STATUS_USER_FOUND = "100";
    String STATUS_USER_NOT_FOUND = "101";
    String STATUS_USER_CREATED = "102";
    String STATUS_USER_ALREADY_EXISTS = "103";

    String STATUS_ORDER_CREATED = "1001";
    String STATUS_ORDER_PAYMENT_SUCCESS = "1002";
    String STATUS_ORDER_PAYMENT_FAILS = "1003";
    String STATUS_ORDER_QR_GENERATED = "1004";
    String STATUS_ORDER_EXPIRED = "1005";
    String STATUS_ORDER_REFUNDED = "1006";
    String STATUS_ORDER_CREATION_FAILED = "1007";
    String STATUS_NO_ORDER_FOUND = "1008";
    String STATUS_FETCHED_ORDER_SUCCESSFULLY = "1009";

    String STATUS_QR_FAILED_TO_FETCH_STATUS = "1010";
    String STATUS_QR_STATUS_UPDATED_SUCCESSFULLY = "1011";

    String STATUS_NO_QR_CODE_FOUND = "1012";
    String STATUS_QR_CODE_FETCHED_SUCCESSFULLY = "1012";

    String STATUS_PASS_GENERATED = "1013";
    String STATUS_NO_PASS_FOUND = "1014";
    String STATUS_PASS_FETCHED_SUCCESSFULLY = "1015";
    String STATUS_PASS_CANCELLED = "1016";

    String ORDER_TYPE_NORMAL = "3000";
    String ORDER_TYPE_RELOAD = "3001";

    public static synchronized Status getInstance() {
        if (status == null) status = new Status();
        return status;
    }
    public String getORDER_TYPE_NORMAL() {
        return ORDER_TYPE_NORMAL;
    }
    public String getSTATUS_USER_FOUND() {
        return STATUS_USER_FOUND;
    }
    public String getSTATUS_USER_NOT_FOUND() {
        return STATUS_USER_NOT_FOUND;
    }
    public String getSTATUS_USER_CREATED() {
        return STATUS_USER_CREATED;
    }
    public String getSTATUS_USER_ALREADY_EXISTS() {
        return STATUS_USER_ALREADY_EXISTS;
    }
    public String getSTATUS_ORDER_CREATED() {
        return STATUS_ORDER_CREATED;
    }
    public String getSTATUS_ORDER_PAYMENT_SUCCESS() {
        return STATUS_ORDER_PAYMENT_SUCCESS;
    }
    public String getSTATUS_ORDER_PAYMENT_FAILS() {
        return STATUS_ORDER_PAYMENT_FAILS;
    }
    public String getSTATUS_ORDER_QR_GENERATED() {
        return STATUS_ORDER_QR_GENERATED;
    }
    public String getSTATUS_ORDER_EXPIRED() {
        return STATUS_ORDER_EXPIRED;
    }
    public String getSTATUS_ORDER_REFUNDED() {
        return STATUS_ORDER_REFUNDED;
    }
    public String getSTATUS_ORDER_CREATION_FAILED() {
        return STATUS_ORDER_CREATION_FAILED;
    }
    public String getSTATUS_NO_ORDER_FOUND() {
        return STATUS_NO_ORDER_FOUND;
    }
    public String getSTATUS_FETCHED_ORDER_SUCCESSFULLY() {
        return STATUS_FETCHED_ORDER_SUCCESSFULLY;
    }
    public String getSTATUS_QR_FAILED_TO_FETCH_STATUS() {
        return STATUS_QR_FAILED_TO_FETCH_STATUS;
    }
    public String getSTATUS_QR_STATUS_UPDATED_SUCCESSFULLY() {
        return STATUS_QR_STATUS_UPDATED_SUCCESSFULLY;
    }
    public String getSTATUS_NO_QR_CODE_FOUND() {
        return STATUS_NO_QR_CODE_FOUND;
    }
    public String getSTATUS_QR_CODE_FETCHED_SUCCESSFULLY() {
        return STATUS_QR_CODE_FETCHED_SUCCESSFULLY;
    }
    public String getSTATUS_PASS_GENERATED() {
        return STATUS_PASS_GENERATED;
    }
    public String getSTATUS_NO_PASS_FOUND() {
        return STATUS_NO_PASS_FOUND;
    }
    public String getSTATUS_PASS_FETCHED_SUCCESSFULLY() {
        return STATUS_PASS_FETCHED_SUCCESSFULLY;
    }
    public String getSTATUS_PASS_CANCELLED() {
        return STATUS_PASS_CANCELLED;
    }
    public String getORDER_TYPE_RELOAD() {
        return ORDER_TYPE_RELOAD;
    }
}
