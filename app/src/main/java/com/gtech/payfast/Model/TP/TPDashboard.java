package com.gtech.payfast.Model.TP;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;

public class TPDashboard implements Serializable {
  private Trip trip;

  private Pass pass;

  private User user;

  private Boolean status;

  public Trip getTrip() {
    return this.trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  public Pass getPass() {
    return this.pass;
  }

  public void setPass(Pass pass) {
    this.pass = pass;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public static class Trip implements Serializable {
    private Integer trp_deducted;

    private Integer balance_trp;

    private Object ref_qr_no;

    private String insert_date;

    private Integer qr_status;

    private String sl_qr_no;

    private Integer sale_or_id;

    private String txn_date;

    private Integer mm_ms_acc_id;

    private String sl_qr_exp;

    private Integer mm_sl_acc_id;

    private String qr_data;

    private Integer id;

    public Integer getTrp_deducted() {
      return this.trp_deducted;
    }

    public void setTrp_deducted(Integer trp_deducted) {
      this.trp_deducted = trp_deducted;
    }

    public Integer getBalance_trp() {
      return this.balance_trp;
    }

    public void setBalance_trp(Integer balance_trp) {
      this.balance_trp = balance_trp;
    }

    public Object getRef_qr_no() {
      return this.ref_qr_no;
    }

    public void setRef_qr_no(Object ref_qr_no) {
      this.ref_qr_no = ref_qr_no;
    }

    public String getInsert_date() {
      return this.insert_date;
    }

    public void setInsert_date(String insert_date) {
      this.insert_date = insert_date;
    }

    public Integer getQr_status() {
      return this.qr_status;
    }

    public void setQr_status(Integer qr_status) {
      this.qr_status = qr_status;
    }

    public String getSl_qr_no() {
      return this.sl_qr_no;
    }

    public void setSl_qr_no(String sl_qr_no) {
      this.sl_qr_no = sl_qr_no;
    }

    public Integer getSale_or_id() {
      return this.sale_or_id;
    }

    public void setSale_or_id(Integer sale_or_id) {
      this.sale_or_id = sale_or_id;
    }

    public String getTxn_date() {
      return this.txn_date;
    }

    public void setTxn_date(String txn_date) {
      this.txn_date = txn_date;
    }

    public Integer getMm_ms_acc_id() {
      return this.mm_ms_acc_id;
    }

    public void setMm_ms_acc_id(Integer mm_ms_acc_id) {
      this.mm_ms_acc_id = mm_ms_acc_id;
    }

    public String getSl_qr_exp() {
      return this.sl_qr_exp;
    }

    public void setSl_qr_exp(String sl_qr_exp) {
      this.sl_qr_exp = sl_qr_exp;
    }

    public Integer getMm_sl_acc_id() {
      return this.mm_sl_acc_id;
    }

    public void setMm_sl_acc_id(Integer mm_sl_acc_id) {
      this.mm_sl_acc_id = mm_sl_acc_id;
    }

    public String getQr_data() {
      return this.qr_data;
    }

    public void setQr_data(String qr_data) {
      this.qr_data = qr_data;
    }

    public Integer getId() {
      return this.id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }

  public static class Pass implements Serializable {
    private Integer des_stn_id;

    private Integer pass_id;

    private Integer total_price;

    private Object pg_txn_no;

    private Integer src_stn_id;

    private String insert_date;

    private String destination;

    private String sale_or_no;

    private String source;

    private Integer unit_price;

    private String ms_qr_no;

    private Integer sale_or_id;

    private String txn_date;

    private Integer unit;

    private Integer sale_or_status;

    private Object ref_sl_qr;

    private Integer mm_ms_acc_id;

    private Integer product_id;

    private String ms_qr_exp;

    private Integer pg_id;

    private Integer media_type_id;

    private Integer op_type_id;

    private Integer pax_id;

    public Integer getDes_stn_id() {
      return this.des_stn_id;
    }

    public void setDes_stn_id(Integer des_stn_id) {
      this.des_stn_id = des_stn_id;
    }

    public Integer getPass_id() {
      return this.pass_id;
    }

    public void setPass_id(Integer pass_id) {
      this.pass_id = pass_id;
    }

    public Integer getTotal_price() {
      return this.total_price;
    }

    public void setTotal_price(Integer total_price) {
      this.total_price = total_price;
    }

    public Object getPg_txn_no() {
      return this.pg_txn_no;
    }

    public void setPg_txn_no(Object pg_txn_no) {
      this.pg_txn_no = pg_txn_no;
    }

    public Integer getSrc_stn_id() {
      return this.src_stn_id;
    }

    public void setSrc_stn_id(Integer src_stn_id) {
      this.src_stn_id = src_stn_id;
    }

    public String getInsert_date() {
      return this.insert_date;
    }

    public void setInsert_date(String insert_date) {
      this.insert_date = insert_date;
    }

    public String getDestination() {
      return this.destination;
    }

    public void setDestination(String destination) {
      this.destination = destination;
    }

    public String getSale_or_no() {
      return this.sale_or_no;
    }

    public void setSale_or_no(String sale_or_no) {
      this.sale_or_no = sale_or_no;
    }

    public String getSource() {
      return this.source;
    }

    public void setSource(String source) {
      this.source = source;
    }

    public Integer getUnit_price() {
      return this.unit_price;
    }

    public void setUnit_price(Integer unit_price) {
      this.unit_price = unit_price;
    }

    public String getMs_qr_no() {
      return this.ms_qr_no;
    }

    public void setMs_qr_no(String ms_qr_no) {
      this.ms_qr_no = ms_qr_no;
    }

    public Integer getSale_or_id() {
      return this.sale_or_id;
    }

    public void setSale_or_id(Integer sale_or_id) {
      this.sale_or_id = sale_or_id;
    }

    public String getTxn_date() {
      return this.txn_date;
    }

    public void setTxn_date(String txn_date) {
      this.txn_date = txn_date;
    }

    public Integer getUnit() {
      return this.unit;
    }

    public void setUnit(Integer unit) {
      this.unit = unit;
    }

    public Integer getSale_or_status() {
      return this.sale_or_status;
    }

    public void setSale_or_status(Integer sale_or_status) {
      this.sale_or_status = sale_or_status;
    }

    public Object getRef_sl_qr() {
      return this.ref_sl_qr;
    }

    public void setRef_sl_qr(Object ref_sl_qr) {
      this.ref_sl_qr = ref_sl_qr;
    }

    public Integer getMm_ms_acc_id() {
      return this.mm_ms_acc_id;
    }

    public void setMm_ms_acc_id(Integer mm_ms_acc_id) {
      this.mm_ms_acc_id = mm_ms_acc_id;
    }

    public Integer getProduct_id() {
      return this.product_id;
    }

    public void setProduct_id(Integer product_id) {
      this.product_id = product_id;
    }

    public String getMs_qr_exp() {
      return this.ms_qr_exp;
    }

    public void setMs_qr_exp(String ms_qr_exp) {
      this.ms_qr_exp = ms_qr_exp;
    }

    public Integer getPg_id() {
      return this.pg_id;
    }

    public void setPg_id(Integer pg_id) {
      this.pg_id = pg_id;
    }

    public Integer getMedia_type_id() {
      return this.media_type_id;
    }

    public void setMedia_type_id(Integer media_type_id) {
      this.media_type_id = media_type_id;
    }

    public Integer getOp_type_id() {
      return this.op_type_id;
    }

    public void setOp_type_id(Integer op_type_id) {
      this.op_type_id = op_type_id;
    }

    public Integer getPax_id() {
      return this.pax_id;
    }

    public void setPax_id(Integer pax_id) {
      this.pax_id = pax_id;
    }
  }

  public static class User implements Serializable {
    private String pax_name;

    private String insert_date;

    private String pax_email;

    private Long pax_mobile;

    private Object is_verified;

    private Integer pax_id;

    private Integer points;

    public String getPax_name() {
      return this.pax_name;
    }

    public void setPax_name(String pax_name) {
      this.pax_name = pax_name;
    }

    public String getInsert_date() {
      return this.insert_date;
    }

    public void setInsert_date(String insert_date) {
      this.insert_date = insert_date;
    }

    public String getPax_email() {
      return this.pax_email;
    }

    public void setPax_email(String pax_email) {
      this.pax_email = pax_email;
    }

    public Long getPax_mobile() {
      return this.pax_mobile;
    }

    public void setPax_mobile(Long pax_mobile) {
      this.pax_mobile = pax_mobile;
    }

    public Object getIs_verified() {
      return this.is_verified;
    }

    public void setIs_verified(Object is_verified) {
      this.is_verified = is_verified;
    }

    public Integer getPax_id() {
      return this.pax_id;
    }

    public void setPax_id(Integer pax_id) {
      this.pax_id = pax_id;
    }

    public Integer getPoints() {
      return this.points;
    }

    public void setPoints(Integer points) {
      this.points = points;
    }
  }
}
