package com.gtech.payfast.Model;

public class ProcessedTicket {
      Boolean status;
      int product_id;
      String order_id;
      int op_type_id;

    public ProcessedTicket(Boolean status, int product_id, String order_id, int op_type_id) {
        this.status = status;
        this.product_id = product_id;
        this.order_id = order_id;
        this.op_type_id = op_type_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getOp_type_id() {
        return op_type_id;
    }

    public void setOp_type_id(int op_type_id) {
        this.op_type_id = op_type_id;
    }
}
