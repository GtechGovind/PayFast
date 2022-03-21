package com.gtech.payfast.Model.IssueToken.Response;

public class IssueResponse {

    String status, message, order_no, error;
    int code;
    ResponseData data;

    public IssueResponse(String status, String message, String order_no, int code, ResponseData data) {
        this.status = status;
        this.message = message;
        this.order_no = order_no;
        this.code = code;
        this.data = data;
    }

    public IssueResponse(String status, String error, int code) {
        this.status = status;
        this.error = error;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public ResponseData getData() {
        return data;
    }
    public void setData(ResponseData data) {
        this.data = data;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
