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
    String order_id;

    // GET QR
    List<Qr> qrs;

    // CHECK PASS
    ResponseData data;

    // SVP PASS PAYMENT PROCESSING RESPONSE
    int product_id;
    int op_type_id;

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

    // Check user
    public ResponseModel(User user, Boolean status) {
        this.user = user;
        this.status = status;
    }

    // CREATE TICKET RESPONSE WITH ORDER ID
    public ResponseModel(Boolean status, String order_id) {
        this.status = status;
        this.order_id = order_id;
    }

    public ResponseModel(boolean status, int product_id, int op_type_id) {
        this.status = status;
        this.product_id = product_id;
        this.op_type_id = op_type_id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOp_type_id() {
        return op_type_id;
    }

    public void setOp_type_id(int op_type_id) {
        this.op_type_id = op_type_id;
    }
}
