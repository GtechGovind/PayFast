package com.gtech.payfast.Model.Ticket;

public class UpwardTicket {
    int sale_or_id;
    String sale_or_no;
    String txn_date;
    int pax_id;
    int src_stn_id;
    int des_stn_id;
    int mm_ms_acc_id;
    String ms_qr_no;
    String ms_qr_exp;
    int unit;
    int unit_price;
    int total_price;
    int op_type_id;
    int media_type_id;
    int product_id;
    int pass_id;
    int pg_id;
    String pg_txn_no;
    String ref_sl_qr;
    int sale_or_status;
    String insert_date;
    String source;
    String destination;
    int id;
    int mm_sl_acc_id;
    String sl_qr_no;
    String sl_qr_exp;
    String ref_qr_no;
    int qr_dir;
    int qr_status;
    String qr_data;
    int amt_deducted;
    int balance_amt;

    public UpwardTicket(int sale_or_id, String sale_or_no, String txn_date, int pax_id, int src_stn_id, int des_stn_id, int mm_ms_acc_id, String ms_qr_no, String ms_qr_exp, int unit, int unit_price, int total_price, int op_type_id, int media_type_id, int product_id, int pass_id, int pg_id, String pg_txn_no, String ref_sl_qr, int sale_or_status, String insert_date, String source, String destination, int id, int mm_sl_acc_id, String sl_qr_no, String sl_qr_exp, String ref_qr_no, int qr_dir, int qr_status, String qr_data) {
        this.sale_or_id = sale_or_id;
        this.sale_or_no = sale_or_no;
        this.txn_date = txn_date;
        this.pax_id = pax_id;
        this.src_stn_id = src_stn_id;
        this.des_stn_id = des_stn_id;
        this.mm_ms_acc_id = mm_ms_acc_id;
        this.ms_qr_no = ms_qr_no;
        this.ms_qr_exp = ms_qr_exp;
        this.unit = unit;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.op_type_id = op_type_id;
        this.media_type_id = media_type_id;
        this.product_id = product_id;
        this.pass_id = pass_id;
        this.pg_id = pg_id;
        this.pg_txn_no = pg_txn_no;
        this.ref_sl_qr = ref_sl_qr;
        this.sale_or_status = sale_or_status;
        this.insert_date = insert_date;
        this.source = source;
        this.destination = destination;
        this.id = id;
        this.mm_sl_acc_id = mm_sl_acc_id;
        this.sl_qr_no = sl_qr_no;
        this.sl_qr_exp = sl_qr_exp;
        this.ref_qr_no = ref_qr_no;
        this.qr_dir = qr_dir;
        this.qr_status = qr_status;
        this.qr_data = qr_data;
    }

    public UpwardTicket(int sale_or_id, String sale_or_no, String txn_date, int pax_id, int src_stn_id, int des_stn_id, int mm_ms_acc_id, String ms_qr_no, String ms_qr_exp, int unit, int unit_price, int total_price, int op_type_id, int media_type_id, int product_id, int pass_id, int pg_id, String pg_txn_no, String ref_sl_qr, int sale_or_status, String insert_date, String source, String destination) {
        this.sale_or_id = sale_or_id;
        this.sale_or_no = sale_or_no;
        this.txn_date = txn_date;
        this.pax_id = pax_id;
        this.src_stn_id = src_stn_id;
        this.des_stn_id = des_stn_id;
        this.mm_ms_acc_id = mm_ms_acc_id;
        this.ms_qr_no = ms_qr_no;
        this.ms_qr_exp = ms_qr_exp;
        this.unit = unit;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.op_type_id = op_type_id;
        this.media_type_id = media_type_id;
        this.product_id = product_id;
        this.pass_id = pass_id;
        this.pg_id = pg_id;
        this.pg_txn_no = pg_txn_no;
        this.ref_sl_qr = ref_sl_qr;
        this.sale_or_status = sale_or_status;
        this.insert_date = insert_date;
        this.source = source;
        this.destination = destination;
    }

    public UpwardTicket(int sale_or_id, String txn_date, int mm_ms_acc_id, String insert_date, int id, int mm_sl_acc_id, String sl_qr_no, String sl_qr_exp, String ref_qr_no, int qr_status, String qr_data, int amt_deducted, int balance_amt) {
        this.sale_or_id = sale_or_id;
        this.txn_date = txn_date;
        this.mm_ms_acc_id = mm_ms_acc_id;
        this.insert_date = insert_date;
        this.id = id;
        this.mm_sl_acc_id = mm_sl_acc_id;
        this.sl_qr_no = sl_qr_no;
        this.sl_qr_exp = sl_qr_exp;
        this.ref_qr_no = ref_qr_no;
        this.qr_status = qr_status;
        this.qr_data = qr_data;
        this.amt_deducted = amt_deducted;
        this.balance_amt = balance_amt;
    }

