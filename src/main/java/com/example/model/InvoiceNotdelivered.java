package com.example.model;

import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 * 미배송건수 통계
 */

@Entity
@Table(name = "INVOICE_NOTDELIVERED")

public class InvoiceNotdelivered {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id; // key값 자동증가, int(number(11)

    @Column(name = "TIER_CODE")
    private String tiercode;

    @Column(name = "REG_DATE")
    private String regdate;

    @Column(name = "INVOICE")
    private String invoice; // 미배송건수

    @Column(name = "CNT")
    private String cnt; // 중복되지 않은 송장 유효갯수

    @Column(name = "COM_CODE")
    private String comcode; // 배송사

    @Column(name = "DIFF_PERCENT")
    private String diffpercent; // 미배송율 (미배송건수/전체송장건수)

    public InvoiceNotdelivered() {
    }

    public InvoiceNotdelivered(String tiercode, String invoice, String cnt) {
        this.tiercode = tiercode;
        this.invoice = invoice;
        this.cnt = cnt;
    }


    public InvoiceNotdelivered(String tiercode, String regdate, String invoice, String cnt) {
        this.tiercode = tiercode;
        this.regdate = regdate;
        this.invoice = invoice;
        this.cnt = cnt;
    }

    public InvoiceNotdelivered(String tiercode, String regdate, String invoice, String cnt, String comcode) {
        this.tiercode = tiercode;
        this.regdate = regdate;
        this.invoice = invoice;
        this.cnt = cnt;
        this.comcode = comcode;
    }

    public InvoiceNotdelivered(String tiercode, String regdate, String invoice, String cnt, String comcode, String diffpercent) {
        this.tiercode = tiercode;
        this.regdate = regdate;
        this.invoice = invoice;
        this.cnt = cnt;
        this.comcode = comcode;
        this.diffpercent = diffpercent;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTiercode() {
        return tiercode;
    }

    public void setTiercode(String tiercode) {
        this.tiercode = tiercode;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public String getDiffpercent() {
        return diffpercent;
    }

    public void setDiffpercent(String diffpercent) {
        this.diffpercent = diffpercent;
    }
}
