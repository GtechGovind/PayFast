package com.gtech.payfast.Model.SVP;

public class CreateSVPass {
    Integer price;
    String pax_mobile;

    public CreateSVPass(Integer price, String pax_mobile) {
        this.price = price;
        this.pax_mobile = pax_mobile;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPax_mobile() {
        return pax_mobile;
    }

    public void setPax_mobile(String pax_mobile) {
        this.pax_mobile = pax_mobile;
    }
}
