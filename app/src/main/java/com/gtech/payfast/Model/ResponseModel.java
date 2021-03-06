package com.gtech.payfast.Model;

import com.gtech.payfast.Model.Auth.User;
import com.gtech.payfast.Model.Fetch.Qr;
import com.gtech.payfast.Model.IssueToken.Response.ResponseData;

import java.util.List;

public class ResponseModel {

    // UNIVERSAL
    boolean status;
    int code;
    String error;
    User user;

    // ORDER
    String message, order_no;

    // GET QR
    List<Qr> qrs;

    // CHECK PASS
    ResponseData data;

    // IF_USER_DOES_NOT_EXIST
    public ResponseModel(boolean status, int code, String error) {
        this.status = status;
        this.code = code;
        this.error = error;
    }

    // USER_EXIST
    public ResponseModel(boolean status, int code, User user) {
        this.status = status;
        this.code = code;
        this.user = user;
    }

    // ORDER CREATED
    public ResponseModel(boolean status, int code, String message, String order_no) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.order_no = order_no;
    }

    // GET QR CODES
    public ResponseModel(boolean status, int code, List<Qr> qrs) {
        this.status = status;
        this.code = code;
        this.qrs = qrs;
    }

    // CHECK IF PASS EXITS
    public ResponseModel(boolean status, int code, ResponseData data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    // GETTERS AND SETTERS
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getOrder_no() {
        return order_no;
    }
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
    public List<Qr> getQrs() {
        return qrs;
    }
    public void setQrs(List<Qr> qrs) {
        this.qrs = qrs;
    }
    public ResponseData getData() {
        return data;
    }
    public void setData(ResponseData data) {
        this.data = data;
    }
}
