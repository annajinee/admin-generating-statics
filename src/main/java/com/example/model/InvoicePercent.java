package com.example.model;

import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 */

@Entity
@Table(name = "INVOICE_PERCENT")

public class InvoicePercent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id; // key값 자동증가, int(number(11)

    @Column(name = "TIER_CODE")
    private String tiercode;

    @Column(name = "REG_DATE")
    private String regdate;

    @Column(name = "INVOICE")
    private String invoice; // 총 갯수

    @Column(name = "DELIVERY_PERIOD")
    private String delivery_period; // 배송기간 (1일 / 2일 / 3일 / 4일 이상)

    @Column(name = "COM_CODE")
    private String comcode; // 배송사

    public InvoicePercent() {
    }

    public InvoicePercent(String tiercode, String regdate, String invoice, String delivery_period, String comcode) {
        this.tiercode = tiercode;
        this.regdate = regdate;
        this.invoice = invoice;
        this.delivery_period = delivery_period;
        this.comcode = comcode;
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

    public String getDelivery_period() {
        return delivery_period;
    }

    public void setDelivery_period(String delivery_period) {
        this.delivery_period = delivery_period;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }
}
