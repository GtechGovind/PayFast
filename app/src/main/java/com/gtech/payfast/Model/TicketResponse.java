package com.gtech.payfast.Model;

public class TicketResponse {
    String order_id;
    Boolean status;

    public TicketResponse(Boolean status, String order_id) {
        this.order_id = order_id;
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
