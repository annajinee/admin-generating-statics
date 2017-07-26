package com.example.model;


import javax.persistence.*;

/**
 * Created by gim-anna on 2017. 1. 16..
 * 운송장 Invoice 테이블 Model
 */
@Entity
@Table(name = "INVOICE")

public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id; // key값 자동증가, int(number(11)

    @Column(name = "COM_CODE")
    private String comcode;

    @Column(name = "INVOICE")
    private String invoice;

    @Column(name = "LEVEL")
    private String level;

    @Column(name = "DETAIL_COUNT")
    private String detailcount;

    @Column(name = "DEST_KIND_ID")
    private String destkindid;

    @Column(name = "LAST_STATE")
    private String laststate;

    @Column(name = "REG_DATE")
    private String regdate;

    @Column(name = "TIER_CODE")
    private String tiercode;

    @Column(name = "SENDER_NAME")
    private String sendername;

    @Column(name = "RECEIVER_NAME")
    private String receivername;

    @Column(name = "RECEIVER_ADDR")
    private String receiveraddr;

    @Column(name = "RECEIVER_ZIPCODE")
    private String receiverzipcode;

    @Column(name = "ITEM_NAME")
    private String itemname;

    @Column(name = "RECIPIENT")
    private String recipent;

    @Column(name = "ESTMATE")
    private String estmate;

    @Column(name = "INTF_STAT_CODE")
    private String intfstatcode;

    @Column(name = "INTF_TIME")
    private String intftime;

    @Column(name = "CALLBACK_URL")
    private String callbackurl;

    @Column(name = "product_info")
    private String productinfo;

    @Column(name = "USERKEY")
    private String userkey;

    @Column(name = "FID")
    private String fid;

    @Column(name = "MOD_DATE")
    private String moddate;

    public Invoice() {
    }

    public Invoice(String comcode, String invoice, String level, String detailcount, String destkindid, String laststate, String regdate, String tiercode, String sendername, String receivername, String receiveraddr, String receiverzipcode, String itemname, String recipent, String estmate, String intfstatcode, String intftime, String callbackurl, String productinfo, String userkey, String fid, String moddate) {
        this.comcode = comcode;
        this.invoice = invoice;
        this.level = level;
        this.detailcount = detailcount;
        this.destkindid = destkindid;
        this.laststate = laststate;
        this.regdate = regdate;
        this.tiercode = tiercode;
        this.sendername = sendername;
        this.receivername = receivername;
        this.receiveraddr = receiveraddr;
        this.receiverzipcode = receiverzipcode;
        this.itemname = itemname;
        this.recipent = recipent;
        this.estmate = estmate;
        this.intfstatcode = intfstatcode;
        this.intftime = intftime;
        this.callbackurl = callbackurl;
        this.productinfo = productinfo;
        this.userkey = userkey;
        this.fid = fid;
        this.moddate = moddate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoce) {
        this.invoice = invoice;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDetailcount() {
        return detailcount;
    }

    public void setDetailcount(String detailcount) {
        this.detailcount = detailcount;
    }

    public String getDestkindid() {
        return destkindid;
    }

    public void setDestkindid(String destkindid) {
        this.destkindid = destkindid;
    }

    public String getLaststate() {
        return laststate;
    }

    public void setLaststate(String laststate) {
        this.laststate = laststate;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getTiercode() {
        return tiercode;
    }

    public void setTiercode(String tiercode) {
        this.tiercode = tiercode;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getReceiveraddr() {
        return receiveraddr;
    }

    public void setReceiveraddr(String receiveraddr) {
        this.receiveraddr = receiveraddr;
    }

    public String getReceiverzipcode() {
        return receiverzipcode;
    }

    public void setReceiverzipcode(String receiverzipcode) {
        this.receiverzipcode = receiverzipcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getRecipent() {
        return recipent;
    }

    public void setRecipent(String recipent) {
        this.recipent = recipent;
    }

    public String getEstmate() {
        return estmate;
    }

    public void setEstmate(String estmate) {
        this.estmate = estmate;
    }

    public String getIntfstatcode() {
        return intfstatcode;
    }

    public void setIntfstatcode(String intfstatcode) {
        this.intfstatcode = intfstatcode;
    }

    public String getIntftime() {
        return intftime;
    }

    public void setIntftime(String intftime) {
        this.intftime = intftime;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getModdate() {
        return moddate;
    }

    public void setModdate(String moddate) {
        this.moddate = moddate;
    }
}
