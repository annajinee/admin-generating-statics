package com.example.model;

import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 * 미배송건수 통계
 */

@Entity
@Table(name = "INVOICE_NUMBER")

public class InvoiceNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id; // key값 자동증가, int(number(11)

    @Column(name = "TIER_CODE")
    private String tiercode;

    @Column(name = "REG_DATE")
    private String regdate;

    @Column(name = "COM_CODE")
    private String comcode; // 배송사

    public InvoiceNumber() {
    }

    public InvoiceNumber(String tiercode, String regdate, String comcode) {
        this.tiercode = tiercode;
        this.regdate = regdate;
        this.comcode = comcode;
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

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }
}
