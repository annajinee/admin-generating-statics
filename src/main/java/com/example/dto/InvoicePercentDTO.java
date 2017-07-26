package com.example.dto;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by annakim on 2017. 3. 5..
 */
@Entity
public class InvoicePercentDTO {

    @Id
    private int id;

    private String invoice; // 중복되지 않은 송장 유효갯수

    private String com_code;

    private String tier_code;

    private String reg_date;

    public InvoicePercentDTO() {
    }

    public InvoicePercentDTO(int id, String invoice, String com_code, String tier_code, String reg_date) {
        this.id = id;
        this.invoice = invoice;
        this.com_code = com_code;
        this.tier_code = tier_code;
        this.reg_date = reg_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCom_code() {
        return com_code;
    }

    public void setCom_code(String com_code) {
        this.com_code = com_code;
    }

    public String getTier_code() {
        return tier_code;
    }

    public void setTier_code(String tier_code) {
        this.tier_code = tier_code;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
}