    public UpwardTicket(int sale_or_id, String sale_or_no, String txn_date, int pax_id, int src_stn_id, int des_stn_id, int mm_ms_acc_id, String ms_qr_no, String ms_qr_exp, int unit, int unit_price, int total_price, int op_type_id, int media_type_id, int product_id, int pass_id, int pg_id, String pg_txn_no, String ref_sl_qr, int sale_or_status, String insert_date) {
        this.sale_or_id = sale_or_id;
        this.sale_or_no = sale_or_no;
        this.txn_date = txn_date;
        this.pax_id = pax_id;
        this.src_stn_id = src_stn_id;
        this.des_stn_id = des_stn_id;
        this.mm_ms_acc_id = mm_ms_acc_id;
        this.ms_qr_no = ms_qr_no;
        this.ms_qr_exp = ms_qr_exp;
        this.unit = unit;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.op_type_id = op_type_id;
        this.media_type_id = media_type_id;
        this.product_id = product_id;
        this.pass_id = pass_id;
        this.pg_id = pg_id;
        this.pg_txn_no = pg_txn_no;
        this.ref_sl_qr = ref_sl_qr;
        this.sale_or_status = sale_or_status;
        this.insert_date = insert_date;
    }

    public int getSale_or_id() {
        return sale_or_id;
    }

    public void setSale_or_id(int sale_or_id) {
        this.sale_or_id = sale_or_id;
    }

    public String getSale_or_no() {
        return sale_or_no;
    }

    public void setSale_or_no(String sale_or_no) {
        this.sale_or_no = sale_or_no;
    }

    public String getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(String txn_date) {
        this.txn_date = txn_date;
    }

    public int getPax_id() {
        return pax_id;
    }

    public void setPax_id(int pax_id) {
        this.pax_id = pax_id;
    }

    public int getSrc_stn_id() {
        return src_stn_id;
    }

    public void setSrc_stn_id(int src_stn_id) {
        this.src_stn_id = src_stn_id;
    }

    public int getDes_stn_id() {
        return des_stn_id;
    }

    public void setDes_stn_id(int des_stn_id) {
        this.des_stn_id = des_stn_id;
    }

    public int getMm_ms_acc_id() {
        return mm_ms_acc_id;
    }

    public void setMm_ms_acc_id(int mm_ms_acc_id) {
        this.mm_ms_acc_id = mm_ms_acc_id;
    }

    public String getMs_qr_no() {
        return ms_qr_no;
    }

    public void setMs_qr_no(String ms_qr_no) {
        this.ms_qr_no = ms_qr_no;
    }

    public String getMs_qr_exp() {
        return ms_qr_exp;
    }

    public void setMs_qr_exp(String ms_qr_exp) {
        this.ms_qr_exp = ms_qr_exp;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getOp_type_id() {
        return op_type_id;
    }

    public void setOp_type_id(int op_type_id) {
        this.op_type_id = op_type_id;
    }

    public int getMedia_type_id() {
        return media_type_id;
    }

    public void setMedia_type_id(int media_type_id) {
        this.media_type_id = media_type_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPass_id() {
        return pass_id;
    }

    public void setPass_id(int pass_id) {
        this.pass_id = pass_id;
    }

    public int getPg_id() {
        return pg_id;
    }

    public void setPg_id(int pg_id) {
        this.pg_id = pg_id;
    }

    public String getPg_txn_no() {
        return pg_txn_no;
    }

    public void setPg_txn_no(String pg_txn_no) {
        this.pg_txn_no = pg_txn_no;
    }

    public String getRef_sl_qr() {
        return ref_sl_qr;
    }

    public void setRef_sl_qr(String ref_sl_qr) {
        this.ref_sl_qr = ref_sl_qr;
    }

    public int getSale_or_status() {
        return sale_or_status;
    }

    public void setSale_or_status(int sale_or_status) {
        this.sale_or_status = sale_or_status;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMm_sl_acc_id() {
        return mm_sl_acc_id;
    }

    public void setMm_sl_acc_id(int mm_sl_acc_id) {
        this.mm_sl_acc_id = mm_sl_acc_id;
    }

    public String getSl_qr_no() {
        return sl_qr_no;
    }

    public void setSl_qr_no(String sl_qr_no) {
        this.sl_qr_no = sl_qr_no;
    }

    public String getSl_qr_exp() {
        return sl_qr_exp;
    }

    public void setSl_qr_exp(String sl_qr_exp) {
        this.sl_qr_exp = sl_qr_exp;
    }

    public String getRef_qr_no() {
        return ref_qr_no;
    }

    public void setRef_qr_no(String ref_qr_no) {
        this.ref_qr_no = ref_qr_no;
    }

    public int getQr_dir() {
        return qr_dir;
    }

    public void setQr_dir(int qr_dir) {
        this.qr_dir = qr_dir;
    }

    public int getQr_status() {
        return qr_status;
    }

    public void setQr_status(int qr_status) {
        this.qr_status = qr_status;
    }

    public String getQr_data() {
        return qr_data;
    }

    public void setQr_data(String qr_data) {
        this.qr_data = qr_data;
    }

    public int getAmt_deducted() {
        return amt_deducted;
    }

    public void setAmt_deducted(int amt_deducted) {
        this.amt_deducted = amt_deducted;
    }

    public int getBalance_amt() {
        return balance_amt;
    }

    public void setBalance_amt(int balance_amt) {
        this.balance_amt = balance_amt;
    }
}
