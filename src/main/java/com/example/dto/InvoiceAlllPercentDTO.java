package com.example.dto;


import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 */
@Entity
public class InvoiceAlllPercentDTO {

    @Id
    private String tier_code;

    private String invoice; // 중복되지 않은 송장 유효갯수

    private String reg_date;

    public InvoiceAlllPercentDTO() {
    }

    public InvoiceAlllPercentDTO(String tier_code, String invoice, String reg_date) {
        this.tier_code = tier_code;
        this.invoice = invoice;
        this.reg_date = reg_date;
    }

    public String getTier_code() {
        return tier_code;
    }

    public void setTier_code(String tier_code) {
        this.tier_code = tier_code;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
}
